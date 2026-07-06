package org.cy.micoservice.app.infra.console.controller.gateway;

import jakarta.validation.Valid;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;
import org.cy.micoservice.app.entity.gateway.model.RouteChangeLog;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.infra.console.aspect.annotations.InternalCallCheck;
import org.cy.micoservice.app.infra.console.aspect.holder.RequestHolder;
import org.cy.micoservice.app.infra.console.service.interfaces.gateway.RouteConfigService;
import org.cy.micoservice.app.infra.console.vo.req.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/11/25
 * @Description:
 */
@RestController
@RequestMapping("/gateway/config")
public class RouteConfigController {

  @Autowired
  private RouteConfigService routeConfigService;

  /**
   *
   * @param req
   * @return
   */
  @PostMapping("/pageList")
  public ApiResp<ApiPageResult<RouteConfig>> pageList(@RequestBody @Validated({BasePageReq.GroupPageQuery.class}) RouteConfigQueryPageReq req) {
    ApiPageResult<RouteConfig> routeConfigApiPageResult = routeConfigService.pageRouteConfigList(req);
    return ApiResp.success(routeConfigApiPageResult);
  }

  /**
   * @param req
   * @return
   * @throws Exception
   */
  @PostMapping("/list")
  public ApiResp<List<RouteConfig>> list(@RequestBody @Valid RouteConfigQueryReq req) throws Exception {
    return routeConfigService.routeConfigList(req);
  }

  /**
   * create route config
   * @param req
   * @return
   * @throws Exception
   */
  @PostMapping("/create")
  public ApiResp<Long> create(@RequestBody @Validated({RouteConfigAddReq.GroupRouteConfigAdd.class}) RouteConfigAddReq req) throws Exception {
    req.setAdminId(RequestHolder.getCurrentAdmin().getId());
    return routeConfigService.create(req);
  }

  /**
   *
   * @param req
   * @return
   * @throws Exception
   */
  @PostMapping("/update")
  public ApiResp<String> update(@RequestBody @Valid RouteConfigEditReq req) throws Exception {
    req.setAdminId(RequestHolder.getCurrentAdmin().getId());
    return routeConfigService.update(req);
  }

  /**
   * @param req
   * @return
   * @throws Exception
   */
  @DeleteMapping("/delete")
  public ApiResp<String> delete(@Valid RouteConfigDelReq req) throws Exception {
    req.setAdminId(RequestHolder.getCurrentAdmin().getId());
    return routeConfigService.delete(req);
  }

  /**
   * @return
   */
  @GetMapping("/appNameList")
  public ApiResp<List<RouteConfig>> appNameList() {
    return routeConfigService.appNameList();
  }

  /**
   * @param req
   * @return
   */
  @GetMapping("/configLogList")
  public ApiResp<List<RouteChangeLog>> configLogList(@Valid RouteConfigGetReq req) {
    return routeConfigService.routeConfigLogList(req.getConfigId());
  }

  /** ============================ infra-console-sdk ============================ **/
  @InternalCallCheck
  @PostMapping("/listInternal")
  public ApiResp<List<RouteConfig>> listInternal(@RequestBody @Valid RouteConfigQueryReq req) throws Exception {
    return routeConfigService.routeConfigInternalList(req);
  }

  @InternalCallCheck
  @PostMapping("/createInternal")
  public ApiResp<Long> createInternal(@RequestBody @Validated({RouteConfigAddReq.GroupRouteConfigAddInternal.class}) RouteConfigAddReq req) throws Exception {
    // req.setAdminId(RequestHolder.getCurrentAdmin().getId());
    req.setAdminId(1l);
    return routeConfigService.createInternal(req);
  }
}