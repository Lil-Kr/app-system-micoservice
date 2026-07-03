package org.cy.micoservice.app.infra.console.service.interfaces.permission;


import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.infra.console.vo.req.sys.permission.PermissionReq;

import java.util.Map;

/**
 * @Author: Lil-K
 * @Date: 2025/3/18
 * @Description: 菜单
 */
public interface SysPermissionService {

  // 获取当前用户的菜单数据和按钮数据
  ApiResp<Map<String, Object>> permission(PermissionReq req);
}
