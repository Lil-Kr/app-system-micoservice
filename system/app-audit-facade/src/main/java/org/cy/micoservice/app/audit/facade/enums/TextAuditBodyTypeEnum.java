package org.cy.micoservice.app.audit.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lil-K
 * @Date: 2025/12/15
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum TextAuditBodyTypeEnum {
  // 笔记审核
  NOTE(1,"note"),
  // IM-聊天审核
  CHAT(2,"chat"),
  ;

  Integer code;
  String desc;
}
