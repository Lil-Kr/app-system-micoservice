package org.cy.micoservice.app.shortlink.facade.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Lil-K
 * @Date: 2026/2/22
 * @Description:
 */
@Data
public class CreateShortUrlReqDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -6908254566929433526L;

  private String shortCode;

  private String originUrl;

  /**
   * 原始URL的MD5哈希值
   */
  private String originUrlHash;

  private Integer expireDays;

  private Long accessCount;

  private Integer status;

  private String customCode;

  private Long createId;

  private Long updateId;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}