package org.cy.micoservice.app.user.facade.interfaces;

import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.user.facade.dto.req.UserFollowerReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserFollowerRespDTO;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */

public interface UserFollowerFacade {

  /**
   * user is or not follower
   * @param userFollowerReqDTO
   * @return
   */
  RpcResponse<Boolean> isFollower(UserFollowerReqDTO userFollowerReqDTO);

  /**
   * add follower
   * @param userFollowerReqDTO
   * @return
   */
  RpcResponse<Boolean> addFollower(UserFollowerReqDTO userFollowerReqDTO);

  /**
   * cancel follower
   * @param userFollowerReqDTO
   * @return
   */
  RpcResponse<Boolean> cancel(UserFollowerReqDTO userFollowerReqDTO);

  /**
   * query user follower list
   * @param userId
   * @return
   */
  RpcResponse<List<UserFollowerRespDTO>> queryFollowerByUserId(Long userId);
}