package org.cy.micoservice.app.shortlink.api.service.impl;

import com.alibaba.fastjson2.JSONArray;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.filter.TimeSliceBloomFilter;
import org.cy.micoservice.app.shortlink.api.service.LocalBloomFilterService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: 本地时间片布隆过滤器逻辑统一管理
 */
@Slf4j
@Service
public class LocalBloomFilterServiceImpl implements LocalBloomFilterService {

  private final ConcurrentMap<String, TimeSliceBloomFilter> localTimeSlices = new ConcurrentHashMap<>();
  // 当前时间片
  private volatile String currentLocalTimeSlice;
  // 预热进度控制 (内存计数器与基准头片)
  private volatile String prewarmBaselineHeadKey;
  private volatile boolean prewarmAllDone = false;
  private final AtomicInteger prewarmInitializedCount = new AtomicInteger(0);

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private RedissonClient redissonClient;

  /**
   * 本地布隆过滤器初始化
   */
  @PostConstruct
  public void init() {
    this.currentLocalTimeSlice = this.getCurrentLocalSliceKey();
    this.createLocalTimeSlice(currentLocalTimeSlice);
    this.prewarmBaselineHeadKey = currentLocalTimeSlice;
    // 当前片已初始化
    this.prewarmInitializedCount.set(1);
    this.prewarmAllDone = false;
    log.info("本地时间片初始化完成, 当前片: {}", currentLocalTimeSlice);
  }

  /**
   * 定时清理local的时间分片
   * 每5分钟执行一次
   */
  @Scheduled(fixedDelay = 5 * 60 * 1000)
  public void cleanLocalBloomExpiredSlices() {
    try {
      log.info("开始清理Local布隆过滤器时间分片");
      this.doCleanupLocalSlices();
    } catch (Exception e) {
      log.error("清理Local过期时间片失败: ", e);
    }
  }

  /**
   * 每次仅预热一个缺失的本地时间片 (按 Redis 时间片从新到旧)
   * 当目标范围内全部本地片已初始化, 则本次预热跳过
   */
  @Scheduled(fixedRateString = "${shortlink.bloom.local.prewarm.fixed-rate-ms:300000}")
  public void initLocalSlicesFromRedis() {
    try {
      // 若时间片头部发生变化 (进入新时间片), 重置预热进度
      String expectedHead = this.getCurrentLocalSliceKey();
      if (prewarmBaselineHeadKey == null || ! prewarmBaselineHeadKey.equals(expectedHead)) {
        prewarmBaselineHeadKey = expectedHead;
        prewarmInitializedCount.set(localTimeSlices.containsKey(expectedHead) ? 1 : 0);
        prewarmAllDone = false;
        log.info("预热基准头片更新为: {}, 已初始化计数: {}", expectedHead, prewarmInitializedCount.get());
      }

      // 若已完成, 直接跳过
      if (prewarmAllDone) {
        log.info("预热跳过: 预热已完成, 计数: {}/{}", prewarmInitializedCount.get(), Math.max(properties.getLocalKeepSliceCount(), 1));
        return;
      }

      // 获取已经存在的redis时间分片
      List<String> redisKeys = this.listExistingRedisSliceKeysSorted();
      int target = Math.max(properties.getLocalKeepSliceCount(), 1);
      // 组装目标范围 (从新到旧)
      List<String> targetKeys = new ArrayList<>();
      for (String rk : redisKeys) {
        targetKeys.add(rk);
        if (targetKeys.size() >= target) {
          break;
        }
      }

      // 计算当前已初始化数量, 并找出缺失的第一个本地片
      int currentCount = 0;
      String firstMissingLocalKey = null;
      for (String rk : targetKeys) {
        String lk = this.toLocalSliceKey(rk);
        if (localTimeSlices.containsKey(lk)) {
          currentCount ++;
        } else if (firstMissingLocalKey == null) {
          firstMissingLocalKey = lk;
        }
      }
      prewarmInitializedCount.set(currentCount);

      // 若目标范围已全部存在, 则标记完成并跳过
      if (currentCount >= target || firstMissingLocalKey == null) {
        prewarmAllDone = true;
        log.debug("预热跳过: 目标范围已全部存在, 计数: {}/{}", currentCount, target);
        return;
      }

      // 每次仅初始化一个缺失片
      this.createLocalTimeSlice(firstMissingLocalKey);
      int after = prewarmInitializedCount.incrementAndGet();
      log.info("预热本地时间片: {}, 进度: {}/{}", firstMissingLocalKey, after, target);
      if (after >= target) {
        prewarmAllDone = true;
        log.info("本地时间片预热全部完成: {}/{}", after, target);
      }
    } catch (Exception e) {
      log.error("本地时间片预热失败", e);
    }
  }

  /**
   * 仅添加到本地 (用于接收Stream同步)
   */
  @Override
  public void addLocal(String shortCode) {
    String key = this.currentLocalTimeSlice;
    if (StringUtils.isBlank(key)) {
      key = this.getCurrentLocalSliceKey();
      this.createLocalTimeSlice(key);
      this.currentLocalTimeSlice = key;
    }
    TimeSliceBloomFilter slice = localTimeSlices.get(key);
    if (slice != null) {
      slice.add(shortCode);
    }
    log.debug("本地添加短链到时间片布隆过滤器: {} (片: {})", shortCode, key);
  }

