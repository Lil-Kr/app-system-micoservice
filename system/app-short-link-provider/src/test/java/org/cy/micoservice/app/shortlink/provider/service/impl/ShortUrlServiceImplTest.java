package org.cy.micoservice.app.shortlink.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.cy.micoservice.app.shortlink.facade.dto.resp.CreateShortUrlRespDTO;
import org.cy.micoservice.app.shortlink.provider.service.ShortUrlService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description: 短链服务分库分表测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceImplTest {

  @Autowired
  private ShortUrlService shortUrlService;

  /**
   * 测试单条数据插入 - 显示路由信息
   */
  // @Test
  public void testCreateSingleShortUrl() {
    String testShortCode = "ABCDEF01";
    CreateShortUrlReqDTO reqDTO = new CreateShortUrlReqDTO();
    reqDTO.setOriginUrl("https://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/%E5%B7%A6%E8%80%B3%E5%90%AC%E9%A3%8E");
    reqDTO.setExpireDays(30);

    log.info("==================== 开始测试单条短链创建 ====================");
    log.info("测试短码: {}", testShortCode);
    log.info("短码 HashCode: {}", testShortCode.hashCode());

    // 计算并打印路由信息
    int dbIndex = Math.abs(testShortCode.hashCode()) % 16;
    int tableIndex = Math.abs(testShortCode.hashCode()) % 64;

    log.info("");
    log.info("【路由计算】");
    log.info("  └─ 数据库: ds_{} (mico_app_short_link_{})", dbIndex, dbIndex);
    log.info("  └─ 表名: t_short_url_mapping_{}", tableIndex);
    log.info("");

    CreateShortUrlRespDTO response = shortUrlService.createShortUrl(reqDTO);

    log.info("【创建结果】");
    // log.info("  └─ ID: {}", response.getId());
    log.info("  └─ ShortCode: {}", response.getShortCode());
    log.info("  └─ OriginUrl: {}", response.getOriginUrl());
    log.info("==================== 测试完成 ====================");
  }
}
