package org.cy.micoservice.app.shortlink.api.config;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.shortlink.api.factory.JetCacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: Lil-K
 * @Date: 2026/3/5
 * @Description: build Cache Manage
 */
@Configuration
public class JetCacheConfig {

  public static final Duration TEST_EXPIRE_TIME = Duration.ofMillis(5000);
  public static final Duration LOCAL_EXPIRE_TIME = Duration.ofMinutes(30);
  public static final Duration DEFAULT_EXPIRE_TIME = Duration.ofHours(1);
  public static final Duration HOT_DATA_EXPIRE_TIME = Duration.ofHours(24);
  public static final Duration COUNT_EXPIRE_TIME = Duration.ofDays(7);

  @Autowired
  private ShortLinkCacheKeyBuilder cacheKeyBuilder;

  /**
   * short url cache
   * @param factory
   * @return
   */
  @Bean
  public Cache<String, ShortUrlMapping> shortUrlCache(JetCacheFactory factory) {
    return factory.createJetCache(
      cacheKeyBuilder.buildUrlCacheKey(),
      LOCAL_EXPIRE_TIME.plusMinutes(ThreadLocalRandom.current().nextInt(10)),
      DEFAULT_EXPIRE_TIME.plusMinutes(ThreadLocalRandom.current().nextInt(20)),
      CacheType.BOTH,
      10000,
      true,
      true,
      true);
  }

  /**
   * short url hot cache
   * @param factory
   * @return
   */
  @Bean
  public Cache<String, ShortUrlMapping> shortUrlHotCache(JetCacheFactory factory) {
    return factory.createJetCache(
      cacheKeyBuilder.buildHotUrlCacheKey(),
      LOCAL_EXPIRE_TIME.plusMinutes(ThreadLocalRandom.current().nextInt(30)),
      HOT_DATA_EXPIRE_TIME,
      CacheType.BOTH,
      10000,
      true,
      true,
      true);
  }
}