package org.cy.micoservice.app.infra.console.facade.eunm.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lil-K
 * @Date: 2026/7/5
 * @Description: acl type enum
 */
@Getter
@AllArgsConstructor
public enum AclTypeEnum {

  // menu type
  MENU(1, "menu"),
  // button type
  BUTTON(2, "button"),
  // query type
  QUERY(3, "query")
  ;

  private int code;
  private String name;
}