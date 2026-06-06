package org.cy.micoservice.app.user.facade.interfaces;

import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.user.facade.dto.req.TestReq;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRegisterRespDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/11/20
 * @Description: 用户服务facade
 */
public interface UserFacade {

  /**
   * user register
   * @param userRegisterReqDTO
   * @return
   */
  RpcResponse<UserRegisterRespDTO> register(UserRegisterReqDTO userRegisterReqDTO);

  /**
   * query user info by userId
   * @param userId
   * @return
   */
  RpcResponse<UserRespDTO> queryByUserId(Long userId);

  // /**
  //  *
  //  * @param userId
  //  * @return
  //  */
  // RpcResponse<UserShard> queryByUserId(Long userId);

  /**
   * 批量查询用户信息
   * @param userIds
   * @return
   */
  RpcResponse<List<UserRespDTO>> queryInUserIds(List<Long> userIds);


  String test(TestReq req);
}
