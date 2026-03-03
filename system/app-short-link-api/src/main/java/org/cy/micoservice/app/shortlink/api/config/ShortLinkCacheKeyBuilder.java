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
  private static final String URL_CACHE_KEY = CACHE_PREFIX + "url:";
  private static final String COUNT_CACHE_KEY = CACHE_PREFIX + "count:";
  private static final String HASH_MAPPING_KEY = CACHE_PREFIX + "hash:";
  private static final String LOCK_PREFIX_KEY = CACHE_PREFIX + "lock:";

  /**
   * build cache lock key
   * @param urlHash
   * @return
   */
  public String buildCacheLockKey(String urlHash) {
    return String.format(COMMENT_FORMAT_COLON_SPLIT, "create_url", urlHash);
  }

  /**
   * build cache lock key
   * @param key
   * @return
   */
  public String buildCacheFullLockKey(String key) {
    return LOCK_PREFIX_KEY + key;
  }
}