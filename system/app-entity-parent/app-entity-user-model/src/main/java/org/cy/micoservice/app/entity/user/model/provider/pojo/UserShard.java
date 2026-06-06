package org.cy.micoservice.app.entity.user.model.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description: sharding table
 */
@Data
@TableName("t_user_shard")
public class UserShard implements Serializable {

  @Serial
  private static final long serialVersionUID = -5053671343930064404L;

  @TableId(type = IdType.AUTO)
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
