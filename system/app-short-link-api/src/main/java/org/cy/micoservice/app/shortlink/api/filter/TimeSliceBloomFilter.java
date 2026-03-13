package org.cy.micoservice.app.shortlink.api.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;
import static org.cy.micoservice.app.shortlink.api.constants.ShortUrlConstant.EXPECTED_INSERTIONS;
import static org.cy.micoservice.app.shortlink.api.constants.ShortUrlConstant.FALSE_PROBABILITY;

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

  public TimeSliceBloomFilter(String sliceKey) {
    this.sliceKey = sliceKey;
    this.createTime = LocalDateTime.now();
    this.bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), EXPECTED_INSERTIONS, FALSE_PROBABILITY);
    log.info("创建Local布隆过滤器时间片: {}, 预期容量: {}, 误判率: {}", sliceKey, EXPECTED_INSERTIONS, FALSE_PROBABILITY);
  }

  /**
   * 判断本地布隆过滤器是否存在当前 short code
   * @param shortCode
   * @return
   */
  public boolean mightContain(String shortCode) {
    return this.bloomFilter.mightContain(shortCode);
  }

  /**
   * 添加 short code 到本地布隆过滤器
   * @param shortCode
   */
  public void add(String shortCode) {
    this.bloomFilter.put(shortCode);
    this.elementCount.incrementAndGet();
  }

  /**
   * 获取布隆过滤器中的元素个数
   * @return
   */
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