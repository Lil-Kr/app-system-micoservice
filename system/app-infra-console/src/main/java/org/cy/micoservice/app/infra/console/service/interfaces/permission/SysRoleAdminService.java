package org.cy.micoservice.app.infra.console.service.interfaces.permission;


import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.infra.console.vo.req.sys.roleuser.RoleAdminReq;
import org.cy.micoservice.app.infra.console.vo.resp.sys.role.RoleAdminResp;

/**
 * role-user service
 * @Author: Lil-K
 * @Date: 2025/3/31
 * @Description:
 */
public interface SysRoleAdminService {

	ApiResp<String> updateRoleAdmins(RoleAdminReq req);

	ApiResp<RoleAdminResp> roleAdminList(RoleAdminReq req);
}
