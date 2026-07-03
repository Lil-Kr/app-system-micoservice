package org.cy.micoservice.app.note.content.provider.dao.es;

import co.elastic.clients.elasticsearch._types.Conflicts;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateByQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cy.micoservice.app.audit.facade.enums.note.content.NoteStatusEnum;
import org.cy.micoservice.app.common.enums.biz.DeleteStatusEnum;
import org.cy.micoservice.app.entity.note.content.model.es.NoteEs;
import org.cy.micoservice.app.framework.elasticsearch.starter.utils.ElasticsearchUtil;
import org.cy.micoservice.app.framework.id.starter.service.IdService;
import org.cy.micoservice.app.note.content.provider.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
@Slf4j
@Repository
public class NoteEsMapper {

  @Autowired
  private IdService idService;
  @Autowired
  private ElasticsearchUtil elasticsearchUtil;
  @Autowired
  private ApplicationProperties applicationProperties;

  /**
   * insert note
   * @param noteEs
   * @return
   */
  public boolean insert(NoteEs noteEs) {
    Long id = idService.getId();
    IndexResponse response = elasticsearchUtil.indexDocument(applicationProperties.getNoteEsIndex(), String.valueOf(id), noteEs);
    return Result.Created == response.result() || Result.Updated == response.result();
  }

  /**
   * update by noteId
   * @param noteEs
   * @return
   */
  public boolean updateByNoteId(NoteEs noteEs) {
    Query query = Query.of(q -> q
      .bool(b -> b
        .must(m1 -> m1
          .term(t1 -> t1
            .field("noteId")
            .value(FieldValue.of(noteEs.getNoteId()))
          )
        )
      )
    );
    Map<String, Object> updateParam = new HashMap<>();
    if (StringUtils.isNotBlank(noteEs.getTitle())) {
      updateParam.put("title", noteEs.getTitle());
    }

    if (StringUtils.isNotBlank(noteEs.getTitle())) {
      updateParam.put("content", noteEs.getContent());
    }

    updateParam.put("status", String.valueOf(NoteStatusEnum.UNDER_REVIEW.getCode()));
    updateParam.put("deleted", String.valueOf(DeleteStatusEnum.ACTIVE.getCode()));
    UpdateByQueryResponse updateByQueryResponse = elasticsearchUtil.updateByQuery(applicationProperties.getNoteEsIndex(), query, updateParam, Conflicts.Proceed);
    return updateByQueryResponse.updated().equals(1L);
  }

  /**
   * query by note id
   * @param noteId
   * @return
   */
  public NoteEs queryByNoteId(Long noteId) {
    List<NoteEs> noteList = elasticsearchUtil.termQuery(applicationProperties.getNoteEsIndex(), "noteId", String.valueOf(noteId), NoteEs.class);
    if (CollectionUtils.isEmpty(noteList)) {
      return null;
    }
    return noteList.get(0);
  }

  /**
   * query by note ids
   * @param noteIds
   * @return
   */
  public List<NoteEs> queryByNoteIds(List<Long> noteIds) {
    List<String> noteIdStrList = noteIds.stream().map(String::valueOf).toList();
    return elasticsearchUtil.batchGetByIds(applicationProperties.getNoteEsIndex(), noteIdStrList, NoteEs.class);
  }

  /**
   *
   * @param id
   * @return
   */
  public boolean deleteById(Long id) {
    Query query = Query.of(q -> q
      .bool(b -> b
        .must(m1 -> m1
          .term(t1 -> t1
            .field("_id")
            .value(FieldValue.of(id))
          )
        )
      )
    );

    Map<String, Object> deleteUpdateParam = new HashMap<>();
    deleteUpdateParam.put("deleted", String.valueOf(DeleteStatusEnum.DELETED.getCode()));
    UpdateByQueryResponse updateByQueryResponse = elasticsearchUtil.updateByQuery(applicationProperties.getNoteEsIndex(), query, deleteUpdateParam, Conflicts.Proceed);
    return updateByQueryResponse.updated().equals(1L);
  }
}