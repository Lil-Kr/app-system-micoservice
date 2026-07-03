package org.cy.micoservice.app.user.provider.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.user.model.User;
import org.cy.micoservice.app.entity.user.model.UserShard;
import org.cy.micoservice.app.user.provider.mq.UserInfoSyncConsumer;
import org.cy.micoservice.app.user.provider.service.UserService;
import org.cy.micoservice.app.user.provider.service.UserShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
   * increment sync
   */
  public void incrementSync() {
    // 开启mq的消费, 实现双写逻辑
    try {
      userInfoSyncConsumer.startConsume();
    } catch (MQClientException e) {
      log.error("start increment sync task error:", e);
    }
  }

  /**
   * verify user info data task
   * @return
   */
  public boolean startVerifyUserDataTask() {
    String lockKey = "user-info-verify-task-key";
    boolean status = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 1, TimeUnit.DAYS);
    if (status) {
      Thread syncTask = new Thread(() -> verifyUserData());
      syncTask.setName("user-info-verify-task");
      syncTask.start();
      return true;
    }
    return false;
  }

  /**
   * user info verify
   */
  public void verifyUserData() {
    long offset = 0l;
    while (true) {
      List<Long> notExistUserIdList = new ArrayList<>();
      List<Long> diffUserIdList = new ArrayList<>();
      List<User> oldUserList = userService.queryByOffset(offset, batchSize);
      if (CollectionUtils.isEmpty(oldUserList)) {
        break;
      }
      List<Long> userIdList = oldUserList.stream().map(User::getId).collect(Collectors.toList());
      List<UserShard> userShardList = userShardService.queryInUserIds(userIdList);
      Map<Long, UserShard> userShardMap = userShardList.stream().collect(Collectors.toMap(UserShard::getUserId, item -> item));

      for (User user : oldUserList) {
        UserShard userShard = userShardMap.getOrDefault(user.getUserId(), null);
        if (userShard == null) {
          notExistUserIdList.add(user.getUserId());
          log.error("user shard is null, userId:{}", userShard.getUserId());
          continue;
        }

        if (this.compareUserAndUserShard(user, userShard)) {
          continue;
        }
        diffUserIdList.add(user.getUserId());
      }

      if (CollectionUtils.isNotEmpty(diffUserIdList)) {
        log.info("diffUserIdList user list size:{}, userIds:{}", diffUserIdList.size(), diffUserIdList);
      }

      if (CollectionUtils.isEmpty(notExistUserIdList)) {
        log.info("notExistUserIdList user list size:{}, userIds:{}", notExistUserIdList.size(), notExistUserIdList);
      }
      offset = batchSize + offset;
    }
  }

  /**
   *
   * @param user
   * @param userShard
   * @return
   */
  private boolean compareUserAndUserShard(User user, UserShard userShard) {
    if (Objects.isNull(user.getUserId())) {
      return false;
    }
    return Objects.equals(user.getUserId(), userShard.getUserId()) &&
      Objects.equals(user.getAvatar(), userShard.getAvatar()) &&
      user.getNickname().equals(userShard.getNickname()) &&
      user.getSign().equals(userShard.getSign()) &&
      user.getSex().equals(userShard.getSex()) &&
      user.getStatus().equals(userShard.getStatus()) &&
      user.getLastLoginTime().equals(userShard.getLastLoginTime()) &&
      user.getBirthday().equals(userShard.getBirthday()) &&
      user.getRegistryTime().equals(userShard.getRegistryTime()) &&
      user.getCreateTime().equals(userShard.getCreateTime());
  }
}