package org.cy.micoservice.app.shortlink.api.service.impl;

import com.alibaba.fastjson2.JSONArray;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.service.BloomFilterStreamService;
import org.cy.micoservice.app.shortlink.api.service.LocalBloomFilterService;
import org.cy.micoservice.app.shortlink.api.service.RedisBloomFilterService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.cy.micoservice.app.shortlink.api.constants.ShortUrlConstant.EXPECTED_INSERTIONS;
import static org.cy.micoservice.app.shortlink.api.constants.ShortUrlConstant.FALSE_PROBABILITY;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description:
 */
@Slf4j
@Service
public class RedisBloomFilterServiceImpl implements RedisBloomFilterService {

  // Redis时间分片布隆过滤器映射
  private final ConcurrentMap<String, RBloomFilter<String>> redisTimeSlices = new ConcurrentHashMap<>();
  private volatile String currentTimeSlice;
  // 节点ID缓存
  private volatile String nodeId;

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private RedissonClient redissonClient;
  @Autowired
  private BloomFilterStreamService streamService;
  @Autowired
  private LocalBloomFilterService localBloomFilterService;

  @PostConstruct
  public void init() {
    // 初始化Redis当前时间片
    this.currentTimeSlice = this.getTimeSliceKey(LocalDateTime.now());
    // 初始化所有活跃的Redis时间分片
    this.initializeActiveTimeSlices();
    log.info("时间分片布隆过滤器初始化完成 - Redis当前片: {}, Redis活跃片数: {}", currentTimeSlice, redisTimeSlices.size());
  }

  /**
   * 定时清理过期的Redis时间片
   * 简化的清理方法: 所有节点都执行, 无需分布式锁
   * 每5分钟检查一次
   */
  @Scheduled(fixedDelay = 5 * 60 * 1000)
  public void cleanRedisBloomExpiredSlices() {
    /**
     * 清理过期的Redis时间片 (幂等安全)
     * 输入: 无
     * 输出: 无; 副作用为删除过期Redis分片并释放内存映射
     */
    try {
      log.info("开始清理Redis过期时间片 - 节点: {}", getNodeId());
      this.doCleanupExpiredSlices();
    } catch (Exception e) {
      log.error("清理Redis过期时间片失败 - 节点: {}", getNodeId(), e);
    }
  }

  /**
   * 添加数据到布隆过滤器
   * @param shortCode
   */
  @Override
  public void add(String shortCode) {
    /**
     * 将短码写入当前时间片
     * 输入: 短码字符串
     * 输出: 无 --> 副作用为写入本地与Redis时间分片并发布 Stream 事件
     */
    // 处理Redis时间片 (使用同步块避免并发重复构造)
    String currentRedisSlice = this.getTimeSliceKey(LocalDateTime.now());
    if (! currentRedisSlice.equals(currentTimeSlice)) {
      synchronized (this) {
        if (! currentRedisSlice.equals(currentTimeSlice)) {
          this.createRedisTimeSlice(currentRedisSlice);
          currentTimeSlice = currentRedisSlice;
          log.info("创建新的Redis时间片: {}", currentRedisSlice);
        }
      }
    }

    // 本地添加通过Stream消费执行, 此处不直接写本地
    // 添加到当前Redis时间片
    RBloomFilter<String> redisSlice = redisTimeSlices.get(currentTimeSlice);
    if (redisSlice != null) {
      try {
        redisSlice.add(shortCode);
      } catch (Exception e) {
        log.error("添加到Redis时间分片失败: slice={}, shortCode={}", currentTimeSlice, shortCode, e);
      }
    }

    // 发布到Stream同步其他节点的本地时间片
    try {
      streamService.publishNewShortCode(shortCode);
    } catch (Exception e) {
      log.warn("发布Stream事件失败: {}", e.getMessage());
    }
  }

  /**
   * redis时间分片 布隆过滤器
   * @param shortCode
   * @return
   */
  @Override
  public boolean mightContain(String shortCode) {
    /**
     * 检查短码是否可能存在
     * 输入: 短码字符串
     * 输出: 如果本地或Redis任一时间片包含该短码, 返回true, 否则false
     */
    // 第二层: Redis时间分片, 兜底 (跨节点共享)
    for (RBloomFilter<String> slice : redisTimeSlices.values()) {
      try {
        if (slice.contains(shortCode)) {
          return true;
        }
      } catch (Exception e) {
        log.warn("检查Redis时间分片失败: {}", e.getMessage());
      }
    }
    return false;
  }

  /**
   *
   * @return
   */
  @Override
  public String getNodeId() {
    /**
     * 获取当前节点ID
     * 输入: 无
     * 输出: 节点标识字符串（hostname-pid-timestamp）
     */
    if (nodeId == null) {
      synchronized (this) {
        if (nodeId == null) {
          try {
            String hostname = InetAddress.getLocalHost().getHostName();
            String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
            long timestamp = System.currentTimeMillis() % 100000; // 取后5位避免太长
            nodeId = String.format("%s-%s-%d", hostname, pid, timestamp);
          } catch (Exception e) {
            // 如果获取失败，使用备用方案
            nodeId = "node-" + System.currentTimeMillis() % 10000;
            log.warn("获取节点信息失败，使用备用节点ID: {}", nodeId);
          }
          log.info("节点ID初始化: {}", nodeId);
        }
      }
    }
    return nodeId;
  }

  @Override
  public String getRedisStats() {
    /**
     * 获取Redis时间分片统计信息
     * 输入: 无
     * 输出: 统计字符串（活跃片数/当前片/保留策略）
     */
    return String.format("Redis时间分片统计 - 活跃片数: %d, 当前片: %s, 保留策略: %d天",
      redisTimeSlices.size(), currentTimeSlice,
      (properties.getTimeSliceHours() * properties.getRedisKeepSliceCount()) / 24);
  }

