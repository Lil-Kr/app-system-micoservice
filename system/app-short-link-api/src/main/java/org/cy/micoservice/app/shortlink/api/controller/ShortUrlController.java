package org.cy.micoservice.app.shortlink.api.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.shortlink.model.api.req.CreateShortUrlReq;
import org.cy.micoservice.app.entity.shortlink.model.api.req.ShortUrlGetReq;
import org.cy.micoservice.app.entity.shortlink.model.api.resp.CreateShortUrlResp;
import org.cy.micoservice.app.framework.web.starter.annotations.NoAuthCheck;
import org.cy.micoservice.app.shortlink.api.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/shortUrl")
public class ShortUrlController {

  @Autowired
  private ShortUrlService shortUrlService;

  /**
   * create short url
   * @param req
   * @return
   */
  @NoAuthCheck
  @PostMapping("/create")
  public ApiResp<CreateShortUrlResp> create(@RequestBody @Valid CreateShortUrlReq req) {
    CreateShortUrlResp resp = shortUrlService.createShortUrl(req);
    return ApiResp.success(resp);
  }

  /**
   * get short url info
   */
  @NoAuthCheck
  @GetMapping("/info")
  public ApiResp<CreateShortUrlResp> getShortUrlInfo(@Valid ShortUrlGetReq req) {
    CreateShortUrlResp resp = shortUrlService.getShortUrlInfo(req.getShortCode());
    return ApiResp.success(resp);
  }

  /**
   * url redirect
   * @param req
   * @param response
   * @return
   */
  @NoAuthCheck
  @GetMapping("/redirect")
  public void redirect(@Valid ShortUrlGetReq req, HttpServletResponse response) throws IOException {
    shortUrlService.redirect(req, response);
  }

}