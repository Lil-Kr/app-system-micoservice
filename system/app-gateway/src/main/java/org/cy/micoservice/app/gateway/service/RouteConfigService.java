package org.cy.micoservice.app.gateway.service;


import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import java.util.List;
import java.util.Set;

/**
 * @Author: Lil-K
 * @Date: 2025/11/24
 * @Description:
 */
public interface RouteConfigService {

  /**
   * query all VALID route config list
   * @return
   */
  List<RouteConfig> queryRouteConfigAllValidaList();

  /**
   * query config list by config_ids
   * @param saveConfigIds
   * @return
   */
  List<RouteConfig> queryInConfigIds(Set<Long> saveConfigIds);

}