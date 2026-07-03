// package org.cy.micoservice.app.user.provider.facade;
//
// import lombok.extern.slf4j.Slf4j;
// import org.apache.dubbo.config.annotation.DubboService;
// import org.cy.micoservice.app.common.base.provider.RpcResponse;
// import org.cy.micoservice.app.common.utils.BeanCopyUtils;
// import org.cy.micoservice.app.entity.user.model.provider.pojo.UserShard;
// import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
// import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;
// import org.cy.micoservice.app.user.facade.interfaces.UserShardFacade;
// import org.cy.micoservice.app.user.provider.service.UserShardService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.util.List;
//
// /**
//  * @Author: Lil-K
//  * @Date: 2026/6/6
//  * @Description:
//  */
// @Slf4j
// @Service
// @DubboService
// public class UserShardFacadeImpl implements UserShardFacade {
//
//   @Autowired
//   private UserShardService userShardService;
//
//   /**
//    *
//    * @param userRegisterList
//    * @return
//    */
//   @Override
//   public RpcResponse<Boolean> batchSave(List<UserRegisterReqDTO> userRegisterList) {
//     List<UserShard> userShardList = BeanCopyUtils.convertList(userRegisterList, UserShard.class);
//     return RpcResponse.success(userShardService.batchSave(userShardList));
//   }
//
//   /**
//    *
//    * @param userId
//    * @return
//    */
//   @Override
//   public RpcResponse<UserRespDTO> getUserByShardId(Long userId) {
//     UserShard userShard = userShardService.queryUserById(userId);
//     if (userShard == null) {
//       return RpcResponse.emptyResult();
//     }
//     return RpcResponse.success(BeanCopyUtils.convert(userShard, UserRespDTO.class));
//   }
// }
