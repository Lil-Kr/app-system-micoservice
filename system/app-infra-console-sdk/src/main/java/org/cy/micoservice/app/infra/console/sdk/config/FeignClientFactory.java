package org.cy.micoservice.app.infra.console.sdk.config;

import com.alibaba.nacos.api.naming.pojo.Instance;
import feign.Feign;
import org.cy.micoservice.app.common.constants.CommonFormatConstants;
import org.cy.micoservice.app.infra.console.facade.constants.InfraConsoleSdkConstants;
import org.cy.micoservice.app.infra.console.sdk.interceptor.InfraConsoleSdkHttpRequestInterceptor;

/**
 * @Author: Lil-K
 * @Date: Created at 2025/10/5
 * @Description: feign客户端工厂
 */
public class FeignClientFactory {

  private final NacosServiceDiscovery nacosDiscovery;

  public FeignClientFactory(NacosServiceDiscovery nacosDiscovery) {
    this.nacosDiscovery = nacosDiscovery;
  }

  /**
   * 创建 Feign 客户端 (动态替换URL为Nacos发现的实例)
   */
  public <T> T createClient(Class<T> clazz, String serviceName, String serviceGroup, String clientName) throws Exception {
    // get one of healthy target service instance from Nacos
    Instance instance = nacosDiscovery.getRandomHealthyInstance(serviceName, serviceGroup);
    // http://ip:port
    String httpUrl = String.format(CommonFormatConstants.COMMENT_FORMAT_COLON_SPLIT, InfraConsoleSdkConstants.HTTP_URL_PREFIX + instance.getIp(), instance.getPort());
    // each request will through this interceptor
    InfraConsoleSdkHttpRequestInterceptor requestInterceptor = new InfraConsoleSdkHttpRequestInterceptor(clientName, serviceName, serviceGroup, nacosDiscovery);

    return Feign.builder()
      .requestInterceptor(requestInterceptor)
      .encoder(FeignJacksonConfig.feignEncoder())
      .decoder(FeignJacksonConfig.feignDecoder())
      .target(clazz, httpUrl);
  }
}