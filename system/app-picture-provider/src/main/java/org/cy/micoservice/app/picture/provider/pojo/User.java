package org.cy.micoservice.app.picture.provider.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/3/29
 * @Description:
 */
@Data
@TableName("t_user")
public class User implements Serializable {

  @Serial
  private static final long serialVersionUID = -1316506574359350638L;

  private Long id;

  private String username;
}