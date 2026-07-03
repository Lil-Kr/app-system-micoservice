package org.cy.micoservice.app.infra.console.service.impl;


import org.cy.micoservice.app.infra.console.vo.req.image.ImageUploadReq;
import org.cy.micoservice.app.infra.console.vo.req.sys.admin.AvatarUploadReq;
import org.cy.micoservice.app.infra.console.service.interfaces.UploadService;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: Lil-K
 * @Date: 2025/4/20
 * @Description:
 */
@Service
public class UploadServiceImpl implements UploadService {

  @Override
  public boolean uploadImage(String fullPath, ImageUploadReq req) throws IOException {
    return false;
  }

  @Override
  public boolean uploadAvatar(String fullPath, AvatarUploadReq req) throws IOException {
    return false;
  }
}
