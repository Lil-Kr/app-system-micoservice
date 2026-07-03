package org.cy.micoservice.app.note.content.provider.service.impl;

import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteEditReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NotePageReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteRespDTO;
import org.cy.micoservice.app.note.content.provider.service.NoteService;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
public class NoteServiceImpl implements NoteService {
  @Override
  public NoteRespDTO createNote(NoteEditReqDTO note) {
    return null;
  }

  @Override
  public boolean updateByNoteId(NoteEditReqDTO note) {
    return false;
  }

  @Override
  public boolean batchUpdateStatus(List<Long> ids, int status) {
    return false;
  }

  @Override
  public RpcPageResponse<NoteRespDTO> pageQueryNote(NotePageReqDTO notePageReqDTO) {
    return null;
  }

  @Override
  public boolean deleteById(Long id) {
    return false;
  }

  @Override
  public NoteRespDTO queryByNoteId(Long noteId) {
    return null;
  }

  @Override
  public List<NoteRespDTO> queryInNoteIds(List<Long> ids) {
    return List.of();
  }
}
