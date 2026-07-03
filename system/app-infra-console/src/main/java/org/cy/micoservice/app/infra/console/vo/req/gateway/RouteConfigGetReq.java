package org.cy.micoservice.app.infra.console.vo.req.gateway;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/1/15
 * @Description:
 */
@Data
public class RouteConfigGetReq implements Serializable {

  @Serial
  private static final long serialVersionUID = -3812154315344047111L;

  private Long configId;
}