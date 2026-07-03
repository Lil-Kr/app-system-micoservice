package org.cy.micoservice.app.entity.base.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/6/11
 * @Description: Base ES Entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEsEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 5355729569164028563L;

  private String id;

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