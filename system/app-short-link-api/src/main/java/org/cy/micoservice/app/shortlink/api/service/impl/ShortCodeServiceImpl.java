package org.cy.micoservice.app.shortlink.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.utils.shortlink.Base62Util;
import org.cy.micoservice.app.common.utils.shortlink.DigestUtils;
import org.cy.micoservice.app.shortlink.api.config.ShortCodeConfig;
import org.cy.micoservice.app.shortlink.api.generator.ShortCodeGenerator;
import org.cy.micoservice.app.shortlink.api.service.ShortCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author: Lil-K
 * @Date: 2026/2/28
 * @Description:
 */
@Slf4j
@Service
public class ShortCodeServiceImpl implements ShortCodeService {

  private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final int BASE = 62;
  private final Random random = new Random();

  @Autowired
  private ShortCodeGenerator shortCodeGenerator;
  @Autowired
  private ShortCodeConfig shortCodeConfig;

  @Override
  public String generateByStrategy(String url) {
    return shortCodeConfig.getStrategy().generate(url, this);
  }

  @Override
  public String generateUniqueCode() {
    long id = shortCodeGenerator.generateId();
    String shortCode = Base62Util.encodeWithMinLength(id, shortCodeConfig.getLength());

    // 确保不超过8位长度
    if (shortCode.length() > shortCodeConfig.getLength()) {
      shortCode = shortCode.substring(0, shortCodeConfig.getLength());
    }
    return shortCode;
  }

  @Override
  public String generateByUrlHashDeterministic(String url) {
    // 不添加时间戳，保证相同URL生成相同短链
    String hash = DigestUtils.md5(url);
    return this.convertHashToBase62(hash);
  }

  @Override
  public String generateByUrlHashWithRandomness(String url) {
    String hash = DigestUtils.md5(url + System.currentTimeMillis());
    return this.convertHashToBase62(hash);
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

    // 双重保险: 如果仍然超过8位，截取前8位
    if (shortCode.length() > shortCodeConfig.getLength()) {
      shortCode = shortCode.substring(0, shortCodeConfig.getLength());
    }

    return shortCode;
  }
}
