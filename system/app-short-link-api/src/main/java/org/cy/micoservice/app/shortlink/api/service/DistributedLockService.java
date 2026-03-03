package org.cy.micoservice.app.shortlink.api.service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 分布式锁服务
 */
public interface DistributedLockService {

  <T> T executeWithLock(String lockKey, Supplier<T> supplier);

  <T> T executeWithLock(String lockKey, long waitTime, long leaseTime, TimeUnit timeUnit, Supplier<T> supplier);
}