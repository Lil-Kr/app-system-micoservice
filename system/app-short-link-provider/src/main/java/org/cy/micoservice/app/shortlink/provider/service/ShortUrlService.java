package org.cy.micoservice.app.shortlink.provider.service;

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
   * create short url resp dto
   * @param shortCode
   * @return
   */
  CreateShortUrlRespDTO getShortUrlInfo(String shortCode);

}