package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/3/1
 * @Description:
 */
public interface CacheSyncMonitorService {

  /**
   *
   */
  void monitorStreamStatus();

  /**
   *
   */
  void smartCleanupExpiredMessages();

}