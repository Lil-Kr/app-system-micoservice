package org.cy.micoservice.app.user.api.service;

import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.user.api.vo.resp.UserRespVO;

/**
 * @Author: Lil-K
 * @Date: 2025/11/20
 * @Description:
 */
public interface UserProfileService {

  ApiResp<UserRespVO> profile(Long id);

  ApiResp<UserRespVO> getUserBySurrogateId(Long surrogateId);
}
