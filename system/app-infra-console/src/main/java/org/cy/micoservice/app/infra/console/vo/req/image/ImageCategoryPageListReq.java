package org.cy.micoservice.app.infra.console.vo.req.image;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.base.model.api.BasePageReq;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2024/5/29
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCategoryPageListReq extends BasePageReq {

  @Serial
  private static final long serialVersionUID = -5299126422788332985L;

  private String name;

  private String remark;
}
