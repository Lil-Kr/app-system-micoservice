package org.cy.micoservice.app.shortlink.api.service;

import org.cy.micoservice.app.entity.shortlink.model.ShortUrlMapping;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: 集群感知缓存服务, 使用Redis集群分片和Hash Tag策略优化数据分布
 */
public interface ClusterAwareCacheService {

  /**
   * URL哈希映射缓存
   * @param originUrlHash
   * @param shortCode
   */
  void putUrlHashMapping(String originUrlHash, String shortCode);

  /**
   * get URL hash mapping
   * @param originUrlHash
   * @return
   */
  String getShortCodeByUrlHash(String originUrlHash);

  /**
   * bloom filter check
   * @param shortCode
   * @return
   */
  boolean existsInBloomFilter(String shortCode);

  /**
   * 添加到布隆过滤器
   * @param shortCode
   */
  void addToBloomFilter(String shortCode);

  /**
   * 带Sentinel保护的短链查询 (支持分库分表和Redis集群分片)
   * @param shortCode
   * @return
   */
  ShortUrlMapping getShortUrlWithSentinel(String shortCode);

  /**
   * 刷新缓存
   * @param shortUrlMapping
   * @return
   */
  void refreshCache(ShortUrlMapping shortUrlMapping);

  /**
   * 检查是否为热点数据
   * @param accessCount
   * @return
   */
  boolean isHotData(Long accessCount);

  /**
   *
   * @param shortUrlMapping
   */
  void updateAccessCountAsync(ShortUrlMapping shortUrlMapping);

  /**
   *
   * @param shortCode
   * @param accessCount
   */
  void updateAccessCountInDatabase(String shortCode, Long accessCount);
}