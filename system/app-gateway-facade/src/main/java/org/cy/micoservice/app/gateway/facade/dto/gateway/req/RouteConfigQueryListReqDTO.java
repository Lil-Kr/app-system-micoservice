package org.cy.micoservice.app.gateway.facade.dto.gateway.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/7/3
 * @Description:
 */
@Data
public class RouteConfigQueryListReqDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -7710651248465059474L;

  private String appName;

  private String uri;
}