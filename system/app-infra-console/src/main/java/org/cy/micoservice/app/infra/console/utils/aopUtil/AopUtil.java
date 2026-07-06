package org.cy.micoservice.app.infra.console.utils.aopUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.cy.micoservice.app.infra.console.aspect.annotations.IgnoreAuthCheck;

import java.lang.reflect.Method;

/**
 * @Author: Lil-K
 * @Date: 2026/7/4
 * @Description:
 */
public class AopUtil {
  private AopUtil() {}

  public static boolean hasIgnoreAuthCheck(ProceedingJoinPoint joinPoint) {
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    return method.isAnnotationPresent(IgnoreAuthCheck.class);
  }
}