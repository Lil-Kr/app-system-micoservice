package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: 本地缓存服务
 */
public interface LocalCacheService {

  /**
   * 从本地缓存获取短链信息 - 使用自定义Key生成器
   * @param shortCode
   * @return
   */
  // ShortUrlMapping getFromLocalCache(String shortCode);

  /**
   * 缓存短链信息到本地缓存 - 使用自定义Key生成器
   * @param shortCode
   * @param shortUrlMapping
   * @return
   */
  // ShortUrlMapping putToLocalCache(String shortCode, ShortUrlMapping shortUrlMapping);

  /**
   * 安全的缓存操作 - 带完整异常处理
   * @param shortCode
   * @param shortUrlMapping
   */
  // ShortUrlMapping safePutToLocalCache(String shortCode, ShortUrlMapping shortUrlMapping);

  /**
   * 安全的缓存移除操作 - 带完整异常处理
   * @param shortCode
   */
  // void safeEvictFromLocalCache(String shortCode);

  /**
   * 从本地缓存中移除指定的短链
   * @param shortCode
   */
  // void evictFromLocalCache(String shortCode);
}