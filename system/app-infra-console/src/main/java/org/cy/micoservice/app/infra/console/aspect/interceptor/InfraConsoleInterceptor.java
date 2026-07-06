package org.cy.micoservice.app.infra.console.aspect.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum;
import org.cy.micoservice.app.common.exception.BizException;
import org.cy.micoservice.app.entity.infra.console.model.sys.SysAdmin;
import org.cy.micoservice.app.infra.console.aspect.annotations.IgnoreAuthCheck;
import org.cy.micoservice.app.infra.console.aspect.annotations.InternalCallCheck;
import org.cy.micoservice.app.infra.console.aspect.holder.RequestHolder;
import org.cy.micoservice.app.infra.console.dao.permission.SysAdminMapper;
import org.cy.micoservice.app.infra.console.facade.constants.InfraConsoleConstant;
import org.cy.micoservice.app.infra.console.service.interfaces.permission.PermissionCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: Lil-K
 * @Date: 2026/7/4
 * @Description:
 */
@Slf4j
public class InfraConsoleInterceptor implements HandlerInterceptor {

  @Autowired
  private SysAdminMapper adminMapper;
  @Autowired
  private PermissionCacheService permissionCacheService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (!(handler instanceof HandlerMethod handlerMethod)) {
      log.debug("invalid request, uri: {}", request.getRequestURI());
      throw new BizException("internal API can not allow to call", ApiReturnCodeEnum.INVALID_REQUEST);
    }

    Method method = handlerMethod.getMethod();
    log.debug("request uri: {}", request.getRequestURI());

    InternalCallCheck internalCallCheck = method.getAnnotation(InternalCallCheck.class);
    if (Objects.nonNull(internalCallCheck)) {
      String internal = request.getHeader(InfraConsoleConstant.INTERNAL_CALL_HEADER);
      if (StringUtils.isBlank(internal) || ! InfraConsoleConstant.INTERNAL_CALL_HEADER_VALUE.equals(internal)) {
        throw new BizException("internal API can not allow to call", ApiReturnCodeEnum.NO_ACCESS);
      }
      return true;
    }

    IgnoreAuthCheck ignoreAuthCheck = method.getAnnotation(IgnoreAuthCheck.class);
    if (Objects.nonNull(ignoreAuthCheck)) {
      return true;
    }

    // get token auth
    String token = request.getHeader(InfraConsoleConstant.INFRA_TOKEN_HEADER);
    if (StringUtils.isBlank(token)) {
      throw new BizException("token is null", ApiReturnCodeEnum.DO_NOT_INJECT);
    }

    /**
     * 先与缓存中对应的用户 token 做校验
     * 如果缓存中没有 token 就查询用户在DB中的 token, 并返回
     */
    SysAdmin admin = Optional.ofNullable(permissionCacheService.getAdminTokenCache(token))
      .orElseGet(() -> adminMapper.getAdminByToken(token));

    if (Objects.isNull(admin)) {
      log.error("The request {} try fake token", "ip");
      throw new BizException(ApiReturnCodeEnum.NOT_LOGIN);
    }
    permissionCacheService.setAdminTokenCache(token, admin);

    /**
     * record admin info into ThreadLocal
     */
    RequestHolder.setHttpServletRequest(request);
    RequestHolder.setCurrentAdmin(admin);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    RequestHolder.remove();
  }
}
