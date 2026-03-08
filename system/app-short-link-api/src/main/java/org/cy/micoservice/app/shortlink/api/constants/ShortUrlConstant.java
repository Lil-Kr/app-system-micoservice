package org.cy.micoservice.app.shortlink.api.constants;

/**
 * @Author: Lil-K
 * @Date: 2026/3/3
 * @Description:
 */
public class ShortUrlConstant {

  // Local Stream 配置
  public static final String LOCAL_STREAM_KEY = "bloom_local_stream";
  public static final String LOCAL_CONSUMER_SYNC_GROUP = "local_sync_group";

  // Redis Stream 配置
  public static final String REMOTE_STREAM_KEY = "bloom_remote_stream";
  public static final String REMOTE_CONSUMER_SYNC_GROUP = "remote_sync_group";

  // bloom filter 配置
  /**
   * 误判率与内存使用关系
   *
   * 1% 误判率：每个元素约需 9.6 bits
   * 0.1% 误判率：每个元素约需 14.4 bits
   * 0.01% 误判率：每个元素约需 19.2 bits
   *
   * 选择1%误判率的理由：
   * 1. 内存效率高：2.16亿元素约占用260MB
   * 2. 性能影响小：1%误判不会显著影响系统性能
   * 3. 成本效益佳：内存成本与性能收益平衡最优
   */
  public static final double FALSE_PROBABILITY = 0.01;

  /**
   * 每个时间片预期容量 (6小时 * 1万TPS * 3600秒 = 2.16亿)
   * 时间分片容量计算
   *
   * 业务场景：100万QPS短链系统
   * 时间分片：6小时
   * 预期TPS：100万/秒
   *
   * 计算公式：
   * 单个时间片容量 = TPS × 时间片秒数 × 安全系数
   * = 1,000,000 × (6 × 3600) × 1.5
   * = 1,000,000 × 21,600 × 1.5
   * = 32,400,000,000 ≈ 324亿
   *
   * 但考虑内存限制，设置为2.16亿（满足99%+场景）
   */
  public static final long EXPECTED_INSERTIONS = 216_000_000L;

  // MACHINE
  public static final String MACHINE_ID_KEY = "shortlink:machine-id-registry";
  public static final String MACHINE_ID_LOCK = "shortlink:machine-id-lock";
  // 10位机器ID的最大值
  public static final long MAX_MACHINE_ID = 1023;
}