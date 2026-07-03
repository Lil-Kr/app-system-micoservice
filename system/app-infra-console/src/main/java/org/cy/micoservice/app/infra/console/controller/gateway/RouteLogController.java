package org.cy.micoservice.app.infra.console.controller.gateway;

import jakarta.validation.Valid;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;
import org.cy.micoservice.app.entity.gateway.model.LogPrintStrategy;
import org.cy.micoservice.app.infra.console.vo.req.gateway.LogPrintStrategyAddReq;
import org.cy.micoservice.app.infra.console.vo.req.gateway.LogPrintStrategyEditReq;
import org.cy.micoservice.app.infra.console.vo.req.gateway.LogPrintStrategyPageReq;
import org.cy.micoservice.app.infra.console.vo.req.gateway.RouteConfigLogDelReq;
import org.cy.micoservice.app.framework.web.starter.annotations.NoAuthCheck;
import org.cy.micoservice.app.framework.web.starter.web.RequestContext;
import org.cy.micoservice.app.infra.console.service.interfaces.gateway.LogPrintStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lil-K
 * @Date: 2025/12/1
 * @Description:
 */
@RestController
@RequestMapping("/route/log")
public class RouteLogController {

  @Autowired
  private LogPrintStrategyService logPrintStrategyService;

  @NoAuthCheck
  @PostMapping("/page")
  public ApiResp<ApiPageResult<LogPrintStrategy>> pageRouteConfigList(@RequestBody @Validated({BasePageReq.GroupPageQuery.class}) LogPrintStrategyPageReq req) {
    ApiPageResult<LogPrintStrategy> logPrintStrategyApiPageResult = logPrintStrategyService.pagePrintStrategyList(req);
    return ApiResp.success(logPrintStrategyApiPageResult);
  }

  @NoAuthCheck
  @PostMapping("/add")
  public ApiResp<String> add(@RequestBody @Valid LogPrintStrategyAddReq req) {
    req.setAdminId(RequestContext.getUserId());
    return logPrintStrategyService.add(req);
  }

  @NoAuthCheck
  @PostMapping("/edit")
  public ApiResp<String> edit(@RequestBody @Valid LogPrintStrategyEditReq req) {
    req.setAdminId(RequestContext.getUserId());
    return logPrintStrategyService.edit(req);
  }

  @NoAuthCheck
  @DeleteMapping("/delete")
  public ApiResp<String> delete(@Valid RouteConfigLogDelReq req) {
    req.setAdminId(RequestContext.getUserId());
    return logPrintStrategyService.delete(req);
  }
}