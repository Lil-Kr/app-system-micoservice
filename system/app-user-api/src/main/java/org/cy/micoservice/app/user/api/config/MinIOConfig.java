package org.cy.micoservice.app.user.api.config;

/**
 * @Author: Lil-K
 * @Date: 2025/12/31
 * @Description: MinIO的配置对象
 */
// @Configuration
// public class MinIOConfig {
//
//   @Value("${minio.endpoint:}")
//   private String endpoint;
//
//   @Value("${minio.access-key:}")
//   private String accessKey;
//
//   @Value("${minio.secret-key:}")
//   private String secretKey;
//
//   @Bean
//   public MinioClient minioClient() {
//     return MinioClient.builder()
//       .endpoint(endpoint)
//       .credentials(accessKey, secretKey)
//       .build();
//   }
// }