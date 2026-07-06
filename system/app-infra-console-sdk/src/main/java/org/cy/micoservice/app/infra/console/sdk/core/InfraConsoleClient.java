package org.cy.micoservice.app.infra.console.sdk.core;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.infra.console.facade.dto.req.RouteConfigQueryReqDTO;
import org.cy.micoservice.app.infra.console.facade.dto.req.RouteConfigSaveReqDTO;
import org.cy.micoservice.app.infra.console.sdk.config.FeignClientFactory;
import org.cy.micoservice.app.infra.console.sdk.config.NacosServiceDiscovery;
import org.cy.micoservice.app.infra.console.sdk.config.SdkProperties;
import org.cy.micoservice.app.infra.console.sdk.http.InfraConsoleFacade;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Lil-K
 * @Date: Created at 2025/10/5
 * @Description: 基础控制台client
 */
@Slf4j
@Component
public class InfraConsoleClient {

  private SdkProperties sdkProperties;
  private NacosServiceDiscovery nacosServiceDiscovery;
  private InfraConsoleFacade infraConsoleFacade;

  public InfraConsoleClient(SdkProperties sdkProperties) {
    this.sdkProperties = sdkProperties;
  }

  /**
   * init openfeign factory
   * @throws Exception
   */
  public void init() throws Exception {
    log.info("sdkProperties: {}", sdkProperties);
    // 1. 初始化 Nacos 服务发现
    this.nacosServiceDiscovery = new NacosServiceDiscovery(sdkProperties.getNacosAddress(),
      sdkProperties.getNacosNamespace(),
      sdkProperties.getNacosUser(),
      sdkProperties.getNacosPwd());

    // 2. create Feign client factory
    FeignClientFactory factory = new FeignClientFactory(nacosServiceDiscovery);

    // 3. create call client interface api
    this.infraConsoleFacade = factory.createClient(InfraConsoleFacade.class, sdkProperties.getInfraConsoleServiceName(), sdkProperties.getInfraConsoleServiceGroup(), sdkProperties.getClientName());
    log.info("InfraConsoleClient init success");
  }

  private InfraConsoleFacade getInfraConsoleFacade() {
    return this.infraConsoleFacade;
  }

  /**
   * create gateway route config API
   * @param request
   * @return
   */
  public ApiResp<Long> createRouteConfig(RouteConfigSaveReqDTO request) {
    return this.getInfraConsoleFacade().createRouteConfig(request);
  }

  /**
   * query all route list API
   * @param req
   * @return
   */
  public Set<String> routeList(RouteConfigQueryReqDTO req) {
    ApiResp<List<RouteConfig>> resp = this.getInfraConsoleFacade().routeList(req);
    return Optional.ofNullable(resp.getData())
      .orElse(Collections.emptyList())
      .stream()
      .map(RouteConfig::getPath)
      .collect(Collectors.toSet());
  }
}