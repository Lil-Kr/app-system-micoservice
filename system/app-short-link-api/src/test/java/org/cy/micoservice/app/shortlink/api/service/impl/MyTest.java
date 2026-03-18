package org.cy.micoservice.app.shortlink.api.service.impl;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.cy.micoservice.app.shortlink.api.constants.ShortUrlConstant.REDIS_BLOOM_FILTER_PREFIX_KEY;

/**
 * @Author: Lil-K
 * @Date: 2026/3/13
 * @Description:
 */
public class MyTest {

  private static long id = 647643272529969152L;

  public static String getRedisSliceKey(LocalDateTime dateTime) {
    int timeSliceHours = 6;
    LocalDateTime sliceTime = dateTime
      .withMinute(0)
      .withSecond(0)
      .withNano(0)
      .withHour((dateTime.getHour() / timeSliceHours) * timeSliceHours);
    return REDIS_BLOOM_FILTER_PREFIX_KEY + sliceTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HH"));
  }

  @Test
  public void test1() {
    String shortcode = "lkwAIfB71k";
    System.out.println(shortcode.length());

    int maxValue = 10;
    System.out.println(Math.abs(id) % maxValue);
  }

  @Test
  public void test2() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime t = now.minusHours(6);
    String redisSliceKey = getRedisSliceKey(t);
    System.out.println(redisSliceKey);
  }
}