  /**
   * 兼容旧接口: 返回合并统计
   */
  @Override
  public String getStats() {
    return String.format("时间分片统计 - 本地: [%s], Redis: [%s]", localBloomFilterService.getLocalStats(), getRedisStats());
  }

  /**
   * 创建 redis 时间片
   * @param sliceKey
   */
  private void createRedisTimeSlice(String sliceKey) {
    try {
      RBloomFilter<String> slice = redissonClient.getBloomFilter(sliceKey);
      if (!slice.isExists()) {
        slice.tryInit(EXPECTED_INSERTIONS, FALSE_PROBABILITY);
      }
      redisTimeSlices.put(sliceKey, slice);
      log.info("创建Redis时间分片: {}", sliceKey);
    } catch (Exception e) {
      log.error("创建Redis时间分片失败: {}", sliceKey, e);
    }
  }

  /**
   * 初始化所有活跃的时间分片
   * 根据配置的保留时间片数量, 加载所有可能存在的时间分片
   */
  private void initializeActiveTimeSlices() {
    int redisKeepSliceCount = properties.getRedisKeepSliceCount();
    LocalDateTime now = LocalDateTime.now();
    // 计算需要加载的时间片范围
    for (int i = 0; i < redisKeepSliceCount; i ++) {
      LocalDateTime sliceTime = now.minusHours(i * properties.getTimeSliceHours());
      String sliceKey = getTimeSliceKey(sliceTime);

      try {
        // 检查Redis中是否存在该时间片
        RBloomFilter<String> slice = redissonClient.getBloomFilter(sliceKey);
        if (slice.isExists()) {
          redisTimeSlices.put(sliceKey, slice);
          log.debug("加载已存在的时间分片: {}", sliceKey);
        } else if (sliceKey.equals(currentTimeSlice)) {
          // 如果是当前时间片但不存在, 则创建它
          this.createRedisTimeSlice(sliceKey);
        }
      } catch (Exception e) {
        log.warn("初始化时间分片失败: {}, 错误: {}", sliceKey, e.getMessage());
      }
    }
    log.info("活跃时间分片初始化完成, 加载了 {} 个时间分片", redisTimeSlices.size());
  }

  /**
   * 根据指定时间生成时间分片key
   */
  private String getTimeSliceKey(LocalDateTime dateTime) {
    int timeSliceHours = properties.getTimeSliceHours();
    LocalDateTime sliceTime = dateTime
      .withMinute(0)
      .withSecond(0)
      .withNano(0)
      .withHour((dateTime.getHour() / timeSliceHours) * timeSliceHours);
    return "redis_bloom_" + sliceTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
  }

  /**
   * 实际的清理逻辑 (支持多节点并发执行)
   */
  private void doCleanupExpiredSlices() {
    try {
      List<String> expiredSlicesList = new ArrayList<>();

      // 1. 找出过期的时间片
      for (String sliceKey : redisTimeSlices.keySet()) {
        if (this.isSliceExpired(sliceKey)) {
          expiredSlicesList.add(sliceKey);
        }
      }
      log.info("Redis过期时间片信息: {}", JSONArray.toJSONString(expiredSlicesList));
      int memoryCleanedCount = 0;
      int redisCleanedCount = 0;

      // 2. 清理过期时间片
      for (String expiredSlice : expiredSlicesList) {
        // 2.1 清理内存映射 (每个节点清理自己的)
        RBloomFilter<String> removed = redisTimeSlices.remove(expiredSlice);
        if (removed != null) {
          memoryCleanedCount ++;

          try {
            // 2.2 清理Redis数据 (多节点并发执行, 幂等安全)
            if (removed.isExists()) {
              removed.delete();
              redisCleanedCount ++;
              log.info("清理时间片成功: {} - 节点: {}", expiredSlice, getNodeId());
            } else {
              log.debug("时间片已被清理: {} - 节点: {}", expiredSlice, getNodeId());
            }
          } catch (Exception e) {
            log.warn("Redis清理失败: {} - 节点: {}, 错误: {}",
              expiredSlice, getNodeId(), e.getMessage());
            // 注意: 即使Redis删除失败, 内存已经清理, 不会影响应用运行
          }
        }
      }

      if (memoryCleanedCount > 0 || redisCleanedCount > 0) {
        log.info("清理完成 - 节点: {}, 内存清理: {}, Redis清理: {}, 剩余片数: {}",
          getNodeId(), memoryCleanedCount, redisCleanedCount, redisTimeSlices.size());
      }
    } catch (Exception e) {
      log.error("清理过期时间片失败 - 节点: {}", getNodeId(), e);
    }
  }

  /**
   * 判断时间分片是否过期
   * @param sliceKey
   * @return
   */
  private boolean isSliceExpired(String sliceKey) {
    try {
      // 从sliceKey中提取时间片标识符，格式为 "redis_bloom_yyyyMMdd_HH"
      String timeStr = sliceKey.replace("redis_bloom_", "");
      LocalDateTime sliceTime = LocalDateTime.parse(timeStr,
        DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
      // 使用Redis专用的保留时间片数量
      LocalDateTime expireTime = sliceTime.plusHours(properties.getTimeSliceHours() * properties.getRedisKeepSliceCount());
      return LocalDateTime.now().isAfter(expireTime);
    } catch (Exception e) {
      log.warn("解析Redis时间片key失败: {}", sliceKey);
      return true; // 解析失败认为已过期
    }
  }
}