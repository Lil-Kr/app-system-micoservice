package org.cy.micoservice.app.infra.console.sdk.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: Lil-K
 * @Date: Created at 2025/10/5
 * @Description:
 */
@Data
public class SdkProperties {
  // nacos address
  @Value("${infra.console.sdk.nacos.address:}")
  private String nacosAddress;

  // nacos namespace
  @Value("${infra.console.sdk.nacos.namespace:}")
  private String nacosNamespace;

  // nacos username
  @Value("${infra.console.sdk.nacos.user:}")
  private String nacosUser;

  // nacos password
  @Value("${infra.console.sdk.nacos.pwd:}")
  private String nacosPwd;

  // service-name
  @Value("${infra.console.service-name:}")
  private String infraConsoleServiceName;

  // service-group
  @Value("${infra.console.service-group:}")
  private String infraConsoleServiceGroup;

  // client service name
  @Value("${spring.application.name:unknow}")
  private String clientName;

}
