package org.cy.micoservice.app.user.facade.interfaces;

import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description: 
 */
public interface UserShardFacade {

  /**
   * 批量保存用户信息
   * @param userRegisterList
   * @return
   */
  RpcResponse<Boolean> batchSave(List<UserRegisterReqDTO> userRegisterList);

  /**
   *
   * @param userId
   * @return
   */
  RpcResponse<UserRespDTO> getUserByShardId(Long userId);
}