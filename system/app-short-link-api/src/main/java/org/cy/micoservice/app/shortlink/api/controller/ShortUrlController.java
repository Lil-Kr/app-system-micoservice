package org.cy.micoservice.app.shortlink.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.shortlink.model.api.req.CreateShortUrlReq;
import org.cy.micoservice.app.entity.shortlink.model.api.resp.CreateShortUrlResp;
import org.cy.micoservice.app.shortlink.api.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/short/url")
public class ShortUrlController {

  @Autowired
  private ShortUrlService shortUrlService;

  /**
   * create short url
   * @param req
   * @return
   */
  @PostMapping("/create")
  public ApiResp<CreateShortUrlResp> createShortUrl(@RequestBody @Valid CreateShortUrlReq req) {
    CreateShortUrlResp resp = shortUrlService.createShortUrl(req);
    return ApiResp.success(resp);
  }
}