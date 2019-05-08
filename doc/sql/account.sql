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

 Date: 08/05/2019 18:15:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userName` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(11) NOT NULL,
  `createAt` datetime(0) NOT NULL,
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `likeCount` int(11) NOT NULL DEFAULT 0 COMMENT '被赞次数',
  `isThird` int(2) NULL DEFAULT 0 COMMENT '是否第三方登陆 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'ChoxSu', 'choxsu@gmail.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-18 09:00:19', '175.12.244.105', '0/1.jpg', 0, 0);
INSERT INTO `account` VALUES (2, '管理员', 'admin@styg.site', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-19 10:19:11', '175.12.244.105', '0/1.jpg', 0, 0);
INSERT INTO `account` VALUES (3, 'test', 'test@test.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', 'RS_xQw8fhclJqZU2iDPYqa8EYyF9T6pc', 1, '2018-09-27 12:04:25', '183.64.28.18', 'x.jpg', 0, 0);
INSERT INTO `account` VALUES (4, 'test1', 'test1@test.com', 'b7cf9ab8832c2caeee7753efd1e70787b0fd72f17539f2e730e58ba01063b5ca', 'CR3yZ3xuDO2EB4jiEtj4HhwY0tpuq_-y', 1, '2018-12-21 22:11:14', '0:0:0:0:0:0:0:1', 'x.jpg', 0, 0);
INSERT INTO `account` VALUES (5, 'ChoxSu', '', '', '', 1, '2019-04-26 15:20:30', '127.0.0.1', 'http://thirdqq.qlogo.cn/g?b=oidb&k=xicaTlW45aV3cfeTh6fa8LA&s=40', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
