package org.cy.micoservice.app.shortlink.provider.service;

import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;

/**
 * @Author: Lil-K
 * @Date: 2026/2/22
 * @Description: 短链url服务 - 集群分片版本
 */
public interface ShortUrlService {

  /**
   * create short url
   * @param reqDTO
   * @return
   */
  CreateShortUrlRespDTO createShortUrl(CreateShortUrlReqDTO reqDTO);

  /**
   * find shor-url by short code
   * @param shortCode
   * @return
   */
  RpcResponse<CreateShortUrlRespDTO> findByShortCode(String shortCode);

  /**
   * find shor-url by origin url hash
   * @param originUrlHash
   * @return
   */
  RpcResponse<CreateShortUrlRespDTO> findByOriginUrlHash(String shortCode, String originUrlHash);
}