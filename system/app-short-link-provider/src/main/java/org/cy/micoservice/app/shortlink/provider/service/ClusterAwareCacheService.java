package org.cy.micoservice.app.shortlink.provider.service;

import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 集群感知缓存服务, 使用Redis集群分片和Hash Tag策略优化数据分布
 */
public interface ClusterAwareCacheService {

  /**
   *
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
   * get short link info from cache
   * @param shortCode
   * @return
   */
  ShortUrlMapping getFromCache(String shortCode);

  /**
   * 将短链信息放入集群缓存 (支持Hash Tag)
   * @param shortCode
   * @param shortUrlMapping
   */
  void putToCache(String shortCode, ShortUrlMapping shortUrlMapping);

  /**
   * URL哈希映射缓存
   * @param originUrlHash
   * @param shortCode
   */
  void putUrlHashMapping(String originUrlHash, String shortCode);

  /**
   * 添加到布隆过滤器
   * @param shortCode
   */
  void addToBloomFilter(String shortCode);
}