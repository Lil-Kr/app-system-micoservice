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
import org.springframework.stereotype.Service;

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
   * 增加访问计数 (集群分片优化)
   * @param shortCode
   * @return
   */
  @Override
  public Long incrementAccessCount(String shortCode) {
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
   * 判断是否为热点数据
   */
  private boolean isHotData(ShortUrlMapping shortUrlMapping) {
    return shortUrlMapping.getAccessCount() != null && shortUrlMapping.getAccessCount() > 1000;
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

  // /**
  //  * 缓存到Redis集群
  //  * @param shortCode
  //  * @param shortUrlMapping
  //  */
  // private void cacheToRedisCluster(String shortCode, ShortUrlMapping shortUrlMapping) {
  //   try {
  //     String key = this.generateHashTagKey(cacheKeyBuilder.buildUrlCacheKey(), shortCode);
  //     String json = JSONObject.toJSONString(shortUrlMapping);
  //
  //     RBucket<String> bucket = redissonClient.getBucket(key);
  //     Duration expireTime = this.isHotData(shortUrlMapping) ? HOT_DATA_EXPIRE_TIME : DEFAULT_EXPIRE_TIME;
  //     bucket.set(json, expireTime);
  //
  //     log.debug("Redis集群缓存成功: {}", shortCode);
  //   } catch (JSONException e) {
  //     log.error("Redis集群序列化失败: shortCode={}, error={}", shortCode, e.getMessage());
  //   } catch (Exception e) {
  //     log.error("Redis集群缓存失败: shortCode={}, error={}", shortCode, e.getMessage());
  //   }
  // }

  // /**
  //  * 从Redis集群获取数据
  //  * @param shortCode
  //  * @return
  //  */
  // private ShortUrlMapping getFromRedisCluster(String shortCode) {
  //   try {
  //     String key = this.generateHashTagKey(cacheKeyBuilder.buildUrlCacheKey(), shortCode);
  //     RBucket<String> bucket = redissonClient.getBucket(key);
  //     String json = bucket.get();
  //
  //     if (json != null) {
  //       return JSONObject.parseObject(json, ShortUrlMapping.class);
  //     }
  //   } catch (JSONException e) {
  //     log.error("Redis集群反序列化失败: shortCode={}, error={}", shortCode, e.getMessage());
  //   } catch (Exception e) {
  //     log.error("Redis集群查询失败: shortCode={}, error={}", shortCode, e.getMessage());
  //   }
  //   return null;
  // }


  // /**
  //  * 从集群缓存获取短链信息 (支持Hash Tag)
  //  * @param shortCode
  //  * @return
  //  */
  // @Override
  // public ShortUrlMapping getFromCache(String shortCode) {
  //   if (StringUtils.isBlank(shortCode)) {
  //     log.warn("shortCode为空，无法获取缓存");
  //     return null;
  //   }
  //
  //   // 1. 本地缓存获取
  //   ShortUrlMapping shortUrlMapping = localCacheService.getFromLocalCache(shortCode);
  //   if (shortUrlMapping != null) {
  //     log.debug("本地缓存命中: {}", shortCode);
  //     return shortUrlMapping;
  //   }
  //
  //   // 2. Redis集群获取
  //   shortUrlMapping = this.getFromRedisCluster(shortCode);
  //   if (shortUrlMapping != null) {
  //     log.debug("Redis集群缓存命中: {}, 分片槽位: {}", shortCode, shardingStrategyService.calculateSlot(shortCode));
  //     // 将Redis数据放入本地缓存
  //     localCacheService.putToLocalCache(shortCode, shortUrlMapping);
  //   }
  //   return shortUrlMapping;
  // }

  // /**
  //  * 将短链信息放入集群缓存 (支持Hash Tag)
  //  * @param shortCode
  //  * @param shortUrlMapping
  //  */
  // @Override
  // public void putToCache(String shortCode, ShortUrlMapping shortUrlMapping) {
  //   if (StringUtils.isBlank(shortCode) || shortUrlMapping == null) {
  //     log.warn("参数为空, 跳过缓存操作: shortCode={}", shortCode);
  //     return;
  //   }
  //
  //   // 放入本地缓存
  //   localCacheService.putToLocalCache(shortCode, shortUrlMapping);
  //
  //   // 放入Redis集群缓存
  //   this.cacheToRedisCluster(shortCode, shortUrlMapping);
  //
  //   log.debug("缓存短链信息到集群: {}, 分片槽位: {}", shortCode, shardingStrategyService.calculateSlot(shortCode));
  // }
}
