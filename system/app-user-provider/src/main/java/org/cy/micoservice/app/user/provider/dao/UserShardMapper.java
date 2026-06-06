package org.cy.micoservice.app.user.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cy.micoservice.app.entity.user.model.provider.pojo.UserShard;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */
@Repository
public interface UserShardMapper extends BaseMapper<UserShard> {

  UserShard getUserByShardId(Long userId);
}