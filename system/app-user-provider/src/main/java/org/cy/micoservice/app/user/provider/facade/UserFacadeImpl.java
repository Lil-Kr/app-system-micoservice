package org.cy.micoservice.app.user.provider.facade;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.user.model.provider.pojo.User;
import org.cy.micoservice.app.user.facade.dto.req.TestReq;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRegisterRespDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;
import org.cy.micoservice.app.user.facade.interfaces.UserFacade;
import org.cy.micoservice.app.user.provider.service.UserService;
import org.cy.micoservice.app.user.provider.service.UserShardService;
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
  private UserShardService userShardService;

  @Autowired
  private UserService userService;

  /**
   * new user register
   * @param userRegisterReqDTO
   * @return
   */
  @Override
  public RpcResponse<UserRegisterRespDTO> register(UserRegisterReqDTO userRegisterReqDTO) {
    // UserSaveReqDTO user = BeanCopyUtils.convert(userRegisterReqDTO, UserSaveReqDTO.class);
    User register = userService.register(userRegisterReqDTO);
    UserRegisterRespDTO userRegisterRespDTO = BeanCopyUtils.convert(register, UserRegisterRespDTO.class);
    return RpcResponse.success(userRegisterRespDTO);
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
    UserRespDTO convert = BeanCopyUtils.convert(user, UserRespDTO.class);
    return RpcResponse.success(convert);
  }

  /**
   * query user info list by userIds
   * @param userIds
   * @return
   */
  @Override
  public RpcResponse<List<UserRespDTO>> queryInUserIds(List<Long> userIds) {
    List<UserRespDTO> userRespDTOList = BeanCopyUtils.convertList(userService.queryInUserIds(userIds), UserRespDTO.class);
    return RpcResponse.success(userRespDTOList);
  }

  /**
   * test
   * @param req
   * @return
   */
  @Override
  public String test(TestReq req) {
    log.info("参数调用: {}", JSONObject.toJSONString(req));
    return JSONObject.toJSONString(req);
  }
}
