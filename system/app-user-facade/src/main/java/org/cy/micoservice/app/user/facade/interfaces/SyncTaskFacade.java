package org.cy.micoservice.app.user.facade.interfaces;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description: sync task facade
 */
public interface SyncTaskFacade {

  boolean startTask();

  void startVerifyUserDataTask();
}
