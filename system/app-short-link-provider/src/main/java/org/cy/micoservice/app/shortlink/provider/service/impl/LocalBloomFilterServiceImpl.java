package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.provider.config.ShortLinkProviderProperties;
import org.cy.micoservice.app.shortlink.provider.filter.TimeSliceBloomFilter;
import org.cy.micoservice.app.shortlink.provider.service.LocalBloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Slf4j
@Service
public class LocalBloomFilterServiceImpl implements LocalBloomFilterService {

  private volatile String currentLocalTimeSlice;
  /**
   * 预热进度控制 (内存计数器与基准头片)
   */
  private volatile String prewarmBaselineHeadKey;

  private final AtomicInteger prewarmInitializedCount = new AtomicInteger(0);

  private ConcurrentHashMap<String, TimeSliceBloomFilter> localTimeSlices = new ConcurrentHashMap<>();

  private volatile boolean prewarmAllDone = false;

  @Autowired
  private ShortLinkProviderProperties properties;

  // @PostConstruct
  // public void init() {
  //   currentLocalTimeSlice = this.getCurrentLocalSliceKey();
  //   createLocalTimeSlice(currentLocalTimeSlice);
  //   prewarmBaselineHeadKey = currentLocalTimeSlice;
  //   prewarmInitializedCount.set(1); // 当前片已初始化
  //   prewarmAllDone = false;
  //   log.info("本地时间片初始化完成, 当前片: {}", currentLocalTimeSlice);
  // }

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
  private String getCurrentLocalSliceKey() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime sliceTime = now.withMinute(0)
      .withSecond(0)
      .withNano(0)
      .withHour((now.getHour() / properties.getTimeSliceHours()) * properties.getTimeSliceHours());
    return sliceTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
  }

  /**
   *
   * @param sliceKey
   */
  private void createLocalTimeSlice(String sliceKey) {
    localTimeSlices.put(sliceKey, new TimeSliceBloomFilter(sliceKey));
  }
}
