package org.cy.micoservice.app.shortlink.api.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 单个时间片的布隆过滤器
 */
@Slf4j
public class TimeSliceBloomFilter {

  // 时间片标识
  private final String sliceKey;
  // Google Guava实现
  private final BloomFilter<String> bloomFilter;
  // 创建时间
  private final LocalDateTime createTime;
  // 元素计数器
  private final AtomicLong elementCount = new AtomicLong(0);

  // 每个时间片预期容量 (6小时 * 1万TPS * 3600秒 = 2.16亿)
  private static final long EXPECTED_INSERTIONS = 216_000_000L;
  private static final double FALSE_PROBABILITY = 0.01;

  public TimeSliceBloomFilter(String sliceKey) {
    this.sliceKey = sliceKey;
    this.createTime = LocalDateTime.now();
    this.bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), EXPECTED_INSERTIONS, FALSE_PROBABILITY);
    log.info("创建时间片布隆过滤器: {}, 预期容量: {}, 误判率: {}", sliceKey, EXPECTED_INSERTIONS, FALSE_PROBABILITY);
  }

  /**
   *
   * @param shortCode
   * @return
   */
  public boolean mightContain(String shortCode) {
    return bloomFilter.mightContain(shortCode);
  }

  public void add(String shortCode) {
    bloomFilter.put(shortCode);
    elementCount.incrementAndGet();
  }

  public long getApproximateElementCount() {
    return elementCount.get();
  }

  public String getSliceKey() {
    return sliceKey;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public double getCurrentFalseProbability() {
    return bloomFilter.expectedFpp();
  }
}