-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '信息公告标题(系统信息、紧急信息、优惠通知)',
  `content` text NOT NULL COMMENT '信息公告内容',
  `create_time` datetime NOT NULL COMMENT '信息公告时间 (建立時間 yyyy-MM-dd HH:mm:ss)',
  `update_time` datetime DEFAULT NULL COMMENT '信息更新时间',
  `is_noticed` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读',
  `start_time` datetime DEFAULT NULL COMMENT '信息開始時間',
  `end_time` datetime DEFAULT NULL COMMENT '信息結束時間',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '信息狀態 (0: 關閉, 1: 開啟)',
  `is_pined` tinyint NOT NULL DEFAULT '0' COMMENT '是否置頂',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告訊息';


-- 2023-10-03 06:19:13
