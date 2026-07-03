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
 * @Date: 2026/6/23
 * @Description:
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPhoneEs extends BaseEsEntity {

  @Serial
  private static final long serialVersionUID = 8862169359070828969L;

  private Long userId;

  private String encryptPhoneStr;

}
