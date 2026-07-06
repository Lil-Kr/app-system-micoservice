package org.cy.micoservice.app.infra.console.service.impl.gateway;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.enums.biz.DeleteStatusEnum;
import org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum;
import org.cy.micoservice.app.common.utils.AssertUtil;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.entity.gateway.model.RouteChangeLog;
import org.cy.micoservice.app.entity.gateway.model.RouteConfig;
import org.cy.micoservice.app.framework.id.starter.service.IdService;
import org.cy.micoservice.app.gateway.facade.dto.gateway.RouteChangeBodyDTO;
import org.cy.micoservice.app.gateway.facade.enums.GatewayChangeEventEnum;
import org.cy.micoservice.app.gateway.facade.enums.GatewayDeletedEnum;
import org.cy.micoservice.app.gateway.facade.enums.GatewaySchemaEnum;
import org.cy.micoservice.app.gateway.facade.enums.GatewayStatusEnum;
import org.cy.micoservice.app.infra.console.config.InfraCacheKeyBuilder;
import org.cy.micoservice.app.infra.console.dao.gateway.RouteConfigMapper;
import org.cy.micoservice.app.infra.console.facade.constants.InfraConsoleConstant;
import org.cy.micoservice.app.infra.console.facade.constants.InfraConsoleSdkConstants;
import org.cy.micoservice.app.infra.console.service.interfaces.gateway.NacosService;
import org.cy.micoservice.app.infra.console.service.interfaces.gateway.RouteConfigChangeLogService;
import org.cy.micoservice.app.infra.console.service.interfaces.gateway.RouteConfigService;
import org.cy.micoservice.app.infra.console.vo.req.gateway.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Lil-K
 * @Date: 2025/11/25
 * @Description:
 */
@Slf4j
@Service
public class RouteConfigServiceImpl implements RouteConfigService, InitializingBean {

  @Autowired
  private RouteConfigMapper routeConfigMapper;
  @Autowired
  private RouteConfigChangeLogService routeConfigChangeLogService;
  @Autowired
  private NacosService nacosService;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  @Autowired
  private InfraCacheKeyBuilder infraCacheKeyBuilder;
  @Autowired
  private IdService idService;
  // cache key
  private String LOCK_KEY;
  private String LOCK_VALUE;

  @Override
  public void afterPropertiesSet() throws Exception {
    this.LOCK_KEY = infraCacheKeyBuilder.changeRouteConfigKey(InfraConsoleConstant.CHANGE_ROUTE_CONFIG_KEY);
    this.LOCK_VALUE = InfraConsoleConstant.CHANGE_ROUTE_CONFIG_VALUE;
  }

  /**
   * query by route config
   * @param req
   * @return
   */
  @Override
  public ApiPageResult<RouteConfig> pageRouteConfigList(RouteConfigQueryPageReq req) {
    req.setDeleted(DeleteStatusEnum.ACTIVE.getCode());
    List<RouteConfig> pageList = routeConfigMapper.pageRouteConfigList(req);
    Integer count = routeConfigMapper.countPageRouteConfigList(req);
    if (CollectionUtils.isEmpty(pageList)) {
      return ApiPageResult.emptyPage();
    }
    return new ApiPageResult<>(pageList, count);
  }

  /**
   * @param req
   * @return
   */
  @Override
  public ApiResp<List<RouteConfig>> routeConfigList(RouteConfigQueryReq req) {
    List<RouteConfig> configs = routeConfigMapper.queryRouteConfigList(req);
    return ApiResp.success(configs);
  }

  /**
   * insert --> init config statue is invalid
   * @param req
   * @return
   * @throws NacosException
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ApiResp<Long> create(RouteConfigAddReq req) throws Exception {
    /**
     * 加入分布式锁, 3秒释放
     */
    Boolean lockStatus = redisTemplate.opsForValue().setIfAbsent(this.LOCK_KEY, this.LOCK_VALUE, 5, TimeUnit.SECONDS);
    AssertUtil.isTrue(lockStatus, ApiReturnCodeEnum.SYSTEM_ERROR);

