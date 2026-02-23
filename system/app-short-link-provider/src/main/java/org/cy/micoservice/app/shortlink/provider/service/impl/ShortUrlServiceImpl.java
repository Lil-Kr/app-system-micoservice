package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cy.micoservice.app.common.constants.ShortLinkConstants;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.common.utils.shortlink.DigestUtils;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.framework.id.starter.service.IdService;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
import org.cy.micoservice.app.shortlink.facade.enums.ShortUrlEnum;
import org.cy.micoservice.app.shortlink.provider.config.ShortCodeConfig;
import org.cy.micoservice.app.shortlink.provider.config.ShortLinkProviderProperties;
import org.cy.micoservice.app.shortlink.provider.dao.ShortUrlMapper;
import org.cy.micoservice.app.shortlink.provider.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.cy.micoservice.app.common.constants.CommonFormatConstants.COMMENT_FORMAT_SLASH_SPLIT;
import static org.cy.micoservice.app.common.constants.CommonFormatConstants.COMMENT_FORMAT_UNDERSCORE_SPLIT;
import static org.cy.micoservice.app.common.constants.ShortLinkConstants.NEW_SHARDING_DATABASE_COUNT;
import static org.cy.micoservice.app.common.constants.ShortLinkConstants.NEW_SHARDING_TABLE_COUNT;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 短链url服务 - 集群分片版本
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

  @Autowired
  private ShortLinkProviderProperties properties;
  @Autowired
  private ShortCodeConfig shortCodeConfig;
  @Autowired
  private ClusterAwareCacheService clusterAwareCacheService;
  @Autowired
  private ShardingStrategyService shardingStrategyService;
  @Autowired
  private DistributedLockService distributedLockService;
  @Autowired
  private ShortCodeService shortCodeService;
  @Autowired
  private TransactionTemplate transactionTemplate;
  @Autowired
  private IdService idService;
  @Autowired
  private ShortUrlMapper shortUrlMapper;

  @Data
  @AllArgsConstructor
  static class CacheCheckResult {
    private CreateShortUrlRespDTO createShortUrlRespDTO;
    private String currentHash;
  }

  /**
   * create short url
   * @param reqDTO
   * @return
   */
  public CreateShortUrlRespDTO createShortUrl(CreateShortUrlReqDTO reqDTO) {
    ShortUrlMapping entity = BeanCopyUtils.convert(reqDTO, ShortUrlMapping.class);
    long id = idService.getId();
    entity.setId(id);
    entity.setShortCode("ABCDEGFH");
    entity.setOriginUrlHash("ababa");
    entity.setAccessCount(0L);
    entity.setStatus(ShortUrlEnum.ENABLE.getCode());
    entity.setCreateId(0L);
    entity.setUpdateId(0L);
    LocalDateTime now = DateUtil.localDateTimeNow();
    entity.setCreateTime(now);
    entity.setUpdateTime(now);

    int insert = shortUrlMapper.insert(entity);
    if (insert < 1) {
      return new CreateShortUrlRespDTO();
    }

    CreateShortUrlRespDTO resp = BeanCopyUtils.convert(entity, CreateShortUrlRespDTO.class);
    return resp;
  }

  // @Override
  // public CreateShortUrlRespDTO createShortUrl(CreateShortUrlReqDTO reqDTO) {
  //   // generate multiple hash value
  //   List<String> urlHashList = this.generateMultipleUrlHashes(reqDTO.getOriginUrl());
  //   log.info("urlHashList: {}", JSONArray.toJSONString(urlHashList));
  //
  //   // first layer protect: smart cache verify
  //   CacheCheckResult cacheCheckResult = this.smartCacheCheck(reqDTO.getOriginUrl(), urlHashList);
  //   if (Objects.nonNull(cacheCheckResult.getCreateShortUrlRespDTO())) {
  //     return cacheCheckResult.getCreateShortUrlRespDTO();
  //   }
  //
  //   // 主哈希值用于主要逻辑
  //   String primaryUrlHash = cacheCheckResult.getCurrentHash();
  //
  //   // 第二层防护: 使用分布式锁保证同一URL的串行处理
  //   String lockKey = String.format(COMMENT_FORMAT_COLON_SPLIT, "create_url", primaryUrlHash);
  //
  //   return distributedLockService.executeWithLock(lockKey, () -> {
  //     // 在锁内只检查单个哈希值的缓存状态（避免重复的多重哈希检查）
  //     String cachedShortCode = clusterAwareCacheService.getShortCodeByUrlHash(primaryUrlHash);
  //     if (StringUtils.isNotBlank(cachedShortCode)) {
  //       // 直接从缓存获取完整信息，避免额外的getShortUrlInfo调用
  //       ShortUrlMapping cachedMapping = clusterAwareCacheService.getFromCache(cachedShortCode);
  //       if (cachedMapping != null && reqDTO.getOriginUrl().equals(cachedMapping.getOriginUrl())) {
  //         log.info("分布式锁内缓存命中: shortCode={}, originUrl={}", cachedShortCode, reqDTO.getOriginUrl());
  //         return buildResponse(cachedMapping);
  //       }
  //     }
  //
  //     // 使用分布式ID生成策略生成短链code
  //     final String initialShortCode = shortCodeService.generateByStrategy(reqDTO.getOriginUrl());
  //
  //     // 根据shortCode计算精确的分片位置
  //     int dbIndex = Math.abs(initialShortCode.hashCode()) % NEW_SHARDING_DATABASE_COUNT;
  //     int tableIndex = Math.abs(initialShortCode.hashCode()) % NEW_SHARDING_TABLE_COUNT;
  //
  //     // ShortUrlMapping mapping = shortUrlMapper.preCheckByShortCode(dbIndex, tableIndex, initialShortCode, primaryUrlHash)
  //     ShortUrlMapping mapping = shortUrlMapper.preCheckByShortCode(dbIndex, tableIndex, initialShortCode, primaryUrlHash).orElse(null);
  //     if (mapping != null) {
  //       // 缓存查询结果
  //       clusterAwareCacheService.putToCache(mapping.getShortCode(), mapping);
  //       clusterAwareCacheService.putUrlHashMapping(primaryUrlHash, mapping.getShortCode());
  //       log.info("数据库智能查询命中，返回已存在短链: shortCode={}, originUrl={}", mapping.getShortCode(), reqDTO.getOriginUrl());
  //       return this.buildResponse(mapping);
  //     }
  //
  //     try {
  //       // 第三层防护：数据库事务 + 唯一索引
  //       return transactionTemplate.execute(status -> {
  //         try {
  //           String currentShortCode = initialShortCode;
  //
  //           // 检查生成的短链code是否已存在（避免哈希冲突）
  //           Optional<ShortUrlMapping> existingByCode = Optional.ofNullable(shortUrlMapper.findByShortCode(currentShortCode));
  //           if (existingByCode.isPresent()) {
  //             ShortUrlMapping existing = existingByCode.get();
  //             // 如果是相同的原始URL，直接返回
  //             if (reqDTO.getOriginUrl().equals(existing.getOriginUrl())) {
  //               clusterAwareCacheService.putToCache(currentShortCode, existing);
  //               clusterAwareCacheService.putUrlHashMapping(primaryUrlHash, currentShortCode);
  //               log.info("发现相同URL的短链: shortCode={}, originUrl={}, 数据库分片: db={}, table={}, Redis分片槽位={}",
  //                 currentShortCode, reqDTO.getOriginUrl(),
  //                 calculateDatabaseIndex(currentShortCode), calculateTableIndex(currentShortCode),
  //                 shardingStrategyService.calculateSlot(currentShortCode));
  //               return buildResponse(existing);
  //             } else {
  //               // 哈希冲突，重新生成
  //               int retryCount = 0;
  //               int maxRetries = shortCodeConfig.getMaxRetries();
  //               while (existingByCode.isPresent() && retryCount < maxRetries) {
  //                 currentShortCode = shortCodeService.generateByStrategy(reqDTO.getOriginUrl());
  //                 existingByCode = Optional.ofNullable(shortUrlMapper.findByShortCode(currentShortCode));
  //                 retryCount++;
  //               }
  //               if (retryCount >= maxRetries) {
  //                 throw new RuntimeException("生成短链失败，请重试");
  //               }
  //             }
  //           }
  //
  //           // 创建新的短链记录
  //           ShortUrlMapping shortUrlMapping = new ShortUrlMapping();
  //           shortUrlMapping.setShortCode(currentShortCode);
  //           shortUrlMapping.setOriginUrl(reqDTO.getOriginUrl());
  //           shortUrlMapping.setOriginUrlHash(primaryUrlHash);
  //           shortUrlMapping.setExpireDays(reqDTO.getExpireDays() != null ? reqDTO.getExpireDays() : properties.getDefaultExpireDays());
  //           shortUrlMapping.setCreateId(reqDTO.getCreateId());
  //           shortUrlMapping.setStatus(1);
  //           shortUrlMapping.setCreateTime(LocalDateTime.now());
  //
  //           // 在保存到数据库之前添加日志
  //           log.info("准备保存短链: shortCode={}, originUrl={}, originUrlHash={}, Redis分片槽位={}",
  //             currentShortCode, reqDTO.getOriginUrl(), primaryUrlHash,
  //             shardingStrategyService.calculateSlot(currentShortCode));
  //
  //           // shortUrlMapping = shortUrlMapper.save(shortUrlMapping, dbIndex, tableIndex);
  //           int insert = shortUrlMapper.insert(shortUrlMapping);
  //           // entityManager.flush();
  //
  //           log.info("短链保存完成: shortCode={}, 实际shortCode={}", currentShortCode, shortUrlMapping.getShortCode());
  //
  //           // 添加到布隆过滤器
  //           clusterAwareCacheService.addToBloomFilter(shortUrlMapping.getShortCode());
  //
  //           // 缓存短链信息和URL哈希映射到Redis集群
  //           clusterAwareCacheService.putToCache(shortUrlMapping.getShortCode(), shortUrlMapping);
  //           clusterAwareCacheService.putUrlHashMapping(primaryUrlHash, shortUrlMapping.getShortCode());
  //
  //           log.info("创建短链成功: shortCode={}, originUrl={}, Redis分片槽位={}",
  //             shortUrlMapping.getShortCode(), reqDTO.getOriginUrl(),
  //             shardingStrategyService.calculateSlot(shortUrlMapping.getShortCode()));
  //           return buildResponse(shortUrlMapping);
  //
  //         } catch (Exception e) {
  //           status.setRollbackOnly();
  //           throw e;
  //         }
  //       });
  //     } catch (Exception e) {
  //       log.error("创建短链失败: originUrl={}, error={}", reqDTO.getOriginUrl(), e.getMessage(), e);
  //       throw new RuntimeException("创建短链失败: " + e.getMessage(), e);
  //     }
  //   });
  //   return null;
  // }

  /**
   * 高性能哈希冲突解决方案 - 多重哈希策略
   * 使用多种哈希算法生成候选URL哈希值, 减少冲突概率
   */
  private List<String> generateMultipleUrlHashes(String originUrl) {
    List<String> hashList = new ArrayList<>();

    // 主哈希: MD5
    hashList.add(DigestUtils.md5(originUrl));

    // 备用哈希1: SHA-256的前32位
    String sha256 = DigestUtils.sha256(originUrl);
    hashList.add(sha256.substring(0, 32));

    /**
     * 备用哈希2: 带时间戳盐值的MD5
     * 秒级时间戳
     */
    String saltedUrl = String.format(COMMENT_FORMAT_UNDERSCORE_SPLIT, originUrl, System.currentTimeMillis() / 1000);
    hashList.add(DigestUtils.md5(saltedUrl));

    // 备用哈希3: URL长度 + MD5的组合
    String lengthPrefixedUrl = String.format(COMMENT_FORMAT_UNDERSCORE_SPLIT, originUrl.length(), originUrl);
    hashList.add(DigestUtils.md5(lengthPrefixedUrl));
    return hashList;
  }

  /**
   * 智能缓存验证 - 批量检查多个哈希值
   * 返回第一个匹配的缓存结果, 如果都不匹配则返回null
   * @param originalUrl
   * @param urlHashes
   * @return
   */
  private CacheCheckResult smartCacheCheck(String originalUrl, List<String> urlHashes) {
    for (String urlHash : urlHashes) {
      String cachedShortCode = clusterAwareCacheService.getShortCodeByUrlHash(urlHash);
      if (StringUtils.isNotBlank(cachedShortCode)) {
        CreateShortUrlRespDTO cachedResponse = this.getShortUrlInfo(cachedShortCode);
        if (cachedResponse != null && originalUrl.equals(cachedResponse.getOriginUrl())) {
          log.info("智能缓存命中: urlHash={}, shortCode={}, originUrl={}", urlHash, cachedShortCode, originalUrl);
          return new CacheCheckResult(cachedResponse, urlHash);
        } else {
          // 发现哈希冲突, 记录日志但不删除映射 (因为这可能是其他URL的正确映射)
          log.warn("检测到哈希冲突, 跳过此哈希值: urlHash={}, 缓存URL={}, 请求URL={}", urlHash, cachedResponse != null ? cachedResponse.getOriginUrl() : "null", originalUrl);
          // 不删除映射, 继续尝试下一个哈希值
        }
      } else {
        // 找到第一个可用的哈希值, 直接返回
        log.debug("找到可用哈希值: urlHash={}", urlHash);
        return new CacheCheckResult(null, urlHash);
      }
    }
    // 如果所有哈希值都冲突, 抛出异常
    throw new RuntimeException("创建短链hash值失败, 请稍后重试");
  }

  /**
   * get create short url resp info
   * @param shortCode
   * @return
   */
  @Override
  public CreateShortUrlRespDTO getShortUrlInfo(String shortCode) {
    // 参数校验
    if (StringUtils.isBlank(shortCode)) {
      return null;
    }

    // 先检查布隆过滤器
    if (!clusterAwareCacheService.existsInBloomFilter(shortCode)) {
      log.debug("布隆过滤器检查失败: shortCode={}", shortCode);
      return null;
    }

    // 获取短链映射 (会自动路由到正确分片)
    ShortUrlMapping shortUrlMapping = getShortUrlWithSentinel(shortCode);
    if (shortUrlMapping == null) {
      log.debug("短链不存在: shortCode={}", shortCode);
      return null;
    }

    // 检查是否过期
    if (this.isExpired(shortUrlMapping)) {
      log.debug("短链已过期: shortCode={}, createTime={}, expireDays={}", shortCode, shortUrlMapping.getCreateTime(), shortUrlMapping.getExpireDays());
      return null;
    }

    // 检查状态
    if (shortUrlMapping.getStatus() == null || shortUrlMapping.getStatus() != 1) {
      log.debug("短链状态异常: shortCode={}, status={}", shortCode, shortUrlMapping.getStatus());
      return null;
    }

    return this.buildResponse(shortUrlMapping);
  }

  /**
   * 智能缓存验证 - 批量检查多个哈希值
   * 返回第一个匹配的缓存结果, 如果都不匹配则返回null
   */
  private CacheCheckResult smartCacheVerify(String originalUrl, List<String> urlHashes) {
    for (String urlHash : urlHashes) {
      String cachedShortCode = clusterAwareCacheService.getShortCodeByUrlHash(urlHash);
      if (StringUtils.isNotBlank(cachedShortCode)) {
        CreateShortUrlRespDTO cachedResponse = getShortUrlInfo(cachedShortCode);
        if (cachedResponse != null && originalUrl.equals(cachedResponse.getOriginUrl())) {
          log.info("智能缓存命中: urlHash={}, shortCode={}, originUrl={}", urlHash, cachedShortCode, originalUrl);
          return new CacheCheckResult(cachedResponse, urlHash);
        } else {
          // 发现哈希冲突, 记录日志但不删除映射（因为这可能是其他URL的正确映射）
          log.warn("检测到哈希冲突, 跳过此哈希值: urlHash={}, 缓存URL={}, 请求URL={}", urlHash, cachedResponse != null ? cachedResponse.getOriginUrl() : "null", originalUrl);
          // 不删除映射, 继续尝试下一个哈希值
        }
      } else {
        // 找到第一个可用的哈希值, 直接返回
        log.debug("找到可用哈希值: urlHash={}", urlHash);
        return new CacheCheckResult(null, urlHash);
      }
    }
    // 如果所有哈希值都冲突, 抛出异常
    throw new RuntimeException("创建短链hash值失败, 请稍后重试");
  }

  /**
   *
   * @param shortCode
   * @return
   */
  private ShortUrlMapping getShortUrlWithSentinel(String shortCode) {
    // 从集群缓存获取
    ShortUrlMapping shortUrlMapping = clusterAwareCacheService.getFromCache(shortCode);
    if (shortUrlMapping != null) {
      return shortUrlMapping;
    }

    // 缓存未命中, 查询数据库 (ShardingSphere会自动路由到正确的分片)
    try {
      Optional<ShortUrlMapping> optional = Optional.ofNullable(shortUrlMapper.findByShortCode(shortCode));
      if (optional.isPresent()) {
        shortUrlMapping = optional.get();
        // 缓存查询结果到Redis集群
        clusterAwareCacheService.putToCache(shortCode, shortUrlMapping);
        log.debug("数据库查询成功: shortCode={}, 数据库分片: db={}, table={}, Redis分片槽位={}",
          shortCode,
          this.calculateDatabaseIndex(shortCode),
          this.calculateTableIndex(shortCode),
          shardingStrategyService.calculateSlot(shortCode));
        return shortUrlMapping;
      }
    } catch (Exception e) {
      log.error("数据库查询失败: shortCode={}, error={}", shortCode, e.getMessage(), e);
      throw e;
    }

    this.recordNotExistLog(shortCode);
    return null;
  }

  /**
   * 构建响应对象
   */
  private CreateShortUrlRespDTO buildResponse(ShortUrlMapping shortUrlMapping) {
    CreateShortUrlRespDTO response = BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlRespDTO.class);
    response.setShortUrl(String.format(COMMENT_FORMAT_SLASH_SPLIT, properties.getDomain(), shortUrlMapping.getShortCode()));
    return response;
  }

  /**
   *
   * @param shortCode
   */
  private void recordNotExistLog(String shortCode) {
    log.debug("short url not exist: shortCode={}", shortCode);
  }

  /**
   * 是否过期
   * @return 是否过期
   */
  private boolean isExpired(ShortUrlMapping shortUrlMapping) {
    Integer expireDays = shortUrlMapping.getExpireDays();
    // 如果过期天数为null或小于等于0, 表示永不过期
    if (expireDays == null || expireDays <= 0) {
      return false;
    }

    // 如果创建时间为null, 认为已过期（异常情况）
    if (shortUrlMapping.getCreateTime() == null) {
      return true;
    }

    // 计算过期时间：创建时间 + 过期天数
    LocalDateTime expireTime = shortUrlMapping.getCreateTime().plusDays(expireDays);

    // 判断当前时间是否超过过期时间
    return LocalDateTime.now().isAfter(expireTime);
  }

  /**
   * 计算数据库索引（更新为32个数据库）
   */
  private int calculateDatabaseIndex(String shortCode) {
    if (properties.isDualWriteEnabled()) {
      return Math.abs(shortCode.hashCode()) % NEW_SHARDING_DATABASE_COUNT;
    }
    return Math.abs(shortCode.hashCode()) % ShortLinkConstants.SHARDING_DATABASE_COUNT;
  }

  /**
   * 计算表索引（更新为256张表）
   */
  private int calculateTableIndex(String shortCode) {
    if (properties.isDualWriteEnabled()) {
      return Math.abs(shortCode.hashCode()) % NEW_SHARDING_TABLE_COUNT;
    }
    return Math.abs(shortCode.hashCode()) % ShortLinkConstants.SHARDING_TABLE_COUNT;
  }
}
