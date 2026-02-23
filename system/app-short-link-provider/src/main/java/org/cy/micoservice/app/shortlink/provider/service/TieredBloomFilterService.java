package org.cy.micoservice.app.shortlink.provider.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
public interface TieredBloomFilterService {

  /**
   *
   * @param shortCode
   * @return
   */
  boolean mightContain(String shortCode);
}