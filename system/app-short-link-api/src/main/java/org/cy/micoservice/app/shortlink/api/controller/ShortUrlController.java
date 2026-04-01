package org.cy.micoservice.app.shortlink.api.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alicp.jetcache.Cache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.entity.shortlink.model.api.req.CreateShortUrlReq;
import org.cy.micoservice.app.entity.shortlink.model.api.req.ShortUrlGetReq;
import org.cy.micoservice.app.entity.shortlink.model.api.resp.CreateShortUrlResp;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.framework.web.starter.annotations.NoAuthCheck;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.cy.micoservice.app.shortlink.api.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.cy.micoservice.app.common.enums.response.ApiReturnCodeEnum.REQUEST_RESOURCE_NOT_EXIST;

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
  @Autowired
  private Cache<String, ShortUrlMapping> shortUrlCache;
  @Autowired
  private ShortLinkApiProperties properties;

  /**
   * create short url
   * @param req
   * @return
   */
  @NoAuthCheck
  @PostMapping("/create")
  public ApiResp<CreateShortUrlResp> create(@RequestBody @Valid CreateShortUrlReq req) {
    CreateShortUrlResp resp = shortUrlService.createShortUrl(req);
    return resp == null ? ApiResp.warning(REQUEST_RESOURCE_NOT_EXIST) : ApiResp.success(resp);
  }

  /**
   * get short url info
   */
  @NoAuthCheck
  @GetMapping("/info")
  public ApiResp<CreateShortUrlResp> getShortUrlInfo(@Valid ShortUrlGetReq req) {
    CreateShortUrlResp resp = shortUrlService.getShortUrlInfo(req.getShortCode());
    return resp == null ? ApiResp.warning(REQUEST_RESOURCE_NOT_EXIST) : ApiResp.success(resp);
  }

  /**
   * get origin url
   * @param req
   * @return
   */
  @NoAuthCheck
  @GetMapping("/originUrl")
  public ApiResp<String> originUrl(@Valid ShortUrlGetReq req) {
    return ApiResp.success(shortUrlService.getOriginUrl(req.getShortCode()));
  }

  /**
   *
   * @param req
   * @param response
   * @return
   * @throws Exception
   */
  @NoAuthCheck
  @SentinelResource(
    value = "redirectShortUrl",
    blockHandler = "redirectBlockHandler",
    fallback = "redirectFallback"
  )
  @GetMapping("/redirect")
  public ApiResp<String> redirect(@Valid ShortUrlGetReq req, HttpServletResponse response) throws Exception {
    return shortUrlService.redirect(req, response);
  }

  /** ======================== Sentinel 处理方法 ======================== **/
  public void redirectBlockHandler(String shortCode,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   BlockException ex) throws IOException {
    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    response.getWriter().write("Too many requests. Please try again later.");
  }

  // Sentinel 降级处理
  public void redirectFallback(String shortCode,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               Throwable ex) throws IOException {
    response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
    response.getWriter().write("Service is temporarily unavailable. Please try again later.");
  }
}