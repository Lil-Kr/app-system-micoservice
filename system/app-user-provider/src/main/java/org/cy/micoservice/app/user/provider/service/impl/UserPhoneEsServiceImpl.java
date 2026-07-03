package org.cy.micoservice.app.user.provider.service.impl;

import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.security.Crypto;
import org.cy.micoservice.app.entity.user.model.es.UserPhoneEs;
import org.cy.micoservice.app.framework.elasticsearch.starter.utils.ElasticsearchUtil;
import org.cy.micoservice.app.user.provider.config.ApplicationProperties;
import org.cy.micoservice.app.user.provider.service.UserPhoneEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lil-K
 * @Date: 2026/6/23
 * @Description:
 */
@Slf4j
@Service
public class UserPhoneEsServiceImpl implements UserPhoneEsService {

  @Autowired
  private ElasticsearchUtil elasticsearchUtil;
  @Autowired
  private ApplicationProperties applicationProperties;
  @Autowired
  private Crypto crypto;

  /**
   * add user phone number info
   * @param userPhoneEs
   * @return
   */
  @Override
  public boolean add(UserPhoneEs userPhoneEs) {
    IndexResponse response = elasticsearchUtil.indexDocument(applicationProperties.getUserPhoneRelationEsIndex(),
      userPhoneEs.getId(),
      userPhoneEs
    );

    return Result.Created.equals(response.result());
  }

  /**
   * query by user_id
   * @param userId
   * @return
   */
  @Override
  public List<UserPhoneEs> queryByUserId(Long userId) {
    return elasticsearchUtil.termQuery(applicationProperties.getUserPhoneRelationEsIndex(),
      "userId",
      String.valueOf(userId),
      UserPhoneEs.class
    );
  }

  /**
   * query by phone
   * @param phone
   * @return
   */
  @Override
  public UserPhoneEs queryByPhone(String phone) {
    try {
      String encryptPhone = crypto.encrypt(phone);
      return elasticsearchUtil.termQuery(applicationProperties.getUserPhoneRelationEsIndex(),
          "encryptPhoneStr",
          encryptPhone,
          UserPhoneEs.class
        )
        .stream()
        .findFirst()
        .orElse(null);
    } catch (Exception e) {
      log.error("queryByPhone has error", e);
      throw new RuntimeException(e);
    }
  }
}
