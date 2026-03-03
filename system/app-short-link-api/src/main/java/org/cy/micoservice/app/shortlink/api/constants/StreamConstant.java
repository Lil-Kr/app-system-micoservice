package org.cy.micoservice.app.shortlink.api.constants;

/**
 * @Author: Lil-K
 * @Date: 2026/3/3
 * @Description:
 */
public class StreamConstant {

  // Local Stream 配置
  public static final String LOCAL_STREAM_KEY = "local_cache_stream";
  public static final String CACHE_SYNC_GROUP = "cache_sync_group";

  // Redis Stream 配置
  public static final String STREAM_KEY = "bloom_filter_stream";
  public static final String CONSUMER_GROUP = "bloom_sync_group";
}