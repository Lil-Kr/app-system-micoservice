package org.cy.micoservice.app.infra.console.controller.permission;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.infra.console.service.interfaces.permission.SysOrgService;
import org.cy.micoservice.app.infra.console.dto.permission.org.OrgLevelDTO;
import org.cy.micoservice.app.infra.console.vo.req.sys.org.OrgListAllReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.org.OrgPageReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.org.OrgReq;
import org.cy.micoservice.app.infra.console.vo.resp.sys.org.SysOrgResp;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/3/3
 * @Description: org api
 */
@RestController
@RequestMapping("/sys/org")
@Slf4j
public class OrgController {

  @Autowired
  private SysOrgService orgService;

  /**
   * add org info
   * @param req
   * @return
   * @throws Exception
   */
  @PostMapping("add")
  public ApiResp<String> add(@RequestBody @Validated({OrgReq.GroupAdd.class}) OrgReq req) {
    return orgService.add(req);
  }

  /**
   * edit org info
   * @param req
   * @return
   */
  @PostMapping("edit")
  public ApiResp<String> edit(@RequestBody @Validated({OrgReq.GroupEdit.class}) OrgReq req) {
    return orgService.edit(req);
  }

  /**
   * retrieve org info by tree struct
   * @return
   */
  @PostMapping("orgTreeList")
  public ApiResp<List<OrgLevelDTO>> orgTreeList() {
    List<OrgLevelDTO> orgLevelList = orgService.orgTree();
    return ApiResp.success(orgLevelList);
  }

  /**
   * retrieve page org list
   * @return
   */
  @PostMapping("/pageList")
  public ApiResp<ApiPageResult<SysOrgResp>> pageList(@RequestBody @Validated({BasePageReq.GroupPageQuery.class}) OrgPageReq req) {
    ApiPageResult<SysOrgResp> list = orgService.pageList(req);
    return ApiResp.success(list);
  }

  @PostMapping("/list")
  public ApiResp<List<SysOrgResp>> list(@RequestBody OrgListAllReq req) {
    List<SysOrgResp> list = orgService.list(req);
    return ApiResp.success(list);
  }

  /**
   * delete org
   * @param surrogateId
   * @return
   * @throws Exception
   */
  @DeleteMapping("/delete")
  public ApiResp<String> delete(@RequestParam("surrogateId") @NotNull(message = "surrogateId是必须的") Long surrogateId) {
    return orgService.delete(surrogateId);
  }
}