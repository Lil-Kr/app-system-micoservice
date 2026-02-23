package org.cy.micoservice.app.shortlink.provider.config;

import lombok.Data;
import org.cy.micoservice.app.shortlink.provider.service.ShortCodeService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "shortlink.code")
public class ShortCodeConfig {

  /**
   * 短链生成策略
   * DISTRIBUTED_ID: 基于分布式ID（推荐）
   * URL_HASH_DETERMINISTIC: 基于URL哈希（确定性）
   * URL_HASH_RANDOM: 基于URL哈希（带随机性）
   */
  private GenerationStrategy strategy = GenerationStrategy.DISTRIBUTED_ID;

  /**
   * 短链长度
   */
  private int length = 8;

  /**
   * 最大重试次数
   */
  private int maxRetries = 3;


  public enum GenerationStrategy {
    DISTRIBUTED_ID {
      @Override
      public String generate(String url, ShortCodeService service) {
        return service.generateUniqueCode();
      }
    },
    URL_HASH_DETERMINISTIC {
      @Override
      public String generate(String url, ShortCodeService service) {
        return service.generateByUrlHashDeterministic(url);
      }
    },
    URL_HASH_RANDOM {
      @Override
      public String generate(String url, ShortCodeService service) {
        return service.generateByUrlHashWithRandomness(url);
      }
    };

    public abstract String generate(String url, ShortCodeService service);
  }
}