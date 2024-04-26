/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : demo2

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 27/04/2024 00:20:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_fund
-- ----------------------------
DROP TABLE IF EXISTS `user_fund`;
CREATE TABLE `user_fund`  (
  `fund_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_amount` decimal(10, 2) NULL DEFAULT 100.00,
  PRIMARY KEY (`fund_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
