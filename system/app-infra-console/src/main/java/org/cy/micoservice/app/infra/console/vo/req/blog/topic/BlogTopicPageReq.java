package org.cy.micoservice.app.infra.console.vo.req.blog.topic;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2024/5/25
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogTopicPageReq extends BasePageReq {

  @Serial
  private static final long serialVersionUID = 1922840476308891810L;

  private Long surrogateId;

  private Integer number;

  private String name;

  private String remark;
}
