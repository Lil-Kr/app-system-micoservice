package org.cy.micoservice.app.user.provider.bo;

import lombok.Data;
import org.cy.micoservice.app.user.facade.enums.SyncUserInfoMsgTypeEnum;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description:
 */
@Data
public class SyncUserInfoMQMsgBO {

  /**
   * 操作类型
   * @see SyncUserInfoMsgTypeEnum
   */
  private String msgType;

  /**
   * 原始数据的json格式
   */
  private String json;
}