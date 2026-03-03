package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.framework.id.starter.service.IdService;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
import org.cy.micoservice.app.shortlink.provider.config.ShortLinkProviderProperties;
import org.cy.micoservice.app.shortlink.provider.dao.ShortUrlMapper;
import org.cy.micoservice.app.shortlink.provider.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 短链url服务 - 集群分片版本
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

  @Autowired
  private ShortLinkProviderProperties properties;
  @Autowired
  private IdService idService;
  @Autowired
  private ShortUrlMapper shortUrlMapper;

  /**
   * create short url
   * @param reqDTO
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public CreateShortUrlRespDTO createShortUrl(CreateShortUrlReqDTO reqDTO) {
    ShortUrlMapping entity = BeanCopyUtils.convert(reqDTO, ShortUrlMapping.class);
    long id = idService.getId();
    entity.setId(id);
    entity.setAccessCount(0L);
    entity.setUpdateId(reqDTO.getCreateId());
    LocalDateTime now = DateUtil.localDateTimeNow();
    entity.setCreateTime(now);
    entity.setUpdateTime(now);

    int insert = shortUrlMapper.insert(entity);
    if (insert < 1) {
      return new CreateShortUrlRespDTO();
    }
    CreateShortUrlRespDTO resp = BeanCopyUtils.convert(entity, CreateShortUrlRespDTO.class);
    return resp;
  }

  /**
   * findByShortCode
   * @param shortCode
   * @return
   */
  @Override
  public RpcResponse<CreateShortUrlRespDTO> findByShortCode(String shortCode) {
    CreateShortUrlReqDTO reqDTO = new CreateShortUrlReqDTO();
    reqDTO.setShortCode(shortCode);
    ShortUrlMapping shortUrlMapping = shortUrlMapper.findByShortCode(reqDTO);
    if (Objects.isNull(shortUrlMapping)) {
      return RpcResponse.emptyResult();
    }
    CreateShortUrlRespDTO respDTO = BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlRespDTO.class);
    return RpcResponse.success(respDTO);
  }

  /**
   * find origin url hash
   * @param originUrlHash
   * @return
   */
  @Override
  public RpcResponse<CreateShortUrlRespDTO> findByOriginUrlHash(String shortCode, String originUrlHash) {
    CreateShortUrlReqDTO reqDTO = new CreateShortUrlReqDTO();
    reqDTO.setShortCode(shortCode);
    reqDTO.setOriginUrlHash(originUrlHash);
    ShortUrlMapping shortUrlMapping = shortUrlMapper.findByOriginUrlHash(reqDTO);
    if (Objects.isNull(shortUrlMapping)) {
      return RpcResponse.emptyResult();
    }
    CreateShortUrlRespDTO respDTO = BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlRespDTO.class);
    return RpcResponse.success(respDTO);
  }

}
