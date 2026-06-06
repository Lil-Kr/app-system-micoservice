package org.cy.micoservice.app.user.provider.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description: application config
 */
@Configuration
@RefreshScope
@Data
public class ApplicationProperties {

  /**
   * 同步用户数据的topic
   * app-sync-user-info-topic
   */
  @Value("${sync.user.info.topic:}")
  private String syncUserInfoTopic;
}