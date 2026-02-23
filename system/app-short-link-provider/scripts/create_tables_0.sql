-- ----------------------------
-- Table structure for t_short_url_mapping
-- ----------------------------
USE mico_app_short_link_0;
DROP TABLE IF EXISTS `t_short_url_mapping_0`;
CREATE TABLE `t_short_url_mapping_0`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_0';

DROP TABLE IF EXISTS `t_short_url_mapping_1`;
CREATE TABLE `t_short_url_mapping_1`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_1';

DROP TABLE IF EXISTS `t_short_url_mapping_2`;
CREATE TABLE `t_short_url_mapping_2`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_2';

DROP TABLE IF EXISTS `t_short_url_mapping_3`;
CREATE TABLE `t_short_url_mapping_3`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_3';

DROP TABLE IF EXISTS `t_short_url_mapping_4`;
CREATE TABLE `t_short_url_mapping_4`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_4';

DROP TABLE IF EXISTS `t_short_url_mapping_5`;
CREATE TABLE `t_short_url_mapping_5`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_5';

DROP TABLE IF EXISTS `t_short_url_mapping_6`;
CREATE TABLE `t_short_url_mapping_6`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_6';

DROP TABLE IF EXISTS `t_short_url_mapping_7`;
CREATE TABLE `t_short_url_mapping_7`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_7';

DROP TABLE IF EXISTS `t_short_url_mapping_8`;
CREATE TABLE `t_short_url_mapping_8`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_8';

DROP TABLE IF EXISTS `t_short_url_mapping_9`;
CREATE TABLE `t_short_url_mapping_9`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_9';

DROP TABLE IF EXISTS `t_short_url_mapping_10`;
CREATE TABLE `t_short_url_mapping_10`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_10';

DROP TABLE IF EXISTS `t_short_url_mapping_11`;
CREATE TABLE `t_short_url_mapping_11`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_11';

DROP TABLE IF EXISTS `t_short_url_mapping_12`;
CREATE TABLE `t_short_url_mapping_12`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_12';

DROP TABLE IF EXISTS `t_short_url_mapping_13`;
CREATE TABLE `t_short_url_mapping_13`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_13';

DROP TABLE IF EXISTS `t_short_url_mapping_14`;
CREATE TABLE `t_short_url_mapping_14`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_14';

DROP TABLE IF EXISTS `t_short_url_mapping_15`;
CREATE TABLE `t_short_url_mapping_15`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_15';

DROP TABLE IF EXISTS `t_short_url_mapping_16`;
CREATE TABLE `t_short_url_mapping_16`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_16';

DROP TABLE IF EXISTS `t_short_url_mapping_17`;
CREATE TABLE `t_short_url_mapping_17`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_17';

DROP TABLE IF EXISTS `t_short_url_mapping_18`;
CREATE TABLE `t_short_url_mapping_18`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_18';

DROP TABLE IF EXISTS `t_short_url_mapping_19`;
CREATE TABLE `t_short_url_mapping_19`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_19';

DROP TABLE IF EXISTS `t_short_url_mapping_20`;
CREATE TABLE `t_short_url_mapping_20`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_20';

DROP TABLE IF EXISTS `t_short_url_mapping_21`;
CREATE TABLE `t_short_url_mapping_21`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_21';

DROP TABLE IF EXISTS `t_short_url_mapping_22`;
CREATE TABLE `t_short_url_mapping_22`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_22';

DROP TABLE IF EXISTS `t_short_url_mapping_23`;
CREATE TABLE `t_short_url_mapping_23`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_23';

DROP TABLE IF EXISTS `t_short_url_mapping_24`;
CREATE TABLE `t_short_url_mapping_24`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_24';

DROP TABLE IF EXISTS `t_short_url_mapping_25`;
CREATE TABLE `t_short_url_mapping_25`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_25';

DROP TABLE IF EXISTS `t_short_url_mapping_26`;
CREATE TABLE `t_short_url_mapping_26`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_26';

DROP TABLE IF EXISTS `t_short_url_mapping_27`;
CREATE TABLE `t_short_url_mapping_27`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_27';

DROP TABLE IF EXISTS `t_short_url_mapping_28`;
CREATE TABLE `t_short_url_mapping_28`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_28';

DROP TABLE IF EXISTS `t_short_url_mapping_29`;
CREATE TABLE `t_short_url_mapping_29`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_29';

DROP TABLE IF EXISTS `t_short_url_mapping_30`;
CREATE TABLE `t_short_url_mapping_30`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_30';

DROP TABLE IF EXISTS `t_short_url_mapping_31`;
CREATE TABLE `t_short_url_mapping_31`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_31';

DROP TABLE IF EXISTS `t_short_url_mapping_32`;
CREATE TABLE `t_short_url_mapping_32`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_32';

