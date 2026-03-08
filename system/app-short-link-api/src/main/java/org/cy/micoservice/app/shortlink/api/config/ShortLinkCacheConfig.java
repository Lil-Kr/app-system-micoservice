package org.cy.micoservice.app.shortlink.api.config;

/**
 * @Author: Lil-K
 * @Date: 2026/3/4
 * @Description: 短链缓存配置常量
 */
public class ShortLinkCacheConfig {
  /**
   * 本地缓存配置 (Caffeine)
   */
  // URL Hash → ShortCode 映射
  public static final int LOCAL_HASH_CACHE_SIZE = 10000;
  // 5分钟
  public static final int LOCAL_HASH_CACHE_EXPIRE_SECONDS = 300;

  // ShortCode → ShortUrlMapping 详情
  public static final int LOCAL_URL_CACHE_SIZE = 5000;
  // 5分钟
  public static final int LOCAL_URL_CACHE_EXPIRE_SECONDS = 300;

  /**
   * Redis 远程缓存配置
   */
  // 普通数据过期时间, 1小时
  public static final int REMOTE_CACHE_EXPIRE_SECONDS = 3600;
  // 热点数据过期时间, 24小时
  public static final int REMOTE_HOT_CACHE_EXPIRE_SECONDS = 86400;
  // URL hash 映射过期时间, 2小时
  public static final int REMOTE_HASH_CACHE_EXPIRE_SECONDS = 7200;
}