-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `private_message`;
CREATE TABLE `private_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '使用者ID',
  `is_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已讀(0:未讀、1:已讀)',
  `title` varchar(60) NOT NULL COMMENT '站內信標題',
  `content` text NOT NULL COMMENT '站內信內容',
  `sender_id` int NOT NULL COMMENT '发件者 uid (-1: 系统管理员)',
  `type` tinyint NOT NULL COMMENT '站内信类型 (1: 一般信息, 2: 系统信息, 3: 紧急信息, 4: 优惠通知)',
  `create_time` datetime NOT NULL COMMENT '站内信发送时间 (timestamp)',
  `update_time` datetime DEFAULT NULL COMMENT '信件更新時間',
  `delete_time` datetime DEFAULT NULL COMMENT '信件刪除時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='會員站內信';


-- 2023-10-03 06:20:12
