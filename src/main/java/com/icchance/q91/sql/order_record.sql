-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `order_record`;
CREATE TABLE `order_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` tinyint NOT NULL COMMENT '訂單狀態',
  `amount` decimal(10,2) NOT NULL COMMENT '交易數量',
  `order_number` varchar(45) NOT NULL COMMENT '订单编号',
  `create_time` datetime NOT NULL COMMENT '建立時間',
  `user_id` int NOT NULL COMMENT '掛單用戶UID、買方用戶UID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='訂單紀錄';


-- 2023-10-03 06:19:54
