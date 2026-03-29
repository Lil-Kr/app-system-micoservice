package org.cy.micoservice.app.shortlink.api.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alicp.jetcache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkCacheKeyBuilder;
import org.cy.micoservice.app.shortlink.api.service.ClusterAwareCacheService;
import org.cy.micoservice.app.shortlink.api.service.ShardingStrategyService;
import org.cy.micoservice.app.shortlink.api.service.TieredBloomFilterService;
import org.cy.micoservice.app.shortlink.api.utils.CalculateIndexUtil;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
import org.cy.micoservice.app.shortlink.facade.interfaces.ShortUrlFacade;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static org.cy.micoservice.app.shortlink.api.config.JetCacheConfig.COUNT_EXPIRE_TIME;
import static org.cy.micoservice.app.shortlink.api.config.JetCacheConfig.DEFAULT_EXPIRE_TIME;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: 集群感知缓存服务, 使用Redis集群分片和 [Hash Tag] 策略优化数据分布
 */
@Slf4j
@Service
public class ClusterAwareCacheServiceImpl implements ClusterAwareCacheService {

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private ShortLinkCacheKeyBuilder cacheKeyBuilder;
  @Autowired
  private RedissonClient redissonClient;
  @Autowired
  private Cache<String, ShortUrlMapping> shortUrlCache;
  @Autowired
  private Cache<String, ShortUrlMapping> shortUrlHotCache;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  @Autowired
  private ShardingStrategyService shardingStrategyService;
  @Autowired
  private TieredBloomFilterService tieredBloomFilterService;
  @Autowired
  private CalculateIndexUtil calculateIndexUtil;
  @DubboReference(check = false)
  private ShortUrlFacade shortUrlFacade;

  /**
   * URL哈希映射缓存
   * @param originUrlHash
   * @param shortCode
   */
  @Override
  public void putUrlHashMapping(String originUrlHash, String shortCode) {
    try {
      String key = this.generateHashTagKey(cacheKeyBuilder.buildHashMappingKey(), originUrlHash);
      RBucket<String> bucket = redissonClient.getBucket(key);
      bucket.set(shortCode, DEFAULT_EXPIRE_TIME);
      log.debug("缓存URL哈希映射: hash={}, shortCode={}, 分片槽位: {}", originUrlHash, shortCode, shardingStrategyService.calculateSlot(key));
    } catch (Exception e) {
      log.error("缓存URL哈希映射失败: hash={}, error={}", originUrlHash, e.getMessage());
    }
  }

  /**
   * 获取URL哈希映射
   * @param originUrlHash
   * @return shortCode
   */
  @Override
  public String getShortCodeByUrlHash(String originUrlHash) {
    try {
      String key = this.generateHashTagKey(cacheKeyBuilder.buildHashMappingKey(), originUrlHash);
      log.info("getShortCodeByUrlHash key={}", key);
      RBucket<String> bucket = redissonClient.getBucket(key);
      return bucket.get();
    } catch (Exception e) {
      log.error("获取URL哈希映射失败: hash={}, error={}", originUrlHash, e.getMessage());
    }
    return null;
  }

  /**
   * 第一层 + 第二层: 布隆过滤器检查
   * @param shortCode
   * @return
   */
  @Override
  public boolean existsInBloomFilter(String shortCode) {
    return tieredBloomFilterService.mightContain(shortCode);
  }

  /**
   * 添加到布隆过滤器
   * @param shortCode
   */
  @Override
  public void addToBloomFilter(String shortCode) {
    tieredBloomFilterService.put(shortCode);
  }

  /**
   * 带Sentinel保护的短链查询 (支持分库分表和Redis集群分片)
   * @param shortCode
   * @return
   */
  @SentinelResource(
    value = "databaseQuery",
    blockHandler = "databaseQueryBlockHandler",
    fallback = "databaseQueryFallback"
  )
  @Override
  public ShortUrlMapping getShortUrlWithSentinel(String shortCode) {
    // 先查询 hot key
    ShortUrlMapping shortUrlMapping = shortUrlHotCache.get(shortCode);
    if (shortUrlMapping != null) {
      return shortUrlMapping;
    }

    return shortUrlCache.computeIfAbsent(shortCode, key -> {
      RpcResponse<CreateShortUrlRespDTO> response = shortUrlFacade.findByShortCode(shortCode);
      if (response.getData() == null) {
        return null;
      }
      ShortUrlMapping mapping = BeanCopyUtils.convert(response.getData(), ShortUrlMapping.class);
      log.debug("DB加载成功 shortCode={}", shortCode);

      return mapping;
    });
  }

