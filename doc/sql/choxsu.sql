/*
 Navicat Premium Data Transfer

 Source Server         : 207
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 47.104.210.207:13306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 21/06/2019 10:05:30
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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `account` VALUES (1, 'ChoxSu', 'choxsu@gmail.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-18 09:00:19', '175.12.244.105', '0/1.jpg', 0, 0);
INSERT INTO `account` VALUES (2, '管理员', 'admin@styg.site', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-19 10:19:11', '175.12.244.105', '0/1.jpg', 0, 0);
INSERT INTO `account` VALUES (3, 'test', 'test@test.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', 'RS_xQw8fhclJqZU2iDPYqa8EYyF9T6pc', 1, '2018-09-27 12:04:25', '183.64.28.18', 'x.jpg', 0, 0);
INSERT INTO `account` VALUES (4, 'test1', 'test1@test.com', 'b7cf9ab8832c2caeee7753efd1e70787b0fd72f17539f2e730e58ba01063b5ca', 'CR3yZ3xuDO2EB4jiEtj4HhwY0tpuq_-y', 1, '2018-12-21 22:11:14', '0:0:0:0:0:0:0:1', 'x.jpg', 0, 0);
INSERT INTO `account` VALUES (5, 'ChoxSu', '', '', '', 1, '2019-04-26 15:20:30', '127.0.0.1', 'http://thirdqq.qlogo.cn/g?b=oidb&k=xicaTlW45aV3cfeTh6fa8LA&s=40', 0, 1);


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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户-登录第三方表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role`  (
  `accountId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`accountId`, `roleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES (1, 1);
INSERT INTO `account_role` VALUES (2, 6);
INSERT INTO `account_role` VALUES (3, 9);



-- ----------------------------
-- Table structure for auth_code
-- ----------------------------
DROP TABLE IF EXISTS `auth_code`;
CREATE TABLE `auth_code`  (
  `id` varchar(33) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  `type` int(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `accountId` int(11) NULL DEFAULT NULL COMMENT '博客主id',
  `title` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `markedContent` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '待解析内容',
  `createAt` datetime(0) NOT NULL COMMENT '创建时间',
  `updateAt` datetime(0) NOT NULL COMMENT '修改时间',
  `clickCount` int(11) NOT NULL DEFAULT 0 COMMENT '点击次数',
  `likeCount` int(11) NOT NULL DEFAULT 0 COMMENT '喜欢次数',
  `favoriteCount` int(11) NOT NULL DEFAULT 0 COMMENT '收藏次数',
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型 note（笔记）favorite(收藏）code(代码）about(关于）',
  `isDelete` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除 0否1是',
  `tag_id` int(11) NULL DEFAULT NULL COMMENT 'tag_id',
  `category_id` int(11) NULL DEFAULT NULL COMMENT '代码分类id，如果category为code时候，这个值才会生效',
  `allowComments` tinyint(1) NULL DEFAULT 0 COMMENT '允许评论',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `title`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_reply
-- ----------------------------
DROP TABLE IF EXISTS `blog_reply`;
CREATE TABLE `blog_reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `accountId` int(11) NOT NULL COMMENT '账户ID',
  `blogId` int(11) NOT NULL COMMENT '文章ID',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `createTime` datetime(0) NOT NULL COMMENT '创建时间',
  `report` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `accountId` int(11) NOT NULL COMMENT '账户ID',
  `friendId` int(11) NOT NULL COMMENT '朋友账户ID',
  `createAt` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`accountId`, `friendId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for images
-- ----------------------------
DROP TABLE IF EXISTS `images`;
CREATE TABLE `images`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` int(11) NOT NULL COMMENT '上传者',
  `src` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片路径',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片原始名称',
  `type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片类型',
  `created` int(255) NOT NULL COMMENT '创建时间',
  `source` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
  `file_size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件大小',
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原始名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '图片上厂记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ip_repository
-- ----------------------------
DROP TABLE IF EXISTS `ip_repository`;
CREATE TABLE `ip_repository`  (
  `id` bigint(20) NOT NULL COMMENT ' ',
  `ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP',
  `country` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家',
  `area` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域',
  `region` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域2',
  `city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `county` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '县',
  `isp` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运营商',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `country_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家代码',
  `area_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `region_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `county_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isp_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ip`(`ip`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'IP库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_manager
-- ----------------------------
DROP TABLE IF EXISTS `job_manager`;
CREATE TABLE `job_manager`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '任务名',
  `group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '组名',
  `clazz` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类名',
  `cron_expression` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '定时表达式',
  `is_enabled` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '是否开启',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`, `group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `accountId` int(11) NOT NULL,
  `loginAt` datetime(0) NOT NULL,
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `accountId_index`(`accountId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user` int(11) NOT NULL COMMENT '消息的主人',
  `friend` int(11) NOT NULL COMMENT '对方的ID',
  `sender` int(11) NOT NULL COMMENT '发送者',
  `receiver` int(11) NOT NULL COMMENT '接收者',
  `type` tinyint(2) NOT NULL COMMENT '0：普通消息，1：系统消息',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细内容',
  `createAt` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news_feed
-- ----------------------------
DROP TABLE IF EXISTS `news_feed`;
CREATE TABLE `news_feed`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL COMMENT '动态创建者',
  `refType` tinyint(2) NOT NULL COMMENT '动态引用类型',
  `refId` int(11) NOT NULL DEFAULT 0 COMMENT '动态引用所关联的 id',
  `refParentType` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'reply所属的贴子类型, 与type 字段填的值一样',
  `refParentId` int(11) NOT NULL DEFAULT 0,
  `createAt` datetime(0) NOT NULL COMMENT '动态创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actionKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `controller` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 161 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for refer_me
-- ----------------------------
DROP TABLE IF EXISTS `refer_me`;
CREATE TABLE `refer_me`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `referAccountId` int(11) NOT NULL COMMENT '接收者账号id',
  `newsFeedId` int(11) NOT NULL COMMENT 'newsFeedId',
  `type` tinyint(2) NOT NULL COMMENT '@我、评论我等等的refer类型',
  `createAt` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for remind
-- ----------------------------
DROP TABLE IF EXISTS `remind`;
CREATE TABLE `remind`  (
  `accountId` int(11) NOT NULL COMMENT '用户账号id，必须手动指定，不自增',
  `referMe` int(11) NOT NULL DEFAULT 0 COMMENT '提到我的消息条数',
  `message` int(11) NOT NULL DEFAULT 0 COMMENT '私信条数',
  `fans` int(11) NOT NULL DEFAULT 0 COMMENT '粉丝增加个数',
  PRIMARY KEY (`accountId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `createAt` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `role` VALUES (1, '超级管理员', '2018-03-19 09:58:19');
INSERT INTO `role` VALUES (6, '管理员', '2018-05-03 10:15:44');
INSERT INTO `role` VALUES (8, '小编', '2018-05-03 10:25:52');
INSERT INTO `role` VALUES (9, 'test', '2018-09-27 11:04:02');


-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`, `permissionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sensitive_words
-- ----------------------------
DROP TABLE IF EXISTS `sensitive_words`;
CREATE TABLE `sensitive_words`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `word_pinyin` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2699 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `id` varchar(33) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_run_log
-- ----------------------------
DROP TABLE IF EXISTS `task_run_log`;
CREATE TABLE `task_run_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定时任务名称',
  `createAt` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for upload_counter
-- ----------------------------
DROP TABLE IF EXISTS `upload_counter`;
CREATE TABLE `upload_counter`  (
  `uploadType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `counter` int(11) NOT NULL,
  `descr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`uploadType`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求的IP地址',
  `url` varchar(336) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求的页面路径',
  `method` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方法',
  `client` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '客户端',
  `requestTime` datetime(0) NULL DEFAULT NULL COMMENT '请求时间',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip所在地区',
  `addressJson` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询结果json',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23016 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