  /**
   * 获取redis时间片布隆过滤器, 用于本地布隆过滤器预热
   * @param dateTime
   * @return
   */
  private String getRedisSliceKey(LocalDateTime dateTime) {
    int timeSliceHours = properties.getTimeSliceHours();
    LocalDateTime sliceTime = dateTime
      .withMinute(0)
      .withSecond(0)
      .withNano(0)
      .withHour((dateTime.getHour() / timeSliceHours) * timeSliceHours);
    return "redis_bloom_" + sliceTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
  }

  /**
   * 检查本地布隆过滤器
   * @param shortCode
   * @return
   */
  @Override
  public boolean mightContain(String shortCode) {
    for (TimeSliceBloomFilter slice : localTimeSlices.values()) {
      if (slice.mightContain(shortCode)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取当前时间片中
   * @return
   */
  @Override
  public String getLocalStats() {
    long totalElements = localTimeSlices.values().stream()
      .mapToLong(TimeSliceBloomFilter::getApproximateElementCount)
      .sum();
    return String.format("本地时间分片统计 - 活跃片数: %d, 当前片: %s, 总元素数: %d, 保留策略: %d天",
      localTimeSlices.size(), currentLocalTimeSlice,
      totalElements, (properties.getTimeSliceHours() * properties.getLocalKeepSliceCount()) / 24);
  }

  /**
   * 清除过期的时间片
   */
  private void doCleanupLocalSlices() {
    List<String> expired = new ArrayList<>();
    for (String sliceKey : localTimeSlices.keySet()) {
      if (this.isLocalSliceExpired(sliceKey)) {
        expired.add(sliceKey);
      }
    }

    if (CollectionUtils.isEmpty(expired)) {
      return;
    }
    log.info("Local布隆过滤器过期时间分片信息: {}", JSONArray.toJSONString(expired));

    for (String key : expired) {
      TimeSliceBloomFilter removed = localTimeSlices.remove(key);
      if (removed != null) {
        log.info("清理本地过期时间片: {}, 估计元素数: {}", key, removed.getApproximateElementCount());
      }
    }
    log.info("本地时间片清理完成，当前活跃片数: {}", localTimeSlices.size());
  }

  /**
   * 获取当前时间点所在的时间分片
   * @return
   */
  private String getCurrentLocalSliceKey() {
    int timeSliceHours = properties.getTimeSliceHours();
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime sliceTime = now
      .withMinute(0)
      .withSecond(0)
      .withNano(0)
      .withHour((now.getHour() / timeSliceHours) * timeSliceHours);
    return sliceTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
  }

  /**
   * 创建 local 布隆过滤器
   * @param sliceKey
   */
  private void createLocalTimeSlice(String sliceKey) {
    localTimeSlices.put(sliceKey, new TimeSliceBloomFilter(sliceKey));
  }

  /**
   * 判断本地布隆过滤器时间片是否过期
   * @param sliceKey
   * @return
   */
  private boolean isLocalSliceExpired(String sliceKey) {
    try {
      LocalDateTime sliceTime = LocalDateTime.parse(sliceKey, DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
      LocalDateTime expireTime = sliceTime.plusHours(properties.getTimeSliceHours() * properties.getLocalKeepSliceCount());
      return LocalDateTime.now().isAfter(expireTime);
    } catch (Exception e) {
      log.warn("解析本地时间片key失败: {}", sliceKey);
      return true;
    }
  }

  /**
   * 获取存在的redis布隆过滤器时间分片, 并按照时间由近到远排序
   * @return
   */
  private List<String> listExistingRedisSliceKeysSorted() {
    // 根据时间窗口探测可能存在的Redis时间片, 并按新到旧排序
    List<String> keys = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    // 探测更宽窗口, 避免遗漏
    int window = Math.max(properties.getLocalKeepSliceCount(), 1) * 4;
    for (int i = 0; i < window; i ++) {
      LocalDateTime t = now.minusHours(i * properties.getTimeSliceHours());
      // 组装当前时间的 redis_bloom_xxx 分片名
      String sliceKey = this.getRedisSliceKey(t);
      try {
        RBloomFilter<String> slice = redissonClient.getBloomFilter(sliceKey);
        if (slice.isExists()) {
          keys.add(sliceKey);
        }
      } catch (Exception e) {
        log.warn("探测Redis时间片失败: {}", sliceKey);
      }
    }
    keys.sort((a, b) -> this.parseRedisSliceTime(b).compareTo(this.parseRedisSliceTime(a)));
    return keys;
  }

  /**
   *
   * @param redisSliceKey
   * @return
   */
  private LocalDateTime parseRedisSliceTime(String redisSliceKey) {
    try {
      String timeStr = this.toLocalSliceKey(redisSliceKey);
      return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
    } catch (Exception e) {
      log.warn("解析Redis时间片失败: {}", redisSliceKey);
      return LocalDateTime.MIN;
    }
  }

  /**
   *
   * @param redisSliceKey
   * @return
   */
  private String toLocalSliceKey(String redisSliceKey) {
    return redisSliceKey.replace("redis_bloom_", "");
  }
}
