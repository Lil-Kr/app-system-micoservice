package org.cy.micoservice.app.user.facade.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description:
 */
@Data
public class UserSaveReqDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 6809415689784191719L;

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
   * 性别
   */
  private Integer sex;

  /**
   * salt value
   */
  private String salt;

  /**
   * avatar
   */
  private String avatar;

  /**
   * 个性签名
   */
  private String sign;

  /**
   * 状态
   */
  private Integer status;

  /**
   * delete statue
   */
  private Integer deleted;

  /**
   * 上次登录时间
   */
  private LocalDateTime lastLoginTime;

  /**
   * 首次注册时间
   */
  private LocalDateTime registryTime;

  /**
   * 生日
   */
  private LocalDate birthday;

  /**
   * create time
   */
  private LocalDateTime createTime;

  /**
   * update time
   */
  private LocalDateTime updateTime;
}