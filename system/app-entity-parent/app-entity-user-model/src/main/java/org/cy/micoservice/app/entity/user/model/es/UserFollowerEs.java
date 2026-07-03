package org.cy.micoservice.app.entity.user.model.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cy.micoservice.app.entity.base.model.entity.BaseEsEntity;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2026/1/19
 * @Description: user follower ES entity
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserFollowerEs extends BaseEsEntity {

  @Serial
  private static final long serialVersionUID = -1591280980715407751L;

  /**
   * 被关注的人
   */
  private Long userId;

  /**
   * 粉丝id
   */
  private Long followerId;
}
