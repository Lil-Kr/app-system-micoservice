package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: Redis集群槽位计算、分片路由优化
 */
public interface ShardingStrategyService {

  /**
   * 计算Redis集群槽位
   * 使用CRC16算法, 与Redis集群保持一致
   * @param key
   * @return
   */
  int calculateSlot(String key);
}