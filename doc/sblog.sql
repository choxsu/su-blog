/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : sblog

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 05/01/2018 21:35:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `accountId` int(11) DEFAULT NULL COMMENT '博客主id',
  `title` varchar(150) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `createAt` datetime NOT NULL COMMENT '创建时间',
  `updateAt` datetime NOT NULL COMMENT '修改时间',
  `clickCount` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `likeCount` int(11) NOT NULL DEFAULT '0' COMMENT '喜欢次数',
  `favoriteCount` int(11) NOT NULL DEFAULT '0' COMMENT '收藏次数',
  `category` varchar(255) NOT NULL DEFAULT '' COMMENT '类型 note（笔记）favorite(收藏）code(代码）about(关于）',
  `isDelete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除 0否1是',
  `tag_id` int(11) DEFAULT NULL COMMENT 'tag_id',
  `category_id` int(11) DEFAULT NULL COMMENT '代码分类id，如果category为code时候，这个值才会生效',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `title` (`title`),
  FULLTEXT KEY `content` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for blog_account
-- ----------------------------
DROP TABLE IF EXISTS `blog_account`;
CREATE TABLE `blog_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nickName` varchar(50) NOT NULL COMMENT '昵称',
  `userName` varchar(150) NOT NULL COMMENT '用户名',
  `password` varchar(150) NOT NULL COMMENT '密码 md5加密后',
  `salt` varchar(150) NOT NULL COMMENT '盐',
  `status` int(11) NOT NULL COMMENT '状态 0-待激活 1-正常 2-禁用',
  `createAt` datetime NOT NULL COMMENT '创建时间',
  `ip` varchar(100) DEFAULT NULL COMMENT 'ip地址',
  `avatar` varchar(100) NOT NULL COMMENT '头像',
  `likeCount` int(11) NOT NULL DEFAULT '0' COMMENT '被赞次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='类别表';

-- ----------------------------
-- Records of blog_category
-- ----------------------------
BEGIN;
INSERT INTO `blog_category` VALUES (1, 'JAVA', 0);
INSERT INTO `blog_category` VALUES (2, 'Python', 0);
INSERT INTO `blog_category` VALUES (3, 'C', 0);
INSERT INTO `blog_category` VALUES (4, 'C++', 0);
INSERT INTO `blog_category` VALUES (5, 'Spring', 0);
INSERT INTO `blog_category` VALUES (6, 'GO', 0);
INSERT INTO `blog_category` VALUES (7, 'JavaScript', 0);
INSERT INTO `blog_category` VALUES (8, 'PHP', 0);
INSERT INTO `blog_category` VALUES (9, 'Kotiln', 0);
COMMIT;

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='标签表';

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
BEGIN;
INSERT INTO `blog_tag` VALUES (1, 'JAVA', 0);
INSERT INTO `blog_tag` VALUES (2, 'PHP', 0);
INSERT INTO `blog_tag` VALUES (3, 'JavaScript', 0);
INSERT INTO `blog_tag` VALUES (4, 'Python', 0);
INSERT INTO `blog_tag` VALUES (5, '排序算法', 0);
INSERT INTO `blog_tag` VALUES (6, 'APP', 0);
INSERT INTO `blog_tag` VALUES (7, '数据库', 0);
INSERT INTO `blog_tag` VALUES (8, 'Mysql', 0);
INSERT INTO `blog_tag` VALUES (9, 'SQL', 0);
INSERT INTO `blog_tag` VALUES (10, 'Unit', 0);
INSERT INTO `blog_tag` VALUES (11, '读书笔记', 0);
INSERT INTO `blog_tag` VALUES (12, 'JAVA基础', 0);
INSERT INTO `blog_tag` VALUES (13, '网络爬虫', 0);
INSERT INTO `blog_tag` VALUES (14, 'Hadoop', 0);
INSERT INTO `blog_tag` VALUES (15, '逻辑回归', 0);
INSERT INTO `blog_tag` VALUES (16, '算法', 0);
INSERT INTO `blog_tag` VALUES (17, 'HTML5', 0);
INSERT INTO `blog_tag` VALUES (18, 'Linux', 0);
INSERT INTO `blog_tag` VALUES (19, '数据挖掘', 0);
INSERT INTO `blog_tag` VALUES (20, 'Jfinal', 0);
INSERT INTO `blog_tag` VALUES (21, 'SSM', 0);
INSERT INTO `blog_tag` VALUES (22, 'SSH', 0);
INSERT INTO `blog_tag` VALUES (23, 'SpringBoot', 0);
INSERT INTO `blog_tag` VALUES (24, 'SpringCloud', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
