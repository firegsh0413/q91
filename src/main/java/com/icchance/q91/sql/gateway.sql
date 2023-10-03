-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `gateway`;
CREATE TABLE `gateway` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '使用者ID',
  `type` tinyint NOT NULL COMMENT '收付款方式  (1: 银行卡, 2: 微信, 3: 支付宝)',
  `name` varchar(45) NOT NULL COMMENT '綁定名字',
  `gateway_name` varchar(45) NOT NULL COMMENT '綁定名稱',
  `gateway_receipt_code` blob NOT NULL COMMENT '收付款號碼',
  `gateway_account` varchar(45) NOT NULL COMMENT '帳號',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付方式';


-- 2023-10-03 06:19:24
