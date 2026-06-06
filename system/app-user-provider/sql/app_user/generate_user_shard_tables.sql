DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `create_t_user_shard_tables`()
BEGIN
  SET @i = 0;
  WHILE @i < 100 DO
    SET @sql = CONCAT(
      'CREATE TABLE IF NOT EXISTS `t_user_', @i, '` (
        `id` bigint unsigned NOT NULL AUTO_INCREMENT,
        `user_id` bigint unsigned NOT NULL DEFAULT ''0'' COMMENT ''用户id'',
        `nickname` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '''' COMMENT ''昵称'',
        `sex` tinyint(1) NOT NULL DEFAULT ''0'' COMMENT ''男1 女2 未填写0'',
        `salt` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '''' COMMENT ''盐'',
        `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '''' COMMENT ''头像'',
        `sign` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '''' COMMENT ''个性签名'',
        `status` tinyint NOT NULL DEFAULT ''0'' COMMENT ''状态'',
        `last_login_time` datetime NOT NULL COMMENT ''最近登录时间（毫秒）'',
        `registry_time` datetime NOT NULL COMMENT ''首次注册时间（毫秒）'',
        `birthday` date DEFAULT NULL COMMENT ''生日'',
        `deleted` bigint NOT NULL DEFAULT ''0'' COMMENT ''逻辑删除（0未删除）'',
        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`),
        UNIQUE KEY `udx_user_id` (`user_id`)
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT=''用户基础信息表（分表', @i, '）'';'
    );
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
    SET @i = @i + 1;
  END WHILE;
END;;
DELIMITER ;