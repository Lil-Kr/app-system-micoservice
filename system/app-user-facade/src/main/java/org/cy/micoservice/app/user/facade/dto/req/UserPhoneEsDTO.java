package org.cy.micoservice.app.user.facade.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/6/23
 * @Description:
 */
@Data
public class UserPhoneEsDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -1444988691848929790L;

  private String id;

  private Long userId;

  private String encryptPhoneStr;

  /**
   * create time
   */
  private Long createTime;

  /**
   * update time
   */
  private Long updateTime;

  /**
   * delete statue
   */
  private Integer deleted;
}