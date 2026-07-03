package org.cy.micoservice.app.user.provider.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.entity.user.model.User;
import org.cy.micoservice.app.framework.rocketmq.starter.producer.RocketMQProducerClient;
import org.cy.micoservice.app.user.facade.dto.req.UserListPageReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserRegisterReqDTO;
import org.cy.micoservice.app.user.facade.dto.req.UserSaveReqDTO;
import org.cy.micoservice.app.user.facade.dto.resp.UserRespDTO;
import org.cy.micoservice.app.user.facade.enums.SyncUserInfoMsgTypeEnum;
import org.cy.micoservice.app.user.provider.bo.SyncUserInfoMQMsgBO;
import org.cy.micoservice.app.user.provider.config.ApplicationProperties;
import org.cy.micoservice.app.user.provider.dao.UserMapper;
import org.cy.micoservice.app.user.provider.service.UserService;
import org.cy.micoservice.app.user.provider.task.UserSyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description: user service (old)
 */
@Deprecated
@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private ApplicationProperties applicationProperties;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private UserSyncTask userSyncTask;
  @Autowired
  private RocketMQProducerClient mqProducerClient;

  /**
   * user register
   * @param dto
   * @return
   */
  @Override
  public User register(UserRegisterReqDTO dto) {
    dto.setRegistryTime(DateUtil.localDateTimeNow());
    User user = BeanCopyUtils.convert(dto, User.class);
    userMapper.insert(user);

    /**
     * user data migrate to new databases, new user data need incremental synchronization
     * need to judgement timestamp, if registry time great than sync finish time, then need pass into MQ
     * use the mq to help for migrate
     */
    boolean isStartedSyncTask = userSyncTask.getStartTime() != null && System.nanoTime() > userSyncTask.getStartTime();
    if (isStartedSyncTask) {
      // 避免重复insert, 依靠t_user分表底层的user_id作为唯一索引来避免重复
      // UserShard userShard = BeanCopyUtils.convert(user, UserShard.class);
      // this.sendSyncMsg(SyncUserInfoMsgTypeEnum.INSERT, JSON.toJSONString(userShard));
    }
    return user;
  }

  /**
   *
   * @param userId
   * @return
   */
  @Override
  public User queryUserById(Long userId) {
    return userMapper.getUserById(userId);
  }

  /**
   *
   * @param offset
   * @param limit
   * @return
   */
  @Override
  public List<User> queryByOffset(Long offset, Long limit) {
    return userMapper.queryByOffset(offset, limit);
  }

  /**
   *
   * @param userIds
   * @return
   */
  @Override
  public List<User> queryInUserIds(Collection<Long> userIds) {
    return List.of();
  }

  /**
   *
   * @param req
   * @return
   */
  @Override
  public RpcPageResponse<UserRespDTO> pageUserList(UserListPageReqDTO req) {
    return null;
  }

  /**
   *
   * @param reqDTO
   * @return
   */
  @Override
  public boolean update(UserSaveReqDTO reqDTO) {

    /**
     * user data migrate to new databases, new user data need incremental synchronization
     * need to judgement timestamp, if registry time great than sync finish time, then need pass into MQ
     * use the mq to help for migrate
     */
    boolean isStartedSyncTask = userSyncTask.getStartTime() != null && System.currentTimeMillis() > userSyncTask.getStartTime();
    if (isStartedSyncTask) {
      // 如果要保障 update_time 等字段一摸一样, 这里可以重新查询一次db
      // 在一定场景下 允许重复update, 因为只会对update_time造成影响, 对业务无影响
      // this.sendSyncMsg(SyncUserInfoMsgTypeEnum.UPDATE, JSON.toJSONString(reqDTO));
    }
    return true;
  }

  /**
   *
   * @param userId
   * @return
   */
  @Override
  public RpcResponse<String> delete(Long userId) {
    return null;
  }

  /**
   * 增量同步处理逻辑, 一定要注意消息的有序性
   * mq生产端: 设置单条队列
   * mq消费端: 设置顺序性消费
   * @param type
   * @param jsonStrBody
   */
  private void sendSyncMsg(SyncUserInfoMsgTypeEnum type, String jsonStrBody) {
    SyncUserInfoMQMsgBO syncUserInfoMQMsgBO = new SyncUserInfoMQMsgBO();
    syncUserInfoMQMsgBO.setMsgType(type.name());
    syncUserInfoMQMsgBO.setJson(jsonStrBody);
    Message message = new Message();
    message.setTopic(applicationProperties.getSyncUserInfoTopic());
    message.setBody(JSON.toJSONBytes(syncUserInfoMQMsgBO));
    try {
      SendResult sendResult = mqProducerClient.send(message);
      if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
        log.error("send sync msg status: {}, type: {}, body: {}", sendResult.getSendStatus(), type, jsonStrBody);
      }
    } catch (Exception e) {
      log.error("send sync msg error, type: {}, body: {}", e, type, jsonStrBody);
    }

  }
}
