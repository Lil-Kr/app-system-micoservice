package org.cy.micoservice.app.infra.console.service.interfaces.gateway;

import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.entity.gateway.model.LogPrintStrategy;
import org.cy.micoservice.app.infra.console.vo.req.gateway.LogPrintStrategyAddReq;
import org.cy.micoservice.app.infra.console.vo.req.gateway.LogPrintStrategyEditReq;
import org.cy.micoservice.app.infra.console.vo.req.gateway.LogPrintStrategyPageReq;
import org.cy.micoservice.app.infra.console.vo.req.gateway.RouteConfigLogDelReq;

/**
 * @Author: Lil-K
 * @Date: 2025/12/1
 * @Description: 打印日志的策略 service
 */
public interface LogPrintStrategyService {

  /**
   * 分页查询
   * @return
   */
  ApiPageResult<LogPrintStrategy> pagePrintStrategyList(LogPrintStrategyPageReq req);

  /**
   * 添加路由日志打印策略
   */
  ApiResp<String> add(LogPrintStrategyAddReq req);

  /**
   * 编辑 路由日志打印策略
   * @param req
   * @return
   */
  ApiResp<String> edit(LogPrintStrategyEditReq req);

  /**
   * 删除 路由日志打印策略
   * @param req
   * @return
   */
  ApiResp<String> delete(RouteConfigLogDelReq req);
}