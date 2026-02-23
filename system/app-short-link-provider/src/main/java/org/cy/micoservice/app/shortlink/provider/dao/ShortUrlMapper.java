package org.cy.micoservice.app.shortlink.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cy.micoservice.app.entity.shortlink.model.provider.pojo.ShortUrlMapping;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Repository
public interface ShortUrlMapper extends BaseMapper<ShortUrlMapping> {

  ShortUrlMapping findByShortCode(String shortCode);
}
