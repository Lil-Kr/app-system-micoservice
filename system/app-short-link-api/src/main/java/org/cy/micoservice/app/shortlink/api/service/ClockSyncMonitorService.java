package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/28
 * @Description:
 * 时钟同步监控服务
 * 监控服务器时钟偏移，防止时钟回拨问题
 */
public interface ClockSyncMonitorService {

  void checkClockSync();

  long getReferenceTime();
}
