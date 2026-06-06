package org.cy.micoservice.app.common.base.provider;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2025/12/26
 * @Description: 分页响应体, provider层使用
 */
@Data
public class RpcPageResponse<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 2611737943133650738L;

  private int page;
  private int size;
  private List<T> dataList;
  private boolean hasNext;
  private Long searchOffset;

  public static <T> RpcPageResponse<T> emptyPage() {
    RpcPageResponse<T> RpcPageResponse = new RpcPageResponse<>();
    RpcPageResponse.setHasNext(false);
    RpcPageResponse.setDataList(Collections.emptyList());
    return RpcPageResponse;
  }
}
