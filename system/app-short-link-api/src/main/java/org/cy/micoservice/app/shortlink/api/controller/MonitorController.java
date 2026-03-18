package org.cy.micoservice.app.shortlink.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.common.base.api.ApiResp;
import org.cy.micoservice.app.framework.web.starter.annotations.NoAuthCheck;
import org.cy.micoservice.app.shortlink.api.generator.ShortCodeGenerator;
import org.cy.micoservice.app.shortlink.api.service.ClockSyncMonitorService;
import org.cy.micoservice.app.shortlink.api.service.LocalBloomFilterService;
import org.cy.micoservice.app.shortlink.api.service.MachineIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Lil-K
 * @Date: 2026/2/27
 * @Description: 系统监控接口
 */
@Slf4j
@RestController
@RequestMapping("/monitor")
public class MonitorController {

  @Autowired
  private ShortCodeGenerator shortCodeGenerator;
  @Autowired
  private MachineIdService machineIdService;
  @Autowired
  private LocalBloomFilterService localBloomFilterService;
  @Autowired
  private ClockSyncMonitorService clockSyncMonitorService;

  @NoAuthCheck
  @GetMapping("/localstats")
  public ApiResp<String> localstats() {
    String localStats = localBloomFilterService.getLocalStats();
    return ApiResp.success(localStats);
  }
}