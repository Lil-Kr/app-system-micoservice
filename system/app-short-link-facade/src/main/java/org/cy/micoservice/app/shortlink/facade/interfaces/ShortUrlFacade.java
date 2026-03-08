package org.cy.micoservice.app.shortlink.facade.interfaces;

import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
public interface ShortUrlFacade {

  /**
   * create short url
   * @param reqDTO
   * @return
   */
  RpcResponse<CreateShortUrlRespDTO> createShortUrl(CreateShortUrlReqDTO reqDTO);

  /**
   * find shor-url by short code
   * @param shortCode
   * @return
   */
  RpcResponse<CreateShortUrlRespDTO> findByShortCode(String shortCode);

  /**
   * find shor-url by origin url hash
   * @param
   * @return
   */
  RpcResponse<CreateShortUrlRespDTO> findByOriginUrlHash(String shortCode, String originUrlHash);

  /**
   * 更新访问次数
   * @param shortCode
   * @param accessCount
   * @return
   */
  RpcResponse<Integer> updateAccessCount(String shortCode, Long accessCount);
}