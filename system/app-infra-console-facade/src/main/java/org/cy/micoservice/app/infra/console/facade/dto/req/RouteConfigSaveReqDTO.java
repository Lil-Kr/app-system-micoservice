package org.cy.micoservice.app.infra.console.facade.dto.req;

import lombok.Data;
import org.cy.micoservice.app.common.enums.biz.AuthTypeEnum;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/7/6
 * @Description:
 */
@Data
public class RouteConfigSaveReqDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 7089717524839079211L;

  private String appName;

  private String schema;

  private String method;

  private String path;

  private String uri;

  /**
   * 认证方式
   * @see AuthTypeEnum
   */
  private String authType;

  private Integer status;

  private String dubboInvokeParamClass;

  private String providerName;

  private String providerInterface;

  private String providerInterfaceMethod;
}