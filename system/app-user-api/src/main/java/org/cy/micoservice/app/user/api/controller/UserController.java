package org.cy.micoservice.app.user.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.framework.web.starter.annotations.NoAuthCheck;
import org.cy.micoservice.app.framework.web.starter.web.RequestContext;
import org.cy.micoservice.app.user.api.service.UserEnterService;
import org.cy.micoservice.app.user.api.service.UserProfileService;
import org.cy.micoservice.app.user.api.service.UserShardService;
import org.cy.micoservice.app.user.api.vo.resp.UserRespVO;
import org.cy.micoservice.app.user.facade.dto.req.UserEnterInitReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lil-K
 * @Date: 2025/11/20
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserProfileService userProfileService;
  @Autowired
  private UserEnterService userEnterService;
  @Autowired
  private UserShardService userShardService;

  /**
   *
   * @return
   */
  @NoAuthCheck
  @GetMapping("/profile")
  public ApiResp<UserRespVO> profile() {
    return userProfileService.profile(RequestContext.getUserId());
  }

  /**
   *
   * @param userId
   * @return
   */
  @NoAuthCheck
  @GetMapping("/getUser")
  public ApiResp<UserRespVO> getUser(Long userId) {
    return userProfileService.getUserBySurrogateId(userId);
  }

  /**
   *
   * @param userId
   * @return
   */
  @NoAuthCheck
  @GetMapping("/getUserShard")
  public ApiResp<UserRespVO> getUserShard(Long userId) {
    return userShardService.getUserByShardId(userId);
  }

  /**
   * 发送MQ通知, 用户进入程序时触发, 通知下游预加载聊天数据
   * 这个接口请求量非常大
   * @return
   */
  @NoAuthCheck
  @PostMapping("/init")
  public ApiResp<Boolean> init(@RequestBody UserEnterInitReqDTO req) {
    req.setUserId(RequestContext.getUserId());
    return ApiResp.success(userEnterService.enter(req));
  }
}