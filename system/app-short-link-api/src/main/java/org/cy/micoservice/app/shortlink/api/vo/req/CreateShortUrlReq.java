package org.cy.micoservice.app.shortlink.api.vo.req;

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

  @NotBlank(message = "origin URL is not be null")
  @Size(max = 2048, message = "the URL length must not exceed 2048 characters.")
  @Pattern(
    regexp = "^(https?://)[\\w.-]+(:\\d+)?(/.*)?$",
    message = "URL is invalid, just support http/https"
  )
  private String originUrl;

  @NotNull(message = "The expiration time is required.")
  @Min(value = 1, message = "The expiration period must be at least 1 day.")
  @Max(value = 7, message = "The expiration period must not exceed 7 days.")
  private Integer expireDays;

  private Long createId;

  @Size(min = 1, max = 50,message = "The custom short code length must be between 1 and 50 characters.")
  private String customCode;
}
