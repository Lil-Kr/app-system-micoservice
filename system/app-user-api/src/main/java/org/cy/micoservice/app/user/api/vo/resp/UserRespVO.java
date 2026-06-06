package org.cy.micoservice.app.user.api.vo.resp;

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
public class UserRespVO implements Serializable {

  @Serial
  private static final long serialVersionUID = -99719204696822993L;

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
   * 头像
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
   * 首次注册时间
   */
  private LocalDateTime registryTime;

  /**
   * 生日
   */
  private LocalDate birthday;

  /**
   * 上次登录时间
   */
  private LocalDateTime lastLoginTime;


  /**
   * create time
   */
  private LocalDateTime createTime;

  /**
   * update time
   */
  private LocalDateTime updateTime;

  /**
   * delete statue
   */
  private Integer deleted;
}