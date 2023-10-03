-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `user_balance`;
CREATE TABLE `user_balance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '钱包余额',
  `available_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '可售数量',
  `pending_balance` decimal(10,2) DEFAULT '0.00' COMMENT '賣單餘額',
  `trading_amount` decimal(10,2) DEFAULT '0.00' COMMENT '交易中金额',
  `user_id` int NOT NULL COMMENT '使用者ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '錢包建立時間',
  `update_time` datetime DEFAULT NULL COMMENT '錢包更新時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='會員錢包';


-- 2023-10-03 06:20:27
