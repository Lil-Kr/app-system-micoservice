package org.cy.micoservice.app.user.provider.config;

import org.cy.micoservice.app.framework.cache.starter.config.RedisKeyBuilder;
import org.springframework.stereotype.Component;

/**
 * @Author: Lil-K
 * @Date: 2025/11/23
 * @Description: user service key prefix
 */
@Component
public class UserRedisKeyBuilder extends RedisKeyBuilder {

  /**
   * user info cache key
   * @param userId
   * @return
   */
  public String buildUserInfoCacheKey(Long userId) {
    return super.buildKey(String.format("user:%s", userId));
  }

  /**
   * user follower cache
   * @param userId
   * @return
   */
  public String buildUserIsFollowerCacheKey(Long userId, Long followerUserId) {
    return super.buildKey(String.format("is_follower:%s:%s", userId, followerUserId));
  }
}
