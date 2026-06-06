package org.cy.micoservice.app.user.provider.facade;

import org.apache.dubbo.config.annotation.DubboService;
import org.cy.micoservice.app.user.facade.interfaces.SyncTaskFacade;
import org.cy.micoservice.app.user.provider.task.UserSyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lil-K
 * @Date: 2026/6/6
 * @Description: sync user info switch
 */
@Service
@DubboService(group = "sync-group",version = "1.2.0")
public class SyncTaskFacadeImpl implements SyncTaskFacade {

  @Autowired
  private UserSyncTask userSyncTask;

  @Override
  public boolean startTask() {
    return userSyncTask.startSyncTask();
  }
}
