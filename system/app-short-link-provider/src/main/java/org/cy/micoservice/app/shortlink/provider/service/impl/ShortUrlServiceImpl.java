package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.common.utils.BeanCopyUtils;
import org.cy.micoservice.app.common.utils.DateUtil;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.framework.id.starter.service.IdService;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
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
    ShortUrlMapping shortUrlMapping = shortUrlMapper.findByShortCode(reqDTO);
    if (shortUrlMapping != null) {
      shortUrlMapping.setAccessCount(shortUrlMapping.getAccessCount() + 1);
      shortUrlMapping.setUpdateTime(DateUtil.localDateTimeNow());
      CreateShortUrlReqDTO update = BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlReqDTO.class);
      shortUrlMapper.updateAccessCount(update);
      return BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlRespDTO.class);
    }

    // build insert entity
    ShortUrlMapping entity = BeanCopyUtils.convert(reqDTO, ShortUrlMapping.class);
    entity.setId(idService.getId());
    // default
    entity.setAccessCount(1L);
    entity.setUpdateId(reqDTO.getCreateId());
    LocalDateTime now = DateUtil.localDateTimeNow();
    entity.setCreateTime(now);
    entity.setUpdateTime(now);

    try {
      // insert new short code record
      shortUrlMapper.insert(entity);
      return BeanCopyUtils.convert(entity, CreateShortUrlRespDTO.class);
    } catch (Exception e) {
      String errorMsg = e.getMessage();
      log.warn("保存短链异常 - shortCode: {}, error: {}", reqDTO.getShortCode(), errorMsg);
      if (errorMsg != null && (errorMsg.toLowerCase().contains("duplicate") || errorMsg.contains("origin_url_hash"))) {
        ShortUrlMapping conflictRecord = shortUrlMapper.findByShortCode(reqDTO);
        if (conflictRecord != null) {
          log.info("冲突处理 - 找到已存在记录, 更新访问次数");
          conflictRecord.setAccessCount(conflictRecord.getAccessCount() + 1);
          conflictRecord.setUpdateTime(DateUtil.localDateTimeNow());
          CreateShortUrlReqDTO update = BeanCopyUtils.convert(conflictRecord, CreateShortUrlReqDTO.class);
          shortUrlMapper.updateAccessCount(update);
          return BeanCopyUtils.convert(conflictRecord, CreateShortUrlRespDTO.class);
        }
      }
      throw e;
    }
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
    ShortUrlMapping shortUrlMapping = shortUrlMapper.findByShortCode(reqDTO);
    if (Objects.isNull(shortUrlMapping)) {
      return RpcResponse.emptyResult();
    }
    CreateShortUrlRespDTO respDTO = BeanCopyUtils.convert(shortUrlMapping, CreateShortUrlRespDTO.class);
    return RpcResponse.success(respDTO);
  }

  /**
   * update access count
   * @param shortCode
   * @param accessCount
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public RpcResponse<Integer> updateAccessCount(String shortCode, Long accessCount) {
    CreateShortUrlReqDTO reqDTO = new CreateShortUrlReqDTO();
    reqDTO.setShortCode(shortCode);
    reqDTO.setAccessCount(accessCount);
    Integer update = shortUrlMapper.updateAccessCount(reqDTO);
    return RpcResponse.success(update);
  }

}
