package org.cy.micoservice.app.infra.console.sdk.http;

import feign.Headers;
import feign.RequestLine;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.infra.console.facade.dto.req.RouteConfigQueryReqDTO;
import org.cy.micoservice.app.infra.console.facade.dto.req.RouteConfigSaveReqDTO;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: Created at 2025/10/5
 * @Description: 统一控制台接口 facade 定义
 */
public interface InfraConsoleFacade {

  @RequestLine(value = "POST /api/gateway/config/createInternal")
  @Headers(value = {"Content-Type: application/json"})
  ApiResp<Long> createRouteConfig(RouteConfigSaveReqDTO req);

  @RequestLine(value = "POST /api/gateway/config/listInternal")
  @Headers(value = {"Content-Type: application/json"})
  ApiResp<List<RouteConfig>> routeList(RouteConfigQueryReqDTO req);
}
