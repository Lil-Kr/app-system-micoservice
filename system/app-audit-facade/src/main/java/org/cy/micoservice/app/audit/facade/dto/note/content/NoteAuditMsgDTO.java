package org.cy.micoservice.app.audit.facade.dto.note.content;

import lombok.Data;
import org.cy.micoservice.app.audit.facade.enums.note.content.NoteAuditTypeEnum;
import java.io.Serial;
import java.io.Serializable;


/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
@Data
public class NoteAuditMsgDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -2616955092683013399L;

  /**
   * @see NoteAuditTypeEnum
   */
  private Integer auditType;

  private String auditBody;
}