    if (GatewaySchemaEnum.DUBBO.getCode().equals(req.getSchema())) {
      req.setUri(InfraConsoleSdkConstants.DUBBO_URL_PREFIX + req.getProviderInterface() + "#" + req.getProviderInterfaceMethod());
    } else {
      req.setUri(InfraConsoleSdkConstants.LB_SERVICE_PREFIX + req.getAppName());
    }

    RouteConfig routeConfig = BeanCopyUtils.convert(req, RouteConfig.class);
    routeConfig.setId(idService.getId());
    routeConfig.setStatus(GatewayStatusEnum.INVALID.getCode());
    routeConfig.setCreateId(req.getAdminId());
    routeConfig.setUpdateId(req.getAdminId());
    routeConfig.setDeleted(GatewayDeletedEnum.ACTIVE.getCode());
    LocalDateTime now = DateUtil.localDateTimeNow();
    routeConfig.setCreateTime(now);
    routeConfig.setUpdateTime(now);
    int insert = routeConfigMapper.insert(routeConfig);

    // insert route change log
    RouteChangeLog routeChangeLog = RouteChangeLog.builder()
      .id(idService.getId())
      .configId(routeConfig.getId())
      .changeEvent(GatewayChangeEventEnum.INSERT.getCode())
      .version(0L)
      .build();

    RouteChangeBodyDTO routeChangeBodyDTO = new RouteChangeBodyDTO(RouteConfig.builder().build(), routeConfig);
    routeChangeLog.setChangeBody(JSONObject.toJSONString(routeChangeBodyDTO));
    routeChangeLog.setCreateId(req.getAdminId());
    routeChangeLog.setUpdateId(req.getAdminId());
    routeChangeLog.setCreateTime(now);
    routeChangeLog.setUpdateTime(now);
    routeChangeLog.setDeleted(GatewayDeletedEnum.ACTIVE.getCode());
    Integer insertLog = routeConfigChangeLogService.create(routeChangeLog);

