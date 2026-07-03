package org.cy.micoservice.app.shortlink.api.vo.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Data
public class CreateShortUrlResp implements Serializable {

  @Serial
  private static final long serialVersionUID = 6513902861764022955L;

  private Long id;

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
   * 访问次数
   */
  private Long accessCount;

  /**
   * 状态: 0-正常, 1-禁用, 2-已过期
   * org.cy.micoservice.app.shortlink.facade.provider.enums.ShortUrlEnum
   */
  private Integer status;

  /**
   * 创建人
   */
  private Long createId;

  /**
   * 修改人
   */
  private Long updateId;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更改时间
   */
  private LocalDateTime updateTime;
}
