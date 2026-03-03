package org.cy.micoservice.app.shortlink.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.api.service.BloomFilterStreamService;
import org.cy.micoservice.app.shortlink.api.service.TieredBloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Lil-K
 * @Date: 2026/2/27
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/bloom")
public class BloomFilterController {

  @Autowired
  private TieredBloomFilterService tieredBloomFilterService;
  @Autowired
  private BloomFilterStreamService streamService;

  /**
   * 获取布隆过滤器统计信息
   */
  @GetMapping("/status")
  public Map<String, Object> getStatus() {
    return Map.of(
      "nodeInfo", tieredBloomFilterService.getNodeInfo(),
      "nodeId", streamService.getNodeId()
    );
  }

  /**
   * 手动测试短链是否存在
   */
  @GetMapping("/check/{shortCode}")
  public Map<String, Object> checkShortCode(@PathVariable("shortCode") String shortCode) {
    boolean exists = tieredBloomFilterService.mightContain(shortCode);
    return Map.of(
      "shortCode", shortCode,
      "mightExist", exists,
      "nodeId", streamService.getNodeId()
    );
  }

  /**
   * 手动添加短链 (测试用)
   */
  @PostMapping("/add/{shortCode}")
  public Map<String, Object> addShortCode(@PathVariable("shortCode") String shortCode) {
    tieredBloomFilterService.put(shortCode);
    return Map.of(
      "message", "短链已添加到布隆过滤器",
      "shortCode", shortCode,
      "nodeId", streamService.getNodeId()
    );
  }
}