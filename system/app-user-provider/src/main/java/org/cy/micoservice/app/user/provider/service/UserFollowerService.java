package org.cy.micoservice.app.user.provider.service;

import org.cy.micoservice.app.user.facade.dto.req.UserFollowerReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserFollowerRespDTO;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */
public interface UserFollowerService {

  /**
   * user is or not follower
   * @param userFollowerReqDTO
   * @return
   */
  boolean isFollower(UserFollowerReqDTO userFollowerReqDTO);

  /**
   * add follower
   * @param userFollowerReqDTO
   * @return
   */
  boolean addFollower(UserFollowerReqDTO userFollowerReqDTO);

  /**
   * cancel follower
   * @param userFollowerReqDTO
   * @return
   */
  boolean cancel(UserFollowerReqDTO userFollowerReqDTO);

  /**
   * query user follower list
   * @param userId
   * @return
   */
  List<UserFollowerRespDTO> queryFollowerByUserId(Long userId);
}