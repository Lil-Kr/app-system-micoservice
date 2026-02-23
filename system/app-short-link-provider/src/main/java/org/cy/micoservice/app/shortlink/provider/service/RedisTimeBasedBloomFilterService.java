package org.cy.micoservice.app.shortlink.provider.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: Redis时间分片布隆过滤器服务
 */
public interface RedisTimeBasedBloomFilterService {

  /**
   *
   * @param shortCode
   * @return
   */
  boolean mightContain(String shortCode);
}
