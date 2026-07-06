package org.cy.micoservice.app.gateway.facade.dto.gateway.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.common.enums.biz.AuthTypeEnum;
import org.cy.micoservice.app.entity.base.model.api.BaseReq;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2026/7/3
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RouteConfigSaveReqDTO extends BaseReq {

  @Serial
  private static final long serialVersionUID = 9168922784985032124L;

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