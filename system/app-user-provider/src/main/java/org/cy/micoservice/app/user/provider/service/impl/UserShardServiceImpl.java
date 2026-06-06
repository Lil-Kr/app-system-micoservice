package org.cy.micoservice.app.user.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.entity.user.model.provider.pojo.UserShard;
import org.cy.micoservice.app.entity.user.model.provider.req.UserListPageReq;
import org.cy.micoservice.app.entity.user.model.provider.resp.UserResp;
import org.cy.micoservice.app.user.facade.dto.req.UserSaveReqDTO;
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
   * @param userShard
   * @return
   */
  @Override
  public UserShard register(UserShard userShard) {
    userShard.setRegistryTime(DateUtil.localDateTimeNow());
    userShardMapper.insert(userShard);
    return userShard;
  }

  /**
   * query user by userId
   * @param userId
   * @return
   */
  @Override
  public UserShard getUserByShardId(Long userId) {
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
  public RpcPageResponse<UserResp> pageUserList(UserListPageReq req) {
    return null;
  }

  @Override
  public RpcResponse<String> update(UserSaveReqDTO req) {
    return null;
  }

  @Override
  public RpcResponse<String> delete(Long userId) {
    return null;
  }
}
