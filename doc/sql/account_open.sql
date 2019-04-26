/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : choxsu

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 26/04/2019 17:47:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_open
-- ----------------------------
DROP TABLE IF EXISTS `account_open`;
CREATE TABLE `account_open`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `accountId` int(11) NOT NULL COMMENT '账户ID',
  `openType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方类型，比如qq、weibo',
  `openId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代表用户唯一身份的ID',
  `accessToken` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用接口需要用到的token，比如利用accessToken发表微博等，如果只是对接登录的话，这个其实没啥用',
  `expiredTime` bigint(20) NULL DEFAULT NULL COMMENT '授权过期时间，第三方登录授权都是有过期时间的，比如3个月之后，这里存放毫秒数，过期的毫秒数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `accountIdAndOpenId`(`accountId`, `openId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户-登录第三方表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
