package org.cy.micoservice.app.shortlink.api.service.impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Lil-K
 * @Date: 2026/3/6
 * @Description:
 */
@Slf4j
public class LocalBloomFilterTest {

  private static final long EXPECTED_INSERTIONS = 216_000_000L;
  private static final double FALSE_PROBABILITY = 0.01;
  private BloomFilter<String> bloomFilter;

  @BeforeEach
  public void setUp() {
    String sliceKey = "redis_bloom_20260305_06";
    this.bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), EXPECTED_INSERTIONS, FALSE_PROBABILITY);
    // log.info("创建时间片布隆过滤器: {}, 预期容量: {}, 误判率: {}", sliceKey, EXPECTED_INSERTIONS, FALSE_PROBABILITY);
  }

  /**
   *
   */
  @Test
  public void test1() {
    String shortCode = "LGMezBQaoK";
    for (int i = 0; i < 100; i++) {
      bloomFilter.put(shortCode);
    }

    boolean res = bloomFilter.mightContain(shortCode);
    Assertions.assertEquals(true, res);

    res = bloomFilter.mightContain(shortCode + "a");
    Assertions.assertEquals(false, res);
  }

  @Test
  public void test2() {
    LocalDateTime sliceTime = LocalDateTime.parse("20260303_18", DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
    LocalDateTime expireTime = sliceTime.plusHours(48);
    boolean after = LocalDateTime.now().isAfter(expireTime);
    Assertions.assertEquals(true, after);
  }
}