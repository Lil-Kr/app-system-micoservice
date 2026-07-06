package org.cy.micoservice.app.infra.console.vo.req.blog.label;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2024/4/4
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogLabelPageReq extends BasePageReq {

  @Serial
  private static final long serialVersionUID = 6858743242669678910L;

  private Long surrogateId;

  private Integer number;

  private String name;

  private String remark;
}
