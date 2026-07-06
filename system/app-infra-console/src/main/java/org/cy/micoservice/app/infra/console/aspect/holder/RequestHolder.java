package org.cy.micoservice.app.infra.console.aspect.holder;

import jakarta.servlet.http.HttpServletRequest;
import org.cy.micoservice.app.entity.infra.console.model.sys.SysAdmin;
import org.springframework.stereotype.Component;

/**
 * @Author: Lil-K
 * @Date: 2026/7/4
 * @Description:
 */
@Component
public class RequestHolder {

  /**
   * 存放线程隔离的管理员信息
   * 每个管理员登录进来都会分别存放用户信息
   */
  private static final ThreadLocal<SysAdmin> adminHolder = new ThreadLocal<>();

  /**
   * 存放线程隔离的http请求信息
   */
  private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

  /**
   * 存放用户信息
   * @param admin
   */
  public static void setCurrentAdmin(SysAdmin admin){
    adminHolder.set(admin);
  }

  /**
   * 获取线程隔离的用户信息
   * @return SysUser
   */
  public static SysAdmin getCurrentAdmin(){
    return adminHolder.get();
  }

  /**
   * 存放线程隔离的http请求信息
   * @param request
   */
  public static void setHttpServletRequest(HttpServletRequest request){
    requestHolder.set(request);
  }

  /**
   * 获取线程隔离的http请求信息
   * @return HttpServletRequest
   */
  public static HttpServletRequest getHttpServletRequest(){
    return requestHolder.get();
  }

  /**
   * 用户退出时移除用户信息
   */
  public static void removeAdmin() {
    adminHolder.remove();
  }

  /**
   * 移除 HttpServletRequest
   */
  public static void removeHttpServletRequest() {
    requestHolder.remove();
  }

  /**
   * 同时移除用户信息和 HttpServletRequest
   */
  public static void remove() {
    adminHolder.remove();
    requestHolder.remove();
  }
}