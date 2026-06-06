package org.cy.micoservice.app.entity.user.model.provider.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cy.micoservice.app.entity.base.model.api.BaseEntity;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2026/1/19
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_follower")
public class UserFollower extends BaseEntity {

  @Serial
  private static final long serialVersionUID = -1962103341799935748L;

  /**
   * 被关注的人
   */
  private Long userId;

  /**
   * 粉丝id
   */
  private Long followerId;
}
