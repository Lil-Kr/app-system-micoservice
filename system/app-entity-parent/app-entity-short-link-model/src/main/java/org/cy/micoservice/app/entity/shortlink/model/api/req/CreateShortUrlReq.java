package org.cy.micoservice.app.entity.shortlink.model.api.req;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Data
public class CreateShortUrlReq implements Serializable {

  @Serial
  private static final long serialVersionUID = -3671236016730217070L;

  @NotBlank(message = "原始URL不能为空")
  @Size(max = 2048, message = "URL长度不能超过2048个字符")
  private String originUrl;

  @NotNull(message = "过期时间不能为空")
  @Min(value = 1, message = "过期天数最少为1天")
  @Max(value = 7, message = "过期天数最多为7天")
  private Integer expireDays;

  private Long createId;

  private String customCode;
}
