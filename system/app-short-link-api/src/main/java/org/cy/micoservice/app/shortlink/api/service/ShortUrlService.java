package org.cy.micoservice.app.shortlink.api.service;

import jakarta.servlet.http.HttpServletResponse;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.shortlink.api.vo.req.CreateShortUrlReq;
import org.cy.micoservice.app.shortlink.api.vo.req.ShortUrlGetReq;
import org.cy.micoservice.app.shortlink.api.vo.resp.CreateShortUrlResp;

import java.io.IOException;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 短链url服务 - 集群分片版本
 */
public interface ShortUrlService {

  /**
   * create short url
   * @param request
   * @return
   */
  CreateShortUrlResp createShortUrl(CreateShortUrlReq request);

  /**
   * 获取短链信息 (支持分库分表和Redis集群分片)
   * @param shortCode
   * @return
   */
  CreateShortUrlResp getShortUrlInfo(String shortCode);

  /**
   * 带Sentinel保护的短链查询 (支持分库分表和Redis集群分片)
   * @param shortCode
   * @return
   */
  // ShortUrlMapping getShortUrlWithSentinel(String shortCode);

  /**
   *
   * @param req
   * @param response
   * @throws IOException
   */
  ApiResp<String> redirect(ShortUrlGetReq req, HttpServletResponse response) throws Exception;

  /**
   * 更新访问次数
   * @param shortCode
   * @param count
   */
  // void updateAccessCountInDatabase(String shortCode, Long count);

  /**
   * 异步更新访问次数 (支持分库分表和Redis集群分片)
   * @param shortCode
   */
  // void updateAccessCountAsync(String shortCode);

  /**
   *
   * @param shortCode
   * @return
   */
  String getOriginUrl(String shortCode);
}