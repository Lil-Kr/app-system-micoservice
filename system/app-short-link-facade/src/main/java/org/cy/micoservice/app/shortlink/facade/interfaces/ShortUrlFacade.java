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

  RpcResponse<CreateShortUrlRespDTO> createShortUrl(CreateShortUrlReqDTO reqDTO);
}