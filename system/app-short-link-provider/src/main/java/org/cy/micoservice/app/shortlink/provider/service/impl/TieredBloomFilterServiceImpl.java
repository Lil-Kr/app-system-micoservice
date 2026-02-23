package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.provider.service.RedisTimeBasedBloomFilterService;
import org.cy.micoservice.app.shortlink.provider.service.TieredBloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Slf4j
@Service
public class TieredBloomFilterServiceImpl implements TieredBloomFilterService {

  @Autowired
  private RedisTimeBasedBloomFilterService redisTimeBasedBloomFilter;

  @Override
  public boolean mightContain(String shortCode) {
    // 统一委托给RedisTimeBasedBloomFilterService（内部已包含本地+Redis两层检查）
    return redisTimeBasedBloomFilter.mightContain(shortCode);
  }
}