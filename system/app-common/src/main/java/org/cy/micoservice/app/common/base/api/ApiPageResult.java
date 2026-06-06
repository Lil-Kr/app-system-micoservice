package org.cy.micoservice.app.common.base.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/3/30
 * @Description: 分页结果组件
 */
@Getter
@Setter
public class ApiPageResult<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = -8163365823187941281L;

  private List<T> list;

  /**
   * 总记录数
   */
  private Integer total;

  public ApiPageResult() {
  }

  public ApiPageResult(List<T> list, Integer total) {
    this.list = list;
    this.total = total;
  }

  public static <T> ApiPageResult<T> emptyPage() {
    return new ApiPageResult<>(null, 0);
  }
}
