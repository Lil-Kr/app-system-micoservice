package org.cy.micoservice.app.shortlink.provider.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/22
 * @Description:
 */
public interface ShortCodeService {

  String generateByStrategy(String url);

  String generateUniqueCode();

  String generateByUrlHashDeterministic(String url);

  String generateByUrlHashWithRandomness(String url);

  String generateRandom();

  String[] generateBatchUniqueCodes(int count);
}