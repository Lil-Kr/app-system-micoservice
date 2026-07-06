package org.cy.micoservice.app.infra.console.service.interfaces.permission;

import org.cy.micoservice.app.entity.infra.console.model.sys.SysAcl;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/3/11
 * @Description:
 */
public interface SysAclCoreService {

	/**
	 * 获取当前用户所拥有的权限列表
	 */
	List<SysAcl> getCurrentAdminAclList();

	List<SysAcl> getCurrentAdminAclList(Long adminId);

	/**
	 * 获取[角色-权限]列表
	 */
	List<SysAcl> getRoleAclList(Long roleSurrogateId);

	/**
	 *
	 * @param adminId
	 * @return
	 */
	List<SysAcl> getAdminAclList(Long adminId);

	/**
	 *
	 * @param adminId
	 * @param type
	 * @return
	 */
	List<SysAcl> getAdminAclList(Long adminId, Integer type);

	/**
	 *
	 * @param url
	 * @return
	 */
	boolean hasUrlAcl(String url);
}
