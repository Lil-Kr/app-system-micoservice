package org.cy.micoservice.app.shortlink.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.api.service.ShardingStrategyService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Lil-K
 * @Date: 2026/2/26
 * @Description: Redis集群槽位计算、分片路由优化
 */
@Slf4j
@Service
public class ShardingStrategyServiceImpl implements ShardingStrategyService {
  private static final int REDIS_CLUSTER_SLOTS = 16384;
  private static final MessageDigest MD5_DIGEST;

  static {
    try {
      MD5_DIGEST = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5算法不可用", e);
    }
  }

  /**
   * 计算Redis集群槽位
   * 使用CRC16算法, 与Redis集群保持一致
   * @param key
   * @return
   */
  @Override
  public int calculateSlot(String key) {
    if (key == null || key.isEmpty()) {
      return 0;
    }

    // 提取Hash Tag (如果存在)
    String hashKey = this.extractHashTag(key);

    // 使用CRC16算法计算槽位
    return this.crc16(hashKey.getBytes(StandardCharsets.UTF_8)) % REDIS_CLUSTER_SLOTS;
  }

  /**
   * 提取 Hash Tag
   * 格式: {tag} - 确保相关数据在同一分片
   */
  private String extractHashTag(String key) {
    int start = key.indexOf('{');
    if (start != -1) {
      int end = key.indexOf('}', start + 1);
      if (end != -1 && end != start + 1) {
        return key.substring(start + 1, end);
      }
    }
    return key;
  }

  /**
   * CRC16算法实现 (与Redis集群一致)
   */
  private int crc16(byte[] bytes) {
    int crc = 0x0000;
    int polynomial = 0x1021;

    for (byte b : bytes) {
      for (int i = 0; i < 8; i++) {
        boolean bit = ((b >> (7 - i) & 1) == 1);
        boolean c15 = ((crc >> 15 & 1) == 1);
        crc <<= 1;
        if (c15 ^ bit) {
          crc ^= polynomial;
        }
      }
    }

    return crc & 0xffff;
  }
}
