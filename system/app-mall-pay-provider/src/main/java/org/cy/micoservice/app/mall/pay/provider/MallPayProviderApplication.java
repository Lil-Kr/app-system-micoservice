package org.cy.micoservice.app.mall.pay.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Lil-K
 * @Date: 2025/11/20
 * @Description:
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
// @MapperScan(basePackages = {"org.cy.micoservice.app.message.provider.dao"})
public class MallPayProviderApplication {

  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(MallPayProviderApplication.class);
    springApplication.setWebApplicationType(WebApplicationType.NONE);
    springApplication.run(args);
  }
}