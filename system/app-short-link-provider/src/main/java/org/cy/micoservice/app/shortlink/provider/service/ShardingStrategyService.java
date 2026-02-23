package org.cy.micoservice.app.shortlink.provider.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
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