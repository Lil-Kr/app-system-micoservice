package org.cy.micoservice.app.infra.console.dao.gateway;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.infra.console.vo.req.gateway.RouteConfigQueryPageReq;
import org.cy.micoservice.app.infra.console.vo.req.gateway.RouteConfigQueryReq;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/11/24
 * @Description:
 */
@Repository
public interface RouteConfigMapper extends BaseMapper<RouteConfig> {

  List<RouteConfig> routeConfigAllValidaList(Integer status);

  List<RouteConfig> queryInConfigIds(@Param("configIds") Collection<Long> saveConfigIds);

  List<RouteConfig> pageRouteConfigList(@Param("param") RouteConfigQueryPageReq req);

  Integer countPageRouteConfigList(@Param("param") RouteConfigQueryPageReq req);

  List<RouteConfig> queryRouteConfigList(@Param("param") RouteConfigQueryReq req);

  List<RouteConfig> queryAppNameList();
}