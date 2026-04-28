package org.cy.micoservice.app.mall.pay.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Lil-K
 * @Date: 2026/4/15
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MallPayApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallPayApiApplication.class, args);
  }
}