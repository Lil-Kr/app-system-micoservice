package org.cy.micoservice.app.shortlink.api.service;

import org.cy.micoservice.app.entity.shortlink.model.api.req.CreateShortUrlReq;
import org.cy.micoservice.app.entity.shortlink.model.api.resp.CreateShortUrlResp;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
public interface ShortUrlService {

  CreateShortUrlResp createShortUrl(CreateShortUrlReq request);
}