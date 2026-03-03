package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description:
 */
public interface RedisTimeBasedBloomFilterService {

  boolean mightContain(String shortCode);

  /**
   *
   * @param shortCode
   */
  void add(String shortCode);

  /**
   * 获取当前节点ID
   * 格式: hostname-pid-timestamp
   * @return
   */
  String getNodeId();

  /**
   *
   * @return
   */
  String getRedisStats();

  /**
   *
   * @return
   */
  String getLocalStats();

  /**
   *
   * @return
   */
  String getStats();
}
