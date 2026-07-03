package org.cy.micoservice.app.shortlink.api.service;

import org.cy.micoservice.app.shortlink.api.pojo.NodeInfo;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 分层布隆过滤器 (支持Stream同步)
 */
public interface TieredBloomFilterService {

  /**
   *
   * @param shortCode
   */
  void put(String shortCode);

  /**
   *
   * @param shortCode
   * @return
   */
  boolean mightContain(String shortCode);

  /**
   * @return
   */
  NodeInfo getNodeInfo();
}