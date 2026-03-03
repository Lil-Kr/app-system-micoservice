package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description:
 */
public interface BloomFilterStreamService {
  /**
   *
   * @param shortCode
   */
  void publishNewShortCode(String shortCode);

  /**
   *
   * @return
   */
  Object getNodeId();

  /**
   *
   */
  void onApplicationReady();

  /**
   *
   */
  void startConsumer();
}
