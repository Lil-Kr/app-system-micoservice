package org.cy.micoservice.app.shortlink.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.cy.micoservice.app.common.constants.shortlink.ShortLinkConstants.*;
import static org.cy.micoservice.app.common.constants.shortlink.ShortLinkConstants.SHARDING_TABLE_COUNT;

/**
 * @Author: Lil-K
 * @Date: 2026/3/4
 * @Description:
 */
@Slf4j
@Component
public class CalculateIndexUtil {

  @Autowired
  private ShortLinkApiProperties properties;

  /**
   * 计算数据库索引 (更新为32个数据库)
   */
  public int calculateDatabaseIndex(String shortCode) {
    if (properties.isDualWriteEnabled()) {
      return Math.abs(shortCode.hashCode()) % NEW_SHARDING_DATABASE_COUNT;
    }
    return Math.abs(shortCode.hashCode()) % SHARDING_DATABASE_COUNT;
  }

  /**
   * 计算表索引 (更新为256张表)
   */
  public int calculateTableIndex(String shortCode) {
    if (properties.isDualWriteEnabled()) {
      return Math.abs(shortCode.hashCode()) % NEW_SHARDING_TABLE_COUNT;
    }
    return Math.abs(shortCode.hashCode()) % SHARDING_TABLE_COUNT;
  }

  /**
   * log
   * @param shortCode
   */
  public void recordNotExistLog(String shortCode) {
    log.debug("short url not exist: shortCode={}", shortCode);
  }
}