package org.cy.micoservice.app.gateway.service.impl;

import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.gateway.facade.enums.GatewayStatusEnum;
import org.cy.micoservice.app.gateway.dao.RouteConfigMapper;
import org.cy.micoservice.app.gateway.service.RouteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author: Lil-K
 * @Date: 2025/11/24
 * @Description:
 */
@Service
public class RouteConfigServiceImpl implements RouteConfigService {

  @Autowired
  private RouteConfigMapper routeConfigMapper;

  @Override
  public List<RouteConfig> queryRouteConfigAllValidaList() {
    return routeConfigMapper.queryRouteConfigAllValidaList(GatewayStatusEnum.VALID.getCode());
  }

  @Override
  public List<RouteConfig> queryInConfigIds(Set<Long> saveConfigIds) {
    return routeConfigMapper.queryInConfigIds(saveConfigIds);
  }
}