package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description:
 */
public interface LocalBloomFilterService {

  boolean mightContain(String shortCode);

  /**
   *
   * @return
   */
  String getLocalStats();

  /**
   *
   * @param shortCode
   */
  void addLocal(String shortCode);
}
