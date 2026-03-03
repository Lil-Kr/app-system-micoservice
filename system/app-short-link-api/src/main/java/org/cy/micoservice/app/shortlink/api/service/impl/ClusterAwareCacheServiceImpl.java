package org.cy.micoservice.app.shortlink.api.service.impl;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.service.ClusterAwareCacheService;
import org.cy.micoservice.app.shortlink.api.service.LocalCacheService;
import org.cy.micoservice.app.shortlink.api.service.ShardingStrategyService;
import org.cy.micoservice.app.shortlink.api.service.TieredBloomFilterService;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: 集群感知缓存服务, 使用Redis集群分片和 Hash Tag 策略优化数据分布
 */
@Slf4j
@Service
public class ClusterAwareCacheServiceImpl implements ClusterAwareCacheService {

  private static final String CACHE_PREFIX = "shortlink:";
  private static final String URL_CACHE_KEY = CACHE_PREFIX + "url:";
  private static final String COUNT_CACHE_KEY = CACHE_PREFIX + "count:";
  private static final String HASH_MAPPING_KEY = CACHE_PREFIX + "hash:";

  private static final Duration DEFAULT_EXPIRE_TIME = Duration.ofHours(1);
  private static final Duration HOT_DATA_EXPIRE_TIME = Duration.ofHours(24);
  private static final Duration COUNT_EXPIRE_TIME = Duration.ofDays(7);

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private RedissonClient redissonClient;
  @Autowired
  private LocalCacheService localCacheService;
  @Autowired
  private ShardingStrategyService shardingStrategyService;
  @Autowired
  private TieredBloomFilterService tieredBloomFilterService;

