package org.cy.micoservice.app.infra.console.service.interfaces.permission;

import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.infra.console.vo.req.sys.roleacl.RoleAclSaveReq;

/**
 * @Author: Lil-K
 * @Date: 2025/3/15
 * @Description: SysRoleAclService
 */
public interface SysRoleAclService {
	ApiResp<String> updateRoleAcls(RoleAclSaveReq param);
}
