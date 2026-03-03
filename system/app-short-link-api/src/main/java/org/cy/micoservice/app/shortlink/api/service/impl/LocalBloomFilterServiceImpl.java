package org.cy.micoservice.app.shortlink.api.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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
 * @Description:
 */
@Slf4j
@Service
public class LocalBloomFilterServiceImpl implements LocalBloomFilterService {

  private final ConcurrentMap<String, TimeSliceBloomFilter> localTimeSlices = new ConcurrentHashMap<>();
  private volatile String currentLocalTimeSlice;
  // 预热进度控制 (内存计数器与基准头片)
  private volatile String prewarmBaselineHeadKey;
  private final AtomicInteger prewarmInitializedCount = new AtomicInteger(0);
  private volatile boolean prewarmAllDone = false;

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private RedissonClient redissonClient;

  /**
   * 本地
   */
  @PostConstruct
  public void init() {
    currentLocalTimeSlice = this.getCurrentLocalSliceKey();
    this.createLocalTimeSlice(currentLocalTimeSlice);
    prewarmBaselineHeadKey = currentLocalTimeSlice;
    // 当前片已初始化
    prewarmInitializedCount.set(1);
    prewarmAllDone = false;
    log.info("本地时间片初始化完成，当前片: {}", currentLocalTimeSlice);
  }

  /**
   * 每次仅预热一个缺失的本地时间片 (按 Redis 时间片从新到旧)
   * 当目标范围内全部本地片已初始化, 则本次预热跳过
   */
  @Scheduled(fixedRateString = "${shortlink.bloom.local.prewarm.fixed-rate-ms:300000}")
  public void initLocalSlicesFromRedis() {
    try {
      // 若时间片头部发生变化 (进入新时间片), 重置预热进度
      String expectedHead = getCurrentLocalSliceKey();
      if (prewarmBaselineHeadKey == null || ! prewarmBaselineHeadKey.equals(expectedHead)) {
        prewarmBaselineHeadKey = expectedHead;
        prewarmInitializedCount.set(localTimeSlices.containsKey(expectedHead) ? 1 : 0);
        prewarmAllDone = false;
        log.debug("预热基准头片更新为: {}，已初始化计数: {}", expectedHead, prewarmInitializedCount.get());
      }

      // 若已完成, 直接跳过
      if (prewarmAllDone) {
        log.debug("预热跳过: 预热已完成，计数: {}/{}", prewarmInitializedCount.get(), Math.max(properties.getLocalKeepSliceCount(), 1));
        return;
      }

      List<String> redisKeys = this.listExistingRedisSliceKeysSorted();
      int target = Math.max(properties.getLocalKeepSliceCount(), 1);
      // 组装目标范围（最新到旧）
      List<String> targetKeys = new java.util.ArrayList<>();
      for (String rk : redisKeys) {
        targetKeys.add(rk);
        if (targetKeys.size() >= target) {
          break;
        }
      }

      // 计算当前已初始化数量，并找出缺失的第一个本地片
      int currentCount = 0;
      String firstMissingLocalKey = null;
      for (String rk : targetKeys) {
        String lk = toLocalSliceKey(rk);
        if (localTimeSlices.containsKey(lk)) {
          currentCount++;
        } else if (firstMissingLocalKey == null) {
          firstMissingLocalKey = lk;
        }
      }
      prewarmInitializedCount.set(currentCount);

      // 若目标范围已全部存在，则标记完成并跳过
      if (currentCount >= target || firstMissingLocalKey == null) {
        prewarmAllDone = true;
        log.debug("预热跳过：目标范围已全部存在，计数: {}/{}", currentCount, target);
        return;
      }

      // 每次仅初始化一个缺失片
      createLocalTimeSlice(firstMissingLocalKey);
      int after = prewarmInitializedCount.incrementAndGet();
      log.info("预热本地时间片: {}，进度: {}/{}", firstMissingLocalKey, after, target);
      if (after >= target) {
        prewarmAllDone = true;
        log.info("本地时间片预热全部完成: {}/{}", after, target);
      }
    } catch (Exception e) {
      log.error("本地时间片预热失败", e);
    }
  }

  /**
   *
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
   * 定时清理local的时间分片
   * 每5分钟监控一次
   */
  @Scheduled(fixedRate = 300000)
  public void cleanupLocalSlices() {
    try {
      this.doCleanupLocalSlices();
    } catch (Exception e) {
      log.error("清理本地过期时间片失败: ", e);
    }
  }

  /**
   *
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
   *
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
   * 仅添加到本地 (用于接收Stream同步)
   */
  @Override
  public void addLocal(String shortCode) {
    String key = currentLocalTimeSlice;
    if (key == null) {
      key = this.getCurrentLocalSliceKey();
      this.createLocalTimeSlice(key);
      currentLocalTimeSlice = key;
    }
    TimeSliceBloomFilter slice = localTimeSlices.get(key);
    if (slice != null) {
      slice.add(shortCode);
    }
    log.debug("本地添加短链到时间片布隆过滤器: {} (片: {})", shortCode, key);
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

    for (String key : expired) {
      TimeSliceBloomFilter removed = localTimeSlices.remove(key);
      if (removed != null) {
        log.info("清理本地过期时间片: {}, 估计元素数: {}", key, removed.getApproximateElementCount());
      }
    }
    log.info("本地时间片清理完成，当前活跃片数: {}", localTimeSlices.size());
  }

  /**
   *
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
   *
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
   *
   * @return
   */
  private List<String> listExistingRedisSliceKeysSorted() {
    // 根据时间窗口探测可能存在的Redis时间片，并按新到旧排序
    List<String> keys = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    int window = Math.max(properties.getLocalKeepSliceCount(), 1) * 4; // 探测更宽窗口，避免遗漏
    for (int i = 0; i < window; i++) {
      LocalDateTime t = now.minusHours(i * properties.getTimeSliceHours());
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
