package org.cy.micoservice.app.infra.console.service.interfaces.gateway;

import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.entity.gateway.model.RouteChangeLog;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.infra.console.vo.req.gateway.*;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/11/25
 * @Description:
 */
public interface RouteConfigService {

  ApiPageResult<RouteConfig> pageRouteConfigList(RouteConfigQueryPageReq req);

  ApiResp<List<RouteConfig>> routeConfigList(RouteConfigQueryReq req);
  ApiResp<List<RouteConfig>> routeConfigInternalList(RouteConfigQueryReq req);

  ApiResp<Long> create(RouteConfigAddReq req) throws Exception;

  ApiResp<Long> createInternal(RouteConfigAddReq req) throws Exception;

  ApiResp<String> edit(RouteConfigEditReq req) throws Exception;

  ApiResp<String> delete(RouteConfigDelReq req) throws Exception;

  ApiResp<List<RouteChangeLog>> getConfigLog(Long configId);

  ApiResp<List<RouteConfig>> getAppNameList();
}