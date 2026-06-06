package org.cy.micoservice.app.user.provider.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.user.model.provider.pojo.User;
import org.cy.micoservice.app.entity.user.model.provider.pojo.UserShard;
import org.cy.micoservice.app.user.provider.mq.UserInfoSyncConsumer;
import org.cy.micoservice.app.user.provider.service.UserService;
import org.cy.micoservice.app.user.provider.service.UserShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description:
 */
@Slf4j
@Component
public class UserSyncTask {

  @Autowired
  private UserService userService;
  @Autowired
  private UserShardService userShardService;
  @Autowired
  private UserInfoSyncConsumer userInfoSyncConsumer;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  private final long batchSize = 2000;

  private AtomicLong offset = new AtomicLong(0);

  private Long startTime;


  public Long getStartTime() {
    return startTime;
  }

  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  /**
   * start sync task only enter
   * if distribution deployment, this task will run duplication
   * @return
   */
  public boolean startSyncTask() {
    String lockKey = "user-info-sync-task-key";
    boolean status = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 1, TimeUnit.DAYS);
    if (status) {
      Thread syncTask = new Thread(new Runnable() {
        @Override
        public void run() {
          fullSync();
          incrementSync();
        }
      });
      syncTask.setName("user-info-sync-task");
      syncTask.start();
      return true;
    }
    return false;
  }

  /**
   * full sync
   */
  public void fullSync() {
    this.setStartTime(System.currentTimeMillis());
    log.info("start sync user info");
    while (true) {
      // 1. query old user info list, judgement old userList is empty or not, if empty will break while loop
      List<User> oldUserList = userService.queryByOffset(offset.get(), batchSize);
      if (CollectionUtils.isEmpty(oldUserList)) {
        break;
      }

      // 2. insert new user sharding
      List<UserShard> userShardList = BeanCopyUtils.convertList(oldUserList, UserShard.class);
      userShardService.batchSave(userShardList);

      // 3. set offset value of id
      User user = oldUserList.get(oldUserList.size() - 1);
      offset.set(user.getId());
    }
    log.info("finish full sync user data");
  }

  /**
   * 增量同步
   */
  private void incrementSync() {
    //开启mq的消费，实现双写逻辑
    try {
      userInfoSyncConsumer.startConsume();
    } catch (MQClientException e) {
      log.error("start increment sync task error:", e);
    }
  }
}