package org.cy.micoservice.app.entity.base.model.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Lil-K
 * @Date: 2025/3/30
 * @Description: BaseEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = -3071591161451842453L;

  /**
   * delete statue
   */
  private Integer deleted;

  /**
   * 创建人
   */
  @JsonSerialize(using = ToStringSerializer.class)
  private Long createId;

  /**
   * 修改人
   */
  @JsonSerialize(using = ToStringSerializer.class)
  private Long updateId;

  /**
   * create time
   */
  private LocalDateTime createTime;

  /**
   * update time
   */
  private LocalDateTime updateTime;
}
