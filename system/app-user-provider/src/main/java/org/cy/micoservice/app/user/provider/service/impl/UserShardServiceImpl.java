package org.cy.micoservice.app.user.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.entity.user.model.UserShard;
import org.cy.micoservice.app.user.facade.dto.req.UserListPageReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserSaveReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;
import org.cy.micoservice.app.user.provider.dao.UserShardMapper;
import org.cy.micoservice.app.user.provider.service.UserShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/12/28
 * @Description: user shard service (new sharding)
 */
@Service
public class UserShardServiceImpl extends ServiceImpl<UserShardMapper, UserShard> implements UserShardService {

  @Autowired
  private UserShardMapper userShardMapper;

  /**
   * user register
   * @param userShardDTO
   * @return
   */
  @Override
  public UserShard register(UserRegisterReqDTO userShardDTO) {
    userShardDTO.setRegistryTime(DateUtil.localDateTimeNow());
    UserShard userShard = BeanCopyUtils.convert(userShardDTO, UserShard.class);
    userShardMapper.insert(userShard);
    return userShard;
  }

  /**
   * query user by userId
   * @param userId
   * @return
   */
  @Override
  public UserShard queryUserById(Long userId) {
    return userShardMapper.getUserByShardId(userId);
  }

  /**
   * batch save user info
   * @param userShardList
   * @return
   */
  @Override
  public Boolean batchSave(List<UserShard> userShardList) {
    return super.saveBatch(userShardList);
  }

  /**
   * query user list by ids
   * @param userIds
   * @return
   */
  @Override
  public List<UserShard> queryInUserIds(Collection<Long> userIds) {
    return List.of();
  }

  @Override
  public RpcPageResponse<UserRespDTO> pageUserList(UserListPageReqDTO req) {
    return null;
  }

  /**
   *
   * @param req
   * @return
   */
  @Override
  public boolean update(UserSaveReqDTO req) {
    return true;
  }

  @Override
  public RpcResponse<String> delete(Long userId) {
    return null;
  }
}
