package org.cy.micoservice.app.user.provider.service;

import org.cy.micoservice.app.entity.user.model.es.UserPhoneEs;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/23
 * @Description:
 */
public interface UserPhoneEsService {

  /**
   * create user phone number
   * @param userPhoneEs
   */
  boolean add(UserPhoneEs userPhoneEs);

  /**
   * query user phone number by user_id
   * @param userId
   * @return
   */
  List<UserPhoneEs> queryByUserId(Long userId);

  /**
   * query user info by phone number,
   * @param phone
   * @return
   */
  UserPhoneEs queryByPhone(String phone);
}