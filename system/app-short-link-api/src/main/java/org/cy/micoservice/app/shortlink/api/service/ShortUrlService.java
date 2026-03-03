package org.cy.micoservice.app.shortlink.api.service;

import jakarta.servlet.http.HttpServletResponse;
import org.cy.micoservice.app.entity.shortlink.model.api.req.CreateShortUrlReq;
import org.cy.micoservice.app.entity.shortlink.model.api.req.ShortUrlGetReq;
import org.cy.micoservice.app.entity.shortlink.model.api.resp.CreateShortUrlResp;

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

  void redirect(ShortUrlGetReq req, HttpServletResponse response) throws IOException;
}