package org.cy.micoservice.app.shortlink.api.config;

import org.cy.micoservice.app.framework.cache.starter.config.RedisKeyBuilder;
import org.springframework.stereotype.Component;

import static org.cy.micoservice.app.common.constants.CommonFormatConstants.COMMENT_FORMAT_COLON_SPLIT;

/**
 * @Author: Lil-K
 * @Date: 2025/12/26
 * @Description:
 */
@Component
public class ShortLinkCacheKeyBuilder extends RedisKeyBuilder {

  private static final String CACHE_PREFIX = "shortlink:";

  /**
   * build cache lock key
   * @param urlHash
   * @return create_url:[urlHash]
   */
  public String buildCacheLockKey(String urlHash) {
    return String.format(COMMENT_FORMAT_COLON_SPLIT, "create_url", urlHash);
  }

  /**
   * build cache lock key
   * @param key
   * @return shortlink:lock:[key]
   */
  public String buildCacheFullLockKey(String key) {
    return CACHE_PREFIX + "lock:" + key;
  }

  /**
   * build hash mapping key
   * @return shortlink:hash:[key]
   */
  public String buildHashMappingKey() {
    return CACHE_PREFIX + "hash:";
  }

  /**
   * build count cache key
   * @return shortlink:count:[key]
   * @return
   */
  public String buildCountCacheKey() {
    return CACHE_PREFIX + "count:";
  }

  /**
   * build url cache key
   * @return shortlink:url:[key]
   * @return
   */
  public String buildUrlCacheKey() {
    return CACHE_PREFIX + "url:";
  }


  public String buildHotUrlCacheKey() {
    return CACHE_PREFIX + "url:hot:";
  }

  /**
   * build hash tag key
   * @return
   */
  public String buildHashTagKey(String prefix, String key) {
    return prefix + "{" + key + "}";
  }
}