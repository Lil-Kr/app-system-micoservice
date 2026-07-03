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

  /**
   * switch traffic routing config
   */
  @Value("${dynamic.read.rate:0.5}")
  private Double dynamicReadRate;

  @Value("${dynamic.write.rate:0}")
  private Double dynamicWriteRate;

  // ==================== ES index ====================
  @Value("${es.user.follower.index:app.user.user-follower-relation}")
  private String userFollowerRelationEsIndex;

  @Value("${es.user.phone.index:app.user.user-phone-relation}")
  private String userPhoneRelationEsIndex;

}