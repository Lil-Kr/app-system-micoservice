// package org.cy.micoservice.app.shortlink.api.config;
//
// import org.apache.commons.lang3.StringUtils;
// import org.springframework.cache.interceptor.KeyGenerator;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.util.Arrays;
// import java.util.stream.Collectors;
//
// /**
//  * 缓存配置 - JetCache 改造版
//  *
//  * 改造说明:
//  * - @EnableMethodCache: 启用方法级缓存 (@Cached 注解)
//  * - @EnableCreateCacheAnnotation: 启用 @CreateCache 注解 (Cache API)
//  * - @EnableCaching: 保留 Spring Cache (兼容性)
//  */
// @Configuration
// @EnableCaching
// public class CacheConfig {
//
//   /**
//    * 自定义Key生成器 - 解决 Null Key 问题
//    */
//   @Bean("safeKeyGenerator")
//   public KeyGenerator safeKeyGenerator() {
//     return (target, method, params) -> {
//       if (params == null || params.length == 0) {
//         return "default";
//       }
//
//       return Arrays.stream(params)
//         .map(param -> {
//           if (param == null) {
//             return "null";
//           }
//           if (param instanceof String && StringUtils.isBlank((String) param)) {
//             return "empty";
//           }
//           return param.toString();
//         })
//         .collect(Collectors.joining(":"));
//     };
//   }
//
//   /**
//    * 专门用于shortCode的Key生成器
//    */
//   @Bean("shortCodeKeyGenerator")
//   public KeyGenerator shortCodeKeyGenerator() {
//     return (target, method, params) -> {
//       if (params == null || params.length == 0) {
//         throw new IllegalArgumentException("shortCode参数不能为空");
//       }
//
//       Object shortCodeParam = params[0];
//       if (shortCodeParam == null) {
//         throw new IllegalArgumentException("shortCode不能为null");
//       }
//
//       String shortCode = shortCodeParam.toString();
//       if (StringUtils.isBlank(shortCode)) {
//         throw new IllegalArgumentException("shortCode不能为空字符串");
//       }
//
//       // 添加前缀避免key冲突
//       return "shortUrl:" + shortCode;
//     };
//   }
// }