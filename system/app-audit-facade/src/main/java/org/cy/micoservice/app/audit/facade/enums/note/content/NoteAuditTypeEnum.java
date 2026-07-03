package org.cy.micoservice.app.audit.facade.enums.note.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum NoteAuditTypeEnum {

  TEXT(1, "text type"),
  ;

  Integer code;
  String name;

}