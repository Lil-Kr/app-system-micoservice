package org.cy.micoservice.app.user.api.vo.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2024/3/14
 * @Description:
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLoginAdminReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 6830047792374832648L;

  public interface AdminLogin {};

  private String token;

//  @NotBlank(groups = {AdminLogin.class}, message = "账号不能为空")
  private String account;

//  @NotBlank(groups = {AdminLogin.class}, message = "密码不能为空")
  private String password;

  private String email;
}
