package org.cy.micoservice.app.infra.console.vo.resp.image;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cy.micoservice.app.common.base.api.ApiPageResult;
import org.cy.micoservice.app.entity.infra.console.model.entity.image.ImageCategory;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2024/5/29
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class ImageCategoryResp extends ImageCategory implements Serializable {

  @Serial
  private static final long serialVersionUID = -575350097845168902L;

  private ApiPageResult<ImageInfoResp> imageInfo;
}
