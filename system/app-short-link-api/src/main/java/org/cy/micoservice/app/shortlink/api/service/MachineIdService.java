package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/28
 * @Description:
 * 机器ID分配服务
 * 为分布式环境中的每个节点自动分配唯一的机器ID
 */
public interface MachineIdService {

  long getMachineId();
}
