package org.cy.micoservice.app.user.facade.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */
@Data
public class UserRegisterReqDTO implements Serializable  {
  @Serial
  private static final long serialVersionUID = 5680074218455594562L;

  private Long id;

  /**
   * 用户id
   */
  private Long userId;

  /**
   * 用户名
   */
  private String nickname;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 首次注册时间
   */
  private LocalDateTime registryTime;

  /**
   * 性别
   */
  private Integer sex;

  /**
   * 个性签名
   */
  private String sign;

  /**
   * 生日
   */
  private LocalDate birthday;

  /**
   * 上次登录时间
   */
  private LocalDateTime lastLoginTime;

}
