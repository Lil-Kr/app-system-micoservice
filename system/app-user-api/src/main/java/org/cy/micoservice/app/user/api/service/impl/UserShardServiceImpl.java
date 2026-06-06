package org.cy.micoservice.app.user.api.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.user.api.service.UserShardService;
import org.cy.micoservice.app.user.api.vo.resp.UserRespVO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;
import org.cy.micoservice.app.user.facade.interfaces.UserShardFacade;
import org.springframework.stereotype.Service;

import static org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum.INFO_NOT_EXIST;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description:
 */
@Service
public class UserShardServiceImpl implements UserShardService {

  @DubboReference(check = false)
  private UserShardFacade userShardFacade;

  /**
   *
   * @param userId
   * @return
   */
  @Override
  public ApiResp<UserRespVO> getUserByShardId(Long userId) {
    RpcResponse<UserRespDTO> response = userShardFacade.getUserByShardId(userId);
    UserRespDTO data = response.getData();
    if (data == null) {
      return ApiResp.warning(INFO_NOT_EXIST);
    }
    return ApiResp.success(BeanCopyUtils.convert(data, UserRespVO.class));
  }
}