  /**
   * 刷新缓存
   * @param shortUrlMapping
   * @return
   */
  @Override
  public void refreshCache(ShortUrlMapping shortUrlMapping) {
    shortUrlCache.put(shortUrlMapping.getShortCode(), shortUrlMapping);
  }

  /**
   * 判断是否为热点数据
   */
  @Override
  public boolean isHotData(Long accessCount) {
    return accessCount >= 1000;
  }

  /**
   * async update visit count
   * @param shortUrlMapping
   */
  @SentinelResource(value = "updateAccessCount")
  @Async
  @Override
  public void updateAccessCountAsync(ShortUrlMapping shortUrlMapping) {
    String shortCode = shortUrlMapping.getShortCode();
    try {
      // 先尝试从Redis集群增加计数
      Long count = this.incrementAccessCount(shortCode);

      // 热点key做TTL
      if (this.isHotData(count)) {
        shortUrlHotCache.put(shortCode, shortUrlMapping, 24, TimeUnit.HOURS);
      }

      // 异步更新数据库 (可以考虑批量更新)
      if (count != null && count % 100 == 0) {
        // 每100次访问同步一次数据库 (ShardingSphere会自动路由)
        this.updateAccessCountInDatabase(shortCode, count);
      }
    } catch (Exception e) {
      // 访问计数失败不影响主流程
      log.warn("更新访问次数失败: shortCode={}, error={}", shortCode, e.getMessage());
    }
  }

  /**
   * 数据库访问次数更新 (支持分库分表)
   */
  @Override
  public void updateAccessCountInDatabase(String shortCode, Long accessCount) {
    try {
      RpcResponse<Integer> updatedResp = shortUrlFacade.updateAccessCount(shortCode, accessCount);
      if (updatedResp.getData() > 0) {
        log.debug("访问次数更新成功: shortCode={}, accessCount={}, 数据库分片: db={}, table={}",
          shortCode,
          accessCount,
          calculateIndexUtil.calculateDatabaseIndex(shortCode),
          calculateIndexUtil.calculateTableIndex(shortCode));
      } else {
        log.warn("访问次数更新失败，记录不存在: shortCode={}", shortCode);
      }
    } catch (Exception e) {
      log.error("数据库访问次数更新失败: shortCode={}, accessCount={}, error={}",
        shortCode, accessCount, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 生成 [Hash Tag] 键 (确保相关数据在同一分片)
   * @param prefix
   * @param shortCode
   * @return
   */
  private String generateHashTagKey(String prefix, String shortCode) {
    if (properties.isEnableHashTag()) {
      return cacheKeyBuilder.buildHashTagKey(prefix, shortCode);
    }
    return prefix + shortCode;
  }

  /**
   * 增加访问计数 (集群分片优化)
   * @param shortCode
   * @return
   */
  private Long incrementAccessCount(String shortCode) {
    try {
      String key = this.generateHashTagKey(cacheKeyBuilder.buildCountCacheKey(), shortCode);
      RAtomicLong atomicLong = redissonClient.getAtomicLong(key);

      long count = atomicLong.incrementAndGet();
      atomicLong.expire(COUNT_EXPIRE_TIME);

      log.debug("访问计数增加: {}, 当前计数: {}, 分片槽位: {}", shortCode, count, shardingStrategyService.calculateSlot(key));
      return count;
    } catch (Exception e) {
      log.error("增加访问计数失败: shortCode={}, error={}", shortCode, e.getMessage());
      return null;
    }
  }

  /** ======================== Sentinel 处理方法 ======================== **/
  public ShortUrlMapping databaseQueryBlockHandler(String shortCode, BlockException ex) {
    log.warn("数据库查询被限流: shortCode={}", shortCode);
    return null;
  }

  public ShortUrlMapping databaseQueryFallback(String shortCode, Throwable ex) {
    log.error("数据库查询降级: shortCode={}, error={}", shortCode, ex.getMessage());
    return null;
  }
}
