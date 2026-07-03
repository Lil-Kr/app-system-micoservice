package org.cy.micoservice.app.infra.console.service.interfaces.gateway;

import com.alibaba.nacos.api.exception.NacosException;

/**
 * @Author: Lil-K
 * @Date: 2025/11/25
 * @Description:
 */
public interface NacosService {

  Long incrVersion() throws NacosException;
}
