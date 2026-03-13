package org.cy.micoservice.app.shortlink.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cy.micoservice.app.entity.shortlink.model.api.pojo.NodeInfo;
import org.cy.micoservice.app.shortlink.api.service.LocalBloomFilterService;
import org.cy.micoservice.app.shortlink.api.service.RedisBloomFilterService;
import org.cy.micoservice.app.shortlink.api.service.TieredBloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: 分层布隆过滤器 (支持Stream同步)
 */
@Slf4j
@Service
public class TieredBloomFilterServiceImpl implements TieredBloomFilterService {

  @Autowired
  private RedisBloomFilterService redisTimeBasedBloomFilter;
  @Autowired
  private LocalBloomFilterService localBloomFilterService;

  /**
   * 添加到时间分片布隆过滤器
   */
  @Override
  public void put(String shortCode) {
    if (StringUtils.isBlank(shortCode)) {
      log.warn("shortCode为空或null");
      return;
    }

    try {
      // 向 local 布隆过滤器添加缓存
      localBloomFilterService.addLocal(shortCode);

      // 统一由 RedisTimeBasedBloomFilterService 处理 (内部包含: 本地分片 + Redis分片 + 发布Stream)
      redisTimeBasedBloomFilter.add(shortCode);
      log.debug("时间分片布隆过滤器添加成功: {}", shortCode);
    } catch (Exception e) {
      log.error("时间分片布隆过滤器添加失败: shortCode={}", shortCode, e);
    }
  }

  /**
   * 从布隆过滤器中, 分层查询信息
   * @param shortCode
   * @return
   */
  @Override
  public boolean mightContain(String shortCode) {
    // 第一层: 本地时间分片, 零延迟, 命中概率更高 (委托本地服务)
    if (localBloomFilterService.mightContain(shortCode)) {
      return true;
    }

    /**
     * 第二层: Redis时间分片, 兜底 (跨节点共享)
     * 统一委托给 RedisTimeBasedBloomFilterService
     */
    if (redisTimeBasedBloomFilter.mightContain(shortCode)) {
      localBloomFilterService.addLocal(shortCode);
      return true;
    }
    return false;
  }

  /**
   * 获取节点信息
   * @return
   */
  @Override
  public NodeInfo getNodeInfo() {
    try {
      // 获取节点ID (通过RedisTimeBasedBloomFilterService)
      String nodeId = redisTimeBasedBloomFilter.getNodeId();

      // 获取服务状态
      boolean localServiceActive = redisTimeBasedBloomFilter != null;
      boolean redisServiceActive = redisTimeBasedBloomFilter != null;
      // 由统一服务内部发布
      boolean streamServiceActive = true;

      // 获取统计信息
      String localStats = localServiceActive ? localBloomFilterService.getLocalStats() : "服务未激活";
      String redisStats = redisServiceActive ? redisTimeBasedBloomFilter.getRedisStats() : "服务未激活";

      // 获取系统信息
      Runtime runtime = Runtime.getRuntime();
      long totalMemory = runtime.totalMemory();
      long freeMemory = runtime.freeMemory();
      long usedMemory = totalMemory - freeMemory;
      long maxMemory = runtime.maxMemory();

      return NodeInfo.builder()
        .nodeId(nodeId)
        .timestamp(System.currentTimeMillis())
        .localServiceActive(localServiceActive)
        .redisServiceActive(redisServiceActive)
        .streamServiceActive(streamServiceActive)
        .localStats(localStats)
        .redisStats(redisStats)
        .memoryUsedMB(usedMemory / 1024 / 1024)
        .memoryTotalMB(totalMemory / 1024 / 1024)
        .memoryMaxMB(maxMemory / 1024 / 1024)
        .memoryUsagePercent((double) usedMemory / maxMemory * 100)
        .build();

    } catch (Exception e) {
      log.error("获取节点信息失败", e);
      return NodeInfo.builder()
        .nodeId("unknown")
        .timestamp(System.currentTimeMillis())
        .localServiceActive(false)
        .redisServiceActive(false)
        .streamServiceActive(false)
        .localStats("获取失败: " + e.getMessage())
        .redisStats("获取失败: " + e.getMessage())
        .memoryUsedMB(0L)
        .memoryTotalMB(0L)
        .memoryMaxMB(0L)
        .memoryUsagePercent(0.0)
        .build();
    }
  }
}
