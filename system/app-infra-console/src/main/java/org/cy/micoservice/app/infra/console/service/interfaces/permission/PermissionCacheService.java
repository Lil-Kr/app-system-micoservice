package org.cy.micoservice.app.infra.console.service.interfaces.permission;

import org.cy.micoservice.app.entity.infra.console.model.sys.SysAcl;
import org.cy.micoservice.app.entity.infra.console.model.sys.SysAdmin;
import org.cy.micoservice.app.entity.infra.console.model.sys.SysDict;
import org.cy.micoservice.app.entity.infra.console.model.sys.SysDictDetail;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/3/28
 * @Description: cache service
 */
public interface PermissionCacheService {

	/** ================= admin-user cache by token ============== **/
	void setAdminTokenCache(String token, SysAdmin user);

	SysAdmin getAdminTokenCache(String token);

	void removeAdminTokenCache(String token);

	/** ================= admin-user cache by id, use for data transform case  ================= **/
	void initAdminIdCache(List<SysAdmin> list);

	SysAdmin getAdminIdCache(Long id);

	void setUserAdminIdCache(Long id, SysAdmin user);

	void removeUserAdminIdCache(Long id);

	/**
	 * ================================== dict ===============================
	 **/
	void saveDictDetailCache(List<SysDict> dictVOList, List<SysDictDetail> dictDetailList);

	SysDict getDictCache(Long surrogateId);

	SysDictDetail getDictDetailCache(Long surrogateId);

	void updateDictCache(Long keyDict, SysDict dict, String sign);

	void updateDictDetailCache(Long keyDictDetail, SysDictDetail dictDetail, String sign);

	void removeDicCache(Long key);

	void removeDicDetailCache(Long key);

	/**
	 * ================================== sys admin-user acl ===============================
	 */
	void saveAdminAclCache(Long surrogateId, List<SysAcl> aclList);

	List<SysAcl> getAdminAclListCache(Long userId);

	void invalidAdminAclCache(List<Long> userIdList);

	void invalidAllAdminAclCache();
}
