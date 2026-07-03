package org.cy.micoservice.app.message.facade.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/7/3
 * @Description:
 */
@Data
public class OpenChatReqDTO implements Serializable  {
  @Serial
  private static final long serialVersionUID = -6543601235695306324L;

  // todo: 测试id, 这里不需要判空, 由 RequestContext.getUserId() 自动获取
  @NotNull(message = "userId 不能为空")
  private Long userId;

  @NotBlank(message = "relationId 不能为空")
  private String relationId;

  private Long receiverId;

  @NotNull(message = "seqNo 不能为空")
  private Long seqNo;
}