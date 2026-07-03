package org.cy.micoservice.app.message.api.service;


import org.cy.micoservice.app.common.base.provider.RpcPageResponse;
import org.cy.micoservice.app.message.api.vo.req.ChatRecordPageReq;
import org.cy.micoservice.app.message.api.vo.req.ChatRecordReq;
import org.cy.micoservice.app.message.api.vo.resp.ChatRecordResp;

/**
 * @Author: Lil-K
 * @Date: 2025/12/28
 * @Description: 聊天对话记录 service
 */
public interface ChatRecordService {

  /**
   * 插入发送记录
   * @param req
   * @return
   */
  Boolean add(ChatRecordReq req);

  /**
   * 分页查询聊天记录
   * @param req
   * @return
   */
  RpcPageResponse<ChatRecordResp> pageList(ChatRecordPageReq req);

}
