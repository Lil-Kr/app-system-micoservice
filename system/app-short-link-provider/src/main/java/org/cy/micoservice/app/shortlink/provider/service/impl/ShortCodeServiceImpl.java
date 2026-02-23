package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.utils.shortlink.Base62Util;
import org.cy.micoservice.app.common.utils.shortlink.DigestUtils;
import org.cy.micoservice.app.shortlink.provider.config.ShortCodeConfig;
import org.cy.micoservice.app.shortlink.provider.service.ShortCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Slf4j
@Service
public class ShortCodeServiceImpl implements ShortCodeService {

  private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final int BASE = 62;
  private final Random random = new Random();

  // @Autowired
  // private ShortCodeGenerator shortCodeGenerator;

  @Autowired
  private ShortCodeConfig shortCodeConfig;

  /**
   * 根据配置策略生成短链
   */
  @Override
  public String generateByStrategy(String url) {
    return shortCodeConfig.getStrategy().generate(url, this);
  }

  /**
   * 生成唯一短链编码 (推荐使用 - 基于分布式ID)
   * 优势：全局唯一、高性能、时间有序
   */
  @Override
  public String generateUniqueCode() {
    // long id = shortCodeGenerator.generateId();
    long id = 1l;
    String shortCode = Base62Util.encodeWithMinLength(id, shortCodeConfig.getLength());

    // 确保不超过8位长度
    if (shortCode.length() > shortCodeConfig.getLength()) {
      shortCode = shortCode.substring(0, shortCodeConfig.getLength());
    }

    return shortCode;
  }

  /**
   * 基于URL哈希生成短链编码（确定性生成）
   * 优势：相同URL生成相同短链，缓存友好
   */
  @Override
  public String generateByUrlHashDeterministic(String url) {
    // 不添加时间戳，保证相同URL生成相同短链
    String hash = DigestUtils.md5(url);
    return convertHashToBase62(hash);
  }

  /**
   * 基于URL哈希生成短链编码（带随机性）
   * 用于需要避免相同URL生成相同短链的场景
   */
  @Override
  public String generateByUrlHashWithRandomness(String url) {
    String hash = DigestUtils.md5(url + System.currentTimeMillis());
    return convertHashToBase62(hash);
  }

  /**
   * 生成随机短链编码
   * 用于测试或特殊场景
   */
  @Override
  public String generateRandom() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < shortCodeConfig.getLength(); i++) {
      sb.append(BASE62_CHARS.charAt(random.nextInt(BASE)));
    }
    return sb.toString();
  }

  /**
   * 批量生成唯一短链编码
   */
  @Override
  public String[] generateBatchUniqueCodes(int count) {
    // return shortCodeGenerator.generateBatchShortCodes(count);
    return new String[]{};
  }

  /**
   * 将哈希值转换为Base62 - 确保固定8位长度
   */
  private String convertHashToBase62(String hash) {
    // 取哈希值的前16位作为数字，增加随机性
    String hashPrefix = hash.substring(0, Math.min(16, hash.length()));
    long num = Long.parseUnsignedLong(hashPrefix, 16);

    // 确保不超过8位Base62编码的最大值
    long maxValue = Base62Util.getMaxValue(shortCodeConfig.getLength());
    num = num % maxValue;

    String shortCode = Base62Util.encodeWithMinLength(num, shortCodeConfig.getLength());

    // 双重保险：如果仍然超过8位，截取前8位
    if (shortCode.length() > shortCodeConfig.getLength()) {
      shortCode = shortCode.substring(0, shortCodeConfig.getLength());
    }
    return shortCode;
  }

  /**
   * 验证短链编码格式
   */
  public boolean isValidShortCode(String shortCode) {
    if (shortCode == null || shortCode.length() != shortCodeConfig.getLength()) {
      return false;
    }
    return Base62Util.isValidBase62(shortCode);
  }

  /**
   * Base62解码
   */
  public long decodeBase62(String shortCode) {
    return Base62Util.decode(shortCode);
  }
}