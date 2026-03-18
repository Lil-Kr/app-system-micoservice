package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description:
 */
public interface LocalBloomFilterService {

  /**
   *
   * @param shortCode
   */
  void addLocal(String shortCode);

  /**
   *
   * @param shortCode
   * @return
   */
  boolean mightContain(String shortCode);

  /**
   *
   * @return
   */
  String getLocalStats();

  void warmupLocalBloomFilters(String sliceKey);
}
