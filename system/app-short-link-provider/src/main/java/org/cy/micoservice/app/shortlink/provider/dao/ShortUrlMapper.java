package org.cy.micoservice.app.shortlink.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.cy.micoservice.app.entity.shortlink.model.ShortUrlMapping;
import org.cy.micoservice.app.shortlink.facade.dto.req.CreateShortUrlReqDTO;
import org.springframework.stereotype.Repository;

/**
 * @Author: Lil-K
 * @Date: 2026/2/23
 * @Description:
 */
@Repository
public interface ShortUrlMapper extends BaseMapper<ShortUrlMapping> {

  ShortUrlMapping findByShortCode(@Param("param") CreateShortUrlReqDTO reqDTO);

  // ShortUrlMapping findByOriginUrlHash(@Param("param") CreateShortUrlReqDTO reqDTO);

  Integer updateAccessCount(@Param("param") CreateShortUrlReqDTO reqDTO);
}