DROP TABLE IF EXISTS `t_short_url_mapping_33`;
CREATE TABLE `t_short_url_mapping_33`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_33';

DROP TABLE IF EXISTS `t_short_url_mapping_34`;
CREATE TABLE `t_short_url_mapping_34`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_34';

DROP TABLE IF EXISTS `t_short_url_mapping_35`;
CREATE TABLE `t_short_url_mapping_35`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_35';

DROP TABLE IF EXISTS `t_short_url_mapping_36`;
CREATE TABLE `t_short_url_mapping_36`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_36';

DROP TABLE IF EXISTS `t_short_url_mapping_37`;
CREATE TABLE `t_short_url_mapping_37`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_37';

DROP TABLE IF EXISTS `t_short_url_mapping_38`;
CREATE TABLE `t_short_url_mapping_38`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_38';

DROP TABLE IF EXISTS `t_short_url_mapping_39`;
CREATE TABLE `t_short_url_mapping_39`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_39';

DROP TABLE IF EXISTS `t_short_url_mapping_40`;
CREATE TABLE `t_short_url_mapping_40`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_40';

DROP TABLE IF EXISTS `t_short_url_mapping_41`;
CREATE TABLE `t_short_url_mapping_41`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_41';

DROP TABLE IF EXISTS `t_short_url_mapping_42`;
CREATE TABLE `t_short_url_mapping_42`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_42';

DROP TABLE IF EXISTS `t_short_url_mapping_43`;
CREATE TABLE `t_short_url_mapping_43`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_43';

DROP TABLE IF EXISTS `t_short_url_mapping_44`;
CREATE TABLE `t_short_url_mapping_44`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_44';

DROP TABLE IF EXISTS `t_short_url_mapping_45`;
CREATE TABLE `t_short_url_mapping_45`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_45';

DROP TABLE IF EXISTS `t_short_url_mapping_46`;
CREATE TABLE `t_short_url_mapping_46`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_46';

DROP TABLE IF EXISTS `t_short_url_mapping_47`;
CREATE TABLE `t_short_url_mapping_47`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_47';

DROP TABLE IF EXISTS `t_short_url_mapping_48`;
CREATE TABLE `t_short_url_mapping_48`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_48';

DROP TABLE IF EXISTS `t_short_url_mapping_49`;
CREATE TABLE `t_short_url_mapping_49`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_49';

DROP TABLE IF EXISTS `t_short_url_mapping_50`;
CREATE TABLE `t_short_url_mapping_50`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_50';

DROP TABLE IF EXISTS `t_short_url_mapping_51`;
CREATE TABLE `t_short_url_mapping_51`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_51';

DROP TABLE IF EXISTS `t_short_url_mapping_52`;
CREATE TABLE `t_short_url_mapping_52`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_52';

DROP TABLE IF EXISTS `t_short_url_mapping_53`;
CREATE TABLE `t_short_url_mapping_53`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_53';

DROP TABLE IF EXISTS `t_short_url_mapping_54`;
CREATE TABLE `t_short_url_mapping_54`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_54';

DROP TABLE IF EXISTS `t_short_url_mapping_55`;
CREATE TABLE `t_short_url_mapping_55`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_55';

DROP TABLE IF EXISTS `t_short_url_mapping_56`;
CREATE TABLE `t_short_url_mapping_56`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_56';

DROP TABLE IF EXISTS `t_short_url_mapping_57`;
CREATE TABLE `t_short_url_mapping_57`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_57';

DROP TABLE IF EXISTS `t_short_url_mapping_58`;
CREATE TABLE `t_short_url_mapping_58`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_58';

DROP TABLE IF EXISTS `t_short_url_mapping_59`;
CREATE TABLE `t_short_url_mapping_59`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_59';

DROP TABLE IF EXISTS `t_short_url_mapping_60`;
CREATE TABLE `t_short_url_mapping_60`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_60';

DROP TABLE IF EXISTS `t_short_url_mapping_61`;
CREATE TABLE `t_short_url_mapping_61`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_61';

DROP TABLE IF EXISTS `t_short_url_mapping_62`;
CREATE TABLE `t_short_url_mapping_62`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_62';

DROP TABLE IF EXISTS `t_short_url_mapping_63`;
CREATE TABLE `t_short_url_mapping_63`  (
  `id` bigint NOT NULL,
  `short_code` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短链编码, 固定8位',
  `origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL',
  `origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始URL的MD5哈希值',
  `expire_days` int NULL DEFAULT NULL COMMENT '过期天数',
  `access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问次数',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 1-正常, 0-禁用, 2-已过期',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_id` bigint NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,
  INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,
  INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,
  INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短链接映射表_63';