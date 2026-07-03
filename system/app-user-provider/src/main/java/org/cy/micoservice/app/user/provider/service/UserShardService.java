package org.cy.micoservice.app.user.provider.service;

import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.entity.user.model.UserShard;
import org.cy.micoservice.app.user.facade.dto.req.UserListPageReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserSaveReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/12/28
 * @Description: 用户服务接口层
 */
public interface UserShardService {

  /**
   * user register
   * @param user
   * @return
   */
  UserShard register(UserRegisterReqDTO user);

  /**
   *
   * @param userShardList
   * @return
   */
  Boolean batchSave(List<UserShard> userShardList);

  /**
   * query user info by id
   * @param userId
   * @return
   */
  UserShard queryUserById(Long userId);

  /**
   * batch query user ids
   * @param userIds
   * @return
   */
  List<UserShard> queryInUserIds(Collection<Long> userIds);

  /**
   * pagination query user list
   * @param req
   * @return
   */
  RpcPageResponse<UserRespDTO> pageUserList(UserListPageReqDTO req);

  /**
   * edit user info
   * @param req
   * @return
   */
  boolean update(UserSaveReqDTO req);

  /**
   * delete uver info
   * @param userId
   * @return
   */
  RpcResponse<String> delete(Long userId);
}