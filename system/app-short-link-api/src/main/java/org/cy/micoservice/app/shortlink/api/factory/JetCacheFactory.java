package org.cy.micoservice.app.shortlink.api.factory;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import org.cy.micoservice.app.shortlink.api.config.ShortLinkCacheKeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Author: Lil-K
 * @Date: 2026/3/5
 * @Description:
 */
@Component
public class JetCacheFactory {

  @Autowired
  private ShortLinkCacheKeyBuilder cacheKeyBuilder;
  @Autowired
  private CacheManager cacheManager;

  public <K,V> Cache<K,V> createJetCache(String name,
                                         Duration localExpire,
                                         Duration remoteExpire,
                                         CacheType cacheType,
                                         int limit,
                                         boolean penetrationProtect,
                                         boolean cacheNullValue,
                                         boolean syncLocal
                                         ) {
    QuickConfig qc = QuickConfig.newBuilder(name)
      .localExpire(localExpire)
      .expire(remoteExpire)
      .cacheType(cacheType)
      .localLimit(limit)
      // 缓存穿透保护
      .penetrationProtect(penetrationProtect)
      // .penetrationProtectTimeout()
      // 缓存 null 值
      .cacheNullValue(cacheNullValue)
      // 开启多节点同步
      .syncLocal(syncLocal)
      .build();
    return cacheManager.getOrCreateCache(qc);
  }
}