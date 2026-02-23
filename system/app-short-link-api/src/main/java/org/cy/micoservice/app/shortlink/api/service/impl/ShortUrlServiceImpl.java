package org.cy.micoservice.app.shortlink.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.entity.shortlink.model.api.req.CreateShortUrlReq;
import org.cy.micoservice.app.entity.shortlink.model.api.resp.CreateShortUrlResp;
import org.cy.micoservice.app.shortlink.api.service.ShortUrlService;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
import org.cy.micoservice.app.shortlink.facade.interfaces.ShortUrlFacade;
import org.springframework.stereotype.Service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

  @DubboReference(check = false)
  private ShortUrlFacade shortUrlFacade;

  @Override
  public CreateShortUrlResp createShortUrl(CreateShortUrlReq req) {
    CreateShortUrlReqDTO reqDTO = BeanCopyUtils.convert(req, CreateShortUrlReqDTO.class);
    RpcResponse<CreateShortUrlRespDTO> rpcResponse = shortUrlFacade.createShortUrl(reqDTO);
    CreateShortUrlRespDTO data = rpcResponse.getData();

    CreateShortUrlResp resp = BeanCopyUtils.convert(data, CreateShortUrlResp.class);
    return resp;
  }
}