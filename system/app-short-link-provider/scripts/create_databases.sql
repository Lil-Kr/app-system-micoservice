-- ====================================================================
-- 数据库创建和删除脚本
-- ====================================================================

-- ====================================================================
-- 1. 创建16个库的存储过程
-- ====================================================================

DELIMITER //

DROP PROCEDURE IF EXISTS create_databases_16//

CREATE PROCEDURE create_databases_16()
BEGIN
  DECLARE i INT DEFAULT 0;
  DECLARE db_name VARCHAR(64);

  WHILE i < 16 DO
    SET db_name = CONCAT('mico_app_short_link_', i);
    SET @sql = CONCAT('CREATE DATABASE IF NOT EXISTS `', db_name, '` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
    SET i = i + 1;
  END WHILE;

  SELECT '成功创建16个数据库' AS result;
END //

DELIMITER ;

-- 执行创建16个库
CALL create_databases_16();


-- ====================================================================
-- 2. 创建32个库的存储过程
-- ====================================================================

DELIMITER //

DROP PROCEDURE IF EXISTS create_databases_32//

CREATE PROCEDURE create_databases_32()
BEGIN
  DECLARE i INT DEFAULT 0;
  DECLARE db_name VARCHAR(64);

  WHILE i < 32 DO
    SET db_name = CONCAT('mico_app_short_link_', i);
    SET @sql = CONCAT('CREATE DATABASE IF NOT EXISTS `', db_name, '` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
    SET i = i + 1;
  END WHILE;

  SELECT '成功创建32个数据库' AS result;
END //

DELIMITER ;

-- 执行创建32个库
CALL create_databases_32();


-- ====================================================================
-- 3. 删除所有库的存储过程
-- ====================================================================

DELIMITER //

DROP PROCEDURE IF EXISTS drop_databases//

CREATE PROCEDURE drop_databases(IN db_count INT)
BEGIN
  DECLARE i INT DEFAULT 0;
  DECLARE db_name VARCHAR(64);

  WHILE i < db_count DO
    SET db_name = CONCAT('mico_app_short_link_', i);
    SET @sql = CONCAT('DROP DATABASE IF EXISTS `', db_name, '`');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
    SET i = i + 1;
  END WHILE;

  SELECT CONCAT('成功删除 ', db_count, ' 个数据库') AS result;
END //

DELIMITER ;

-- 删除16个库
CALL drop_databases(16);

-- 删除32个库
CALL drop_databases(32);


-- ====================================================================
-- 静态SQL版本（直接执行，无需存储过程）
-- ====================================================================

-- 创建16个库
CREATE DATABASE IF NOT EXISTS mico_app_short_link_0 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_1 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_2 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_3 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_4 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_5 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_6 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_7 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_8 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_9 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_10 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_11 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_12 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_13 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_14 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_15 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- 创建32个库（追加）
CREATE DATABASE IF NOT EXISTS mico_app_short_link_16 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_17 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_18 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_19 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_20 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_21 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_22 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_23 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_24 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_25 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_26 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_27 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_28 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_29 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_30 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS mico_app_short_link_31 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- 删除16个库（谨慎使用！）
DROP DATABASE IF EXISTS mico_app_short_link_0;
DROP DATABASE IF EXISTS mico_app_short_link_1;
DROP DATABASE IF EXISTS mico_app_short_link_2;
DROP DATABASE IF EXISTS mico_app_short_link_3;
DROP DATABASE IF EXISTS mico_app_short_link_4;
DROP DATABASE IF EXISTS mico_app_short_link_5;
DROP DATABASE IF EXISTS mico_app_short_link_6;
DROP DATABASE IF EXISTS mico_app_short_link_7;
DROP DATABASE IF EXISTS mico_app_short_link_8;
DROP DATABASE IF EXISTS mico_app_short_link_9;
DROP DATABASE IF EXISTS mico_app_short_link_10;
DROP DATABASE IF EXISTS mico_app_short_link_11;
DROP DATABASE IF EXISTS mico_app_short_link_12;
DROP DATABASE IF EXISTS mico_app_short_link_13;
DROP DATABASE IF EXISTS mico_app_short_link_14;
DROP DATABASE IF EXISTS mico_app_short_link_15;


-- 删除32个库（谨慎使用！）
DROP DATABASE IF EXISTS mico_app_short_link_16;
DROP DATABASE IF EXISTS mico_app_short_link_17;
DROP DATABASE IF EXISTS mico_app_short_link_18;
DROP DATABASE IF EXISTS mico_app_short_link_19;
DROP DATABASE IF EXISTS mico_app_short_link_20;
DROP DATABASE IF EXISTS mico_app_short_link_21;
DROP DATABASE IF EXISTS mico_app_short_link_22;
DROP DATABASE IF EXISTS mico_app_short_link_23;
DROP DATABASE IF EXISTS mico_app_short_link_24;
DROP DATABASE IF EXISTS mico_app_short_link_25;
DROP DATABASE IF EXISTS mico_app_short_link_26;
DROP DATABASE IF EXISTS mico_app_short_link_27;
DROP DATABASE IF EXISTS mico_app_short_link_28;
DROP DATABASE IF EXISTS mico_app_short_link_29;
DROP DATABASE IF EXISTS mico_app_short_link_30;
DROP DATABASE IF EXISTS mico_app_short_link_31;
