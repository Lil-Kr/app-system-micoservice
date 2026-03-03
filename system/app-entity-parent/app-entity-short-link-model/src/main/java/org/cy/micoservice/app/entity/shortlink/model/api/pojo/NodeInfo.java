package org.cy.micoservice.app.entity.shortlink.model.api.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Lil-K
 * @Date: 2026/2/27
 * @Description:
 */
@Data
@Builder
public class NodeInfo implements Serializable {

  @Serial
  private static final long serialVersionUID = 7139270514325682650L;

  private String nodeId;
  private long timestamp;
  private boolean localServiceActive;
  private boolean redisServiceActive;
  private boolean streamServiceActive;
  private String localStats;
  private String redisStats;
  private long memoryUsedMB;
  private long memoryTotalMB;
  private long memoryMaxMB;
  private double memoryUsagePercent;
}