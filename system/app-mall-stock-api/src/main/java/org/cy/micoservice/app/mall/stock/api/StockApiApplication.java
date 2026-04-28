package org.cy.micoservice.app.mall.stock.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Lil-K
 * @Date: 2026/4/4
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StockApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockApiApplication.class, args);
  }
}