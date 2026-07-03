package org.cy.micoservice.app.note.content.provider.facade;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteEditReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NotePageReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteRespDTO;
import org.cy.micoservice.app.note.content.facade.interfaces.NoteFacade;
import org.cy.micoservice.app.note.content.provider.config.ApplicationProperties;
import org.cy.micoservice.app.note.content.provider.service.NoteEsService;
import org.cy.micoservice.app.note.content.provider.service.NoteService;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
@DubboService
public class NoteFacadeImpl implements NoteFacade {

  @Resource
  private NoteService noteService;
  @Resource
  private NoteEsService noteEsService;
  @Resource
  private ApplicationProperties applicationProperties;

  /**
   *
   * @param note
   * @return
   */
  @Override
  public RpcResponse<NoteRespDTO> addNote(NoteEditReqDTO note) {
    return null;
  }

  /**
   *
   * @param note
   * @return
   */
  @Override
  public RpcResponse<Boolean> updateByNoteId(NoteEditReqDTO note) {
    return null;
  }

  /**
   *
   * @param ids
   * @param status
   * @return
   */
  @Override
  public RpcResponse<Boolean> batchUpdateStatus(List<Long> ids, int status) {
    return null;
  }

  /**
   *
   * @param notePageReqDTO
   * @return
   */
  @Override
  public RpcPageResponse<NoteRespDTO> queryInPage(NotePageReqDTO notePageReqDTO) {
    return null;
  }

  /**
   *
   * @param id
   * @return
   */
  @Override
  public RpcResponse<Boolean> deleteById(Long id) {
    return null;
  }

  /**
   *
   * @param noteId
   * @return
   */
  @Override
  public RpcResponse<NoteRespDTO> queryByNoteId(Long noteId) {
    return null;
  }

  /**
   *
   * @param ids
   * @return
   */
  @Override
  public RpcResponse<List<NoteRespDTO>> queryInNoteIds(List<Long> ids) {
    return null;
  }
}