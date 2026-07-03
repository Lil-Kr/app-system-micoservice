package org.cy.micoservice.app.infra.console.service.interfaces.permission;

import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.infra.console.dto.permission.org.OrgLevelDTO;
import org.cy.micoservice.app.infra.console.vo.req.sys.org.OrgListAllReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.org.OrgPageReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.org.OrgReq;
import org.cy.micoservice.app.infra.console.vo.resp.sys.org.SysOrgResp;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/3/3
 * @Description:
 */
public interface SysOrgService {
	
	ApiResp<String> add(OrgReq req);

	ApiResp<String> edit(OrgReq req);

	List<OrgLevelDTO> orgTree();

	ApiResp<String> delete(Long surrogateId);

	ApiPageResult<SysOrgResp> pageList(OrgPageReq req);

	List<SysOrgResp> list(OrgListAllReq req);

}