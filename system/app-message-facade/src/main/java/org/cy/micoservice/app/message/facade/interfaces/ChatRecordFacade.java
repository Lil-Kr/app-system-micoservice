package org.cy.micoservice.app.message.facade.interfaces;

import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.message.facade.dto.resp.ChatRecordRespDTO;
import org.cy.micoservice.app.message.facade.dto.req.ChatRecordPageReqDTO;

/**
 * @Author: Lil-K
 * @Date: 2025/12/25
 * @Description:
 */
public interface ChatRecordFacade {

  /**
   * 分页查询聊天记录
   * @param chatRecordPageReqDTO
   * @return
   */
  RpcResponse<RpcPageResponse<ChatRecordRespDTO>> queryRecordInPage(ChatRecordPageReqDTO chatRecordPageReqDTO);
}