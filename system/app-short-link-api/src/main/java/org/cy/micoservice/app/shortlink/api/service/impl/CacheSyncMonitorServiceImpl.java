package org.cy.micoservice.app.shortlink.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.service.CacheSyncMonitorService;
import org.cy.micoservice.app.shortlink.api.service.LocalCacheStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author: Lil-K
 * @Date: 2026/3/1
 * @Description:
 */
@Slf4j
@Service
public class CacheSyncMonitorServiceImpl implements CacheSyncMonitorService {

  private static final String STREAM_KEY = "local_cache_stream";
  private static final String CONSUMER_GROUP = "cache_sync_group";

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;
  @Autowired
  private LocalCacheStreamService streamService;

  /**
   * 监控Stream状态
   * 每5分钟监控一次
   */
  @Scheduled(fixedRate = 300000)
  @Override
  public void monitorStreamStatus() {
    try {
      // 获取Stream信息
      var streamInfo = redisTemplate.opsForStream().info(STREAM_KEY);
      long currentLength = streamInfo.streamLength();

      log.info("=== 本地缓存Stream监控 ===");
      log.info("Stream长度: {}", currentLength);
      log.info("最大长度限制: {}", properties.getMaxStreamLength());
      log.info("消费者组: {}", CONSUMER_GROUP);
      log.info("当前节点: {}", streamService.getNodeId());

      // 检查是否需要紧急清理
      if (currentLength > properties.getMaxStreamLength()) {
        log.warn("Stream长度({})超过最大限制({}), 触发紧急清理", currentLength, properties.getMaxStreamLength());
        emergencyCleanup();
      }

      // 检查消费者组状态
      var groupInfo = redisTemplate.opsForStream().groups(STREAM_KEY);
      if (groupInfo != null && !groupInfo.isEmpty()) {
        log.info("消费者组数量: {}", groupInfo.size());
      }

    } catch (Exception e) {
      log.error("监控本地缓存Stream状态失败", e);
    }
  }

  /**
   * 智能清理过期消息
   */
  @Scheduled(fixedRateString = "${shortlink.cache.stream.cleanup-interval:300000}")
  @Override
  public void smartCleanupExpiredMessages() {
    try {
      var streamInfo = redisTemplate.opsForStream().info(STREAM_KEY);
      long currentLength = streamInfo.streamLength();

      long minRetainLength = properties.getMinRetainLength();
      if (currentLength <= minRetainLength) {
        log.debug("Stream长度({})未超过最小保留数量({}), 跳过清理", currentLength, minRetainLength);
        return;
      }

      // 计算需要保留的消息数量（基于当前长度的80%，但不少于最小保留数量）
      long retainCount = Math.max(minRetainLength, (long)(currentLength * 0.8));

      Long trimmed = redisTemplate.opsForStream().trim(STREAM_KEY, retainCount);
      if (trimmed > 0) {
        log.info("智能清理Stream消息: 清理{}条, 保留{}条, 当前长度: {}",
          trimmed, retainCount, currentLength - trimmed);
      }
    } catch (Exception e) {
      log.error("智能清理Stream消息失败", e);
    }
  }

  /**
   * 紧急清理（当Stream长度超过最大限制时）
   */
  private void emergencyCleanup() {
    try {
      // 紧急情况下保留最近的消息
      long emergencyRetainCount = properties.getMaxStreamLength() / 2;
      Long trimmed = redisTemplate.opsForStream().trim(STREAM_KEY, emergencyRetainCount);
      log.warn("紧急清理完成: 清理{}条消息, 保留{}条", trimmed, emergencyRetainCount);
    } catch (Exception e) {
      log.error("紧急清理失败", e);
    }
  }
}