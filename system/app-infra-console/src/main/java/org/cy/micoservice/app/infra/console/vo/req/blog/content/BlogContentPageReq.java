package org.cy.micoservice.app.infra.console.vo.req.blog.content;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2024/5/26
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogContentPageReq extends BasePageReq {

  @Serial
  private static final long serialVersionUID = 449411183467061614L;

  private Long surrogateId;

  private Integer number;

  private String name;

  private String title;

  private Integer original;

  private Integer recommend;

  private Integer status;

  private String remark;
}
