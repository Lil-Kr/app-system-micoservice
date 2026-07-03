package org.cy.micoservice.app.note.content.facade.interfaces;

import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteEditReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NotePageReqDTO;
import org.cy.micoservice.app.note.content.facade.dto.req.NoteRespDTO;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/24
 * @Description:
 */
public interface NoteFacade {

  /**
   * 新增笔记
   * @param note
   * @return
   */
  RpcResponse<NoteRespDTO> addNote(NoteEditReqDTO note);

  /**
   * 按照noteId更新笔记
   * @param note
   * @return
   */
  RpcResponse<Boolean> updateByNoteId(NoteEditReqDTO note);


  /**
   * 批量更新笔记状态
   * @param ids
   * @param status
   */
  RpcResponse<Boolean> batchUpdateStatus(List<Long> ids, int status);


  /**
   * 笔记分页查询
   * @param notePageReqDTO
   * @return
   */
  RpcPageResponse<NoteRespDTO> queryInPage(NotePageReqDTO notePageReqDTO);

  /**
   * 删除笔记
   * @param id
   * @return
   */
  RpcResponse<Boolean> deleteById(Long id);

  /**
   * 笔记详情查询
   * @param noteId
   * @return
   */
  RpcResponse<NoteRespDTO> queryByNoteId(Long noteId);

  /**
   * 按照笔记id批量查询
   * @param ids
   * @return
   */
  RpcResponse<List<NoteRespDTO>> queryInNoteIds(List<Long> ids);

}