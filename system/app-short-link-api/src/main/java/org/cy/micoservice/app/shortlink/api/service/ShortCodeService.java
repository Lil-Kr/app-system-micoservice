package org.cy.micoservice.app.shortlink.api.service;

/**
 * @Author: Lil-K
 * @Date: 2026/2/28
 * @Description:
 * 短链编码服务
 * 支持多种生成策略: 分布式ID、URL哈希、随机生成
 */
public interface ShortCodeService {

  /**
   * 根据配置策略生成短链
   * @param url
   * @return
   */
  String generateByStrategy(String url);

  /**
   * 生成唯一短链编码 (推荐使用 - 基于分布式ID)
   * 优势: 全局唯一、高性能、时间有序
   * @return
   */
  String generateUniqueCode();

  /**
   * 基于URL哈希生成短链编码 (确定性生成)
   * 优势: 相同URL生成相同短链, 缓存友好
   * @param url
   * @return
   */
  String generateByUrlHashDeterministic(String url);

  /**
   * 基于URL哈希生成短链编码 (带随机性)
   * 用于需要避免相同URL生成相同短链的场景
   * @param url
   * @return
   */
  String generateByUrlHashWithRandomness(String url);

}