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

 Date: 27/04/2024 00:19:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ComName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ComSize` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ComDirection` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ComMember` int NULL DEFAULT 1,
  `ComIspublic` tinyint(1) NULL DEFAULT NULL,
  `IsApply` tinyint(1) NULL DEFAULT 'false',
  `is_frozen` tinyint(1) NULL DEFAULT 'false',
  UNIQUE INDEX `ComName`(`ComName` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('hhh', '12354', 'big', '12355', 1, 1, 1, 0);
INSERT INTO `company` VALUES ('f', '大航海', 'henda', '法律', 1, 1, 1, 0);
INSERT INTO `company` VALUES ('e', '是你的错', '撒', '大', 1, 1, 1, 0);
INSERT INTO `company` VALUES ('lisi', '荒坂', 'large', '科计', 1, 0, 1, 0);
INSERT INTO `company` VALUES ('f', '飘白', '很大', '洗浴', 1, 1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
