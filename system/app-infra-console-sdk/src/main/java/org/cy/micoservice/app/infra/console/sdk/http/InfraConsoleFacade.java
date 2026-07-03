package org.cy.micoservice.app.infra.console.sdk.http;

import feign.Headers;
import feign.RequestLine;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.infra.facade.dto.RouteConfigQueryListReqDTO;
import org.cy.micoservice.app.infra.facade.dto.RouteConfigSaveReqDTO;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: Created at 2025/10/5
 * @Description: 统一控制台接口 facade 定义
 */
public interface InfraConsoleFacade {

  @RequestLine("POST /api/route/config/createInternal")
  @Headers("Content-Type: application/json")
  ApiResp<Long> createRouteConfig(RouteConfigSaveReqDTO req);

  @RequestLine("POST /api/route/config/listInternal")
  @Headers("Content-Type: application/json")
  ApiResp<List<RouteConfig>> routeList(RouteConfigQueryListReqDTO req);
}
