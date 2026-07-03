package org.cy.micoservice.app.infra.console.vo.req.gateway;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2025/11/27
 * @Description:
 */
@Data
public class RouteConfigQueryListReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 8269186067994879247L;

  private String appName;

  private String uri;

}