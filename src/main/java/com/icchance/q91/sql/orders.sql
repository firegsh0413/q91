-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '買方uid',
  `status` int NOT NULL COMMENT '訂單狀態',
  `create_time` datetime NOT NULL COMMENT '建立時間',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `trade_time` datetime DEFAULT NULL COMMENT '下單時間',
  `cut_off_time` datetime DEFAULT NULL COMMENT '截止時間',
  `seller_id` int DEFAULT NULL COMMENT '賣方uid',
  `amount` decimal(10,2) NOT NULL COMMENT '交易數量',
  `order_number` varchar(45) NOT NULL COMMENT '订单编号',
  `cert` blob COMMENT '支付憑證',
  `buyer_gateway_id` int DEFAULT NULL COMMENT '買方所選交易方式uid',
  `seller_gateway_id` int DEFAULT NULL COMMENT '賣方所選交易方式uid',
  `pending_order_id` int NOT NULL COMMENT '對應的掛單uid',
  `operate_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作備註',
  `approve_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '審核備註',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='訂單資訊';


-- 2023-10-03 06:19:46
