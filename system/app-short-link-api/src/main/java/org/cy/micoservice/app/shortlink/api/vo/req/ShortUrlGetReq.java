package org.cy.micoservice.app.shortlink.api.vo.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/2/27
 * @Description:
 */
@Data
public class ShortUrlGetReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 317891221890934437L;

  /**
   *
   */
  @NotBlank(message = "shortCode is require")
  @Size(max = 10, message = "短链url长度不能超过10")
  private String shortCode;
}
