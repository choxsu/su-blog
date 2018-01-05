/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : s_blog

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 05/01/2018 19:16:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `accountId` int(11) DEFAULT NULL COMMENT '博客主id',
  `title` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `createAt` datetime NOT NULL COMMENT '创建时间',
  `updateAt` datetime NOT NULL COMMENT '修改时间',
  `clickCount` int(11) NOT NULL DEFAULT 0 COMMENT '点击次数',
  `likeCount` int(11) NOT NULL DEFAULT 0 COMMENT '喜欢次数',
  `favoriteCount` int(11) NOT NULL DEFAULT 0 COMMENT '收藏次数',
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, 1, '玩转 JFinal 新社区的正确姿势', '<p>JFinal 极速开发新社区于2016年6月6号6点6分6秒正式上线了，社区将提供高品质、专业化的极速开发项目、以及项目的分享与反馈。新社区主要分为项目、分享、反馈三大模块，其用途分别为：</p><h2>1、项目<br></h2><p>发布、收集与 JFinal 极速开发有关的项目，供开发者参考、学习、使用</p><h2>2、分享<br></h2><p>针对于项目，分享有关该项目的一切有价值的知识、代码等等资源，提升开发效率</p><h2>3、反馈<br></h2><p>针对于项目，向作者反馈在使用过程中碰到的问题或者提出改进建议，用户与作者共同打造高水平项目<br></p><h2><span style=\"color: rgb(255, 0, 0);\">用户注意事项：</span></h2><ul class=\" list-paddingleft-2\" style=\"list-style-type: disc;\"><li><p>注册以后换上个人头像有利于社区氛围与文化建设</p></li><li><p>为了保障社区内容的专注与高品质，请支持只发表技术相关内容<br></p></li><li><p><span style=\"font-family: 微软雅黑; font-size: 18px;\">为提升价值、节省开发者时间，</span>低质量、非技术性内容会酌情进行清理，请见谅</p></li></ul><p>&nbsp; &nbsp; JFinal 极度关注为开发者节省时间、提升效率、带来价值，从而会坚持内容的高品质，走少而精的道路，泛娱乐化的与技术无关的内容只会无情地浪费广大开发者有限的生命，请大家支持 JFinal 极速开发社区的价值观！！！</p>', '2066-06-06 06:06:06', '0000-00-00 00:00:00', 0, 17, 1, NULL);
INSERT INTO `blog` VALUES (2, 1, 'JFinal 新社区 share分享栏目', '<h2>乐于分享、传递价值</h2>\n<p>&nbsp; &nbsp; JFinal 新社区分享栏目，用于开发者针对于本站某个项目分享出自己所拥有的有价值的资源，例如实战中具体的代码，项目使用心德、技巧等一切可以为大家节省时间、提升效率的资源。<br></p>', '2066-06-06 06:06:03', '0000-00-00 00:00:00', 0, 8, 3, NULL);
INSERT INTO `blog` VALUES (3, 1, 'JFinal Weixin 1.8 发布，微信极速 SDK', '<p>&nbsp; &nbsp; 离上一次 JFinal weixin 1.7 发布，已经过去了 6 个月。在过去的半年时间里 JFinal Weixin 紧随微信公众平台的演化，不断增加了新的 API，同时也在不断完善原有 API，力求打造一个完备的微信公众平台 SDK，让开发更快速、更开心！</p><p>&nbsp;&nbsp; &nbsp;JFinal Weixin 1.8 共有 27 项新增与改进，新增功能主要有：微信红包接口、微信支付对账单接口、消息转发到指定客服、微信连WIFI联网后下发消息事件、卡券相关事件消息、用户Tag接口、个性化菜单接口等等。1.8 版对原有代码也进行了打磨，例如去除 freemarker 了依赖，截止到今天，此版本是目前市面上 Java 版微信SDK中jar包依赖最少的一个。</p><p>&nbsp; &nbsp; 最后感谢所有对 JFinal Weixin 有贡献的开发者们：@Dreamlu @Javen205 @亻紫菜彡 @osc余书慧 @12叔 @Jimmy哥 @author @Lucare，正是你们无私的奉献让这个世界越来越美好！</p><p><br></p><p>Jar 包下载：<a href=\"http://www.jfinal.com/download?file=jfinal-weixin-1.8-bin-with-src.jar\" target=\"_blank\">http://www.jfinal.com/download?file=jfinal-weixin-1.8-bin-with-src.jar</a></p><p>非 maven 用户获取依赖的 jar包：<a href=\"http://www.jfinal.com/download?file=jfinal-weixin-1.8-lib.zip\" target=\"_blank\">http://www.jfinal.com/download?file=jfinal-weixin-1.8-lib.zip</a></p><p><span style=\"font-family: 微软雅黑; font-size: 18px;\">详细开发文档地址：<a href=\"http://git.oschina.net/jfinal/jfinal-weixin/wikis/home\" target=\"_blank\">http://git.oschina.net/jfinal/jfinal-weixin/wikis/home</a></span></p><p><br></p><p>JFinal Weixin 1.8 Change log&nbsp;</p><p>1：去掉freemarker依赖，感谢@亻紫菜彡的意见&nbsp;</p><p>2：添加个性化菜单接口&nbsp;</p><p>3：添加微信支付对账单接口&nbsp;</p><p>4：添加没有找到对应的消息和事件消息的自定义处理&nbsp;</p><p>5：添加微信连WIFI联网后下发消息事件&nbsp;</p><p>6：fixed客服接口，删除客服帐号&nbsp;</p><p>7：添加获取自动回复规则&nbsp;</p><p>8：更新ReturnCode&nbsp;</p><p>9：新增将消息转发到指定客服&nbsp;</p><p>10：更改pom.xml，打jar包时排除demo目录&nbsp;</p><p>11：添加\"获取在线客服接待信息\"&nbsp;</p><p>12：新增发送图文消息（点击跳转到图文消息页面）&nbsp;</p><p>13：添加微信红包接口，感谢@osc余书慧童鞋的贡献&nbsp;</p><p>14：Bug searchByDevice感谢@12叔&nbsp;</p><p>15：ApiConfig实现序列化，方便缓存感谢@Jimmy哥&nbsp;</p><p>16：企业付款demoWeixinTransfersController感谢@author osc就看看&nbsp;</p><p>17：新增微信支付PC-模式一、模式二demo&nbsp;</p><p>18：添加对okhttp3的支持，修复okhttp2中download误用成httpsClient&nbsp;</p><p>19：添加对直接请求msg接口的异常提示&nbsp;</p><p>20：添加IOutils.toString的字符集参数&nbsp;</p><p>21：修改成maven目录结构&nbsp;</p><p>22：添加卡券相关事件消息&nbsp;</p><p>23：优化xml解析&nbsp;</p><p>24：TemplateData,JsonKit JSON序列化错误&nbsp;</p><p>25：添加用户tag接口&nbsp;</p><p>26：修复AccessToken超时并发问题，感谢@Lucare&nbsp;</p><p>27：添加java doc，详见：<a href=\"http://www.dreamlu.net/jfinal-weixin/apidocs/\">http://www.dreamlu.net/jfinal-weixin/apidocs/</a></p><p><br></p>', '2016-07-11 11:44:30', '0000-00-00 00:00:00', 0, 0, 0, NULL);

-- ----------------------------
-- Table structure for blog_account
-- ----------------------------
DROP TABLE IF EXISTS `blog_account`;
CREATE TABLE `blog_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nickName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `userName` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码 md5加密后',
  `salt` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `status` int(11) NOT NULL COMMENT '状态 0-待激活 1-正常 2-禁用',
  `createAt` datetime NOT NULL COMMENT '创建时间',
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ip地址',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `likeCount` int(11) NOT NULL DEFAULT 0 COMMENT '被赞次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '类别表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for blog_session
-- ----------------------------
DROP TABLE IF EXISTS `blog_session`;
CREATE TABLE `blog_session`  (
  `id` varchar(33) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog_session
-- ----------------------------
INSERT INTO `blog_session` VALUES ('405527628c584695bd028025a9c0d27d', 2, 1608968915262);
INSERT INTO `blog_session` VALUES ('9340ce7f15cf4deeaf0444b3915cfedc', 1, 1608963271845);
INSERT INTO `blog_session` VALUES ('fbd61f3436634b808a19343ba80a9c28', 1, 1608887159887);

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
