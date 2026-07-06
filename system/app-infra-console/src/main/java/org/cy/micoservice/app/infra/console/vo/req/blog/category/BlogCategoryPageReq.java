package org.cy.micoservice.app.infra.console.vo.req.blog.category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2025/3/28
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogCategoryPageReq extends BasePageReq {

  @Serial
  private static final long serialVersionUID = 7850723733268763469L;

  private Long surrogateId;

  private String number;

  private String name;

  private String color;

  private String remark;
}
