package org.cy.micoservice.app.user.facade.interfaces;

import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.entity.user.model.es.UserPhoneEs;
import org.cy.micoservice.app.user.facade.dto.req.UserPhoneEsDTO;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/23
 * @Description:
 */
public interface UserPhoneFacade {

  /**
   * create user phone number
   * @param userPhoneEsDTO
   */
  RpcResponse<Boolean> add(UserPhoneEsDTO userPhoneEsDTO);

  /**
   * query user phone number by user_id
   * @param userId
   * @return
   */
  RpcResponse<List<UserPhoneEs>> queryByUserId(Long userId);

  /**
   * query user info by phone number,
   * @param phone
   * @return
   */
  RpcResponse<UserPhoneEs> queryByPhone(String phone);
}