  /**
   * 获取URL哈希映射
   * @param urlHash
   * @return
   */
  @Override
  public String getShortCodeByUrlHash(String urlHash) {
    try {
      String key = this.generateHashTagKey(HASH_MAPPING_KEY, urlHash);
      log.info("getShortCodeByUrlHash key={}", key);
      RBucket<String> bucket = redissonClient.getBucket(key);
      return bucket.get();
    } catch (Exception e) {
      log.error("获取URL哈希映射失败: hash={}, error={}", urlHash, e.getMessage());
      return null;
    }
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
   * 从集群缓存获取短链信息 (支持Hash Tag)
   * @param shortCode
   * @return
   */
  @Override
  public ShortUrlMapping getFromCache(String shortCode) {
    if (!StringUtils.hasText(shortCode)) {
      log.warn("shortCode为空，无法获取缓存");
      return null;
    }

    // 先从本地缓存获取
    ShortUrlMapping shortUrlMapping = localCacheService.getFromLocalCache(shortCode);
    if (shortUrlMapping != null) {
      log.debug("本地缓存命中: {}", shortCode);
      return shortUrlMapping;
    }

    // 从Redis集群获取
    shortUrlMapping = this.getFromRedisCluster(shortCode);
    if (shortUrlMapping != null) {
      log.debug("Redis集群缓存命中: {}, 分片槽位: {}", shortCode, shardingStrategyService.calculateSlot(shortCode));
      // 将Redis数据放入本地缓存
      localCacheService.putToLocalCache(shortCode, shortUrlMapping);
    }
    return shortUrlMapping;
  }

  /**
   * 将短链信息放入集群缓存 (支持Hash Tag)
   * @param shortCode
   * @param shortUrlMapping
   */
  @Override
  public void putToCache(String shortCode, ShortUrlMapping shortUrlMapping) {
    if (!StringUtils.hasText(shortCode) || shortUrlMapping == null) {
      log.warn("参数为空, 跳过缓存操作: shortCode={}", shortCode);
      return;
    }

    // 放入本地缓存
    localCacheService.putToLocalCache(shortCode, shortUrlMapping);

    // 放入Redis集群缓存
    this.cacheToRedisCluster(shortCode, shortUrlMapping);

    log.debug("缓存短链信息到集群: {}, 分片槽位: {}", shortCode, shardingStrategyService.calculateSlot(shortCode));
  }

  /**
   * URL哈希映射缓存
   * @param originUrlHash
   * @param shortCode
   */
  @Override
  public void putUrlHashMapping(String originUrlHash, String shortCode) {
    try {
      String key = this.generateHashTagKey(HASH_MAPPING_KEY, originUrlHash);
      RBucket<String> bucket = redissonClient.getBucket(key);
      bucket.set(shortCode, DEFAULT_EXPIRE_TIME);

      log.debug("缓存URL哈希映射: hash={}, shortCode={}, 分片槽位: {}", originUrlHash, shortCode, shardingStrategyService.calculateSlot(key));
    } catch (Exception e) {
      log.error("缓存URL哈希映射失败: hash={}, error={}", originUrlHash, e.getMessage());
    }
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
   * 增加访问计数 (集群分片优化)
   * @param shortCode
   * @return
   */
  @Override
  public Long incrementAccessCount(String shortCode) {
    try {
      String key = generateHashTagKey(COUNT_CACHE_KEY, shortCode);
      RAtomicLong atomicLong = redissonClient.getAtomicLong(key);

      long count = atomicLong.incrementAndGet();
      atomicLong.expire(COUNT_EXPIRE_TIME);

      log.debug("访问计数增加: {}, 当前计数: {}, 分片槽位: {}",
        shortCode, count, shardingStrategyService.calculateSlot(key));

      return count;
    } catch (Exception e) {
      log.error("增加访问计数失败: shortCode={}, error={}", shortCode, e.getMessage());
      return null;
    }
  }

  /**
   * 从Redis集群获取数据
   * @param shortCode
   * @return
   */
  private ShortUrlMapping getFromRedisCluster(String shortCode) {
    try {
      String key = this.generateHashTagKey(URL_CACHE_KEY, shortCode);
      RBucket<String> bucket = redissonClient.getBucket(key);
      String json = bucket.get();

      if (json != null) {
        return JSONObject.parseObject(json, ShortUrlMapping.class);
      }
    } catch (JSONException e) {
      log.error("Redis集群反序列化失败: shortCode={}, error={}", shortCode, e.getMessage());
    } catch (Exception e) {
      log.error("Redis集群查询失败: shortCode={}, error={}", shortCode, e.getMessage());
    }
    return null;
  }

  /**
   * 生成 [Hash Tag] 键 (确保相关数据在同一分片)
   * @param prefix
   * @param shortCode
   * @return
   */
  private String generateHashTagKey(String prefix, String shortCode) {
    if (properties.isEnableHashTag()) {
      return prefix + "{" + shortCode + "}";
    }
    return prefix + shortCode;
  }

  /**
   * 缓存到Redis集群
   * @param shortCode
   * @param shortUrlMapping
   */
  private void cacheToRedisCluster(String shortCode, ShortUrlMapping shortUrlMapping) {
    try {
      String key = this.generateHashTagKey(URL_CACHE_KEY, shortCode);
      String json = JSONObject.toJSONString(shortUrlMapping);

      RBucket<String> bucket = redissonClient.getBucket(key);
      Duration expireTime = this.isHotData(shortUrlMapping) ? HOT_DATA_EXPIRE_TIME : DEFAULT_EXPIRE_TIME;
      bucket.set(json, expireTime);

      log.debug("Redis集群缓存成功: {}", shortCode);
    } catch (JSONException e) {
      log.error("Redis集群序列化失败: shortCode={}, error={}", shortCode, e.getMessage());
    } catch (Exception e) {
      log.error("Redis集群缓存失败: shortCode={}, error={}", shortCode, e.getMessage());
    }
  }

  /**
   * 判断是否为热点数据
   */
  private boolean isHotData(ShortUrlMapping shortUrlMapping) {
    return shortUrlMapping.getAccessCount() != null && shortUrlMapping.getAccessCount() > 1000;
  }
}
