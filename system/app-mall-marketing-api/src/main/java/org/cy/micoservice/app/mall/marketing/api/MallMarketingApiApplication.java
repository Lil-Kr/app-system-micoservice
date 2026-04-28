package org.cy.micoservice.app.mall.marketing.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Lil-K
 * @Date: 2025/12/15
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MallMarketingApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallMarketingApiApplication.class, args);
  }
}
