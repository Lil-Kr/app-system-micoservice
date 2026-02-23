package org.cy.micoservice.app.shortlink.facade.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/2/22
 * @Description:
 */
@Data
public class CreateShortUrlReqDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -6908254566929433526L;

  private String originUrl;

  private Integer expireDays;

  private String customCode;

  private Long createId;
}