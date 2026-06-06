package org.cy.micoservice.app.user.provider.facade;

import com.alibaba.fastjson2.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.cy.micoservice.app.common.base.provider.RpcResponse;
import org.cy.micoservice.app.entity.user.model.provider.pojo.UserShard;
import org.cy.micoservice.app.user.facade.interfaces.UserFacade;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: Lil-K
 * @Date: 2025/11/24
 * @Description:
 */
@Slf4j
public class UserProviderTest {

  // 应用配置
  private ApplicationConfig application;
  // 注册中心配置
  private RegistryConfig registry;

  @Before
  public void setUp() {
    application = new ApplicationConfig();
    application.setName("app-user-consumer-test");

    registry = new RegistryConfig();
    registry.setAddress("nacos://192.168.9.200:8848?namespace=app-mico-service-dev&username=nacos&&password=nacos");
  }

  @Test
  public void testDubboGlobalExceptionFilter() {
    // 引用远程服务
    ReferenceConfig<UserFacade> reference = new ReferenceConfig<>();
    reference.setApplication(application);
    reference.setRegistry(registry);
    reference.setInterface(UserFacade.class);
//    reference.setGroup("test-group");
    reference.setAsync(false);
    reference.setTimeout(10000);

    // 获取远程服务代理
    UserFacade userFacade = reference.get();
    try {
      RpcResponse<UserShard> userRpcResponse = userFacade.queryByUserId(null);
      log.info("userDTO: {}", JSONArray.toJSONString(userRpcResponse.getData()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}