package org.cy.micoservice.app.infra.console.service.interfaces.permission;

import org.cy.micoservice.app.infra.console.dto.permission.acl.AclDTO;
import org.cy.micoservice.app.infra.console.dto.permission.aclmodule.AclModuleDTO;
import org.cy.micoservice.app.infra.console.dto.permission.org.OrgLevelDTO;
import org.cy.micoservice.app.infra.console.vo.req.sys.role.RoleSaveReq;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/3/5
 * @Description:
 */
public interface SysTreeService {

	List<OrgLevelDTO> orgTree();

//	List<OrgLevelDto> orgListToTree(List<OrgLevelDto> dtoList);

	List<AclModuleDTO> aclModuleTree();


	List<AclModuleDTO> roleAclTree(RoleSaveReq req);

	List<AclModuleDTO> userAclTree(Long userId);

	List<AclModuleDTO> aclListToTree(List<AclDTO> aclDTOList);

}