package org.cy.micoservice.app.user.provider.facade;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.user.model.es.UserPhoneEs;
import org.cy.micoservice.app.user.facade.dto.req.UserPhoneEsDTO;
import org.cy.micoservice.app.user.facade.interfaces.UserPhoneFacade;
import org.cy.micoservice.app.user.provider.service.UserPhoneEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/23
 * @Description:
 */
@Slf4j
@Service
@DubboService
public class UserPhoneFacadeImpl implements UserPhoneFacade {

  @Autowired
  private UserPhoneEsService userPhoneEsService;

  @Override
  public RpcResponse<Boolean> add(UserPhoneEsDTO userPhoneEsDTO) {
    UserPhoneEs userPhoneEs = BeanCopyUtils.convert(userPhoneEsDTO, UserPhoneEs.class);
    return RpcResponse.success(userPhoneEsService.add(userPhoneEs));
  }

  @Override
  public RpcResponse<List<UserPhoneEs>> queryByUserId(Long userId) {
    List<UserPhoneEs> userPhoneEs = userPhoneEsService.queryByUserId(userId);
    if (CollectionUtils.isEmpty(userPhoneEs)) {
      RpcResponse.emptyResult();
    }
    return RpcResponse.success(userPhoneEs);
  }

  @Override
  public RpcResponse<UserPhoneEs> queryByPhone(String phone) {
    UserPhoneEs userPhoneEs = userPhoneEsService.queryByPhone(phone);
    if (userPhoneEs == null) {
      RpcResponse.emptyResult();
    }
    return RpcResponse.success(userPhoneEs);
  }
}
