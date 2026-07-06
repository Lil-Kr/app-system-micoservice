package org.cy.micoservice.app.framework.web.starter.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Lil-K
 * @Date: 2025/11/23
 * @Description: web配置类
 */
@AutoConfiguration
public class WebAutoConfiguration implements WebMvcConfigurer {

  @Bean
  public AuthInterceptor authInterceptor() {
    return new AuthInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor());
  }
}