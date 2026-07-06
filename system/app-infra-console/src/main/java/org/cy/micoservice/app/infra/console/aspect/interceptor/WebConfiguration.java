package org.cy.micoservice.app.infra.console.aspect.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Lil-K
 * @Date: 2026/7/5
 * @Description:
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Bean
  public InfraConsoleInterceptor infraConsoleInterceptor() {
    return new InfraConsoleInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(infraConsoleInterceptor());
  }
}