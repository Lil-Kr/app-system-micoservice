package org.cy.micoservice.app.infra.console.facade.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/7/6
 * @Description:
 */
@Data
public class RouteConfigQueryReqDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 3376501811129549962L;

  private String appName;

  private String schema;

  private String method;

  private String path;

  private String uri;

  private Integer status;

  private Integer deleted;
}
