package org.cy.micoservice.app.note.content.provider.service;

import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteEditReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NotePageReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteRespDTO;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
public interface NoteService {

  /**
   * create note
   * @param note
   * @return
   */
  NoteRespDTO createNote(NoteEditReqDTO note);

  /**
   * update note by noteId
   * @param note
   * @return
   */
  boolean updateByNoteId(NoteEditReqDTO note);

  /**
   * batch update status
   * @param ids
   * @param status
   */
  boolean batchUpdateStatus(List<Long> ids, int status);

  /**
   * query pagination for note
   * @param notePageReqDTO
   * @return
   */
  RpcPageResponse<NoteRespDTO> pageQueryNote(NotePageReqDTO notePageReqDTO);

  /**
   * delete note
   * @param id
   * @return
   */
  boolean deleteById(Long id);

  /**
   * query note content
   * @param noteId
   * @return
   */
  NoteRespDTO queryByNoteId(Long noteId);

  /**
   * batch query note info by noteId
   * @param ids
   * @return
   */
  List<NoteRespDTO> queryInNoteIds(List<Long> ids);
}