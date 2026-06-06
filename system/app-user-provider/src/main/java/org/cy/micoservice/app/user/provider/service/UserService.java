package org.cy.micoservice.app.user.provider.service;

import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.entity.user.model.provider.pojo.User;
import org.cy.micoservice.app.entity.user.model.provider.req.UserListPageReq;
import org.cy.micoservice.app.entity.user.model.provider.resp.UserResp;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserSaveReqDTO;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description:
 */
public interface UserService {

  /**
   * user register
   * @param reqDTO
   * @return
   */
  User register(UserRegisterReqDTO reqDTO);

  /**
   * query user info by id
   * @param userId
   * @return
   */
  User queryUserById(Long userId);

  /**
   *
   * @param offset
   * @param limit
   * @return
   */
  List<User> queryByOffset(Long offset, Long limit);

  /**
   * batch query user ids
   * @param userIds
   * @return
   */
  List<User> queryInUserIds(Collection<Long> userIds);

  /**
   * pagination query user list
   * @param req
   * @return
   */
  RpcPageResponse<UserResp> pageUserList(UserListPageReq req);

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