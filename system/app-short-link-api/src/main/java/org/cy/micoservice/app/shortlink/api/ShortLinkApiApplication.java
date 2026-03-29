package org.cy.micoservice.app.shortlink.api;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Lil-K
 * @Date: 2026/2/15
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableMethodCache(basePackages = "org.cy.micoservice.app.shortlink.api.service.impl")
public class ShortLinkApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShortLinkApiApplication.class, args);
  }
}