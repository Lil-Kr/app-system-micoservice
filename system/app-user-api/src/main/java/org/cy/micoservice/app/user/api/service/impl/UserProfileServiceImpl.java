package org.cy.micoservice.app.user.api.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.user.api.service.UserProfileService;
import org.cy.micoservice.app.user.api.vo.resp.UserRespVO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;
import org.cy.micoservice.app.user.facade.interfaces.UserFacade;
import org.springframework.stereotype.Service;

import static org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum.INFO_NOT_EXIST;

/**
 * @Author: Lil-K
 * @Date: 2025/11/20
 * @Description:
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

  @DubboReference(check = false)
  private UserFacade userFacade;

  /**
   *
   * @param userId
   * @return
   */
  @Override
  public ApiResp<UserRespVO> profile(Long userId) {
    RpcResponse<UserRespDTO> userRpcResponse = userFacade.queryByUserId(userId);
    UserRespDTO data = userRpcResponse.getData();
    if (data == null) {
      return ApiResp.warning(INFO_NOT_EXIST);
    }
    return ApiResp.success(BeanCopyUtils.convert(data, UserRespVO.class));
  }

  /**
   *
   * @param surrogateId
   * @return
   */
  @Override
  public ApiResp<UserRespVO> getUserBySurrogateId(Long surrogateId) {
    RpcResponse<UserRespDTO> response = userFacade.queryByUserId(surrogateId);
    UserRespDTO data = response.getData();
    if (data == null) {
      return ApiResp.warning(INFO_NOT_EXIST);
    }

    return ApiResp.success(BeanCopyUtils.convert(data, UserRespVO.class));
  }
}
