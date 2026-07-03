package org.cy.micoservice.app.user.facade.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */
@Data
public class UserFollowerReqDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -5461484243669829395L;

  /**
   * id
   */
  private Long id;

  /**
   * user_id
   */
  private Long userId;

  /**
   * follower_id
   */
  private Long followerId;
}