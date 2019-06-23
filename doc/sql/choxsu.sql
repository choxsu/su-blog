/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : localhost:3306
 Source Schema         : choxsu

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : 65001

 Date: 23/06/2019 10:56:05
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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户-登录第三方表' ROW_FORMAT = Compact;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '类别表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for code_config
-- ----------------------------
DROP TABLE IF EXISTS `code_config`;
CREATE TABLE `code_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `base_package` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基础包名',
  `entity_package` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体包名',
  `service_package` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'service包名',
  `entity_base_package` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体基础包',
  `is_clear_prefix` tinyint(1) NULL DEFAULT NULL COMMENT '是否去除前缀',
  `prefix` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前缀',
  `is_generate_chain_setter` tinyint(1) NULL DEFAULT NULL COMMENT 'BaseModel 是否生成链式 setter 方法',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_config
-- ----------------------------
INSERT INTO `code_config` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '图片上厂记录表' ROW_FORMAT = Dynamic;

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
-- Records of ip_repository
-- ----------------------------
INSERT INTO `ip_repository` VALUES (72398690458796032, '127.0.0.1', 'XX', '', 'XX', '内网IP', '内网IP', '内网IP', '2019-06-22 08:51:34', 'xx', '', 'xx', 'local', 'local', 'local');

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
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES (1, '2019-06-21 13:12:22', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2019-06-22 08:51:42', '127.0.0.1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 230 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (161, '/admin', 'com.choxsu._admin.index.IndexAdminController', '后台首页');
INSERT INTO `permission` VALUES (162, '/admin/account', 'com.choxsu._admin.account.AccountAdminController', '账户首页');
INSERT INTO `permission` VALUES (163, '/admin/account/add', 'com.choxsu._admin.account.AccountAdminController', '账户添加页面');
INSERT INTO `permission` VALUES (164, '/admin/account/addRole', 'com.choxsu._admin.account.AccountAdminController', '账户角色添加');
INSERT INTO `permission` VALUES (165, '/admin/account/assignRoles', 'com.choxsu._admin.account.AccountAdminController', '账户分配角色页面');
INSERT INTO `permission` VALUES (166, '/admin/account/del', 'com.choxsu._admin.account.AccountAdminController', '账户删除');
INSERT INTO `permission` VALUES (167, '/admin/account/delTemFile', 'com.choxsu._admin.account.AccountAdminController', '删除存放临时目录的图片文件');
INSERT INTO `permission` VALUES (168, '/admin/account/deleteRole', 'com.choxsu._admin.account.AccountAdminController', '账户角色删除');
INSERT INTO `permission` VALUES (169, '/admin/account/edit', 'com.choxsu._admin.account.AccountAdminController', '账户编辑页面');
INSERT INTO `permission` VALUES (170, '/admin/account/lock', 'com.choxsu._admin.account.AccountAdminController', '账户锁定');
INSERT INTO `permission` VALUES (171, '/admin/account/save', 'com.choxsu._admin.account.AccountAdminController', '账户保存');
INSERT INTO `permission` VALUES (172, '/admin/account/saveAvatar', 'com.choxsu._admin.account.AccountAdminController', '账户头像保存');
INSERT INTO `permission` VALUES (173, '/admin/account/showAdminList', 'com.choxsu._admin.account.AccountAdminController', '显示后台管理员账户首页');
INSERT INTO `permission` VALUES (174, '/admin/account/unlock', 'com.choxsu._admin.account.AccountAdminController', '账户解锁');
INSERT INTO `permission` VALUES (175, '/admin/account/update', 'com.choxsu._admin.account.AccountAdminController', '账户更新');
INSERT INTO `permission` VALUES (176, '/admin/account/uploadAvatar', 'com.choxsu._admin.account.AccountAdminController', '账户头像裁剪');
INSERT INTO `permission` VALUES (177, '/admin/blog', 'com.choxsu._admin.blog.AdminBlogController', '文章首页');
INSERT INTO `permission` VALUES (178, '/admin/blog/add', 'com.choxsu._admin.blog.AdminBlogController', '文章添加页面');
INSERT INTO `permission` VALUES (179, '/admin/blog/allowComments', 'com.choxsu._admin.blog.AdminBlogController', '开启评论');
INSERT INTO `permission` VALUES (180, '/admin/blog/delete', 'com.choxsu._admin.blog.AdminBlogController', '文章删除');
INSERT INTO `permission` VALUES (181, '/admin/blog/edit', 'com.choxsu._admin.blog.AdminBlogController', '文章编辑页面');
INSERT INTO `permission` VALUES (182, '/admin/blog/oneKeyAllowComments', 'com.choxsu._admin.blog.AdminBlogController', '一键开启评论');
INSERT INTO `permission` VALUES (183, '/admin/blog/save', 'com.choxsu._admin.blog.AdminBlogController', '文章保存');
INSERT INTO `permission` VALUES (184, '/admin/blog/unAllowComments', 'com.choxsu._admin.blog.AdminBlogController', '关闭评论');
INSERT INTO `permission` VALUES (185, '/admin/blog/update', 'com.choxsu._admin.blog.AdminBlogController', '文章更新');
INSERT INTO `permission` VALUES (186, '/admin/code', 'com.choxsu._admin.code.CodeController', NULL);
INSERT INTO `permission` VALUES (187, '/admin/codeConfig', 'com.choxsu._admin.code.CodeConfigController', NULL);
INSERT INTO `permission` VALUES (188, '/admin/druid', 'com.choxsu._admin.druid.DruidController', 'Druid管理首页');
INSERT INTO `permission` VALUES (189, '/admin/loginLog', 'com.choxsu._admin.account.AccountAdminController', '账户登录日志首页');
INSERT INTO `permission` VALUES (190, '/admin/permission', 'com.choxsu._admin.permission.PermissionAdminController', '权限首页');
INSERT INTO `permission` VALUES (191, '/admin/permission/delete', 'com.choxsu._admin.permission.PermissionAdminController', '权限删除');
INSERT INTO `permission` VALUES (192, '/admin/permission/edit', 'com.choxsu._admin.permission.PermissionAdminController', '权限编辑页面');
INSERT INTO `permission` VALUES (193, '/admin/permission/sync', 'com.choxsu._admin.permission.PermissionAdminController', '权限同步');
INSERT INTO `permission` VALUES (194, '/admin/permission/update', 'com.choxsu._admin.permission.PermissionAdminController', '权限更新');
INSERT INTO `permission` VALUES (195, '/admin/quartz', 'com.choxsu._admin.quartz.JobManageController', '定时任务管理首页');
INSERT INTO `permission` VALUES (196, '/admin/quartz/add', 'com.choxsu._admin.quartz.JobManageController', '定时任务添加页面');
INSERT INTO `permission` VALUES (197, '/admin/quartz/delete', 'com.choxsu._admin.quartz.JobManageController', '定时任务删除');
INSERT INTO `permission` VALUES (198, '/admin/quartz/edit', 'com.choxsu._admin.quartz.JobManageController', '定时任务编辑页面');
INSERT INTO `permission` VALUES (199, '/admin/quartz/get', 'com.choxsu._admin.quartz.JobManageController', '定时任务详情');
INSERT INTO `permission` VALUES (200, '/admin/quartz/save', 'com.choxsu._admin.quartz.JobManageController', '定时任务保存');
INSERT INTO `permission` VALUES (201, '/admin/quartz/start', 'com.choxsu._admin.quartz.JobManageController', '定时任务开始');
INSERT INTO `permission` VALUES (202, '/admin/quartz/update', 'com.choxsu._admin.quartz.JobManageController', '定时任务更新');
INSERT INTO `permission` VALUES (203, '/admin/reply', 'com.choxsu._admin.reply.ReplyController', '评论列表首页');
INSERT INTO `permission` VALUES (204, '/admin/role', 'com.choxsu._admin.role.RoleAdminController', '角色管理首页');
INSERT INTO `permission` VALUES (205, '/admin/role/add', 'com.choxsu._admin.role.RoleAdminController', '角色添加页面');
INSERT INTO `permission` VALUES (206, '/admin/role/addPermission', 'com.choxsu._admin.role.RoleAdminController', '角色权限增加');
INSERT INTO `permission` VALUES (207, '/admin/role/assignPermissions', 'com.choxsu._admin.role.RoleAdminController', '角色分配权限页面');
INSERT INTO `permission` VALUES (208, '/admin/role/delete', 'com.choxsu._admin.role.RoleAdminController', '角色删除');
INSERT INTO `permission` VALUES (209, '/admin/role/deletePermission', 'com.choxsu._admin.role.RoleAdminController', '角色权限删除');
INSERT INTO `permission` VALUES (210, '/admin/role/edit', 'com.choxsu._admin.role.RoleAdminController', '角色编辑页面');
INSERT INTO `permission` VALUES (211, '/admin/role/save', 'com.choxsu._admin.role.RoleAdminController', '角色保存');
INSERT INTO `permission` VALUES (212, '/admin/role/update', 'com.choxsu._admin.role.RoleAdminController', '角色更新');
INSERT INTO `permission` VALUES (213, '/admin/sensitiveWord', 'com.choxsu._admin.sensitive_word.SensitiveWordAdminController', '敏感字管理首页');
INSERT INTO `permission` VALUES (214, '/admin/sensitiveWord/add', 'com.choxsu._admin.sensitive_word.SensitiveWordAdminController', '敏感字添加页面');
INSERT INTO `permission` VALUES (215, '/admin/sensitiveWord/delete', 'com.choxsu._admin.sensitive_word.SensitiveWordAdminController', '敏感字删除');
INSERT INTO `permission` VALUES (216, '/admin/sensitiveWord/edit', 'com.choxsu._admin.sensitive_word.SensitiveWordAdminController', '敏感字编辑页面');
INSERT INTO `permission` VALUES (217, '/admin/sensitiveWord/exchange', 'com.choxsu._admin.sensitive_word.SensitiveWordAdminController', '敏感字汉字转换拼音');
INSERT INTO `permission` VALUES (218, '/admin/sensitiveWord/save', 'com.choxsu._admin.sensitive_word.SensitiveWordAdminController', '敏感字保存');
INSERT INTO `permission` VALUES (219, '/admin/sensitiveWord/update', 'com.choxsu._admin.sensitive_word.SensitiveWordAdminController', '敏感字更新');
INSERT INTO `permission` VALUES (220, '/admin/tag', 'com.choxsu._admin.tag.AdminTagController', '标签管理首页');
INSERT INTO `permission` VALUES (221, '/admin/tag/add', 'com.choxsu._admin.tag.AdminTagController', '标签添加页面');
INSERT INTO `permission` VALUES (222, '/admin/tag/delete', 'com.choxsu._admin.tag.AdminTagController', '标签删除');
INSERT INTO `permission` VALUES (223, '/admin/tag/edit', 'com.choxsu._admin.tag.AdminTagController', '标签编辑页面');
INSERT INTO `permission` VALUES (224, '/admin/tag/save', 'com.choxsu._admin.tag.AdminTagController', '标签保存');
INSERT INTO `permission` VALUES (225, '/admin/tag/update', 'com.choxsu._admin.tag.AdminTagController', '标签更新');
INSERT INTO `permission` VALUES (226, '/admin/upload/base64ImgUpload', 'com.choxsu.common.upload.UploadController', NULL);
INSERT INTO `permission` VALUES (227, '/admin/upload/editormdImgUpload', 'com.choxsu.common.upload.UploadController', NULL);
INSERT INTO `permission` VALUES (228, '/admin/uploadLog', 'com.choxsu._admin.account.AccountAdminController', '图片上传日志记录首页');
INSERT INTO `permission` VALUES (229, '/admin/visitor', 'com.choxsu._admin.visitor.VisitorAdminController', 'PV管理首页');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Records of role
-- ----------------------------
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
-- Records of session
-- ----------------------------
INSERT INTO `session` VALUES ('22dfa03572a848be9f709f8c85039c0a', 1, 1561251101365);
INSERT INTO `session` VALUES ('63485ba1d92e41fba56a652f509e4c33', 1, 1561180341549);

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
) ENGINE = InnoDB AUTO_INCREMENT = 23028 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of visitor
-- ----------------------------
INSERT INTO `visitor` VALUES (23016, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 10:15:45', 'XX', '{}');
INSERT INTO `visitor` VALUES (23017, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/doLogin', 'POST', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 10:15:53', 'XX', '{}');
INSERT INTO `visitor` VALUES (23018, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 10:16:27', 'XX', '{}');
INSERT INTO `visitor` VALUES (23019, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 10:16:24', 'XX', '{}');
INSERT INTO `visitor` VALUES (23020, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 10:16:27', 'XX', '{}');
INSERT INTO `visitor` VALUES (23021, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 13:12:22', 'XX', '{}');
INSERT INTO `visitor` VALUES (23022, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 13:12:13', 'XX', '{}');
INSERT INTO `visitor` VALUES (23023, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/doLogin', 'POST', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-21 13:12:22', 'XX', '{}');
INSERT INTO `visitor` VALUES (23024, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-22 08:51:34', 'XX/内网IP/XX[内网IP]', '{\"code\":0,\"data\":{\"ip\":\"127.0.0.1\",\"country\":\"XX\",\"area\":\"\",\"region\":\"XX\",\"city\":\"内网IP\",\"county\":\"内网IP\",\"isp\":\"内网IP\",\"country_id\":\"xx\",\"area_id\":\"\",\"region_id\":\"xx\",\"city_id\":\"local\",\"county_id\":\"local\",\"isp_id\":\"local\"}}');
INSERT INTO `visitor` VALUES (23025, '127.0.0.1', 'http://127.0.0.1:1013/login', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-22 08:51:34', 'XX/内网IP/XX[内网IP]', '{\"code\":0,\"data\":{\"ip\":\"127.0.0.1\",\"country\":\"XX\",\"area\":\"\",\"region\":\"XX\",\"city\":\"内网IP\",\"county\":\"内网IP\",\"isp\":\"内网IP\",\"country_id\":\"xx\",\"area_id\":\"\",\"region_id\":\"xx\",\"city_id\":\"local\",\"county_id\":\"local\",\"isp_id\":\"local\"}}');
INSERT INTO `visitor` VALUES (23026, '127.0.0.1', 'http://127.0.0.1:1013/login/doLogin', 'POST', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-22 08:51:41', 'XX/内网IP/XX[内网IP]', '{\"area\":\"\",\"areaId\":\"\",\"city\":\"内网IP\",\"cityId\":\"local\",\"country\":\"XX\",\"countryId\":\"xx\",\"county\":\"内网IP\",\"countyId\":\"local\",\"createTime\":1561164694000,\"id\":72398690458796032,\"ip\":\"127.0.0.1\",\"isp\":\"内网IP\",\"ispId\":\"local\",\"region\":\"XX\",\"regionId\":\"xx\"}');
INSERT INTO `visitor` VALUES (23027, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36', '2019-06-22 17:20:11', 'XX/内网IP/XX[内网IP]', '{\"area\":\"\",\"areaId\":\"\",\"city\":\"内网IP\",\"cityId\":\"local\",\"country\":\"XX\",\"countryId\":\"xx\",\"county\":\"内网IP\",\"countyId\":\"local\",\"createTime\":1561164694000,\"id\":72398690458796032,\"ip\":\"127.0.0.1\",\"isp\":\"内网IP\",\"ispId\":\"local\",\"region\":\"XX\",\"regionId\":\"xx\"}');

SET FOREIGN_KEY_CHECKS = 1;
