-- ====================================================================
-- 统一删除所有库中所有表的脚本
-- 说明: 删除16个库(mico_app_short_link_0 ~ mico_app_short_link_15)中的所有表
-- 每个库包含64张表(t_short_url_mapping_0 ~ t_short_url_mapping_63)
-- ====================================================================

DELIMITER //

DROP PROCEDURE IF EXISTS drop_all_short_link_tables//

CREATE PROCEDURE drop_all_short_link_tables()
BEGIN
  DECLARE db_index INT DEFAULT 0;
  DECLARE table_index INT DEFAULT 0;
  DECLARE db_name VARCHAR(64);
  DECLARE table_name VARCHAR(64);

  -- 循环删除16个库中的所有表
  WHILE db_index < 16 DO
    SET db_name = CONCAT('mico_app_short_link_', db_index);

    -- 循环删除64张表
    SET table_index = 0;
    WHILE table_index < 64 DO
      SET table_name = CONCAT('t_short_url_mapping_', table_index);

      -- 删除表(如果存在)
      SET @drop_sql = CONCAT('DROP TABLE IF EXISTS `', db_name, '`.`', table_name, '`');
      PREPARE stmt FROM @drop_sql;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;

      SET table_index = table_index + 1;
    END WHILE;

    SET db_index = db_index + 1;
  END WHILE;

  SELECT CONCAT('成功删除16个库中的所有表，共计 ', 16 * 64, ' 张表') AS result;
END //

DELIMITER ;

-- 执行存储过程
CALL drop_all_short_link_tables();

-- 删除存储过程(可选)
DROP PROCEDURE IF EXISTS drop_all_short_link_tables;


-- ====================================================================
-- 扩展版本: 删除32个库, 每个库256张表
-- 说明: 删除32个库(mico_app_short_link_0 ~ mico_app_short_link_31)中的所有表
-- 每个库包含256张表(t_short_url_mapping_0 ~ t_short_url_mapping_255)
-- ====================================================================

DELIMITER //

DROP PROCEDURE IF EXISTS drop_all_short_link_tables_extended//

CREATE PROCEDURE drop_all_short_link_tables_extended()
BEGIN
  DECLARE db_index INT DEFAULT 0;
  DECLARE table_index INT DEFAULT 0;
  DECLARE db_name VARCHAR(64);
  DECLARE table_name VARCHAR(64);

  -- 循环删除32个库中的所有表
  WHILE db_index < 32 DO
    SET db_name = CONCAT('mico_app_short_link_', db_index);

    -- 循环删除256张表
    SET table_index = 0;
    WHILE table_index < 256 DO
      SET table_name = CONCAT('t_short_url_mapping_', table_index);

      -- 删除表(如果存在)
      SET @drop_sql = CONCAT('DROP TABLE IF EXISTS `', db_name, '`.`', table_name, '`');
      PREPARE stmt FROM @drop_sql;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;

      SET table_index = table_index + 1;
    END WHILE;

    SET db_index = db_index + 1;
  END WHILE;

  SELECT CONCAT('成功删除64个库中的所有表，共计 ', 32 * 256, ' 张表') AS result;
END //

DELIMITER ;

-- 执行扩展存储过程
CALL drop_all_short_link_tables_extended();

-- 删除存储过程(可选)
DROP PROCEDURE IF EXISTS drop_all_short_link_tables_extended;
