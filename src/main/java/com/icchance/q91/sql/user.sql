-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(12) NOT NULL COMMENT '帳號',
  `username` varchar(16) NOT NULL COMMENT '暱稱',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密碼',
  `fund_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付密碼',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '钱包地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
  `update_time` datetime DEFAULT NULL COMMENT '更新時間',
  `certified` tinyint NOT NULL DEFAULT '0' COMMENT '是否实名制',
  `name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `id_number` varchar(45) DEFAULT NULL COMMENT '身分证号',
  `id_card` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '身分证照片 (base64)',
  `face_photo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '人脸辨别照片 (base64)',
  `role` tinyint DEFAULT NULL COMMENT '角色',
  `avatar` blob COMMENT '头像 base64 图片',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帳號';


-- 2023-10-03 06:20:22
