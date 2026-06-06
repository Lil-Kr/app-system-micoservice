package org.cy.micoservice.app.user.facade.dto.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author: Lil-K
 * @Date: 2026/5/6
 * @Description:
 */
@Data
public class UserFollowerRespDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 3863693295305757425L;

  private Long userId;

  private String nickName;

  private String avatarUrl;

  private String phone;

  private String sign;

  private Integer sex;

  private LocalDate birthDate;

  private String token;
}
