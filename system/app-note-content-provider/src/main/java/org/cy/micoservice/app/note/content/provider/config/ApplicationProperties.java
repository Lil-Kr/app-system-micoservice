package org.cy.micoservice.app.note.content.provider.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
@Data
@Component
public class ApplicationProperties {

  /**
   *
   */
  @Value("${note.audit.topic:common_audit_topic}")
  private String noteAuditTopic;

  /**
   * 审核结果响应topic
   */
  @Value("${common.audit.result.topic:common_audit_result_topic}")
  private String noteAuditResultTopic;

  /**
   *
   */
  @Value("${note.es.index:app.note.note-record}")
  private String noteEsIndex;

  /**
   * dynamic switch rate config
   */
  @Value("${note.es.read.rate:0.1}")
  private Double noteEsReadRate;

  @Value("${note.es.write.rate:0}")
  private Double noteEsWriteRate;
}