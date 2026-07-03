package org.cy.micoservice.app.entity.gateway.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cy.micoservice.app.entity.base.model.entity.BaseEntity;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2025/11/24
 * @Description:
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_route_change_log")
public class RouteChangeLog extends BaseEntity {

  @Serial
  private static final long serialVersionUID = -5964400619741497369L;

  private Long id;

  private Long configId;

  private Long version;

  private String changeEvent;

  /**
   * {"before":"xxx", "after":"xxxxx"}
   */
  private String changeBody;
}