package org.cy.micoservice.app.user.provider.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.cy.micoservice.app.entity.user.model.UserShard;
import org.cy.micoservice.app.framework.rocketmq.starter.consumer.RocketMQConsumerProperties;
import org.cy.micoservice.app.user.facade.dto.req.UserSaveReqDTO;
import org.cy.micoservice.app.user.facade.enums.SyncUserInfoMsgTypeEnum;
import org.cy.micoservice.app.user.provider.bo.SyncUserInfoMQMsgBO;
import org.cy.micoservice.app.user.provider.config.ApplicationProperties;
import org.cy.micoservice.app.user.provider.service.UserShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description: user info sync consumer
 */
@Slf4j
@Component
public class UserInfoSyncConsumer {

  @Autowired
  private ApplicationProperties applicationProperties;
  @Autowired
  private RocketMQConsumerProperties rocketMQConsumerProperties;
  @Autowired
  private UserShardService userShardService;

  /**
   * 开启消费处理
   *
   * @throws MQClientException
   */
  // @PostConstruct
  public void startConsume() throws MQClientException {
    DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
    mqPushConsumer.setVipChannelEnabled(false);
    mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameserver());
    mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroup() + "_" + UserInfoSyncConsumer.class.getSimpleName());
    mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    // each pull 1 message
    mqPushConsumer.setConsumeMessageBatchMaxSize(1);
    mqPushConsumer.subscribe(applicationProperties.getSyncUserInfoTopic(), "");
    // 顺序消费场景, MessageListenerOrderly
    mqPushConsumer.setMessageListener(new MessageListenerOrderly() {
      @Override
      public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        try {
          for (MessageExt msg : msgs) {
            SyncUserInfoMQMsgBO syncUserInfoMQMsgBO = JSON.parseObject(msg.getBody(), SyncUserInfoMQMsgBO.class);
            String msgType = syncUserInfoMQMsgBO.getMsgType();
            if (SyncUserInfoMsgTypeEnum.INSERT.name().equals(msgType)) {
              UserShard userShard = JSONObject.parseObject(syncUserInfoMQMsgBO.getJson(), UserShard.class);
              userShardService.batchSave(Arrays.asList(userShard));
            } else if (SyncUserInfoMsgTypeEnum.UPDATE.name().equals(msgType)) {
              UserSaveReqDTO userSaveReqDTO = JSONObject.parseObject(syncUserInfoMQMsgBO.getJson(), UserSaveReqDTO.class);
              userShardService.update(userSaveReqDTO);
            }
          }
        } catch (Exception e) {
          log.info("用户信息同步过程异常: ", e);
          // if happen exception, will block, need to human intervention
          return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return ConsumeOrderlyStatus.SUCCESS;
      }

    });
    mqPushConsumer.start();
  }

}
