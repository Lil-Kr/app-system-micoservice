package org.cy.micoservice.app.shortlink.api.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Lil-K
 * @Date: 2026/3/8
 * @Description:
 */
@Configuration
public class SentinelConfig {

  @Bean
  public SentinelResourceAspect sentinelResourceAspect() {
    return new SentinelResourceAspect();
  }
}