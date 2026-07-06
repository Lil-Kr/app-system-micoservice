package org.cy.micoservice.app.infra.console.vo.resp.blog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cy.micoservice.app.entity.infra.console.model.blog.BlogDiary;

import java.io.Serial;

/**
 * @Author: Lil-K
 * @Date: 2025/5/8
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogDiaryPageListResp extends BlogDiary {

  @Serial
  private static final long serialVersionUID = -3100079751347543684L;
  
  private String creatorName;
  private String operatorName;
}
