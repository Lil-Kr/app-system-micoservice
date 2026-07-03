package org.cy.micoservice.app.user.api.vo.req;

import lombok.Data;

//import jakarta.validation.constraints.NotNull;

@Data
public class UserDeleteReq {

//  @NotNull(message = "id不能为空")
  private Long id;

//  @NotNull(message = "surrogateId不能为空")
  private Long surrogateId;
}
