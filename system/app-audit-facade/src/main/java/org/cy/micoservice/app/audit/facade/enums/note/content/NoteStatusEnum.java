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
public enum NoteStatusEnum {

  UNDER_REVIEW(0,"审核中"),
  PUBLISHED(1,"已发布"),
  INVALID(2,"不合法")
  ;

  Integer code;
  String desc;
}