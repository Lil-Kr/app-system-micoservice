package org.cy.micoservice.app.gateway.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lil-K
 * @Date: 2025/11/24
 * @Description: gateway statue enum
 */
@Getter
@AllArgsConstructor
public enum GatewaySchemaEnum {
  HTTP("http", "http协议"),
  DUBBO("dubbo", "dubbo协议"),
  ;

  String code;
  String desc;

  public static GatewaySchemaEnum getByCode(String code) {
    for (GatewaySchemaEnum gatewaySchemaEnum : GatewaySchemaEnum.values()) {
      if (gatewaySchemaEnum.getCode().equals(code)) {
        return gatewaySchemaEnum;
      }
    }
    return null;
  }
}