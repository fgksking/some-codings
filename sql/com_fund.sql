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

 Date: 27/04/2024 00:19:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for com_fund
-- ----------------------------
DROP TABLE IF EXISTS `com_fund`;
CREATE TABLE `com_fund`  (
  `com_fund_id` int NOT NULL AUTO_INCREMENT,
  `ComName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `com_amount` decimal(10, 2) NULL DEFAULT 1000.00,
  PRIMARY KEY (`com_fund_id`) USING BTREE,
  INDEX `ComName`(`ComName` ASC) USING BTREE,
  CONSTRAINT `com_fund_ibfk_1` FOREIGN KEY (`ComName`) REFERENCES `company` (`ComName`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
