package org.cy.micoservice.app.infra.console.service.interfaces.permission;

import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.infra.console.vo.req.sys.role.RoleListPageReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.role.RoleSaveReq;
import org.cy.micoservice.app.infra.console.vo.resp.sys.role.SysRoleResp;

/**
 * @Author: Lil-K
 * @Date: 2025/3/12
 * @Description:
 */
public interface SysRoleService {

	ApiResp<String> add(RoleSaveReq param);

	ApiResp<String> edit(RoleSaveReq param);

	ApiResp<String> freeze(RoleSaveReq req);

	ApiResp<String> delete(Long surrogateId);

	ApiPageResult<SysRoleResp> pageList(RoleListPageReq param);

	boolean checkSupperAdminExist();
}
