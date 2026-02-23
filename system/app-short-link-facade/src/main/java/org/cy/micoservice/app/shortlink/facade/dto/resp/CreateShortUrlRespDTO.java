package org.cy.micoservice.app.shortlink.facade.dto.resp;

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
public class CreateShortUrlRespDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -6908254566929433526L;

  /**
   * 短链编码, 固定8位
   */
  private String shortCode;

  /**
   *
   */
  private String shortUrl;

  /**
   * 原始URL
   */
  private String originUrl;

  /**
   * 原始URL的MD5哈希值
   */
  private String originUrlHash;

  /**
   * 过期天数
   */
  private Integer expireDays;

  /**
   * 过期时间
   */
  private LocalDateTime expireTime;

  /**
   * 访问次数
   */
  private Long accessCount = 0L;

  private LocalDateTime createTime;
}