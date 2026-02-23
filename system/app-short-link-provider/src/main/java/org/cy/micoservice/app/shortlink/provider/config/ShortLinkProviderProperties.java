package org.cy.micoservice.app.shortlink.provider.config;

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
public class ShortLinkProviderProperties {

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

  @Value("${shortlink.shorturl.domain:http://localhost:8001}")
  private String domain;

  @Value("${shortlink.expansion.dual-write-enabled:false}")
  private boolean dualWriteEnabled;

  @Value("${shortlink.shorturl.default-expire-days:0}")
  private Integer defaultExpireDays;
}