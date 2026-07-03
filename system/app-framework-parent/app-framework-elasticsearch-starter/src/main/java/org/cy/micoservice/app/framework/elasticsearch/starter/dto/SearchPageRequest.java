package org.cy.micoservice.app.framework.elasticsearch.starter.dto;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2025/12/25
 * @Description:
 */
@Data
public class SearchPageRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = -8851608243002725670L;

  private String indexName;

  private int pageSize;

  private SearchRequest searchRequest;
}