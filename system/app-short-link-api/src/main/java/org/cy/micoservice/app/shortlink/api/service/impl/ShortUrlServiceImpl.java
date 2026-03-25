package org.cy.micoservice.app.shortlink.api.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alicp.jetcache.Cache;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.shortlink.model.api.req.CreateShortUrlReq;
import org.cy.micoservice.app.entity.shortlink.model.api.req.ShortUrlGetReq;
import org.cy.micoservice.app.entity.shortlink.model.api.resp.CreateShortUrlResp;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.shortlink.api.config.ShortCodeConfig;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkCacheKeyBuilder;
import org.cy.micoservice.app.shortlink.api.service.*;
import org.cy.micoservice.app.shortlink.api.utils.CalculateIndexUtil;
import org.cy.micoservice.app.shortlink.api.utils.DigestUtils;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
import org.cy.micoservice.app.shortlink.facade.enums.ShortUrlEnum;
import org.cy.micoservice.app.shortlink.facade.interfaces.ShortUrlFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.cy.micoservice.app.common.constants.CommonFormatConstants.COMMENT_FORMAT_SLASH_SPLIT;
import static org.cy.micoservice.app.common.constants.CommonFormatConstants.COMMENT_FORMAT_UNDERSCORE_SPLIT;
import static org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum.REQUEST_RESOURCE_NOT_EXIST;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 短链url服务 - 集群分片版本
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private ShortCodeConfig shortCodeConfig;
  @Autowired
  private DistributedLockService distributedLockService;
  // 使用集群感知缓存服务替代原有的CacheService
  @Autowired
  private ClusterAwareCacheService clusterAwareCacheService;
  @Autowired
  private ShardingStrategyService shardingStrategyService;
  @Autowired
  private ShortCodeService shortCodeService;
  @Autowired
  private ShortLinkCacheKeyBuilder cacheKeyBuilder;
  @Autowired
  private CalculateIndexUtil calculateIndexUtil;
  @Autowired
  private Cache<String, ShortUrlMapping> shortUrlHotCache;
  @DubboReference(check = false)
  private ShortUrlFacade shortUrlFacade;

  @Data
  @AllArgsConstructor
  static class CacheCheckResult {
    private CreateShortUrlResp createShortUrlResp;
    private String currentHash;
  }

  /**
   * create short url
   * @param req
   * @return
   */
  @SentinelResource(
    value = "createShortUrl",
    blockHandler = "createShortUrlBlockHandler",
    fallback = "createShortUrlFallback"
  )
  @Override
  public CreateShortUrlResp createShortUrl(CreateShortUrlReq req) {
    /**
     * 生成多重 hash
     */
    List<String> urlHashList = this.generateMultipleUrlHashes(req.getOriginUrl());

    /**
     * 第一层防护: 智能缓存检查(支持多重哈希), 增加hash冲突处理逻辑
     */
    CacheCheckResult cacheCheckResult = this.smartCacheCheck(req.getOriginUrl(), urlHashList);
    if (cacheCheckResult.getCreateShortUrlResp() != null) {
      return cacheCheckResult.getCreateShortUrlResp();
    }

    // 主哈希值用于主要逻辑
    String primaryUrlHash = cacheCheckResult.getCurrentHash();

    // 第二层防护: 使用分布式锁保证同一URL的串行处理, key -> create_url:urlhash
    String lockKey = cacheKeyBuilder.buildCacheLockKey(primaryUrlHash);
    return distributedLockService.executeWithLock(lockKey, () -> {
      // 在锁内只检查单个哈希值的缓存状态 (避免重复的多重哈希检查), from redis
      String cachedShortCode = clusterAwareCacheService.getShortCodeByUrlHash(primaryUrlHash);

      if (StringUtils.isNotBlank(cachedShortCode)) {
        // 直接从缓存获取完整信息, 避免额外的getShortUrlInfo调用
        ShortUrlMapping cachedMapping = clusterAwareCacheService.getShortUrlWithSentinel(cachedShortCode);
        if (cachedMapping != null && req.getOriginUrl().equals(cachedMapping.getOriginUrl())) {
          log.info("分布式锁内缓存命中: shortCode={}, originUrl={}", cachedShortCode, req.getOriginUrl());
          return this.buildResponse(cachedMapping);
        }
      }

      // 使用分布式ID生成策略, 生成短链code
      final String initialShortCode = shortCodeService.generateByStrategy(req.getOriginUrl());

      // 根据shortCode计算精确的分片位置
      // int dbIndex = Math.abs(initialShortCode.hashCode()) % SHARDING_DATABASE_COUNT;
      // int tableIndex = Math.abs(initialShortCode.hashCode()) % SHARDING_TABLE_COUNT;
      // int dbIndex = Math.abs(initialShortCode.hashCode()) % NEW_SHARDING_DATABASE_COUNT;
      // int tableIndex = Math.abs(initialShortCode.hashCode()) % NEW_SHARDING_TABLE_COUNT;

      // 从DB查询即将创建短链的数据是否存在
      RpcResponse<CreateShortUrlRespDTO> responseDTO = shortUrlFacade.findByOriginUrlHash(initialShortCode, primaryUrlHash);
      if (responseDTO.getData() != null) {
        // 将查询结果放入缓存
        ShortUrlMapping mapping = BeanCopyUtils.convert(responseDTO.getData(), ShortUrlMapping.class);
        clusterAwareCacheService.refreshCache(mapping);
        clusterAwareCacheService.putUrlHashMapping(primaryUrlHash, mapping.getShortCode());
        //过期检查
        if (this.isExpired(mapping)) {
          return null;
        }
        log.info("数据库智能查询命中, 返回已存在短链: shortCode={}, originUrl={}", mapping.getShortCode(), req.getOriginUrl());
        return this.buildResponse(mapping);
      }

      // 第三层防护: 数据库事务由Provider层管理
      try {
        String currentShortCode = initialShortCode;
        // 检查生成的短链code是否已存在 (避免哈希冲突)
        RpcResponse<CreateShortUrlRespDTO> existShortUrlResp = shortUrlFacade.findByShortCode(currentShortCode);
        if (existShortUrlResp.getData() != null) {
          ShortUrlMapping existing = BeanCopyUtils.convert(existShortUrlResp.getData(), ShortUrlMapping.class);
          // 检查是否过期
          if (this.isExpired(existing)) {
            return new CreateShortUrlResp();
          }
          // 如果是相同的原始URL, 直接返回
          if (req.getOriginUrl().equals(existing.getOriginUrl())) {
            clusterAwareCacheService.putUrlHashMapping(primaryUrlHash, currentShortCode);
            log.info("发现相同URL的短链: shortCode={}, originUrl={}, 数据库分片: db={}, table={}, Redis分片槽位={}",
              currentShortCode,
              req.getOriginUrl(),
              calculateIndexUtil.calculateDatabaseIndex(currentShortCode),
              calculateIndexUtil.calculateTableIndex(currentShortCode),
              shardingStrategyService.calculateSlot(currentShortCode));
            return this.buildResponse(existing);
          } else {
            // 哈希冲突, 则重新生成
            int retryCount = 0;
            // 最大重试次数
            int maxRetries = shortCodeConfig.getMaxRetries();
            while (existShortUrlResp.getData() != null && retryCount < maxRetries) {
              currentShortCode = shortCodeService.generateByStrategy(req.getOriginUrl());
              existShortUrlResp = shortUrlFacade.findByShortCode(currentShortCode);
              retryCount ++;
            }
            if (retryCount >= maxRetries) {
              throw new RuntimeException("生成短链失败, 请重试");
            }
          }
        }

        // 创建新的短链记录
        ShortUrlMapping shortUrlMapping = new ShortUrlMapping();
        shortUrlMapping.setShortCode(currentShortCode);
        shortUrlMapping.setOriginUrl(req.getOriginUrl());
        shortUrlMapping.setOriginUrlHash(primaryUrlHash);
        shortUrlMapping.setExpireDays(req.getExpireDays() != null ? req.getExpireDays() : properties.getDefaultExpireDays());
        shortUrlMapping.setCreateId(req.getCreateId());
        shortUrlMapping.setStatus(ShortUrlEnum.ENABLE.getCode());

        // 在保存到数据库之前添加日志
        log.info("准备保存短链: shortCode={}, originUrl={}, originUrlHash={}, Redis分片槽位={}",
          currentShortCode,
          req.getOriginUrl(),
          primaryUrlHash,
          shardingStrategyService.calculateSlot(currentShortCode));

        CreateShortUrlReqDTO reqDTO = BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlReqDTO.class);
        RpcResponse<CreateShortUrlRespDTO> response = shortUrlFacade.createShortUrl(reqDTO);
        shortUrlMapping = BeanCopyUtils.convert(response.getData(), ShortUrlMapping.class);
        log.info("短链保存完成: shortCode={}, 实际shortCode={}", currentShortCode, shortUrlMapping.getShortCode());

        // 1. 添加到布隆过滤器
        clusterAwareCacheService.addToBloomFilter(shortUrlMapping.getShortCode());

        // 2. 刷新缓存
        clusterAwareCacheService.refreshCache(shortUrlMapping);

        // 3. 缓存短链信息和URL哈希映射到Redis集群
        clusterAwareCacheService.putUrlHashMapping(primaryUrlHash, shortUrlMapping.getShortCode());

        log.info("创建短链成功: shortCode={}, originUrl={}, Redis分片槽位={}",
          shortUrlMapping.getShortCode(), req.getOriginUrl(),
          shardingStrategyService.calculateSlot(shortUrlMapping.getShortCode()));
        return this.buildResponse(shortUrlMapping);
      } catch (Exception e) {
        log.error("创建短链失败: originUrl={}, error={}", req.getOriginUrl(), e.getMessage(), e);
        throw new RuntimeException("创建短链失败: " + e.getMessage(), e);
      }
    });
  }

  /**
   * 获取短链信息 (支持分库分表和Redis集群分片)
   * @param shortCode
   * @return
   */
  @SentinelResource(
    value = "getShortUrlInfo",
    blockHandler = "getShortUrlInfoBlockHandler",
    fallback = "getShortUrlInfoFallback"
  )
  @Override
  public CreateShortUrlResp getShortUrlInfo(String shortCode) {
    if (StringUtils.isBlank(shortCode)) {
      return null;
    }

    // 1. 检查布隆过滤器, 过滤不存在的 shortCode 包含 local + redis 集群
    if (! clusterAwareCacheService.existsInBloomFilter(shortCode)) {
      log.debug("布隆过滤器检查失败: shortCode={}", shortCode);
      return null;
    }

    // 2. 从缓存中获取短链映射 (会自动路由到正确分片)
    ShortUrlMapping shortUrlMapping = clusterAwareCacheService.getShortUrlWithSentinel(shortCode);
    if (shortUrlMapping == null) {
      log.debug("短链不存在: shortCode={}", shortCode);
      return null;
    }

    // 3. 检查是否过期
    if (this.isExpired(shortUrlMapping)) {
      log.debug("短链已过期: shortCode={}, createTime={}, expireDays={}", shortCode, shortUrlMapping.getCreateTime(), shortUrlMapping.getExpireDays());
      return null;
    }

    // 4. 检查状态
    if (shortUrlMapping.getStatus() == null || shortUrlMapping.getStatus() != ShortUrlEnum.ENABLE.getCode()) {
      log.debug("短链状态异常: shortCode={}, status={}", shortCode, shortUrlMapping.getStatus());
      return null;
    }

    // 6. 异步增加短链访问量
    clusterAwareCacheService.updateAccessCountAsync(shortUrlMapping);
    return this.buildResponse(shortUrlMapping);
  }

  /**
   * url重定向
   * @param req
   */
  @Override
  public ApiResp<String> redirect(ShortUrlGetReq req, HttpServletResponse response) throws Exception {
    try {
      CreateShortUrlResp createShortUrlResp = this.getShortUrlInfo(req.getShortCode());
      if (Objects.isNull(createShortUrlResp)) {
        // response.sendError(HttpServletResponse.SC_NOT_FOUND, "short url is not exist or expires");
        return ApiResp.failure(REQUEST_RESOURCE_NOT_EXIST);
      }

      response.setStatus(HttpServletResponse.SC_FOUND);
      response.setHeader("Location", createShortUrlResp.getOriginUrl());
      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Expires", "0");
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
    return ApiResp.success();
  }

  /**
   * 记录当前url的访问次数
   * @param shortCode
   * @return
   */
  @SentinelResource(
    value = "getOriginUrl",
    blockHandler = "getOriginUrlBlockHandler",
    fallback = "getOriginUrlFallback"
  )
  @Override
  public String getOriginUrl(String shortCode) {
    CreateShortUrlResp shortUrlInfo = this.getShortUrlInfo(shortCode);
    if (Objects.isNull(shortUrlInfo)) {
      return null;
    }

    // 异步更新访问次数 (不阻塞主流程) - 使用集群分片
    ShortUrlMapping mapping = BeanCopyUtils.convert(shortUrlInfo, ShortUrlMapping.class);
    clusterAwareCacheService.updateAccessCountAsync(mapping);

    log.debug("短链重定向: shortCode={}, originUrl={}, 数据库分片: db={}, table={}, Redis分片槽位={}",
      shortCode, shortUrlInfo.getOriginUrl(),
      calculateIndexUtil.calculateDatabaseIndex(shortCode), calculateIndexUtil.calculateTableIndex(shortCode),
      shardingStrategyService.calculateSlot(shortCode));

    return shortUrlInfo.getOriginUrl();
  }

  /**
   * 构建响应对象
   */
  private CreateShortUrlResp buildResponse(ShortUrlMapping shortUrlMapping) {
    CreateShortUrlResp response = BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlResp.class);
    response.setShortUrl(String.format(COMMENT_FORMAT_SLASH_SPLIT, properties.getDomain(), shortUrlMapping.getShortCode()));
    return response;
  }

  /**
   * 是否过期
   * @return true / false
   */
  private boolean isExpired(ShortUrlMapping shortUrlMapping) {
    Integer expireDays = shortUrlMapping.getExpireDays();
    // 如果过期天数为null或小于等于0, 表示永不过期
    if (expireDays == null || expireDays <= 0) {
      return false;
    }

    // 如果创建时间为null, 认为已过期 (异常情况)
    if (shortUrlMapping.getCreateTime() == null) {
      return true;
    }

    // 计算过期时间: 创建时间 + 过期天数
    LocalDateTime expireTime = shortUrlMapping.getCreateTime().plusDays(expireDays);

    // 判断当前时间是否超过过期时间
    return LocalDateTime.now().isAfter(expireTime);
  }

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
   * @param urlHashList
   * @return
   */
  private CacheCheckResult smartCacheCheck(String originalUrl, List<String> urlHashList) {
    for (String urlHash : urlHashList) {
      // get short_code from redis
      String shortCode = clusterAwareCacheService.getShortCodeByUrlHash(urlHash);

      if (StringUtils.isBlank(shortCode)) {
        // 找到第一个可用的哈希值, 直接返回
        log.debug("enable hash value is not found: urlHash={}", urlHash);
        return new CacheCheckResult(null, urlHash);
      }

      CreateShortUrlResp createShortUrlResp = this.getShortUrlInfo(shortCode);
      if (createShortUrlResp != null && originalUrl.equals(createShortUrlResp.getOriginUrl())) {
        log.info("smart cache hit: urlHash={}, shortCode={}, originUrl={}", urlHash, shortCode, originalUrl);
        return new CacheCheckResult(createShortUrlResp, urlHash);
      }

      // 发现哈希冲突, 记录日志但不删除映射 (因为这可能是其他URL的正确映射)
      log.warn("检测到哈希冲突, 跳过此哈希值: urlHash={}, 缓存URL={}, 请求URL={}", urlHash, createShortUrlResp != null ? createShortUrlResp.getOriginUrl() : "null", originalUrl);
    }
    // 如果所有哈希值都冲突, 抛出异常
    throw new RuntimeException("创建短链hash值失败, 请稍后重试");
  }

  /** ======================== Sentinel 处理方法 ======================== **/
  public CreateShortUrlResp createShortUrlBlockHandler(CreateShortUrlReq req, BlockException ex) {
    log.warn("创建短链被限流: originUrl={}", req != null ? req.getOriginUrl() : "null");
    CreateShortUrlResp resp = new CreateShortUrlResp();
    resp.setShortCode("RATE_LIMITED");
    resp.setShortUrl("系统繁忙，请稍后重试");
    return resp;
  }

  public CreateShortUrlResp createShortUrlFallback(CreateShortUrlReq request, Throwable ex) {
    log.error("创建短链降级: originUrl={}, error={}", request != null ? request.getOriginUrl() : "null", ex.getMessage());
    CreateShortUrlResp response = new CreateShortUrlResp();
    response.setShortCode("SERVICE_DEGRADED");
    response.setShortUrl("创建短链失败，请稍后重试");
    return response;
  }

  public CreateShortUrlResp getShortUrlInfoBlockHandler(String shortCode, BlockException ex) {
    log.warn("查询短链信息被限流: shortCode={}", shortCode);
    return null;
  }

  public CreateShortUrlResp getShortUrlInfoFallback(String shortCode, Throwable ex) {
    log.error("查询短链信息降级: shortCode={}, error={}", shortCode, ex.getMessage());
    return null;
  }

  public String getOriginUrlBlockHandler(String shortCode, BlockException ex) {
    log.warn("获取原始URL被限流: shortCode={}", shortCode);
    return null;
  }

  public String getOriginUrlFallback(String shortCode, Throwable ex) {
    log.error("获取原始URL降级: shortCode={}, error={}", shortCode, ex.getMessage());
    return null;
  }
}