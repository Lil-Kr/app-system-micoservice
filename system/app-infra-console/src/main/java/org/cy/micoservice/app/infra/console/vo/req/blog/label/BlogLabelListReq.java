package org.cy.micoservice.app.infra.console.vo.req.blog.label;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.base.model.api.BaseReq;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2024/3/31
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogLabelListReq extends BaseReq {

  @Serial
  private static final long serialVersionUID = -7963615286211942759L;

  private Long surrogateId;

  private Integer number;

  private String name;

  private String remark;

}
