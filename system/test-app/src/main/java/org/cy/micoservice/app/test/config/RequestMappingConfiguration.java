package org.cy.micoservice.app.test.config;

import com.alibaba.fastjson2.JSONArray;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cy.micoservice.app.common.enums.biz.AuthTypeEnum;
import org.cy.micoservice.app.infra.console.facade.constants.InfraConsoleSdkConstants;
import org.cy.micoservice.app.infra.console.facade.dto.req.RouteConfigQueryReqDTO;
import org.cy.micoservice.app.infra.console.facade.dto.req.RouteConfigSaveReqDTO;
import org.cy.micoservice.app.infra.console.sdk.core.InfraConsoleClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Lil-K
 * @Date: 2025/11/27
 * @Description: scan all of api from controller
 * 这个配置每个api层都需要独立写, 不能写到这个sdk中, 这里为了方便测试
 */
@Slf4j
// @Component
public class RequestMappingConfiguration implements CommandLineRunner {

  @Resource
  private RequestMappingHandlerMapping requestMappingHandlerMapping;
  @Resource
  private InfraConsoleClient infraConsoleClient;
  @Resource
  private TestApplicationProperties applicationProperties;

  @Override
  public void run(String... args) throws Exception {
    RouteConfigQueryReqDTO reqDTO = new RouteConfigQueryReqDTO();
    reqDTO.setAppName(applicationProperties.getAppName());
    reqDTO.setUri(InfraConsoleSdkConstants.LB_SERVICE_PREFIX + applicationProperties.getAppName());
    Set<String> routeConfigs = infraConsoleClient.routeList(reqDTO);
    routeConfigs.add(InfraConsoleSdkConstants.API_ERROR_SIGN_PATH);

    Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
    Set<RouteConfigSaveReqDTO> routeConfigSaveReqSet = handlerMethods.keySet().stream()
      .filter(requestMappingInfo -> {
        String requestPath = requestMappingInfo.getPathPatternsCondition().getPatternValues().stream().findAny().map(String::toString).orElse("");
        String fullRequestPath = applicationProperties.getServletPath() + requestPath;
        if (StringUtils.isBlank(requestPath) || routeConfigs.contains(requestPath) || routeConfigs.contains(fullRequestPath)) {
          return false;
        }

        String requestMethod = requestMappingInfo.getMethodsCondition().getMethods().stream().findFirst().map(Enum::name).orElse("");
        if (StringUtils.isBlank(requestMethod)) {
          return false;
        }
        return true;
      }).map(requestMappingInfo -> {
        String requestPath = requestMappingInfo.getPathPatternsCondition().getPatternValues().stream().findAny().map(String::toString).orElse("");
        String requestMethod = requestMappingInfo.getMethodsCondition().getMethods().stream().findFirst().map(Enum::name).orElse("");
        RouteConfigSaveReqDTO routeConfigSaveReq = new RouteConfigSaveReqDTO();
        routeConfigSaveReq.setAppName(applicationProperties.getAppName());
        routeConfigSaveReq.setSchema(InfraConsoleSdkConstants.HTTP_PROTOCOL);
        routeConfigSaveReq.setMethod(requestMethod);
        routeConfigSaveReq.setUri(InfraConsoleSdkConstants.LB_SERVICE_PREFIX + applicationProperties.getAppName());
        routeConfigSaveReq.setPath(applicationProperties.getServletPath() + requestPath);
        routeConfigSaveReq.setAuthType(AuthTypeEnum.JWT.getCode());
        return routeConfigSaveReq;
      }).collect(Collectors.toSet());

    if (CollectionUtils.isEmpty(routeConfigSaveReqSet)) {
      log.info("don't need update route config anymore");
      return;
    }
    log.debug("routeConfigSaveReqSet: {}", JSONArray.toJSONString(routeConfigSaveReqSet));
    // insert into DB
    for (RouteConfigSaveReqDTO request : routeConfigSaveReqSet) {
      // ApiResp<Long> routeConfig = infraConsoleClient.createRouteConfig(request);
      // log.info("route config create response: {}", JSONObject.toJSONString(routeConfig));
    }
  }
}
