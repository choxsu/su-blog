/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : sblog

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 06/01/2018 18:18:00
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
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型 note（笔记）favorite(收藏）code(代码）about(关于）',
  `isDelete` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除 0否1是',
  `tag_id` int(11) DEFAULT NULL COMMENT 'tag_id',
  `category_id` int(11) DEFAULT NULL COMMENT '代码分类id，如果category为code时候，这个值才会生效',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `title`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, NULL, 'SpringBoot中使用ActiveRecordPlugin', '<p>\r\n    在写分享前先看了看jfinal-3.3的文档章节：5.13 任意环境下使用 ActiveRecord\r\n</p>\r\n<p>\r\n    ActiveRecordPlugin 可以独立于 java web 环境运行在任何普通的 java 程序中，使用方式极<br/>度简单，相对于 web 项目只需要手动调用一下其 start() 方法即可立即使用。以下是代码示例：\r\n</p>\r\n<pre class=\"brush:java;toolbar:false\">public class ActiveRecordTest {\r\n    public static void main(String[] args) {\r\n        DruidPlugin dp = new DruidPlugin(&quot;localhost&quot;, &quot;userName&quot;, &quot;password&quot;);\r\n        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);\r\n        arp.addMapping(&quot;blog&quot;, Blog.class);\r\n         \r\n        // 与web环境唯一的不同是要手动调用一次相关插件的start()方法\r\n        dp.start();\r\n        arp.start();\r\n         \r\n        // 通过上面简单的几行代码，即可立即开始使用\r\n        new Blog().set(&quot;title&quot;, &quot;title&quot;).set(&quot;content&quot;, &quot;cxt text&quot;).save();\r\n        Blog.dao.findById(123);\r\n    }\r\n}</pre>\r\n<p>\r\n    <span style=\"background-color: rgb(255, 0, 0);\"></span><span style=\"text-decoration: none; color: rgb(255, 0, 0);\">注意</span>：ActiveRecordPlugin 所依赖的其它插件也必须手动调用一下 start()方法，如上例中的<br/>dp.start()。\r\n</p>\r\n<p>\r\n    下面进入正题：\r\n</p>\r\n<p>\r\n    创建一个插件类\r\n</p>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">ActiveRecordPluginConfig 类</pre>\r\n<pre class=\"brush:java;toolbar:false\">package com.choxsu.elastic.config;\r\n\r\nimport com.alibaba.druid.filter.stat.StatFilter;\r\nimport com.alibaba.druid.wall.WallFilter;\r\nimport com.choxsu.elastic.entity._MappingKit;\r\nimport com.jfinal.plugin.activerecord.ActiveRecordPlugin;\r\nimport com.jfinal.plugin.druid.DruidPlugin;\r\nimport com.jfinal.template.source.ClassPathSourceFactory;\r\nimport org.springframework.beans.factory.annotation.Value;\r\nimport org.springframework.context.annotation.Bean;\r\nimport org.springframework.context.annotation.Configuration;\r\n\r\nimport java.sql.Connection;\r\n\r\n/**\r\n * @author chox su\r\n * @date 2017/11/29 10:16\r\n */\r\n@Configuration\r\npublic class ActiveRecordPluginConfig {\r\n\r\n    @Value(&quot;${spring.datasource.username}&quot;)\r\n    private String username;\r\n    @Value(&quot;${spring.datasource.password}&quot;)\r\n    private String password;\r\n    @Value(&quot;${spring.datasource.url}&quot;)\r\n    private String url;\r\n\r\n    @Bean    public ActiveRecordPlugin initActiveRecordPlugin() {\r\n\r\n        DruidPlugin druidPlugin = new DruidPlugin(url, username, password);\r\n        // 加强数据库安全\r\n        WallFilter wallFilter = new WallFilter();\r\n        wallFilter.setDbType(&quot;mysql&quot;);\r\n        druidPlugin.addFilter(wallFilter);\r\n        // 添加 StatFilter 才会有统计数据\r\n        // druidPlugin.addFilter(new StatFilter());\r\n        // 必须手动调用start\r\n        druidPlugin.start();\r\n\r\n        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);\r\n        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);\r\n        _MappingKit.mapping(arp);\r\n        arp.setShowSql(false);\r\n\r\n        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());\r\n        arp.addSqlTemplate(&quot;/sql/all_sqls.sql&quot;);\r\n        // 必须手动调用start\r\n        arp.start();\r\n        return arp;\r\n    }\r\n\r\n}</pre>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">application.yml 配置文件</pre>\r\n<pre class=\"brush:java;toolbar:false;\">server:\r\n  port: 1013\r\nspring:\r\n  application:\r\n    name: elastic\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    url: jdbc:mysql://192.168.3.44:3306/jfinal_club?characterEncoding=utf8&amp;useSSL=false</pre>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">pom.xml 配置文件</pre>\r\n<pre class=\"brush:xml;toolbar:false\">&lt;dependency&gt;\r\n    &lt;groupId&gt;com.alibaba&lt;/groupId&gt;\r\n    &lt;artifactId&gt;fastjson&lt;/artifactId&gt;\r\n    &lt;version&gt;1.2.27&lt;/version&gt;\r\n&lt;/dependency&gt;\r\n\r\n&lt;dependency&gt;\r\n    &lt;groupId&gt;mysql&lt;/groupId&gt;\r\n    &lt;artifactId&gt;mysql-connector-java&lt;/artifactId&gt;\r\n    &lt;version&gt;5.1.42&lt;/version&gt;\r\n&lt;/dependency&gt;\r\n\r\n&lt;dependency&gt;\r\n    &lt;groupId&gt;com.jfinal&lt;/groupId&gt;\r\n    &lt;artifactId&gt;jfinal&lt;/artifactId&gt;\r\n    &lt;version&gt;3.3&lt;/version&gt;\r\n&lt;/dependency&gt;\r\n\r\n&lt;dependency&gt;\r\n    &lt;groupId&gt;com.alibaba&lt;/groupId&gt;\r\n    &lt;artifactId&gt;druid&lt;/artifactId&gt;\r\n    &lt;version&gt;1.0.29&lt;/version&gt;\r\n&lt;/dependency&gt;</pre>\r\n<p>\r\n    主要用到的是上面这几个maven dependency\r\n</p>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">目录结构</pre>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223110647.png\" title=\"53457_20171223110647.png\" alt=\"图片.png\"/>\r\n</p>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">测试效果</pre>\r\n<p>\r\n    Controller类方法定义<br/>\r\n</p>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223111136.png\" title=\"53457_20171223111136.png\" alt=\"图片.png\"/>\r\n</p>\r\n<p>\r\n    swagger接口测试\r\n</p>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223111336.png\" title=\"53457_20171223111336.png\" alt=\"图片.png\"/>\r\n</p>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223111441.png\" title=\"53457_20171223111441.png\" alt=\"图片.png\"/>\r\n</p>\r\n<p>\r\n    sql管理功能这里没贴上来，但我已经测试通过了，springboot打包成jar启动可以找到sql路径，之前我是使用PathKit.getRootPath这种方式，这种方式打包成jar后就找不到路径；按照\r\n</p>\r\n<pre class=\"brush:java;toolbar:false\">ActiveRecordPluginConfig 类</pre>\r\n<p>\r\n    &nbsp; 配置即可<br/>\r\n</p>', '2018-01-06 10:20:37', '2018-01-06 10:20:41', 59, 0, 0, 'blog', 0, 2, NULL);
INSERT INTO `blog` VALUES (5, NULL, 'ActiveRecordPlugin', '<p>\r\n    在写分享前先看了看jfinal-3.3的文档章节：5.13 任意环境下使用 ActiveRecord\r\n</p>\r\n<p>\r\n    ActiveRecordPlugin 可以独立于 java web 环境运行在任何普通的 java 程序中，使用方式极<br/>度简单，相对于 web 项目只需要手动调用一下其 start() 方法即可立即使用。以下是代码示例：\r\n</p>\r\n<pre class=\"brush:java;toolbar:false\">public class ActiveRecordTest {\r\n    public static void main(String[] args) {\r\n        DruidPlugin dp = new DruidPlugin(&quot;localhost&quot;, &quot;userName&quot;, &quot;password&quot;);\r\n        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);\r\n        arp.addMapping(&quot;blog&quot;, Blog.class);\r\n         \r\n        // 与web环境唯一的不同是要手动调用一次相关插件的start()方法\r\n        dp.start();\r\n        arp.start();\r\n         \r\n        // 通过上面简单的几行代码，即可立即开始使用\r\n        new Blog().set(&quot;title&quot;, &quot;title&quot;).set(&quot;content&quot;, &quot;cxt text&quot;).save();\r\n        Blog.dao.findById(123);\r\n    }\r\n}</pre>\r\n<p>\r\n    <span style=\"background-color: rgb(255, 0, 0);\"></span><span style=\"text-decoration: none; color: rgb(255, 0, 0);\">注意</span>：ActiveRecordPlugin 所依赖的其它插件也必须手动调用一下 start()方法，如上例中的<br/>dp.start()。\r\n</p>\r\n<p>\r\n    下面进入正题：\r\n</p>\r\n<p>\r\n    创建一个插件类\r\n</p>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">ActiveRecordPluginConfig 类</pre>\r\n<pre class=\"brush:java;toolbar:false\">package com.choxsu.elastic.config;\r\n\r\nimport com.alibaba.druid.filter.stat.StatFilter;\r\nimport com.alibaba.druid.wall.WallFilter;\r\nimport com.choxsu.elastic.entity._MappingKit;\r\nimport com.jfinal.plugin.activerecord.ActiveRecordPlugin;\r\nimport com.jfinal.plugin.druid.DruidPlugin;\r\nimport com.jfinal.template.source.ClassPathSourceFactory;\r\nimport org.springframework.beans.factory.annotation.Value;\r\nimport org.springframework.context.annotation.Bean;\r\nimport org.springframework.context.annotation.Configuration;\r\n\r\nimport java.sql.Connection;\r\n\r\n/**\r\n * @author chox su\r\n * @date 2017/11/29 10:16\r\n */\r\n@Configuration\r\npublic class ActiveRecordPluginConfig {\r\n\r\n    @Value(&quot;${spring.datasource.username}&quot;)\r\n    private String username;\r\n    @Value(&quot;${spring.datasource.password}&quot;)\r\n    private String password;\r\n    @Value(&quot;${spring.datasource.url}&quot;)\r\n    private String url;\r\n\r\n    @Bean    public ActiveRecordPlugin initActiveRecordPlugin() {\r\n\r\n        DruidPlugin druidPlugin = new DruidPlugin(url, username, password);\r\n        // 加强数据库安全\r\n        WallFilter wallFilter = new WallFilter();\r\n        wallFilter.setDbType(&quot;mysql&quot;);\r\n        druidPlugin.addFilter(wallFilter);\r\n        // 添加 StatFilter 才会有统计数据\r\n        // druidPlugin.addFilter(new StatFilter());\r\n        // 必须手动调用start\r\n        druidPlugin.start();\r\n\r\n        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);\r\n        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);\r\n        _MappingKit.mapping(arp);\r\n        arp.setShowSql(false);\r\n\r\n        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());\r\n        arp.addSqlTemplate(&quot;/sql/all_sqls.sql&quot;);\r\n        // 必须手动调用start\r\n        arp.start();\r\n        return arp;\r\n    }\r\n\r\n}</pre>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">application.yml 配置文件</pre>\r\n<pre class=\"brush:java;toolbar:false;\">server:\r\n  port: 1013\r\nspring:\r\n  application:\r\n    name: elastic\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    url: jdbc:mysql://192.168.3.44:3306/jfinal_club?characterEncoding=utf8&amp;useSSL=false</pre>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">pom.xml 配置文件</pre>\r\n<pre class=\"brush:xml;toolbar:false\">&lt;dependency&gt;\r\n    &lt;groupId&gt;com.alibaba&lt;/groupId&gt;\r\n    &lt;artifactId&gt;fastjson&lt;/artifactId&gt;\r\n    &lt;version&gt;1.2.27&lt;/version&gt;\r\n&lt;/dependency&gt;\r\n\r\n&lt;dependency&gt;\r\n    &lt;groupId&gt;mysql&lt;/groupId&gt;\r\n    &lt;artifactId&gt;mysql-connector-java&lt;/artifactId&gt;\r\n    &lt;version&gt;5.1.42&lt;/version&gt;\r\n&lt;/dependency&gt;\r\n\r\n&lt;dependency&gt;\r\n    &lt;groupId&gt;com.jfinal&lt;/groupId&gt;\r\n    &lt;artifactId&gt;jfinal&lt;/artifactId&gt;\r\n    &lt;version&gt;3.3&lt;/version&gt;\r\n&lt;/dependency&gt;\r\n\r\n&lt;dependency&gt;\r\n    &lt;groupId&gt;com.alibaba&lt;/groupId&gt;\r\n    &lt;artifactId&gt;druid&lt;/artifactId&gt;\r\n    &lt;version&gt;1.0.29&lt;/version&gt;\r\n&lt;/dependency&gt;</pre>\r\n<p>\r\n    主要用到的是上面这几个maven dependency\r\n</p>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">目录结构</pre>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223110647.png\" title=\"53457_20171223110647.png\" alt=\"图片.png\"/>\r\n</p>\r\n<pre style=\"background-color:#2b2b2b;color:#a9b7c6;font-family:&#39;Source Code Pro&#39;;font-size:11.3pt;\">测试效果</pre>\r\n<p>\r\n    Controller类方法定义<br/>\r\n</p>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223111136.png\" title=\"53457_20171223111136.png\" alt=\"图片.png\"/>\r\n</p>\r\n<p>\r\n    swagger接口测试\r\n</p>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223111336.png\" title=\"53457_20171223111336.png\" alt=\"图片.png\"/>\r\n</p>\r\n<p>\r\n    <img src=\"/upload/img/share/0/53457_20171223111441.png\" title=\"53457_20171223111441.png\" alt=\"图片.png\"/>\r\n</p>\r\n<p>\r\n    sql管理功能这里没贴上来，但我已经测试通过了，springboot打包成jar启动可以找到sql路径，之前我是使用PathKit.getRootPath这种方式，这种方式打包成jar后就找不到路径；按照\r\n</p>\r\n<pre class=\"brush:java;toolbar:false\">ActiveRecordPluginConfig 类</pre>\r\n<p>\r\n    &nbsp; 配置即可<br/>\r\n</p>', '2018-01-06 14:11:27', '2018-01-06 14:11:29', 73, 0, 0, 'code', 0, 1, 1);

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
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '类别表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog_category
-- ----------------------------
INSERT INTO `blog_category` VALUES (1, 'JAVA', 0);
INSERT INTO `blog_category` VALUES (2, 'Python', 0);
INSERT INTO `blog_category` VALUES (3, 'C', 0);
INSERT INTO `blog_category` VALUES (4, 'C++', 0);
INSERT INTO `blog_category` VALUES (5, 'Spring', 0);
INSERT INTO `blog_category` VALUES (6, 'GO', 0);
INSERT INTO `blog_category` VALUES (7, 'JavaScript', 0);
INSERT INTO `blog_category` VALUES (8, 'PHP', 0);
INSERT INTO `blog_category` VALUES (9, 'Kotiln', 0);

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '是否有效；0是1否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sensitive_words
-- ----------------------------
INSERT INTO `sensitive_words` VALUES (1, '发票', 1, 'fapiao');

SET FOREIGN_KEY_CHECKS = 1;
