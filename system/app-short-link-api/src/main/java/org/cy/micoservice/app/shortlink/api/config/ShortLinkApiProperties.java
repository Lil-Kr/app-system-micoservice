package org.cy.micoservice.app.shortlink.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Data
@Configuration
public class ShortLinkApiProperties {

  @Value("${server.port:}")
  private String serverPort;
  @Value("${shortlink.cluster.batch-size:50}")
  private int batchSize;
  @Value("${shortlink.cluster.enable-hash-tag:true}")
  private boolean enableHashTag;
  // 布隆过滤器: 每个时间分片的时长: 6h
  @Value("${shortlink.bloom.time-slice.hours:6}")
  private int timeSliceHours;
  // local 保留的时间片数量 (2天) --> 每天4个分片 * 2 = 8
  @Value("${shortlink.bloom.time-slice.local-keep-count:8}")
  private int localKeepSliceCount;
  // Redis 保留的时间片数量 (8天) --> 每天4个分片 * 8 = 32
  @Value("${shortlink.bloom.time-slice.redis-keep-count:32}")
  private int redisKeepSliceCount;
  @Value("${shortlink.shorturl.domain:http://localhost:7901}")
  private String domain;
  @Value("${shortlink.expansion.dual-write-enabled:false}")
  private boolean dualWriteEnabled;
  @Value("${shortlink.shorturl.default-expire-days:0}")
  private Integer defaultExpireDays;

  /** ============================= BloomFilterAsyncConfig =============================**/

  @Value("${shortlink.bloom.threadpool.corePoolSize:4}")
  private int corePoolSize;
  @Value("${shortlink.bloom.threadpool.maxPoolSize:8}")
  private int maxPoolSize;
  @Value("${shortlink.bloom.threadpool.queueCapacity:1000}")
  private int queueCapacity;
  @Value("${shortlink.bloom.threadpool.keepAliveSeconds:60}")
  private int keepAliveSeconds;
  @Value("${shortlink.bloom.threadpool.threadNamePrefix:bloom-filter-}")
  private String threadNamePrefix;
  @Value("${shortlink.bloom.threadpool.awaitTerminationSeconds:30}")
  private int awaitTerminationSeconds;

  /** ============================= bloomFilter monitor config =============================**/
  @Value("${shortlink.bloom.alert.false-probability-threshold:0.01}")
  private double falseProbabilityThreshold;
  @Value("${shortlink.bloom.alert.memory-usage-threshold:0.8}")
  private double memoryUsageThreshold;

  /** ============================= cacheSync monitor config =============================**/
  // 动态配置
  @Value("${shortlink.cache.stream.max-length:50000}")
  private long maxStreamLength;
  @Value("${shortlink.cache.stream.min-retain:10000}")
  private long minRetainLength;

}