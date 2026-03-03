package org.cy.micoservice.app.shortlink.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Lil-K
 * @Date: 2026/2/27
 * @Description: 布隆过滤器线程池配置
 */
@Configuration
public class BloomFilterAsyncConfig {

  @Autowired
  private ShortLinkApiProperties properties;

  @Bean("bloomFilterExecutor")
  public Executor bloomFilterExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(properties.getCorePoolSize());
    executor.setMaxPoolSize(properties.getMaxPoolSize());
    executor.setQueueCapacity(properties.getQueueCapacity());
    executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
    executor.setThreadNamePrefix(properties.getThreadNamePrefix());
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setAwaitTerminationSeconds(properties.getAwaitTerminationSeconds());
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.initialize();
    return executor;
  }
}