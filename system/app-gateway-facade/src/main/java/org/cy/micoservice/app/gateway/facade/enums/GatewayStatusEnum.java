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
public enum GatewayStatusEnum {
  VALID(0, "生效"),
  INVALID(1, "失效"),
  ;

  Integer code;
  String desc;
}