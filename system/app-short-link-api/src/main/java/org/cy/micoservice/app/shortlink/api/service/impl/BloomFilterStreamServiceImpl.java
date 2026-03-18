package org.cy.micoservice.app.shortlink.api.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.constants.ShortUrlConstant;
import org.cy.micoservice.app.shortlink.api.service.BloomFilterStreamService;
import org.cy.micoservice.app.shortlink.api.service.LocalBloomFilterService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.cy.micoservice.app.common.constants.CommonFormatConstants.COMMENT_FORMAT_COLON_SPLIT;
import static org.cy.micoservice.app.shortlink.api.constants.ShortUrlConstant.CONSUMER_NAME;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description:
 * Redis Stream 实现集群间布隆过滤器同步
 * 确保新增短链在所有节点的本地布隆过滤器中都可见
 */
@Slf4j
@Service
public class BloomFilterStreamServiceImpl implements BloomFilterStreamService, ApplicationContextAware {

  private final AtomicBoolean running = new AtomicBoolean(false);
  private String nodeId;
  private String consumerName;
  private ApplicationContext applicationContext;

  @Autowired
  private ShortLinkApiProperties properties;
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;
  @Autowired
  private LocalBloomFilterService localBloomFilter;

  @PostConstruct
  public void init() {
    try {
      // 生成节点ID
      nodeId = String.format(COMMENT_FORMAT_COLON_SPLIT, InetAddress.getLocalHost().getHostAddress(), properties.getServerPort());
      this.consumerName = CONSUMER_NAME + nodeId;

      // 创建消费者组 (如果不存在)
      try {
        redisTemplate.opsForStream().createGroup(ShortUrlConstant.REMOTE_STREAM_KEY, ShortUrlConstant.REMOTE_CONSUMER_SYNC_GROUP);
        log.info("创建Redis Stream消费者组: {}", ShortUrlConstant.REMOTE_CONSUMER_SYNC_GROUP);
      } catch (Exception e) {
        // 组已存在, 忽略错误
        log.info("消费者组已存在: {}", ShortUrlConstant.REMOTE_CONSUMER_SYNC_GROUP);
      }
      log.info("BloomFilterStreamService初始化完成");
    } catch (Exception e) {
      log.error("初始化BloomFilterStreamService失败", e);
    }
  }

  /**
   *
   */
  @EventListener(ApplicationReadyEvent.class)
  @Override
  public void onApplicationReady() {
    log.info("应用启动完成, 开始启动布隆过滤器Stream消费者");
    // 通过ApplicationContext获取代理
    BloomFilterStreamService proxy = applicationContext.getBean(BloomFilterStreamService.class);
    proxy.startConsumer();
  }

  /**
   * 消费者线程: 布隆过滤器
   */
  @Async("bloomFilterExecutor")
  @Override
  public void startConsumer() {
    log.info("消费者线程启动 - 线程名: {}, 线程ID: {}", Thread.currentThread().getName(), Thread.currentThread().getId());
    running.set(true);
    log.info("启动布隆过滤器Stream消费者: {}", consumerName);

    while (running.get()) {
      try {
        // 读取消息
        List<MapRecord<String, Object, Object>> records = redisTemplate
          .opsForStream()
          .read(
              Consumer.from(ShortUrlConstant.REMOTE_CONSUMER_SYNC_GROUP, consumerName),
              StreamReadOptions.empty().count(10).block(Duration.ofSeconds(2)),
              StreamOffset.create(ShortUrlConstant.REMOTE_STREAM_KEY, ReadOffset.lastConsumed()
            )
          );

        if (CollectionUtils.isNotEmpty(records)) {
          this.processRecords(records);
        }
      } catch (Exception e) {
        log.error("消费布隆过滤器Stream消息失败:", e);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }
    log.info("布隆过滤器Stream消费者已停止: {}", consumerName);
  }

  /**
   * 发布新增短链到 stream
   * @param shortCode
   */
  @Override
  public void publishNewShortCode(String shortCode) {
    try {
      Map<String, Object> message = Map.of(
        "shortCode", shortCode,
        "sourceNode", nodeId,
        "action", "ADD",
        "timestamp", String.valueOf(System.currentTimeMillis())
      );

      RecordId recordId = redisTemplate.opsForStream().add(ShortUrlConstant.REMOTE_STREAM_KEY, message);
      log.debug("发布短链到Stream: {} (recordId: {})", shortCode, recordId);
    } catch (Exception e) {
      log.error("发布短链到Stream失败: shortCode={}", shortCode, e);
    }
  }

  @Override
  public Object getNodeId() {
    return nodeId;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @PreDestroy
  public void shutdown() {
    running.set(false);
    log.info("正在关闭布隆过滤器Stream服务...");
  }

  /**
   * 同步布隆过滤器缓存
   * @param records
   */
  private void processRecords(List<MapRecord<String, Object, Object>> records) {
    for (MapRecord<String, Object, Object> record : records) {
      try {
        String shortCode = (String) record.getValue().get("shortCode");
        String sourceNode = (String) record.getValue().get("sourceNode");
        String action = (String) record.getValue().get("action");
        Long timestamp = Long.valueOf(record.getValue().get("timestamp").toString());

        // 不处理自己发送的消息
        if (! nodeId.equals(sourceNode)) {
          if ("ADD".equals(action)) {
            localBloomFilter.addLocal(shortCode);
            log.debug("从Stream同步短链到本地布隆过滤器: {} (来源: {})", shortCode, sourceNode);
          }
        }

        // 确认消息处理完成
        redisTemplate.opsForStream().acknowledge(ShortUrlConstant.REMOTE_STREAM_KEY, ShortUrlConstant.REMOTE_CONSUMER_SYNC_GROUP, record.getId());
      } catch (Exception e) {
        log.error("处理Stream记录失败: {}", record, e);
      }
    }
  }
}