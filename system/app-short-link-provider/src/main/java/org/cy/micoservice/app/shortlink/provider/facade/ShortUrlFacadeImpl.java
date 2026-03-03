package org.cy.micoservice.app.shortlink.provider.facade;

import org.apache.dubbo.config.annotation.DubboService;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
import org.cy.micoservice.app.shortlink.facade.interfaces.ShortUrlFacade;
import org.cy.micoservice.app.shortlink.provider.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Service
@DubboService
public class ShortUrlFacadeImpl implements ShortUrlFacade {

  @Autowired
  private ShortUrlService shortUrlService;

  /**
   * create short url
   * @param reqDTO
   * @return
   */
  @Override
  public RpcResponse<CreateShortUrlRespDTO> createShortUrl(CreateShortUrlReqDTO reqDTO) {
    CreateShortUrlRespDTO shortUrlRespDTO = shortUrlService.createShortUrl(reqDTO);
    return shortUrlRespDTO == null ? RpcResponse.emptyResult() : RpcResponse.success(shortUrlRespDTO);
  }

  /**
   * find shor-url by short code
   * @param shortCode
   * @return
   */
  @Override
  public RpcResponse<CreateShortUrlRespDTO> findByShortCode(String shortCode) {
    return shortUrlService.findByShortCode(shortCode);
  }

  /**
   * find shor-url by origin url hash
   * @param originUrlHash
   * @return
   */
  @Override
  public RpcResponse<CreateShortUrlRespDTO> findByOriginUrlHash(String shortCode, String originUrlHash) {
    return shortUrlService.findByOriginUrlHash(shortCode, originUrlHash);
  }
}
