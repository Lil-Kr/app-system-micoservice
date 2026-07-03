package org.cy.micoservice.app.gateway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author: Lil-K
 * @Date: 2025/11/24
 * @Description:
 */
@Repository
public interface RouteConfigMapper extends BaseMapper<RouteConfig> {

  List<RouteConfig> queryRouteConfigAllValidaList(Integer status);

  List<RouteConfig> queryInConfigIds(@Param("configIds") Set<Long> saveConfigIds);
}