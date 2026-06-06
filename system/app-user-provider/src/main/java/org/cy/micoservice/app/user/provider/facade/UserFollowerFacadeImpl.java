package org.cy.micoservice.app.user.provider.facade;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.user.facade.dto.req.UserFollowerReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserFollowerRespDTO;
import org.cy.micoservice.app.user.facade.interfaces.UserFollowerFacade;
import org.cy.micoservice.app.user.provider.service.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */

@Slf4j
@Service
@DubboService
public class UserFollowerFacadeImpl implements UserFollowerFacade {

  @Autowired
  private UserFollowerService userFollowerService;

  @Override
  public RpcResponse<Boolean> isFollower(UserFollowerReqDTO userFollowerReqDTO) {
    boolean res = userFollowerService.isFollower(userFollowerReqDTO);
    return RpcResponse.success(res);
  }

  @Override
  public RpcResponse<Boolean> addFollower(UserFollowerReqDTO userFollowerReqDTO) {
    boolean res = userFollowerService.addFollower(userFollowerReqDTO);
    return RpcResponse.success(res);
  }

  @Override
  public RpcResponse<Boolean> cancel(UserFollowerReqDTO userFollowerReqDTO) {
    boolean res = userFollowerService.cancel(userFollowerReqDTO);
    return RpcResponse.success(res);
  }

  @Override
  public RpcResponse<List<UserFollowerRespDTO>> queryFollowerByUserId(Long userId) {
    List<UserFollowerRespDTO> userFollowerRespDTOS = userFollowerService.queryFollowerByUserId(userId);
    return RpcResponse.success(userFollowerRespDTOS);
  }
}
