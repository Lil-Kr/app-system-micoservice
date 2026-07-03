package org.cy.micoservice.app.user.provider.facade;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.user.model.User;
import org.cy.micoservice.app.user.facade.dto.req.TestReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserSaveReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRegisterRespDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;
import org.cy.micoservice.app.user.facade.interfaces.UserFacade;
import org.cy.micoservice.app.user.provider.config.ApplicationProperties;
import org.cy.micoservice.app.user.provider.service.UserService;
import org.cy.micoservice.app.user.provider.service.UserShardService;
import org.cy.micoservice.app.user.provider.utils.DynamicInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/11/20
 * @Description: user info facade provider
 */
@Slf4j
@Service
@DubboService
public class UserFacadeImpl implements UserFacade {

  @Autowired
  private ApplicationProperties applicationProperties;
  @Autowired
  private UserShardService userShardService;
  @Autowired
  private UserService userService;
  @Autowired
  private DynamicInvoker dynamicInvoker;

  /**
   * new user register
   * @param userRegisterReqDTO
   * @return
   */
  @Override
  public RpcResponse<UserRegisterRespDTO> register(UserRegisterReqDTO userRegisterReqDTO) {
    // switch traffic routing
    // UserRegisterRespDTO userRegisterRespDTO = dynamicInvoker.invokeByProbabilityWithResult(applicationProperties.getDynamicWriteRate(),
    //   () -> BeanCopyUtils.convert(userShardService.register(userRegisterReqDTO), UserRegisterRespDTO.class),
    //   () -> BeanCopyUtils.convert(userService.register(userRegisterReqDTO), UserRegisterRespDTO.class)
    // );
    // return RpcResponse.success(userRegisterRespDTO);

    User register = userService.register(userRegisterReqDTO);
    return RpcResponse.success(BeanCopyUtils.convert(register, UserRegisterRespDTO.class));
  }

  /**
   * query user info by userId
   * @param userId
   * @return
   */
  @Override
  public RpcResponse<UserRespDTO> queryByUserId(Long userId) {
    User user = userService.queryUserById(userId);
    if (user == null) {
      return RpcResponse.emptyResult();
    }
    UserRespDTO userRespDTO = BeanCopyUtils.convert(user, UserRespDTO.class);
    return RpcResponse.success(userRespDTO);

    // switch traffic routing
    // UserRespDTO userRespDTO = dynamicInvoker.invokeByProbabilityWithResult(applicationProperties.getDynamicReadRate(),
    //   () -> BeanCopyUtils.convert(userService.queryUserById(userId), UserRespDTO.class),
    //   () -> BeanCopyUtils.convert(userShardService.queryUserById(userId), UserRespDTO.class));
    // return RpcResponse.success(userRespDTO);
  }

  /**
   * query user info list by userIds
   * @param userIds
   * @return
   */
  @Override
  public RpcResponse<List<UserRespDTO>> queryInUserIds(List<Long> userIds) {
    List<UserRespDTO> userRespDTOList = BeanCopyUtils.convertList(userService.queryInUserIds(userIds), UserRespDTO.class);
    if (CollectionUtils.isEmpty(userRespDTOList)) {
      return RpcResponse.emptyResult();
    }
    return RpcResponse.success(userRespDTOList);

    // switch traffic routing
    // List<UserRespDTO> userRespDTOList = dynamicInvoker.invokeByProbabilityWithResult(applicationProperties.getDynamicReadRate(),
    //   () -> BeanCopyUtils.convertList(userService.queryInUserIds(userIds), UserRespDTO.class),
    //   () -> BeanCopyUtils.convertList(userShardService.queryInUserIds(userIds), UserRespDTO.class));
    // return RpcResponse.success(userRespDTOList);
  }

  /**
   * update user info
   * @param req
   * @return
   */
  @Override
  public RpcResponse<Boolean> update(UserSaveReqDTO req) {
    // switch traffic routing
    // Boolean result = dynamicInvoker.invokeByProbabilityWithResult(applicationProperties.getDynamicWriteRate(),
    //   () -> userShardService.update(req),
    //   () -> userService.update(req)
    // );
    // return RpcResponse.success(result);

    return RpcResponse.success(true);
  }

  /**
   * test
   * @param req
   * @return
   */
  @Override
  public String test(TestReqDTO req) {
    log.info("参数调用: {}", JSONObject.toJSONString(req));
    return JSONObject.toJSONString(req);
  }
}
