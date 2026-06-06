package org.cy.micoservice.app.user.api.service;

import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.user.api.vo.resp.UserRespVO;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description:
 */
public interface UserShardService {

  ApiResp<UserRespVO> getUserByShardId(Long userId);
}
