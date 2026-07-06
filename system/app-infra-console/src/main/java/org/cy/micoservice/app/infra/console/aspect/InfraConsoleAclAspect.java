package org.cy.micoservice.app.infra.console.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum;
import org.cy.micoservice.app.common.exception.BizException;
import org.cy.micoservice.app.entity.infra.console.model.sys.SysAcl;
import org.cy.micoservice.app.infra.console.aspect.holder.RequestHolder;
import org.cy.micoservice.app.infra.console.service.interfaces.permission.SysAclCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum.SYSTEM_ERROR;

/**
 * @Author: Lil-K
 * @Date: 2026/7/4
 * @Description:
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class InfraConsoleAclAspect {

  @Autowired
  private HttpServletRequest servletRequest;
  @Autowired
  private SysAclCoreService coreService;

  @Pointcut("execution(public * org.cy.micoservice.app.infra.console.controller..*.*(..)) " +
    " && !@annotation(org.cy.micoservice.app.infra.console.aspect.annotations.IgnoreAuthCheck)" +
    " && !@annotation(org.cy.micoservice.app.infra.console.aspect.annotations.InternalCallCheck)")
  public void acl() {}

  // @Around("acl()")
  public Object checkAcl(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      List<SysAcl> userAclList = coreService.getCurrentAdminAclList();
      Set<String> urlAclSet = userAclList.stream().map(SysAcl::getUrl).collect(Collectors.toSet());
      String uri = servletRequest.getRequestURI();
      boolean hasAcl = urlAclSet.stream().anyMatch(item -> item.contains(uri) || uri.startsWith(item));
      if (! hasAcl) {
        log.error("The request is not have acl to call {}", uri);
        throw new BizException(ApiReturnCodeEnum.NO_ACCESS);
      }

      return joinPoint.proceed();
    } catch (Throwable e) {
      log.error("api request ACL ERROR: {}", e.getMessage());
      return ApiResp.warning(SYSTEM_ERROR.getCode(), e.getMessage());
    } finally {
      RequestHolder.remove();
    }
  }
}