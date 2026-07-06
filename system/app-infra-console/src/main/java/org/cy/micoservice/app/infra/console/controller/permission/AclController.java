package org.cy.micoservice.app.infra.console.controller.permission;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;
import org.cy.micoservice.app.infra.console.service.interfaces.permission.SysAclService;
import org.cy.micoservice.app.infra.console.vo.req.sys.acl.AclDeleteReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.acl.AclPageReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.acl.AclReq;
import org.cy.micoservice.app.infra.console.vo.resp.sys.acl.SysAclResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lil-K
 * @Date: 2025/3/5
 * @Description: acl api
 */
@Slf4j
@RestController
@RequestMapping("/sys/acl")
public class AclController {

  @Autowired
  private SysAclService aclService;

  /**
   * query acl list by pagination
   * @param req
   * @return
   * @throws Exception
   */
  @PostMapping("/pageList")
  public ApiResp<ApiPageResult<SysAclResp>> pageList(@RequestBody @Validated({BasePageReq.GroupPageQuery.class}) AclPageReq req) {
    ApiPageResult<SysAclResp> res = aclService.pageList(req);
    return ApiResp.success(res);
  }

  /**
   * create acl
   * @param req
   * @return
   */
  @PostMapping("/add")
  public ApiResp<String> add(@RequestBody @Valid AclReq req) {
    return aclService.add(req);
  }

  /**
   * update acl
   * @param req
   * @return
   */
  @PostMapping("/edit")
  public ApiResp<String> edit(@RequestBody @Valid AclReq req) {
    return aclService.edit(req);
  }

  /**
   * delete acl
   * @param req
   * @return
   */
  @DeleteMapping("/delete")
  public ApiResp<String> delete(@Valid AclDeleteReq req) {
    return aclService.delete(req.getAclId());
  }

}

