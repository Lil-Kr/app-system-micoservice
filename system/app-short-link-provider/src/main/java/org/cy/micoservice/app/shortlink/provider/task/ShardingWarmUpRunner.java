package org.cy.micoservice.app.shortlink.provider.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.cy.micoservice.app.shortlink.provider.dao.ShortUrlMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: Lil-K
 * @Date: 2026/2/24
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShardingWarmUpRunner implements ApplicationRunner {

  private final ShortUrlMapper shortUrlMapper;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("ShardingWarmUpRunner start");
    QueryWrapper<ShortUrlMapping> wrapper = new QueryWrapper<>();
    wrapper.eq("short_code", "warmup");
    shortUrlMapper.selectOne(wrapper);
  }
}