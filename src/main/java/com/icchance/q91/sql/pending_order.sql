-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `pending_order`;
CREATE TABLE `pending_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '賣方uid',
  `status` int NOT NULL COMMENT '挂单状态 (1: 挂卖中, 2: 買家已下單, 3: 買家未付款  4: 買家已付款 5: 已完成, 6: 已取消)',
  `create_time` datetime NOT NULL COMMENT '建立挂单时间 (timestamp)',
  `update_time` datetime DEFAULT NULL COMMENT '最后操作时间 (timestamp)',
  `trade_time` datetime DEFAULT NULL COMMENT '下单时间 (timestamp) (0 為尚未下單)',
  `buyer_id` int DEFAULT NULL COMMENT '购买会员 uid (0 為尚未交易)',
  `buyer_gateway_id` int DEFAULT NULL COMMENT '買方所選交易方式uid',
  `amount` decimal(10,2) NOT NULL COMMENT '挂卖金额',
  `order_number` varchar(45) NOT NULL COMMENT '挂单编号',
  `cert` blob COMMENT '支付凭证 base64 图片',
  `seller_gateway_id` int DEFAULT NULL COMMENT '賣方交易方式uid 買方選擇交易方式後才寫入 ',
  `available_gateway_str` varchar(20) DEFAULT NULL COMMENT '可用收款方式清单 (1: 银行卡, 2: 微信, 3: 支付宝)',
  `order_id` int DEFAULT NULL COMMENT '買方訂單uid',
  `operate_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作備註',
  `approve_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '審核備註',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出售掛賣時初步紀錄訂單';


-- 2023-10-03 06:20:03
