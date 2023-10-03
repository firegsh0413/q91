-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `order_available_gateway`;
CREATE TABLE `order_available_gateway` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL COMMENT '訂單uid，(=pending_order的id欄位)',
  `gateway_id` varchar(45) NOT NULL COMMENT 'table:gateway的id欄位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='將前兩個table mapping而用的table。(pending_order、gateway:)';


-- 2023-10-03 06:19:32
