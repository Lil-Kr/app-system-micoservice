package org.cy.micoservice.app.shortlink.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum ShortUrlEnum {

  ENABLE(0,"正常"),
  DISABLE(1,"禁用"),
  EXPIRED(2,"已过期"),
  ;

  int code;
  String desc;
}