    // release lock
    redisTemplate.delete(LOCK_KEY);
    return insert > 0 && insertLog > 0 ? ApiResp.success(routeConfig.getId()) : ApiResp.failure(ApiReturnCodeEnum.ADD_ERROR);
  }

  /**
   *
   * @param req
   * @return
   * @throws NacosException
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ApiResp<String> update(RouteConfigEditReq req) throws NacosException {
    /**
     * 加入分布式锁, 3秒释放
     */
    Boolean lockStatus = redisTemplate.opsForValue().setIfAbsent(this.LOCK_KEY, this.LOCK_VALUE, 5, TimeUnit.SECONDS);
    AssertUtil.isTrue(lockStatus, ApiReturnCodeEnum.SYSTEM_ERROR);

    RouteConfig before = routeConfigMapper.selectById(req.getId());
    if (Objects.isNull(before)) {
      return ApiResp.failure(ApiReturnCodeEnum.INFO_NOT_EXIST);
    }

    if (GatewaySchemaEnum.DUBBO.getCode().equals(req.getSchema())) {
      req.setUri(InfraConsoleSdkConstants.DUBBO_URL_PREFIX + req.getProviderInterface() + "#" + req.getProviderInterfaceMethod());
    } else {
      req.setUri(InfraConsoleSdkConstants.LB_SERVICE_PREFIX + req.getAppName());
    }

    RouteConfig after = BeanCopyUtils.convert(before, RouteConfig.class);
    after.setUri(req.getUri());
    after.setDubboInvokeParamClass(req.getDubboInvokeParamClass());
    after.setStatus(req.getStatus());
    after.setAuthType(req.getAuthType());
    after.setUpdateId(req.getAdminId());
    after.setUpdateTime(DateUtil.localDateTimeNow());
    int update = routeConfigMapper.updateById(after);

    // insert route change log
    RouteChangeLog routeChangeLog = BeanCopyUtils.convert(after, RouteChangeLog.class);
    routeChangeLog.setId(idService.getId());
    routeChangeLog.setConfigId(before.getId());
    routeChangeLog.setChangeEvent(GatewayChangeEventEnum.UPDATE.getCode());

    RouteChangeBodyDTO routeChangeBodyDTO = new RouteChangeBodyDTO(before, after);
    routeChangeLog.setChangeBody(JSONObject.toJSONString(routeChangeBodyDTO));
    routeChangeLog.setUpdateId(req.getAdminId());
    LocalDateTime now = DateUtil.localDateTimeNow();
    routeChangeLog.setCreateTime(now);
    routeChangeLog.setUpdateTime(now);
    routeChangeLog.setDeleted(GatewayDeletedEnum.ACTIVE.getCode());
    // 触发 nacos 更新版本
    Long version = nacosService.incrVersion();
    routeChangeLog.setVersion(version);
    int insertLog = routeConfigChangeLogService.create(routeChangeLog);

    // release lock
    redisTemplate.delete(LOCK_KEY);
    return (update > 0 && insertLog > 0) ? ApiResp.success() : ApiResp.failure(ApiReturnCodeEnum.UPDATE_ERROR);
  }

  /**
   * @param req
   * @return
   * @throws NacosException
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ApiResp<String> delete(RouteConfigDelReq req) throws NacosException {
    /**
     * 加入分布式锁, 3秒释放
     */
    Boolean lockStatus = redisTemplate.opsForValue().setIfAbsent(this.LOCK_KEY, this.LOCK_VALUE, 3, TimeUnit.SECONDS);
    AssertUtil.isTrue(lockStatus, ApiReturnCodeEnum.SYSTEM_ERROR);

    RouteConfig before = routeConfigMapper.selectById(req.getId());
    if (Objects.isNull(before)) {
      return ApiResp.failure(ApiReturnCodeEnum.DEL_ERROR);
    }
    before.setDeleted(GatewayDeletedEnum.DELETED.getCode());
    int del = routeConfigMapper.updateById(before);

    // insert route change log
    RouteChangeBodyDTO routeChangeBodyDTO = new RouteChangeBodyDTO(before, RouteConfig.builder().build());
    LocalDateTime now = DateUtil.localDateTimeNow();
    // 触发 nacos 更新版本
    Long version = nacosService.incrVersion();
    RouteChangeLog routeChangeLog = RouteChangeLog.builder()
      .id(idService.getId())
      .configId(before.getId())
      .changeEvent(GatewayChangeEventEnum.DELETED.getCode())
      .changeBody(JSONObject.toJSONString(routeChangeBodyDTO))
      .createId(req.getAdminId())
      .updateId(req.getAdminId())
      .deleted(GatewayDeletedEnum.ACTIVE.getCode())
      .createTime(now)
      .updateTime(now)
      .version(version)
      .build();
    int insertLog = routeConfigChangeLogService.create(routeChangeLog);

    // release lock
    redisTemplate.delete(LOCK_KEY);
    return (del > 0 && insertLog > 0) ? ApiResp.success() : ApiResp.failure(ApiReturnCodeEnum.DEL_ERROR);
  }

  /**
   *
   * @param configId
   * @return
   */
  @Override
  public ApiResp<List<RouteChangeLog>> routeConfigLogList(Long configId) {
    List<RouteChangeLog> logList = routeConfigChangeLogService.queryRouteChangeLogList(configId);
    return ApiResp.success(logList);
  }

  /**
   *
   * @return
   */
  @Override
  public ApiResp<List<RouteConfig>> appNameList() {
    List<RouteConfig> appNameList = routeConfigMapper.queryAppNameList();
    return ApiResp.success(appNameList);
  }

  /**
   * @param req
   * @return
   * @throws Exception
   */
  @Override
  public ApiResp<Long> createInternal(RouteConfigAddReq req) throws Exception {
    return this.create(req);
  }

  /**
   * @param req
   * @return
   */
  @Override
  public ApiResp<List<RouteConfig>> routeConfigInternalList(RouteConfigQueryReq req) {
    return this.routeConfigList(req);
  }
}
