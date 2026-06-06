package org.cy.micoservice.app.user.provider.service.impl;

import org.cy.micoservice.app.user.facade.dto.req.UserFollowerReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserFollowerRespDTO;
import org.cy.micoservice.app.user.provider.service.UserFollowerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */
@Service
public class UserFollowerServiceImpl implements UserFollowerService {

  /**
   * current user is or not follower
   * @param userFollowerReqDTO
   * @return
   */
  @Override
  public boolean isFollower(UserFollowerReqDTO userFollowerReqDTO) {
    return false;
  }

  /**
   * add user follower
   * @param userFollowerReqDTO
   * @return
   */
  @Override
  public boolean addFollower(UserFollowerReqDTO userFollowerReqDTO) {
    return false;
  }

  /**
   *
   * @param userFollowerReqDTO
   * @return
   */
  @Override
  public boolean cancel(UserFollowerReqDTO userFollowerReqDTO) {
    return false;
  }

  /**
   *
   * @param userId
   * @return
   */
  @Override
  public List<UserFollowerRespDTO> queryFollowerByUserId(Long userId) {
    return List.of();
  }
}
