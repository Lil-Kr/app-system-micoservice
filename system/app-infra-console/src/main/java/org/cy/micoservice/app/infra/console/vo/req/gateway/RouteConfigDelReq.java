package org.cy.micoservice.app.infra.console.vo.req.gateway;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/1/15
 * @Description:
 */
@Data
public class RouteConfigDelReq implements Serializable {

  @Serial
  private static final long serialVersionUID = -3812154315344047111L;

  private Long adminId;

  @NotNull(message = "id 不能为空")
  private Long id;
}