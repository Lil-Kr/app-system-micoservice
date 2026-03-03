-- ====================================================================
-- 统一创建所有库和表的脚本
-- 说明: 为16个库(mico_app_short_link_0 ~ mico_app_short_link_15)创建所有表
-- 每个库包含64张表(t_short_url_mapping_0 ~ t_short_url_mapping_63)
-- ====================================================================

DELIMITER //

DROP PROCEDURE IF EXISTS create_all_short_link_tables//

CREATE PROCEDURE create_all_short_link_tables()
BEGIN
  DECLARE db_index INT DEFAULT 0;
  DECLARE table_index INT DEFAULT 0;
  DECLARE db_name VARCHAR(64);
  DECLARE table_name VARCHAR(64);

  -- 循环创建16个库
  WHILE db_index < 16 DO
    SET db_name = CONCAT('mico_app_short_link_', db_index);

    -- 创建数据库(如果不存在)
    SET @create_db_sql = CONCAT('CREATE DATABASE IF NOT EXISTS `', db_name, '` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci');
    PREPARE stmt FROM @create_db_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    -- 循环创建64张表
    SET table_index = 0;
    WHILE table_index < 64 DO
      SET table_name = CONCAT('t_short_url_mapping_', table_index);

      -- 删除表(如果存在)
      SET @drop_sql = CONCAT('DROP TABLE IF EXISTS `', db_name, '`.`', table_name, '`');
      PREPARE stmt FROM @drop_sql;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;

      -- 创建表
      SET @create_sql = CONCAT(
        'CREATE TABLE `', db_name, '`.`', table_name, '` (',
        '`id` bigint NOT NULL,',
        '`short_code` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'短链编码, 固定10位\',',
        '`origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'原始URL\',',
        '`origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'原始URL的MD5哈希值\',',
        '`expire_days` int NULL DEFAULT NULL COMMENT \'过期天数\',',
        '`access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT \'访问次数\',',
        '`status` tinyint NOT NULL DEFAULT 1 COMMENT \'状态: 1-正常, 0-禁用, 2-已过期\',',
        '`create_id` bigint NULL DEFAULT NULL COMMENT \'创建人\',',
        '`update_id` bigint NULL DEFAULT NULL COMMENT \'操作人\',',
        '`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,',
        '`update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,',
        'PRIMARY KEY (`id`) USING BTREE,',
        'UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,',
        'UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,',
        'INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,',
        'INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,',
        'INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE',
        ') ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = \'短链接映射表_', table_index, '\''
      );
      PREPARE stmt FROM @create_sql;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;

      SET table_index = table_index + 1;
    END WHILE;

    SET db_index = db_index + 1;
  END WHILE;

  SELECT CONCAT('成功创建16个库，每个库64张表，共计 ', 16 * 64, ' 张表') AS result;
END //

DELIMITER ;

-- 执行存储过程
CALL create_all_short_link_tables();

-- 删除存储过程(可选)
DROP PROCEDURE IF EXISTS create_all_short_link_tables;


-- ====================================================================
-- 扩展版本: 创建64个库, 每个库256张表
-- 说明: 为64个库(mico_app_short_link_0 ~ mico_app_short_link_63)创建所有表
-- 每个库包含256张表(t_short_url_mapping_0 ~ t_short_url_mapping_255)
-- ====================================================================

DELIMITER //

DROP PROCEDURE IF EXISTS create_all_short_link_tables_extended//

CREATE PROCEDURE create_all_short_link_tables_extended()
BEGIN
  DECLARE db_index INT DEFAULT 0;
  DECLARE table_index INT DEFAULT 0;
  DECLARE db_name VARCHAR(64);
  DECLARE table_name VARCHAR(64);

  -- 循环创建64个库
  WHILE db_index < 64 DO
    SET db_name = CONCAT('mico_app_short_link_', db_index);

    -- 创建数据库(如果不存在)
    SET @create_db_sql = CONCAT('CREATE DATABASE IF NOT EXISTS `', db_name, '` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci');
    PREPARE stmt FROM @create_db_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    -- 循环创建256张表
    SET table_index = 0;
    WHILE table_index < 256 DO
      SET table_name = CONCAT('t_short_url_mapping_', table_index);

      -- 删除表(如果存在)
      SET @drop_sql = CONCAT('DROP TABLE IF EXISTS `', db_name, '`.`', table_name, '`');
      PREPARE stmt FROM @drop_sql;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;

      -- 创建表
      SET @create_sql = CONCAT(
        'CREATE TABLE `', db_name, '`.`', table_name, '` (',
        '`id` bigint NOT NULL,',
        '`short_code` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'短链编码, 固定10位\',',
        '`origin_url` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'原始URL\',',
        '`origin_url_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT \'原始URL的MD5哈希值\',',
        '`expire_days` int NULL DEFAULT NULL COMMENT \'过期天数\',',
        '`access_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT \'访问次数\',',
        '`status` tinyint NOT NULL DEFAULT 1 COMMENT \'状态: 1-正常, 0-禁用, 2-已过期\',',
        '`create_id` bigint NULL DEFAULT NULL COMMENT \'创建人\',',
        '`update_id` bigint NULL DEFAULT NULL COMMENT \'操作人\',',
        '`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,',
        '`update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,',
        'PRIMARY KEY (`id`) USING BTREE,',
        'UNIQUE INDEX `uk_short_code`(`short_code` ASC) USING BTREE,',
        'UNIQUE INDEX `uk_origin_url_hash`(`origin_url_hash` ASC) USING BTREE,',
        'INDEX `idx_status_create_time`(`status` ASC, `create_time` ASC) USING BTREE,',
        'INDEX `idx_expire_time`(`expire_days` ASC) USING BTREE,',
        'INDEX `idx_create_id_create_time`(`create_id` ASC, `create_time` ASC) USING BTREE',
        ') ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = \'短链接映射表_', table_index, '\''
      );
      PREPARE stmt FROM @create_sql;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;

      SET table_index = table_index + 1;
    END WHILE;

    SET db_index = db_index + 1;
  END WHILE;

  SELECT CONCAT('成功创建64个库，每个库256张表，共计 ', 64 * 256, ' 张表') AS result;
END //

DELIMITER ;

-- 执行扩展存储过程
CALL create_all_short_link_tables_extended();

-- 删除存储过程(可选)
DROP PROCEDURE IF EXISTS create_all_short_link_tables_extended;
