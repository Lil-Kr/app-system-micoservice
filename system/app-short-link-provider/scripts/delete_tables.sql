DELIMITER $$

CREATE PROCEDURE drop_short_url_tables()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE sql_text TEXT;

    WHILE i <= 63 DO
        SET sql_text = CONCAT('DROP TABLE IF EXISTS `t_short_url_mapping_', i, '`;');
        SET @stmt = sql_text;
PREPARE s FROM @stmt;
EXECUTE s;
DEALLOCATE PREPARE s;
SET i = i + 1;
END WHILE;
END$$

DELIMITER ;

-- 执行删除
CALL drop_short_url_tables();

-- 删除存储过程 (可选)
DROP PROCEDURE drop_short_url_tables;


-- 256张表版本
DELIMITER $$

CREATE PROCEDURE drop_short_url_tables()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE sql_text TEXT;

    WHILE i <= 255 DO
        SET sql_text = CONCAT('DROP TABLE IF EXISTS `t_short_url_mapping_', i, '`;');
        SET @stmt = sql_text;
PREPARE s FROM @stmt;
EXECUTE s;
DEALLOCATE PREPARE s;
SET i = i + 1;
END WHILE;
END$$

DELIMITER ;

-- 执行删除
CALL drop_short_url_tables();

-- 删除存储过程 (可选)
DROP PROCEDURE drop_short_url_tables;