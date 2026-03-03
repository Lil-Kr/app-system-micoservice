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
  @Value("${shortlink.bloom.time-slice.hours:6}")
  private int timeSliceHours;
  @Value("${shortlink.bloom.time-slice.redis-keep-count:32}")
  private int redisKeepSliceCount; // Redis保留的时间片数量 (8天)
  @Value("${shortlink.bloom.time-slice.local-keep-count:8}")
  private int localKeepSliceCount;
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

  /** ============================= CacheSyncMonitorConfig =============================**/

  // 动态配置
  @Value("${shortlink.cache.stream.max-length:50000}")
  private long maxStreamLength;
  @Value("${shortlink.cache.stream.min-retain:10000}")
  private long minRetainLength;

}