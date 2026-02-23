package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.provider.config.ShortLinkProviderProperties;
import org.cy.micoservice.app.shortlink.provider.service.LocalBloomFilterService;
import org.cy.micoservice.app.shortlink.provider.service.RedisTimeBasedBloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: Redis时间分片布隆过滤器服务
 */
@Slf4j
@Service
public class RedisTimeBasedBloomFilterServiceImpl implements RedisTimeBasedBloomFilterService {

  // private final ConcurrentHashMap<String, RBloomFilter<String>> redisTimeSlices = new ConcurrentHashMap<>();

  private volatile String currentTimeSlice;

  // 每个时间片配置
  private static final long EXPECTED_INSERTIONS = 216_000_000L; // 6小时预期容量
  private static final double FALSE_PROBABILITY = 0.01;
  // 节点ID缓存
  private volatile String nodeId;

  @Autowired
  private ShortLinkProviderProperties properties;
  @Autowired
  private LocalBloomFilterService localBloomFilterService;
  // @Autowired
  // private RedissonClient redissonClient;

  // @PostConstruct
  // public void init() {
  //   // 初始化Redis当前时间片
  //   currentTimeSlice = getCurrentTimeSliceKey();
  //   // 初始化所有活跃的Redis时间分片
  //   initializeActiveTimeSlices();
  //
  //   log.info("时间分片布隆过滤器初始化完成 - Redis当前片: {}, Redis活跃片数: {}",
  //     currentTimeSlice, redisTimeSlices.size());
  // }

  @Override
  public boolean mightContain(String shortCode) {
    /**
     * 检查短码是否可能存在
     * 输入: 短码字符串
     * 输出: 如果本地或Redis任一时间片包含该短码, 返回true, 否则false
     */
    // 第一层: 本地时间分片, 零延迟, 命中概率更高 (委托本地服务)
    if (localBloomFilterService.mightContain(shortCode)) {
      return true;
    }

    // 第二层: Redis时间分片, 兜底 (跨节点共享)
    // for (RBloomFilter<String> slice : redisTimeSlices.values()) {
    //   try {
    //     if (slice.contains(shortCode)) {
    //       return true;
    //     }
    //   } catch (Exception e) {
    //     log.warn("检查Redis时间分片失败: {}", e.getMessage());
    //   }
    // }
    return false;
  }

  /**
   *
   * @return
   */
  private String getCurrentTimeSliceKey() {
    return getTimeSliceKey(LocalDateTime.now());
  }

  /**
   * 根据指定时间生成时间分片key
   */
  private String getTimeSliceKey(LocalDateTime dateTime) {
    LocalDateTime sliceTime = dateTime.withMinute(0).withSecond(0).withNano(0)
      .withHour((dateTime.getHour() / properties.getTimeSliceHours()) * properties.getTimeSliceHours());
    return "redis_bloom_" + sliceTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
  }

  /**
   * 初始化所有活跃的时间分片
   * 根据配置的保留时间片数量, 加载所有可能存在的时间分片
   */
  private void initializeActiveTimeSlices() {
    LocalDateTime now = LocalDateTime.now();

    // 计算需要加载的时间片范围
    for (int i = 0; i < properties.getRedisKeepSliceCount(); i++) {
      LocalDateTime sliceTime = now.minusHours(i * properties.getTimeSliceHours());
      String sliceKey = getTimeSliceKey(sliceTime);

      // try {
      //   // 检查Redis中是否存在该时间片
      //   RBloomFilter<String> slice = redissonClient.getBloomFilter(sliceKey);
      //   if (slice.isExists()) {
      //     redisTimeSlices.put(sliceKey, slice);
      //     log.debug("加载已存在的时间分片: {}", sliceKey);
      //   } else if (sliceKey.equals(currentTimeSlice)) {
      //     // 如果是当前时间片但不存在, 则创建它
      //     createRedisTimeSlice(sliceKey);
      //   }
      // } catch (Exception e) {
      //   log.warn("初始化时间分片失败: {}, 错误: {}", sliceKey, e.getMessage());
      // }
    }

    // log.info("活跃时间分片初始化完成, 加载了 {} 个时间分片", redisTimeSlices.size());
  }

  /**
   *
   * @param sliceKey
   */
  private void createRedisTimeSlice(String sliceKey) {
    // try {
    //   RBloomFilter<String> slice = redissonClient.getBloomFilter(sliceKey);
    //   if (!slice.isExists()) {
    //     slice.tryInit(EXPECTED_INSERTIONS, FALSE_PROBABILITY);
    //   }
    //   redisTimeSlices.put(sliceKey, slice);
    //   log.info("创建Redis时间分片: {}", sliceKey);
    // } catch (Exception e) {
    //   log.error("创建Redis时间分片失败: {}", sliceKey, e);
    // }
  }
}