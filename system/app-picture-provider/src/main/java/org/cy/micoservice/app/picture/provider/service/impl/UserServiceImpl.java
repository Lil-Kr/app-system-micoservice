package org.cy.micoservice.app.picture.provider.service.impl;

import org.cy.micoservice.app.picture.provider.dao.UserMapper;
import org.cy.micoservice.app.picture.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lil-K
 * @Date: 2026/3/29
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

}
