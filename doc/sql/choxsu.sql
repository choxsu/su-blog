/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : choxsu

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 20/04/2019 09:46:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `nickName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `userName` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `status` int(11) NOT NULL COMMENT '状态 -1 锁定账号，无法做任何事情 0-注册、未激活 1-正常、已激活',
  `createAt` datetime(0) NOT NULL COMMENT '创建时间',
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册IP',
  `avatar` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `likeCount` int(11) NOT NULL DEFAULT 0 COMMENT '被赞次数',
  `isThird` int(2) NOT NULL DEFAULT 0 COMMENT '是否第三方登陆 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'ChoxSu', 'choxsu@gmail.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-18 09:00:19', '175.12.244.105', 'default.png', 0, 0);
INSERT INTO `account` VALUES (2, '管理员', 'admin@styg.site', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-19 10:19:11', '175.12.244.105', 'default.png', 0, 0);
INSERT INTO `account` VALUES (3, 'test', 'test@test.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', 'RS_xQw8fhclJqZU2iDPYqa8EYyF9T6pc', 1, '2018-09-27 12:04:25', '183.64.28.18', 'default.png', 0, 0);
INSERT INTO `account` VALUES (4, 'test1', 'test1@test.com', 'b7cf9ab8832c2caeee7753efd1e70787b0fd72f17539f2e730e58ba01063b5ca', 'CR3yZ3xuDO2EB4jiEtj4HhwY0tpuq_-y', 1, '2018-12-21 22:11:14', '0:0:0:0:0:0:0:1', 'default.png', 0, 0);
INSERT INTO `account` VALUES (5, 'ChoxSu', '', '', '', 1, '2019-04-19 23:41:15', '0:0:0:0:0:0:0:1', 'http://thirdqq.qlogo.cn/g?b=oidb&k=xicaTlW45aV3cfeTh6fa8LA&s=40', 0, 1);
INSERT INTO `account` VALUES (6, 'chox.su', '', '', '', 1, '2019-04-20 09:21:33', '127.0.0.1', 'http://thirdqq.qlogo.cn/g?b=oidb&k=Z4AZHyxZOk4gL6B5vMYQGw&s=40', 0, 1);
INSERT INTO `account` VALUES (7, '魑魅魍魉ヾ', '', '', '', 1, '2019-04-20 09:38:35', '127.0.0.1', 'http://thirdqq.qlogo.cn/g?b=oidb&k=xnlIYxMiccDcicBeZDsnKTiag&s=40', 0, 1);

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
-- Records of account_open
-- ----------------------------
INSERT INTO `account_open` VALUES (1, 5, 'qq', 'C561FD0C7A0E9B3810F38AE1A12A978D', '60951872E5B815BF8F12859C4334D2E7', 7776000);
INSERT INTO `account_open` VALUES (2, 6, 'qq', '41243E0C5BA6B5AAFB6B2DA29ACD6885', '04BA4C0AAA5357EA5D07B409D641E625', 7776000);
INSERT INTO `account_open` VALUES (3, 7, 'qq', 'C429E2435E2130089250CFD4A271D441', '254BE0956B336F8B3B555FEF55244DB6', 7776000);

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role`  (
  `accountId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`accountId`, `roleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES (1, 1);
INSERT INTO `account_role` VALUES (2, 8);
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `title`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (14, 1, '关于我', '<h5 id=\"h5-u7F51u540D\"><a name=\"网名\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>网名</h5><p>ChoxSu</p>\n<h5 id=\"h5-u7231u597D\"><a name=\"爱好\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>爱好</h5><p>爬山、电影、游戏</p>\n<h5 id=\"h5-u7B7Eu540D\"><a name=\"签名\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>签名</h5><p>没有思考，人生的路会越走越难！</p>\n<h5 id=\"h5-u5730u70B9\"><a name=\"地点\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>地点</h5><p>重庆</p>\n<h5 id=\"h5-u6027u522B\"><a name=\"性别\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>性别</h5><p>男</p>\n<h5 id=\"h5-u535Au5BA2u5730u5740\"><a name=\"博客地址\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>博客地址</h5><p><a href=\"http://118.24.122.21&quot;\">戳我查看博客</a></p>\n<h5 id=\"h5-u7B80u4ECB\"><a name=\"简介\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>简介</h5><p>我的博客，重点是记录我的技术总结，让更多人解决问题，学习知识，没有批评就没有进步。</p>\n', '##### 网名\nChoxSu\n##### 爱好\n爬山、电影、游戏\n#####签名\n没有思考，人生的路会越走越难！\n#####地点\n重庆\n#####性别\n男\n##### 博客地址\n[戳我查看博客](http://118.24.122.21\")\n##### 简介\n我的博客，重点是记录我的技术总结，让更多人解决问题，学习知识，没有批评就没有进步。', '2018-12-22 16:07:12', '2018-12-22 16:31:17', 33, 0, 0, 'about', 0, 1, NULL);
INSERT INTO `blog` VALUES (15, 1, 'JFinal ActiveRecordPlugin插件事物交给Spring管理', '<p>最近在SpringBoot中使用JFinal的ActiveRecordPlugin插件，虽然事物可以直接通过注解<code>@Before(Tx.class)</code>来解决，但是后面项目的需要将事物交给spring来管理，具体实现看下去</p>\n<h3 id=\"h3-u601Du8DEF\"><a name=\"思路\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>思路</h3><p>使用spring AOP代理,这里使用springboot来实现，spring同理</p>\n<h4 id=\"h4-maven-\"><a name=\"maven 依赖\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>maven 依赖</h4><pre><code>&lt;dependency&gt;&lt;!-- spring boot aop starter依赖 --&gt;\n    &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;\n    &lt;artifactId&gt;spring-boot-starter-aop&lt;/artifactId&gt;\n&lt;/dependency&gt;\n&lt;!-- 数据源 --&gt;\n&lt;dependency&gt;\n    &lt;groupId&gt;com.zaxxer&lt;/groupId&gt;\n    &lt;artifactId&gt;HikariCP&lt;/artifactId&gt;\n&lt;/dependency&gt;\n</code></pre><h6 id=\"h6--jfinal\"><a name=\"感谢   如梦技术的代码片段  ,   JFinal\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>感谢 <a href=\"https://gitee.com/596392912/codes\">如梦技术的代码片段</a> , <a href=\"http://jfinal.com\">JFinal</a></h6><h4 id=\"h4-jfinaltxaop\"><a name=\"JFinalTxAop\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>JFinalTxAop</h4><pre><code>package com.choxsu.elastic.config;\n\nimport com.jfinal.kit.LogKit;\nimport com.jfinal.plugin.activerecord.ActiveRecordException;\nimport com.jfinal.plugin.activerecord.Config;\nimport com.jfinal.plugin.activerecord.DbKit;\nimport com.jfinal.plugin.activerecord.NestedTransactionHelpException;\nimport com.jfinal.plugin.activerecord.tx.TxConfig;\nimport org.aspectj.lang.ProceedingJoinPoint;\nimport org.aspectj.lang.annotation.Around;\nimport org.aspectj.lang.annotation.Aspect;\nimport org.aspectj.lang.annotation.Pointcut;\nimport org.aspectj.lang.reflect.MethodSignature;\nimport org.springframework.stereotype.Component;\n\nimport java.lang.reflect.Method;\nimport java.sql.Connection;\nimport java.sql.SQLException;\n\n/**\n * @author choxsu\n * @date 2018/4/13\n */\n@Aspect\n@Component\npublic class JFinalTxAop {\n\n\n    /**\n     * 自定义JFinal 事物注解\n     * value中的意思解释\n     *\n     * @annotation 表示注解只能支持方法上\n     * @within 表示注解在类下面所有的方法 ， 暂时不使用这种方式\n     */\n    @Pointcut(\"@annotation(com.choxsu.elastic.config.JFinalTx)\")\n    private void method() {\n    }\n\n    @Around(value = \"method()\", argNames = \"pjp\")\n    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {\n        Object retVal = null;\n        Config config = getConfigWithTxConfig(pjp);\n        if (config == null)\n            config = DbKit.getConfig();\n\n        Connection conn = config.getThreadLocalConnection();\n        // Nested transaction support\n        if (conn != null) {\n            try {\n                if (conn.getTransactionIsolation() &lt; getTransactionLevel(config))\n                    conn.setTransactionIsolation(getTransactionLevel(config));\n                retVal = pjp.proceed();\n                return retVal;\n            } catch (SQLException e) {\n                throw new ActiveRecordException(e);\n            }\n        }\n\n        Boolean autoCommit = null;\n        try {\n            conn = config.getConnection();\n            autoCommit = conn.getAutoCommit();\n            config.setThreadLocalConnection(conn);\n            conn.setTransactionIsolation(getTransactionLevel(config));// conn.setTransactionIsolation(transactionLevel);\n\n            conn.setAutoCommit(false);\n            retVal = pjp.proceed();\n            conn.commit();\n        } catch (NestedTransactionHelpException e) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            LogKit.logNothing(e);\n        } catch (Throwable t) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            throw t instanceof RuntimeException ? (RuntimeException) t : new ActiveRecordException(t);\n        } finally {\n            try {\n                if (conn != null) {\n                    if (autoCommit != null)\n                        conn.setAutoCommit(autoCommit);\n                    conn.close();\n                }\n            } catch (Throwable t) {\n                // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown\n                LogKit.error(t.getMessage(), t);\n            } finally {\n                // prevent memory leak\n                config.removeThreadLocalConnection();\n            }\n        }\n        return retVal;\n    }\n\n    /**\n     * 获取配置的事务级别\n     *\n     * @param config\n     * @return\n     */\n    protected int getTransactionLevel(Config config) {\n        return config.getTransactionLevel();\n    }\n\n    /**\n     * @param pjp\n     * @return Config\n     */\n    public static Config getConfigWithTxConfig(ProceedingJoinPoint pjp) {\n        MethodSignature ms = (MethodSignature) pjp.getSignature();\n        Method method = ms.getMethod();\n        TxConfig txConfig = method.getAnnotation(TxConfig.class);\n        if (txConfig == null)\n            txConfig = pjp.getTarget().getClass().getAnnotation(TxConfig.class);\n\n        if (txConfig != null) {\n            Config config = DbKit.getConfig(txConfig.value());\n            if (config == null)\n                throw new RuntimeException(\"Config not found with TxConfig: \" + txConfig.value());\n            return config;\n        }\n        return null;\n    }\n}\n</code></pre><h4 id=\"h4-jfinaltx\"><a name=\"JFinalTx\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>JFinalTx</h4><pre><code>package com.choxsu.elastic.config;\n\n/**\n * @author choxsu\n */\n\nimport java.lang.annotation.*;\n\n/**\n * Jfinal事物交给spring管理注解\n * 目前只支持用在方法上\n */\n@Inherited\n@Target({ElementType.METHOD})\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface JFinalTx {\n\n}\n</code></pre><h3 id=\"h3-u4F7Fu7528\"><a name=\"使用\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>使用</h3><h4 id=\"h4-testcontroller\"><a name=\"TestController\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>TestController</h4><pre><code>package com.choxsu.elastic.controller;\n\nimport com.choxsu.elastic.service.TestService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n/**\n * @author choxsu\n */\n@RestController\n@RequestMapping(value = {\"/test/v1\"})\npublic class TestController {\n\n\n    @Autowired\n    private TestService testService;\n\n    @GetMapping(value = \"/testTran\")\n    public Object testTran(){\n\n\n        return testService.testTran();\n    }\n}\n</code></pre><h4 id=\"h4-testservice\"><a name=\"TestService\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>TestService</h4><pre><code>package com.choxsu.elastic.service;\n\nimport com.choxsu.elastic.config.JFinalTx;\nimport com.jfinal.kit.Ret;\nimport com.jfinal.plugin.activerecord.Db;\nimport com.jfinal.plugin.activerecord.Record;\nimport org.springframework.stereotype.Service;\n\n\n/**\n * @author choxsu\n */\n@Service\npublic class TestService {\n\n    /**\n     * 事物测试\n     *\n     * @return\n     */\n    @JFinalTx\n    public Object testTran() {\n        Record record = new Record();\n        record.set(\"id\", 10);\n        Db.save(\"test\", record);\n        if (true) {\n            throw new RuntimeException(\"test\");\n        }\n        return Ret.by(\"msg\", \"success\");\n    }\n}\n</code></pre><h5 id=\"h5-sql-\"><a name=\"sql执行了\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>sql执行了</h5><pre><code>Sql: insert into `test`(`id`) values(?)\n</code></pre><h5 id=\"h5-u4F46u662Fu6570u636Eu5E93u6CA1u6709u6570u636E\"><a name=\"但是数据库没有数据\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>但是数据库没有数据</h5><p><img src=\"https://upload-images.jianshu.io/upload_images/7463793-cb026e4ac9652bef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"image.png\"></p>\n<p>到此证明事物拦截成功，可以使用spring来管理ActiveRecordPlugin的事物了</p>\n<h4 id=\"h4--code-throw-new-runtimeexception-quot-test-quot-code-\"><a name=\"去掉<code>throw new RuntimeException(&quot;test&quot;);</code>的效果\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>去掉<code>throw new RuntimeException(\"test\");</code>的效果</h4><h5 id=\"h5-sql\"><a name=\"sql\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>sql</h5><pre><code>Sql: insert into `test`(`id`) values(?)\n</code></pre><h5 id=\"h5-u6570u636Eu5E93u7ED3u679C\"><a name=\"数据库结果\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>数据库结果</h5><p><img src=\"https://upload-images.jianshu.io/upload_images/7463793-afcb50b9767dbadc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"image.png\"></p>\n', '最近在SpringBoot中使用JFinal的ActiveRecordPlugin插件，虽然事物可以直接通过注解`@Before(Tx.class)`来解决，但是后面项目的需要将事物交给spring来管理，具体实现看下去\n### 思路\n使用spring AOP代理,这里使用springboot来实现，spring同理\n\n\n#### maven 依赖\n```\n<dependency><!-- spring boot aop starter依赖 -->\n	<groupId>org.springframework.boot</groupId>\n	<artifactId>spring-boot-starter-aop</artifactId>\n</dependency>\n<!-- 数据源 -->\n<dependency>\n	<groupId>com.zaxxer</groupId>\n	<artifactId>HikariCP</artifactId>\n</dependency>\n```\n###### 感谢 [如梦技术的代码片段](https://gitee.com/596392912/codes) , [JFinal](http://jfinal.com)\n#### JFinalTxAop \n```\npackage com.choxsu.elastic.config;\n\nimport com.jfinal.kit.LogKit;\nimport com.jfinal.plugin.activerecord.ActiveRecordException;\nimport com.jfinal.plugin.activerecord.Config;\nimport com.jfinal.plugin.activerecord.DbKit;\nimport com.jfinal.plugin.activerecord.NestedTransactionHelpException;\nimport com.jfinal.plugin.activerecord.tx.TxConfig;\nimport org.aspectj.lang.ProceedingJoinPoint;\nimport org.aspectj.lang.annotation.Around;\nimport org.aspectj.lang.annotation.Aspect;\nimport org.aspectj.lang.annotation.Pointcut;\nimport org.aspectj.lang.reflect.MethodSignature;\nimport org.springframework.stereotype.Component;\n\nimport java.lang.reflect.Method;\nimport java.sql.Connection;\nimport java.sql.SQLException;\n\n/**\n * @author choxsu\n * @date 2018/4/13\n */\n@Aspect\n@Component\npublic class JFinalTxAop {\n\n\n    /**\n     * 自定义JFinal 事物注解\n     * value中的意思解释\n     *\n     * @annotation 表示注解只能支持方法上\n     * @within 表示注解在类下面所有的方法 ， 暂时不使用这种方式\n     */\n    @Pointcut(\"@annotation(com.choxsu.elastic.config.JFinalTx)\")\n    private void method() {\n    }\n\n    @Around(value = \"method()\", argNames = \"pjp\")\n    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {\n        Object retVal = null;\n        Config config = getConfigWithTxConfig(pjp);\n        if (config == null)\n            config = DbKit.getConfig();\n\n        Connection conn = config.getThreadLocalConnection();\n        // Nested transaction support\n        if (conn != null) {\n            try {\n                if (conn.getTransactionIsolation() < getTransactionLevel(config))\n                    conn.setTransactionIsolation(getTransactionLevel(config));\n                retVal = pjp.proceed();\n                return retVal;\n            } catch (SQLException e) {\n                throw new ActiveRecordException(e);\n            }\n        }\n\n        Boolean autoCommit = null;\n        try {\n            conn = config.getConnection();\n            autoCommit = conn.getAutoCommit();\n            config.setThreadLocalConnection(conn);\n            conn.setTransactionIsolation(getTransactionLevel(config));// conn.setTransactionIsolation(transactionLevel);\n\n            conn.setAutoCommit(false);\n            retVal = pjp.proceed();\n            conn.commit();\n        } catch (NestedTransactionHelpException e) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            LogKit.logNothing(e);\n        } catch (Throwable t) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            throw t instanceof RuntimeException ? (RuntimeException) t : new ActiveRecordException(t);\n        } finally {\n            try {\n                if (conn != null) {\n                    if (autoCommit != null)\n                        conn.setAutoCommit(autoCommit);\n                    conn.close();\n                }\n            } catch (Throwable t) {\n                // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown\n                LogKit.error(t.getMessage(), t);\n            } finally {\n                // prevent memory leak\n                config.removeThreadLocalConnection();\n            }\n        }\n        return retVal;\n    }\n\n    /**\n     * 获取配置的事务级别\n     *\n     * @param config\n     * @return\n     */\n    protected int getTransactionLevel(Config config) {\n        return config.getTransactionLevel();\n    }\n\n    /**\n     * @param pjp\n     * @return Config\n     */\n    public static Config getConfigWithTxConfig(ProceedingJoinPoint pjp) {\n        MethodSignature ms = (MethodSignature) pjp.getSignature();\n        Method method = ms.getMethod();\n        TxConfig txConfig = method.getAnnotation(TxConfig.class);\n        if (txConfig == null)\n            txConfig = pjp.getTarget().getClass().getAnnotation(TxConfig.class);\n\n        if (txConfig != null) {\n            Config config = DbKit.getConfig(txConfig.value());\n            if (config == null)\n                throw new RuntimeException(\"Config not found with TxConfig: \" + txConfig.value());\n            return config;\n        }\n        return null;\n    }\n}\n```\n#### JFinalTx \n```\npackage com.choxsu.elastic.config;\n\n/**\n * @author choxsu\n */\n\nimport java.lang.annotation.*;\n\n/**\n * Jfinal事物交给spring管理注解\n * 目前只支持用在方法上\n */\n@Inherited\n@Target({ElementType.METHOD})\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface JFinalTx {\n\n}\n```\n### 使用\n#### TestController \n```\npackage com.choxsu.elastic.controller;\n\nimport com.choxsu.elastic.service.TestService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n/**\n * @author choxsu\n */\n@RestController\n@RequestMapping(value = {\"/test/v1\"})\npublic class TestController {\n\n\n    @Autowired\n    private TestService testService;\n\n    @GetMapping(value = \"/testTran\")\n    public Object testTran(){\n\n\n        return testService.testTran();\n    }\n}\n\n```\n#### TestService\n```\npackage com.choxsu.elastic.service;\n\nimport com.choxsu.elastic.config.JFinalTx;\nimport com.jfinal.kit.Ret;\nimport com.jfinal.plugin.activerecord.Db;\nimport com.jfinal.plugin.activerecord.Record;\nimport org.springframework.stereotype.Service;\n\n\n/**\n * @author choxsu\n */\n@Service\npublic class TestService {\n\n    /**\n     * 事物测试\n     *\n     * @return\n     */\n    @JFinalTx\n    public Object testTran() {\n        Record record = new Record();\n        record.set(\"id\", 10);\n        Db.save(\"test\", record);\n        if (true) {\n            throw new RuntimeException(\"test\");\n        }\n        return Ret.by(\"msg\", \"success\");\n    }\n}\n\n```\n##### sql执行了\n```\nSql: insert into `test`(`id`) values(?)\n```\n##### 但是数据库没有数据\n![image.png](https://upload-images.jianshu.io/upload_images/7463793-cb026e4ac9652bef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)\n\n到此证明事物拦截成功，可以使用spring来管理ActiveRecordPlugin的事物了\n\n#### 去掉`throw new RuntimeException(\"test\");`的效果\n##### sql\n```\nSql: insert into `test`(`id`) values(?)\n```\n##### 数据库结果\n![image.png](https://upload-images.jianshu.io/upload_images/7463793-afcb50b9767dbadc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)', '2018-06-14 12:09:34', '2019-04-18 21:57:18', 231, 0, 0, 'blog', 0, 1, NULL);
INSERT INTO `blog` VALUES (47, 1, 'JFinal ActiveRecordPlugin插件事物交给Spring管理1', '<p>最近在SpringBoot中使用JFinal的ActiveRecordPlugin插件，虽然事物可以直接通过注解<code>@Before(Tx.class)</code>来解决，但是后面项目的需要将事物交给spring来管理，具体实现看下去</p>\n<h3 id=\"h3-u601Du8DEF\"><a name=\"思路\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>思路</h3><p>使用spring AOP代理,这里使用springboot来实现，spring同理</p>\n<h4 id=\"h4-maven-\"><a name=\"maven 依赖\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>maven 依赖</h4><pre><code>        &lt;dependency&gt;&lt;!-- spring boot aop starter依赖 --&gt;\n            &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;\n            &lt;artifactId&gt;spring-boot-starter-aop&lt;/artifactId&gt;\n        &lt;/dependency&gt;\n        &lt;!-- 数据源 --&gt;\n        &lt;dependency&gt;\n            &lt;groupId&gt;com.zaxxer&lt;/groupId&gt;\n            &lt;artifactId&gt;HikariCP&lt;/artifactId&gt;\n        &lt;/dependency&gt;\n</code></pre><h6 id=\"h6--jfinal\"><a name=\"感谢   如梦技术的代码片段  ,   JFinal\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>感谢 <a href=\"https://gitee.com/596392912/codes\">如梦技术的代码片段</a> , <a href=\"http://jfinal.com\">JFinal</a></h6><h4 id=\"h4-jfinaltxaop\"><a name=\"JFinalTxAop\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>JFinalTxAop</h4><pre><code>package com.choxsu.elastic.config;\n\nimport com.jfinal.kit.LogKit;\nimport com.jfinal.plugin.activerecord.ActiveRecordException;\nimport com.jfinal.plugin.activerecord.Config;\nimport com.jfinal.plugin.activerecord.DbKit;\nimport com.jfinal.plugin.activerecord.NestedTransactionHelpException;\nimport com.jfinal.plugin.activerecord.tx.TxConfig;\nimport org.aspectj.lang.ProceedingJoinPoint;\nimport org.aspectj.lang.annotation.Around;\nimport org.aspectj.lang.annotation.Aspect;\nimport org.aspectj.lang.annotation.Pointcut;\nimport org.aspectj.lang.reflect.MethodSignature;\nimport org.springframework.stereotype.Component;\n\nimport java.lang.reflect.Method;\nimport java.sql.Connection;\nimport java.sql.SQLException;\n\n/**\n * @author choxsu\n * @date 2018/4/13\n */\n@Aspect\n@Component\npublic class JFinalTxAop {\n\n\n    /**\n     * 自定义JFinal 事物注解\n     * value中的意思解释\n     *\n     * @annotation 表示注解只能支持方法上\n     * @within 表示注解在类下面所有的方法 ， 暂时不使用这种方式\n     */\n    @Pointcut(\"@annotation(com.choxsu.elastic.config.JFinalTx)\")\n    private void method() {\n    }\n\n    @Around(value = \"method()\", argNames = \"pjp\")\n    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {\n        Object retVal = null;\n        Config config = getConfigWithTxConfig(pjp);\n        if (config == null)\n            config = DbKit.getConfig();\n\n        Connection conn = config.getThreadLocalConnection();\n        // Nested transaction support\n        if (conn != null) {\n            try {\n                if (conn.getTransactionIsolation() &lt; getTransactionLevel(config))\n                    conn.setTransactionIsolation(getTransactionLevel(config));\n                retVal = pjp.proceed();\n                return retVal;\n            } catch (SQLException e) {\n                throw new ActiveRecordException(e);\n            }\n        }\n\n        Boolean autoCommit = null;\n        try {\n            conn = config.getConnection();\n            autoCommit = conn.getAutoCommit();\n            config.setThreadLocalConnection(conn);\n            conn.setTransactionIsolation(getTransactionLevel(config));// conn.setTransactionIsolation(transactionLevel);\n\n            conn.setAutoCommit(false);\n            retVal = pjp.proceed();\n            conn.commit();\n        } catch (NestedTransactionHelpException e) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            LogKit.logNothing(e);\n        } catch (Throwable t) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            throw t instanceof RuntimeException ? (RuntimeException) t : new ActiveRecordException(t);\n        } finally {\n            try {\n                if (conn != null) {\n                    if (autoCommit != null)\n                        conn.setAutoCommit(autoCommit);\n                    conn.close();\n                }\n            } catch (Throwable t) {\n                // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown\n                LogKit.error(t.getMessage(), t);\n            } finally {\n                // prevent memory leak\n                config.removeThreadLocalConnection();\n            }\n        }\n        return retVal;\n    }\n\n    /**\n     * 获取配置的事务级别\n     *\n     * @param config\n     * @return\n     */\n    protected int getTransactionLevel(Config config) {\n        return config.getTransactionLevel();\n    }\n\n    /**\n     * @param pjp\n     * @return Config\n     */\n    public static Config getConfigWithTxConfig(ProceedingJoinPoint pjp) {\n        MethodSignature ms = (MethodSignature) pjp.getSignature();\n        Method method = ms.getMethod();\n        TxConfig txConfig = method.getAnnotation(TxConfig.class);\n        if (txConfig == null)\n            txConfig = pjp.getTarget().getClass().getAnnotation(TxConfig.class);\n\n        if (txConfig != null) {\n            Config config = DbKit.getConfig(txConfig.value());\n            if (config == null)\n                throw new RuntimeException(\"Config not found with TxConfig: \" + txConfig.value());\n            return config;\n        }\n        return null;\n    }\n}\n</code></pre><h4 id=\"h4-jfinaltx\"><a name=\"JFinalTx\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>JFinalTx</h4><pre><code>package com.choxsu.elastic.config;\n\n/**\n * @author choxsu\n */\n\nimport java.lang.annotation.*;\n\n/**\n * Jfinal事物交给spring管理注解\n * 目前只支持用在方法上\n */\n@Inherited\n@Target({ElementType.METHOD})\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface JFinalTx {\n\n}\n</code></pre><h3 id=\"h3-u4F7Fu7528\"><a name=\"使用\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>使用</h3><h4 id=\"h4-testcontroller\"><a name=\"TestController\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>TestController</h4><pre><code>package com.choxsu.elastic.controller;\n\nimport com.choxsu.elastic.service.TestService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n/**\n * @author choxsu\n */\n@RestController\n@RequestMapping(value = {\"/test/v1\"})\npublic class TestController {\n\n\n    @Autowired\n    private TestService testService;\n\n    @GetMapping(value = \"/testTran\")\n    public Object testTran(){\n\n\n        return testService.testTran();\n    }\n}\n</code></pre><h4 id=\"h4-testservice\"><a name=\"TestService\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>TestService</h4><pre><code>package com.choxsu.elastic.service;\n\nimport com.choxsu.elastic.config.JFinalTx;\nimport com.jfinal.kit.Ret;\nimport com.jfinal.plugin.activerecord.Db;\nimport com.jfinal.plugin.activerecord.Record;\nimport org.springframework.stereotype.Service;\n\n\n/**\n * @author choxsu\n */\n@Service\npublic class TestService {\n\n    /**\n     * 事物测试\n     *\n     * @return\n     */\n    @JFinalTx\n    public Object testTran() {\n        Record record = new Record();\n        record.set(\"id\", 10);\n        Db.save(\"test\", record);\n        if (true) {\n            throw new RuntimeException(\"test\");\n        }\n        return Ret.by(\"msg\", \"success\");\n    }\n}\n</code></pre><h5 id=\"h5-sql-\"><a name=\"sql执行了\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>sql执行了</h5><pre><code>Sql: insert into `test`(`id`) values(?)\n</code></pre><h5 id=\"h5-u4F46u662Fu6570u636Eu5E93u6CA1u6709u6570u636E\"><a name=\"但是数据库没有数据\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>但是数据库没有数据</h5><p><img src=\"https://upload-images.jianshu.io/upload_images/7463793-cb026e4ac9652bef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"image.png\"></p>\n<p>到此证明事物拦截成功，可以使用spring来管理ActiveRecordPlugin的事物了</p>\n<h4 id=\"h4--code-throw-new-runtimeexception-quot-test-quot-code-\"><a name=\"去掉<code>throw new RuntimeException(&quot;test&quot;);</code>的效果\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>去掉<code>throw new RuntimeException(\"test\");</code>的效果</h4><h5 id=\"h5-sql\"><a name=\"sql\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>sql</h5><pre><code>Sql: insert into `test`(`id`) values(?)\n</code></pre><h5 id=\"h5-u6570u636Eu5E93u7ED3u679C\"><a name=\"数据库结果\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>数据库结果</h5><p><img src=\"https://upload-images.jianshu.io/upload_images/7463793-afcb50b9767dbadc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"image.png\"></p>\n', '最近在SpringBoot中使用JFinal的ActiveRecordPlugin插件，虽然事物可以直接通过注解`@Before(Tx.class)`来解决，但是后面项目的需要将事物交给spring来管理，具体实现看下去\n### 思路\n使用spring AOP代理,这里使用springboot来实现，spring同理\n\n\n#### maven 依赖\n```\n        <dependency><!-- spring boot aop starter依赖 -->\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-aop</artifactId>\n        </dependency>\n        <!-- 数据源 -->\n        <dependency>\n            <groupId>com.zaxxer</groupId>\n            <artifactId>HikariCP</artifactId>\n        </dependency>\n```\n###### 感谢 [如梦技术的代码片段](https://gitee.com/596392912/codes) , [JFinal](http://jfinal.com)\n#### JFinalTxAop \n```\npackage com.choxsu.elastic.config;\n\nimport com.jfinal.kit.LogKit;\nimport com.jfinal.plugin.activerecord.ActiveRecordException;\nimport com.jfinal.plugin.activerecord.Config;\nimport com.jfinal.plugin.activerecord.DbKit;\nimport com.jfinal.plugin.activerecord.NestedTransactionHelpException;\nimport com.jfinal.plugin.activerecord.tx.TxConfig;\nimport org.aspectj.lang.ProceedingJoinPoint;\nimport org.aspectj.lang.annotation.Around;\nimport org.aspectj.lang.annotation.Aspect;\nimport org.aspectj.lang.annotation.Pointcut;\nimport org.aspectj.lang.reflect.MethodSignature;\nimport org.springframework.stereotype.Component;\n\nimport java.lang.reflect.Method;\nimport java.sql.Connection;\nimport java.sql.SQLException;\n\n/**\n * @author choxsu\n * @date 2018/4/13\n */\n@Aspect\n@Component\npublic class JFinalTxAop {\n\n\n    /**\n     * 自定义JFinal 事物注解\n     * value中的意思解释\n     *\n     * @annotation 表示注解只能支持方法上\n     * @within 表示注解在类下面所有的方法 ， 暂时不使用这种方式\n     */\n    @Pointcut(\"@annotation(com.choxsu.elastic.config.JFinalTx)\")\n    private void method() {\n    }\n\n    @Around(value = \"method()\", argNames = \"pjp\")\n    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {\n        Object retVal = null;\n        Config config = getConfigWithTxConfig(pjp);\n        if (config == null)\n            config = DbKit.getConfig();\n\n        Connection conn = config.getThreadLocalConnection();\n        // Nested transaction support\n        if (conn != null) {\n            try {\n                if (conn.getTransactionIsolation() < getTransactionLevel(config))\n                    conn.setTransactionIsolation(getTransactionLevel(config));\n                retVal = pjp.proceed();\n                return retVal;\n            } catch (SQLException e) {\n                throw new ActiveRecordException(e);\n            }\n        }\n\n        Boolean autoCommit = null;\n        try {\n            conn = config.getConnection();\n            autoCommit = conn.getAutoCommit();\n            config.setThreadLocalConnection(conn);\n            conn.setTransactionIsolation(getTransactionLevel(config));// conn.setTransactionIsolation(transactionLevel);\n\n            conn.setAutoCommit(false);\n            retVal = pjp.proceed();\n            conn.commit();\n        } catch (NestedTransactionHelpException e) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            LogKit.logNothing(e);\n        } catch (Throwable t) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            throw t instanceof RuntimeException ? (RuntimeException) t : new ActiveRecordException(t);\n        } finally {\n            try {\n                if (conn != null) {\n                    if (autoCommit != null)\n                        conn.setAutoCommit(autoCommit);\n                    conn.close();\n                }\n            } catch (Throwable t) {\n                // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown\n                LogKit.error(t.getMessage(), t);\n            } finally {\n                // prevent memory leak\n                config.removeThreadLocalConnection();\n            }\n        }\n        return retVal;\n    }\n\n    /**\n     * 获取配置的事务级别\n     *\n     * @param config\n     * @return\n     */\n    protected int getTransactionLevel(Config config) {\n        return config.getTransactionLevel();\n    }\n\n    /**\n     * @param pjp\n     * @return Config\n     */\n    public static Config getConfigWithTxConfig(ProceedingJoinPoint pjp) {\n        MethodSignature ms = (MethodSignature) pjp.getSignature();\n        Method method = ms.getMethod();\n        TxConfig txConfig = method.getAnnotation(TxConfig.class);\n        if (txConfig == null)\n            txConfig = pjp.getTarget().getClass().getAnnotation(TxConfig.class);\n\n        if (txConfig != null) {\n            Config config = DbKit.getConfig(txConfig.value());\n            if (config == null)\n                throw new RuntimeException(\"Config not found with TxConfig: \" + txConfig.value());\n            return config;\n        }\n        return null;\n    }\n}\n```\n#### JFinalTx \n```\npackage com.choxsu.elastic.config;\n\n/**\n * @author choxsu\n */\n\nimport java.lang.annotation.*;\n\n/**\n * Jfinal事物交给spring管理注解\n * 目前只支持用在方法上\n */\n@Inherited\n@Target({ElementType.METHOD})\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface JFinalTx {\n\n}\n```\n### 使用\n#### TestController \n```\npackage com.choxsu.elastic.controller;\n\nimport com.choxsu.elastic.service.TestService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n/**\n * @author choxsu\n */\n@RestController\n@RequestMapping(value = {\"/test/v1\"})\npublic class TestController {\n\n\n    @Autowired\n    private TestService testService;\n\n    @GetMapping(value = \"/testTran\")\n    public Object testTran(){\n\n\n        return testService.testTran();\n    }\n}\n\n```\n#### TestService\n```\npackage com.choxsu.elastic.service;\n\nimport com.choxsu.elastic.config.JFinalTx;\nimport com.jfinal.kit.Ret;\nimport com.jfinal.plugin.activerecord.Db;\nimport com.jfinal.plugin.activerecord.Record;\nimport org.springframework.stereotype.Service;\n\n\n/**\n * @author choxsu\n */\n@Service\npublic class TestService {\n\n    /**\n     * 事物测试\n     *\n     * @return\n     */\n    @JFinalTx\n    public Object testTran() {\n        Record record = new Record();\n        record.set(\"id\", 10);\n        Db.save(\"test\", record);\n        if (true) {\n            throw new RuntimeException(\"test\");\n        }\n        return Ret.by(\"msg\", \"success\");\n    }\n}\n\n```\n##### sql执行了\n```\nSql: insert into `test`(`id`) values(?)\n```\n##### 但是数据库没有数据\n![image.png](https://upload-images.jianshu.io/upload_images/7463793-cb026e4ac9652bef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)\n\n到此证明事物拦截成功，可以使用spring来管理ActiveRecordPlugin的事物了\n\n#### 去掉`throw new RuntimeException(\"test\");`的效果\n##### sql\n```\nSql: insert into `test`(`id`) values(?)\n```\n##### 数据库结果\n![image.png](https://upload-images.jianshu.io/upload_images/7463793-afcb50b9767dbadc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)', '2018-12-22 17:25:28', '2019-04-17 22:18:25', 19, 0, 0, 'blog', 0, 1, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES (1, 'JAVA', 0);
INSERT INTO `blog_tag` VALUES (3, 'JavaScript', 0);
INSERT INTO `blog_tag` VALUES (5, 'Nginx', 0);
INSERT INTO `blog_tag` VALUES (8, 'Mysql', 0);
INSERT INTO `blog_tag` VALUES (9, 'SQL', 0);
INSERT INTO `blog_tag` VALUES (11, '读书笔记', 0);
INSERT INTO `blog_tag` VALUES (12, 'JAVA基础', 0);
INSERT INTO `blog_tag` VALUES (15, 'Tomcat', 0);
INSERT INTO `blog_tag` VALUES (16, '服务器', 0);
INSERT INTO `blog_tag` VALUES (18, 'Linux', 0);
INSERT INTO `blog_tag` VALUES (20, 'Jfinal', 0);
INSERT INTO `blog_tag` VALUES (23, 'SpringBoot', 0);
INSERT INTO `blog_tag` VALUES (24, 'SpringCloud', 0);
INSERT INTO `blog_tag` VALUES (25, 'Vue', 0);
INSERT INTO `blog_tag` VALUES (26, 'Redis', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '图片上厂记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of images
-- ----------------------------
INSERT INTO `images` VALUES (27, 1, '/upload/img/article/0/1_20181222230313.jpeg', '1_20181222230313.jpeg', '.jpeg', 1545490993, '', '0', '1545490993541.jpeg');
INSERT INTO `images` VALUES (28, 1, '/upload/img/article/0/1_20181222230334.jpeg', '1_20181222230334.jpeg', '.jpeg', 1545491014, '', '0', '1545491014660.jpeg');
INSERT INTO `images` VALUES (29, 1, '/upload/img/article/0/1_20181222230548.jpeg', '1_20181222230548.jpeg', '.jpeg', 1545491148, '', '0', '1545491148014.jpeg');
INSERT INTO `images` VALUES (30, 1, '/upload/img/article/0/1_20181222233320.jpeg', '1_20181222233320.jpeg', '.jpeg', 1545492800, '', '5594', '1545492800236.jpeg');
INSERT INTO `images` VALUES (31, 1, '/upload/img/article/0/1_20181222233334.jpeg', '1_20181222233334.jpeg', '.jpeg', 1545492814, '', '5245', '1545492814226.jpeg');
INSERT INTO `images` VALUES (32, 1, '/upload/img/article/0/1_20181222233451.jpeg', '1_20181222233451.jpeg', '.jpeg', 1545492891, '', '12210', '1545492891560.jpeg');
INSERT INTO `images` VALUES (33, 1, '/upload/img/article/0/1_20181222233512.jpeg', '1_20181222233512.jpeg', '.jpeg', 1545492912, '', '23392', '1545492912496.jpeg');
INSERT INTO `images` VALUES (34, 1, '/upload/img/article/0/1_20181222233525.jpeg', '1_20181222233525.jpeg', '.jpeg', 1545492925, '', '30477', '1545492925261.jpeg');
INSERT INTO `images` VALUES (35, 1, '/upload/img/article/0/1_20181222233537.jpeg', '1_20181222233537.jpeg', '.jpeg', 1545492937, '', '20716', '1545492937326.jpeg');
INSERT INTO `images` VALUES (36, 1, '/upload/img/article/0/1_20181222233548.jpeg', '1_20181222233548.jpeg', '.jpeg', 1545492948, '', '7279', '1545492947991.jpeg');
INSERT INTO `images` VALUES (37, 1, '/upload/img/article/0/1_20181222233600.jpeg', '1_20181222233600.jpeg', '.jpeg', 1545492960, '', '9365', '1545492960530.jpeg');
INSERT INTO `images` VALUES (38, 1, '/upload/img/article/0/1_20181222233630.jpeg', '1_20181222233630.jpeg', '.jpeg', 1545492990, '', '25817', '1545492990450.jpeg');
INSERT INTO `images` VALUES (39, 1, '/upload/img/article/0/1_20181222233657.jpeg', '1_20181222233657.jpeg', '.jpeg', 1545493017, '', '15285', '1545493017959.jpeg');
INSERT INTO `images` VALUES (40, 1, '/upload/img/article/0/1_20181222233718.jpeg', '1_20181222233718.jpeg', '.jpeg', 1545493038, '', '45331', '1545493038182.jpeg');
INSERT INTO `images` VALUES (41, 1, '/upload/img/article/0/1_20181222233725.jpeg', '1_20181222233725.jpeg', '.jpeg', 1545493045, '', '15476', '1545493045869.jpeg');
INSERT INTO `images` VALUES (42, 1, '/upload/img/article/0/1_20181222233729.jpeg', '1_20181222233729.jpeg', '.jpeg', 1545493049, '', '15476', '1545493049439.jpeg');
INSERT INTO `images` VALUES (43, 1, '/upload/img/article/0/1_20181222234237.jpeg', '1_20181222234237.jpeg', '.jpeg', 1545493357, '', '14754', '1545493357388.jpeg');
INSERT INTO `images` VALUES (44, 1, '/upload/img/article/0/1_20181222234305.jpeg', '1_20181222234305.jpeg', '.jpeg', 1545493385, '', '13566', '1545493385715.jpeg');
INSERT INTO `images` VALUES (45, 1, '/upload/img/article/0/1_20181222234330.jpeg', '1_20181222234330.jpeg', '.jpeg', 1545493410, '', '47704', '1545493410781.jpeg');
INSERT INTO `images` VALUES (46, 1, '/upload/img/article/0/1_20181222234343.jpeg', '1_20181222234343.jpeg', '.jpeg', 1545493423, '', '43783', '1545493423406.jpeg');
INSERT INTO `images` VALUES (47, 1, '/upload/img/article/0/1_20181222235228.jpg', '1_20181222235228.jpg', '.jpg', 1545493948, '', '141735', 'c.jpg');
INSERT INTO `images` VALUES (48, 1, '/upload/img/article/0/1_20181223000017.jpeg', '1_20181223000017.jpeg', '.jpeg', 1545494417, '', '64556', '1545494417439.jpeg');
INSERT INTO `images` VALUES (49, 1, '/upload/img/article/0/1_20181223000024.jpeg', '1_20181223000024.jpeg', '.jpeg', 1545494424, '', '22179', '1545494424047.jpeg');
INSERT INTO `images` VALUES (50, 1, '/upload/img/article/0/1_20181223000445.jpeg', '1_20181223000445.jpeg', '.jpeg', 1545494685, '', '133195', '1545494685501.jpeg');
INSERT INTO `images` VALUES (51, 1, '/upload/img/article/0/1_20181223001002.png', '1_20181223001002.png', '.png', 1545495002, '', '55220', '1545495013080.png');
INSERT INTO `images` VALUES (52, 1, '/upload/img/article/0/1_20181223001033.png', '1_20181223001033.png', '.png', 1545495033, '', '23413', '1545495043709.png');
INSERT INTO `images` VALUES (53, 1, '/upload/img/article/0/1_20181223001045.png', '1_20181223001045.png', '.png', 1545495045, '', '26973', '1545495055992.png');
INSERT INTO `images` VALUES (54, 1, '/upload/img/article/0/1_20181223001117.png', '1_20181223001117.png', '.png', 1545495077, '', '38897', '1545495087472.png');
INSERT INTO `images` VALUES (55, 1, '/upload/img/article/0/1_20181223001128.png', '1_20181223001128.png', '.png', 1545495088, '', '42917', '1545495099248.png');
INSERT INTO `images` VALUES (56, 1, '/upload/img/article/0/1_20181223001143.png', '1_20181223001143.png', '.png', 1545495103, '', '6732', '1545495113988.png');
INSERT INTO `images` VALUES (57, 1, '/upload/img/article/0/1_20181223001151.png', '1_20181223001151.png', '.png', 1545495111, '', '40637', '1545495121962.png');
INSERT INTO `images` VALUES (58, 1, '/upload/img/article/0/1_20181223001201.png', '1_20181223001201.png', '.png', 1545495121, '', '56626', '1545495131800.png');
INSERT INTO `images` VALUES (59, 1, '/upload/img/article/0/1_20181223001221.png', '1_20181223001221.png', '.png', 1545495141, '', '43202', '1545495151667.png');
INSERT INTO `images` VALUES (60, 1, '/upload/img/article/0/1_20181223001242.png', '1_20181223001242.png', '.png', 1545495162, '', '7300', '1545495173214.png');
INSERT INTO `images` VALUES (61, 1, '/upload/img/article/0/1_20181223001302.png', '1_20181223001302.png', '.png', 1545495182, '', '3446', '1545495192700.png');
INSERT INTO `images` VALUES (62, 1, '/upload/img/article/0/1_20181223001311.png', '1_20181223001311.png', '.png', 1545495191, '', '46768', '1545495202111.png');
INSERT INTO `images` VALUES (63, 1, '/upload/img/article/0/1_20181223001329.png', '1_20181223001329.png', '.png', 1545495209, '', '21796', '1545495220132.png');
INSERT INTO `images` VALUES (64, 1, '/upload/img/article/0/1_20181223001345.png', '1_20181223001345.png', '.png', 1545495225, '', '8270', '1545495235878.png');
INSERT INTO `images` VALUES (65, 1, '/upload/img/article/0/1_20181223001400.png', '1_20181223001400.png', '.png', 1545495240, '', '21961', '1545495251020.png');
INSERT INTO `images` VALUES (66, 1, '/upload/img/article/0/1_20181223001405.png', '1_20181223001405.png', '.png', 1545495245, '', '21961', '1545495255794.png');
INSERT INTO `images` VALUES (67, 5, '0/5.jpg', '/upload/avatar/temp/1_1546938076816.jpg', '.jpg', 1546938080, '', '0', '/upload/avatar/temp/1_1546938076816.jpg');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES (1, '2018-06-14 17:44:44', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-14 17:57:45', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-14 17:59:19', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-14 18:03:55', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:06:59', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:17:36', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:33', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:33', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:33', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:33', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:33', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:24:32', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-14 18:31:49', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-14 22:19:45', '106.87.4.147');
INSERT INTO `login_log` VALUES (1, '2018-06-14 22:26:24', '106.87.4.147');
INSERT INTO `login_log` VALUES (1, '2018-06-14 22:31:25', '106.87.4.147');
INSERT INTO `login_log` VALUES (1, '2018-06-14 22:40:54', '106.87.4.147');
INSERT INTO `login_log` VALUES (1, '2018-06-19 12:36:15', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-19 12:49:11', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-19 13:29:13', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-19 13:56:03', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-19 14:57:13', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-20 10:37:33', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-20 11:10:59', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-20 21:42:44', '106.87.5.52');
INSERT INTO `login_log` VALUES (1, '2018-06-25 19:40:01', '125.82.15.51');
INSERT INTO `login_log` VALUES (1, '2018-06-26 09:18:54', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-26 09:29:05', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-26 09:38:11', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-26 09:55:01', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-26 09:58:35', '183.64.28.18');
INSERT INTO `login_log` VALUES (2, '2018-06-26 10:48:21', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-26 11:55:07', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-26 12:00:31', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-06-26 16:53:04', '47.74.0.27');
INSERT INTO `login_log` VALUES (1, '2018-07-05 08:51:12', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 10:34:16', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 12:47:31', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 17:59:24', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 18:22:25', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 18:23:16', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 18:37:11', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 18:40:21', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-05 21:29:52', '106.87.7.88');
INSERT INTO `login_log` VALUES (1, '2018-07-05 22:04:44', '106.87.7.88');
INSERT INTO `login_log` VALUES (1, '2018-07-05 22:04:44', '106.87.7.88');
INSERT INTO `login_log` VALUES (1, '2018-07-05 22:04:44', '106.87.7.88');
INSERT INTO `login_log` VALUES (1, '2018-07-09 08:51:54', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-09 09:55:42', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-09 10:18:39', '47.74.16.178');
INSERT INTO `login_log` VALUES (1, '2018-07-09 10:46:44', '164.52.13.51');
INSERT INTO `login_log` VALUES (1, '2018-07-09 19:41:46', '47.74.16.178');
INSERT INTO `login_log` VALUES (1, '2018-07-13 09:01:07', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-13 11:09:16', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-13 11:41:52', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-16 11:52:10', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-17 11:10:11', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-18 10:25:19', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-23 11:01:04', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-23 11:09:52', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-23 11:09:52', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-23 11:09:52', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-23 12:58:06', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-07-25 10:32:43', '45.77.182.136');
INSERT INTO `login_log` VALUES (1, '2018-08-06 16:10:02', '125.84.220.230');
INSERT INTO `login_log` VALUES (1, '2018-08-28 08:48:51', '125.84.221.49');
INSERT INTO `login_log` VALUES (1, '2018-09-04 12:07:43', '125.84.223.79');
INSERT INTO `login_log` VALUES (1, '2018-09-10 13:42:42', '125.86.83.173');
INSERT INTO `login_log` VALUES (1, '2018-09-10 15:24:51', '125.86.83.173');
INSERT INTO `login_log` VALUES (1, '2018-09-13 16:25:03', '159.65.133.239');
INSERT INTO `login_log` VALUES (1, '2018-09-13 16:25:41', '159.65.133.239');
INSERT INTO `login_log` VALUES (1, '2018-09-21 09:19:40', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-09-21 09:20:04', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-09-21 09:21:09', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-09-21 09:22:07', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-09-22 18:46:34', '106.87.8.54');
INSERT INTO `login_log` VALUES (1, '2018-09-22 18:49:20', '106.87.8.54');
INSERT INTO `login_log` VALUES (1, '2018-09-22 18:56:47', '106.87.8.54');
INSERT INTO `login_log` VALUES (1, '2018-09-27 10:49:55', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-09-27 11:42:57', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-09-27 12:04:02', '183.64.28.18');
INSERT INTO `login_log` VALUES (3, '2018-09-27 12:05:41', '183.64.28.18');
INSERT INTO `login_log` VALUES (3, '2018-09-27 13:13:37', '125.86.80.222');
INSERT INTO `login_log` VALUES (3, '2018-09-29 08:56:22', '117.136.110.237');
INSERT INTO `login_log` VALUES (3, '2018-10-11 20:37:10', '106.87.5.191');
INSERT INTO `login_log` VALUES (1, '2018-10-11 20:38:36', '106.87.5.191');
INSERT INTO `login_log` VALUES (1, '2018-10-17 16:51:30', '125.86.83.209');
INSERT INTO `login_log` VALUES (1, '2018-10-22 09:36:27', '125.86.82.42');
INSERT INTO `login_log` VALUES (3, '2018-10-24 11:59:05', '124.205.77.28');
INSERT INTO `login_log` VALUES (3, '2018-11-07 10:40:59', '171.118.182.51');
INSERT INTO `login_log` VALUES (1, '2018-11-07 11:29:25', '45.76.78.184');
INSERT INTO `login_log` VALUES (3, '2018-11-13 11:16:03', '121.237.212.112');
INSERT INTO `login_log` VALUES (3, '2018-11-24 16:10:40', '27.47.194.199');
INSERT INTO `login_log` VALUES (1, '2018-11-26 12:49:14', '183.64.28.18');
INSERT INTO `login_log` VALUES (1, '2018-11-28 22:06:23', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-11-28 22:12:26', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-11-28 22:22:40', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-11-28 22:24:11', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-20 20:49:44', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-20 20:58:46', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-20 21:23:12', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-20 22:50:44', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 09:37:18', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 09:39:21', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 20:57:52', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 21:18:56', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 21:49:50', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 21:52:09', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 21:54:01', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 21:58:34', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 21:59:38', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 22:05:03', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 22:09:25', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 22:10:32', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-21 22:31:27', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 11:51:44', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 11:58:12', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 13:28:43', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 14:03:11', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 15:17:48', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 15:49:32', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 15:50:36', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 15:53:12', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 15:59:10', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 15:59:29', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 16:07:05', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 16:28:27', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 16:29:39', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 16:32:45', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2018-12-22 17:12:20', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (1, '2019-04-17 21:55:50', '127.0.0.1');
INSERT INTO `login_log` VALUES (1, '2019-04-18 21:40:11', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (5, '2019-04-19 23:41:15', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (5, '2019-04-20 00:08:37', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (6, '2019-04-20 09:21:33', '127.0.0.1');
INSERT INTO `login_log` VALUES (5, '2019-04-20 09:24:44', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (5, '2019-04-20 09:24:45', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (5, '2019-04-20 09:37:27', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES (7, '2019-04-20 09:38:35', '127.0.0.1');
INSERT INTO `login_log` VALUES (1, '2019-04-20 09:42:20', '127.0.0.1');
INSERT INTO `login_log` VALUES (1, '2019-04-20 09:44:13', '127.0.0.1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (70, '/admin', 'com.choxsu._admin.index.IndexAdminController', '后台首页管理');
INSERT INTO `permission` VALUES (71, '/admin/account', 'com.choxsu._admin.account.AccountAdminController', '账户首页管理');
INSERT INTO `permission` VALUES (72, '/admin/account/addRole', 'com.choxsu._admin.account.AccountAdminController', '增加角色');
INSERT INTO `permission` VALUES (73, '/admin/account/assignRoles', 'com.choxsu._admin.account.AccountAdminController', '分配角色');
INSERT INTO `permission` VALUES (74, '/admin/account/deleteRole', 'com.choxsu._admin.account.AccountAdminController', '删除角色');
INSERT INTO `permission` VALUES (75, '/admin/account/edit', 'com.choxsu._admin.account.AccountAdminController', '账户编辑页面');
INSERT INTO `permission` VALUES (76, '/admin/account/lock', 'com.choxsu._admin.account.AccountAdminController', '账户锁定');
INSERT INTO `permission` VALUES (77, '/admin/account/unlock', 'com.choxsu._admin.account.AccountAdminController', '账户解锁');
INSERT INTO `permission` VALUES (80, '/admin/permission', 'com.choxsu._admin.permission.PermissionAdminController', '权限管理首页');
INSERT INTO `permission` VALUES (81, '/admin/permission/delete', 'com.choxsu._admin.permission.PermissionAdminController', '权限删除');
INSERT INTO `permission` VALUES (82, '/admin/permission/edit', 'com.choxsu._admin.permission.PermissionAdminController', '到权限编辑页面');
INSERT INTO `permission` VALUES (83, '/admin/permission/sync', 'com.choxsu._admin.permission.PermissionAdminController', '权限一键同步');
INSERT INTO `permission` VALUES (84, '/admin/permission/update', 'com.choxsu._admin.permission.PermissionAdminController', '更新权限');
INSERT INTO `permission` VALUES (85, '/admin/role', 'com.choxsu._admin.role.RoleAdminController', '角色管理首页');
INSERT INTO `permission` VALUES (86, '/admin/role/add', 'com.choxsu._admin.role.RoleAdminController', '增加角色页面');
INSERT INTO `permission` VALUES (87, '/admin/role/addPermission', 'com.choxsu._admin.role.RoleAdminController', '角色增加权限页面');
INSERT INTO `permission` VALUES (88, '/admin/role/assignPermissions', 'com.choxsu._admin.role.RoleAdminController', '分配权限');
INSERT INTO `permission` VALUES (89, '/admin/role/delete', 'com.choxsu._admin.role.RoleAdminController', '删除角色');
INSERT INTO `permission` VALUES (90, '/admin/role/deletePermission', 'com.choxsu._admin.role.RoleAdminController', '删除角色权限');
INSERT INTO `permission` VALUES (91, '/admin/role/edit', 'com.choxsu._admin.role.RoleAdminController', '到更新角色页面');
INSERT INTO `permission` VALUES (92, '/admin/role/save', 'com.choxsu._admin.role.RoleAdminController', '保存角色');
INSERT INTO `permission` VALUES (93, '/admin/role/update', 'com.choxsu._admin.role.RoleAdminController', '更新角色');
INSERT INTO `permission` VALUES (94, '/admin/blog', 'com.choxsu._admin.blog.AdminBlogController', '博客管理首页');
INSERT INTO `permission` VALUES (95, '/admin/blog/add', 'com.choxsu._admin.blog.AdminBlogController', '到博客添加页面');
INSERT INTO `permission` VALUES (96, '/admin/blog/delete', 'com.choxsu._admin.blog.AdminBlogController', '删除博客');
INSERT INTO `permission` VALUES (97, '/admin/blog/edit', 'com.choxsu._admin.blog.AdminBlogController', '到博客编辑页面');
INSERT INTO `permission` VALUES (98, '/admin/blog/save', 'com.choxsu._admin.blog.AdminBlogController', '博客保存');
INSERT INTO `permission` VALUES (99, '/admin/tag', 'com.choxsu._admin.blog.tag.AdminTagController', '标签管理首页');
INSERT INTO `permission` VALUES (100, '/admin/tag/add', 'com.choxsu._admin.blog.tag.AdminTagController', '到标签添加页面');
INSERT INTO `permission` VALUES (101, '/admin/tag/delete', 'com.choxsu._admin.blog.tag.AdminTagController', '删除标签');
INSERT INTO `permission` VALUES (102, '/admin/tag/edit', 'com.choxsu._admin.blog.tag.AdminTagController', '到标签编辑页面');
INSERT INTO `permission` VALUES (103, '/admin/tag/save', 'com.choxsu._admin.blog.tag.AdminTagController', '保存标签');
INSERT INTO `permission` VALUES (104, '/admin/tag/update', 'com.choxsu._admin.blog.tag.AdminTagController', '更新标签');
INSERT INTO `permission` VALUES (105, '/admin/blog/update', 'com.choxsu._admin.blog.AdminBlogController', '更新博客');
INSERT INTO `permission` VALUES (107, '/admin/visitor', 'com.choxsu._admin.visitor.VisitorAdminController', '访问管理首页');
INSERT INTO `permission` VALUES (108, '/admin/account/add', 'com.choxsu._admin.account.AccountAdminController', '添加账户页面');
INSERT INTO `permission` VALUES (109, '/admin/account/del', 'com.choxsu._admin.account.AccountAdminController', '账户删除');
INSERT INTO `permission` VALUES (130, '/admin/account/save', 'com.choxsu._admin.account.AccountAdminController', '账户增加');
INSERT INTO `permission` VALUES (131, '/admin/account/showAdminList', 'com.choxsu._admin.account.AccountAdminController', '显示分配过权限的账户');
INSERT INTO `permission` VALUES (132, '/admin/account/update', 'com.choxsu._admin.account.AccountAdminController', '账户更新');
INSERT INTO `permission` VALUES (133, '/admin/druid', 'com.choxsu._admin.druid.DruidController', 'Druid数据库监控页面');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `createAt` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (6, 70);
INSERT INTO `role_permission` VALUES (6, 99);
INSERT INTO `role_permission` VALUES (6, 107);
INSERT INTO `role_permission` VALUES (8, 70);
INSERT INTO `role_permission` VALUES (8, 94);
INSERT INTO `role_permission` VALUES (8, 95);
INSERT INTO `role_permission` VALUES (8, 96);
INSERT INTO `role_permission` VALUES (8, 97);
INSERT INTO `role_permission` VALUES (8, 98);
INSERT INTO `role_permission` VALUES (8, 99);
INSERT INTO `role_permission` VALUES (8, 100);
INSERT INTO `role_permission` VALUES (8, 101);
INSERT INTO `role_permission` VALUES (8, 102);
INSERT INTO `role_permission` VALUES (8, 103);
INSERT INTO `role_permission` VALUES (8, 104);
INSERT INTO `role_permission` VALUES (8, 105);
INSERT INTO `role_permission` VALUES (9, 70);
INSERT INTO `role_permission` VALUES (9, 71);
INSERT INTO `role_permission` VALUES (9, 80);
INSERT INTO `role_permission` VALUES (9, 85);
INSERT INTO `role_permission` VALUES (9, 94);
INSERT INTO `role_permission` VALUES (9, 99);

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

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `id` varchar(33) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of session
-- ----------------------------
INSERT INTO `session` VALUES ('011e97f3ad684fd1a6105d30a46dc352', 1, 1537500004472);
INSERT INTO `session` VALUES ('04b22e2b3bed41599cd6ec84a9939e3d', 1, 1537620393868);
INSERT INTO `session` VALUES ('0860daf6c03f4bb185271337f79cbc1b', 1, 1537620560070);
INSERT INTO `session` VALUES ('0975152e537a4199844cad7b3be331de', 1, 1528868094891);
INSERT INTO `session` VALUES ('145ee44cd5c44e0783850157b86c8541', 1, 1543421182692);
INSERT INTO `session` VALUES ('1c93297a83784880a08e62776d00d1a3', 1, 1541568564924);
INSERT INTO `session` VALUES ('20e15901ab8b466e858bc0656f4b215b', 1, 1531720330380);
INSERT INTO `session` VALUES ('219d443400414d86b431b5f56dc933ac', 1, 1556114149963);
INSERT INTO `session` VALUES ('23e4beff26a34c6fa46d6c36110e5af9', 1, 1537499980450);
INSERT INTO `session` VALUES ('26ad5ba5c3ce4571bcad836da2f68e76', 1, 1537500068692);
INSERT INTO `session` VALUES ('2e83a52f569446cfbc8e555032d1b7fd', 1, 1529390174632);
INSERT INTO `session` VALUES ('34dd4a9093a64391a35f69a05d60cb61', 1, 1527037041382);
INSERT INTO `session` VALUES ('3565f886a4614f51b09797b7b745f92e', 1, 1540179387318);
INSERT INTO `session` VALUES ('3908ab2c8fca4db48c4f0a67ca6cc3da', 1, 1526017667329);
INSERT INTO `session` VALUES ('39506ba4fc2c49099f95a44b0a758456', 1, 1528703964628);
INSERT INTO `session` VALUES ('3959f8e4d0a54b5bbcd60fc43e0dd770', 1, 1528977835196);
INSERT INTO `session` VALUES ('3cbf8e1c040549fe9276e7c5e73a371f', 1, 1531804211435);
INSERT INTO `session` VALUES ('430b5da844ce473e9f3f7edd80f05537', 1, 1528734542302);
INSERT INTO `session` VALUES ('44c5310bf2174861bf03be3c3458cb5c', 1, 1531143705817);
INSERT INTO `session` VALUES ('45558eb8e3c944c9ab737e7af45b59ec', 1, 1529984290931);
INSERT INTO `session` VALUES ('4a08190c613349459be84d5b746f9fd8', 1, 1530793395995);
INSERT INTO `session` VALUES ('4d75c58c797240649e36cd0362a42202', 1, 1529509364118);
INSERT INTO `session` VALUES ('58c77a1bf0b8443e8b20089e6e364ac1', 1, 1545457904442);
INSERT INTO `session` VALUES ('597be02bd55d40349667cf9c333dd4b9', 1, 1529301342104);
INSERT INTO `session` VALUES ('5f205bc108d34768b74f8eb2f4d037bf', 1, 1528945032090);
INSERT INTO `session` VALUES ('5fe04df586aa498b8305e42252b68f54', 1, 1619454238258);
INSERT INTO `session` VALUES ('66a1a936e0b74c54867fe8975532b194', 1, 1532493163206);
INSERT INTO `session` VALUES ('6e7f3f719d494cd6a8a367473e61437b', 1, 1545363438278);
INSERT INTO `session` VALUES ('73d3fb3683ef4b1da8f3fafb3ff3d7cd', 1, 1529469453206);
INSERT INTO `session` VALUES ('7585e3f9da2848ca8f9f8fe7d55bc842', 1, 1536834340521);
INSERT INTO `session` VALUES ('76316c4c2f4248808c6b599c03a1fd1f', 1, 1530791964027);
INSERT INTO `session` VALUES ('7961183b6e674910835e70efd9b8af94', 1, 1525251405079);
INSERT INTO `session` VALUES ('84c53635fc67483a9809148c0647a8a8', 1, 1525242085682);
INSERT INTO `session` VALUES ('8790a908a022489980c2f0b3afd07291', 1, 1531108542002);
INSERT INTO `session` VALUES ('892dfe57faf94aa3900eb294ca37f110', 1, 1531104714187);
INSERT INTO `session` VALUES ('89313f34aa4e45dfb1615bb02eb5b1b2', 1, 1536834302760);
INSERT INTO `session` VALUES ('8a94fc633611488abd5a268858d7e65b', 1, 1536041262529);
INSERT INTO `session` VALUES ('90e0b71868984a6f81b9697940220a6f', 1, 1545404272381);
INSERT INTO `session` VALUES ('946ae77da0284a6ebb338778fe5b7888', 1, 1528734499924);
INSERT INTO `session` VALUES ('96803f3b6b0d4de0a18e8c6f2c1c3500', 5, 1556294917105);
INSERT INTO `session` VALUES ('9c90a3865bab4db79cb08a2f79b3a8f3', 1, 1529301059326);
INSERT INTO `session` VALUES ('9fe5adcd19074bb699c23a4a13ba6022', 1, 1538023795165);
INSERT INTO `session` VALUES ('a4f8f4c9d1974aa9ac311cf4e73ff1e6', 1, 1530804592100);
INSERT INTO `session` VALUES ('a990e8dac11243979f4968e4a152448c', 1, 1531887919018);
INSERT INTO `session` VALUES ('af3d6ab0c9f1446db3a44bdcf1ce147f', 9, 1525321633446);
INSERT INTO `session` VALUES ('b6e786a752a24c868ef76a5d9d7ab908', 1, 1530010383610);
INSERT INTO `session` VALUES ('bb9f91bdf48d4ffba0669c175a40480e', 1, 1528977558572);
INSERT INTO `session` VALUES ('bc8fb990ffab4ef9a15a15dd2dfd2969', 1, 1529934000549);
INSERT INTO `session` VALUES ('be7ddede69da47ac95f99aeb672c2f05', 1, 1529398632560);
INSERT INTO `session` VALUES ('c2ed4d3b98814a3ea86259511b0fc523', 1, 1528425292500);
INSERT INTO `session` VALUES ('c43077d842a84d448db6f5cf76a931d0', 1, 1543214953915);
INSERT INTO `session` VALUES ('d5fe2365980e4bcd9a1c011c7f0b6ca7', 1, 1545324644216);
INSERT INTO `session` VALUES ('d6178dd150454dc98b7040a3e89df74e', 1, 1539773489612);
INSERT INTO `session` VALUES ('d876b9dda2f04e68b2e8b911a5f61e7a', 1, 1539268715820);
INSERT INTO `session` VALUES ('db53604ff6db4db995eb16d2487c8c8e', 1, 1556199610545);
INSERT INTO `session` VALUES ('e0a58e1a8a3b4dcf93db37093296d3e7', 1, 1531458555567);
INSERT INTO `session` VALUES ('e9b5712ee61d47368cc79000354bb995', 1, 1528993184577);
INSERT INTO `session` VALUES ('ee34a1166b1e449b8167adf142d4df99', 1, 1537500127461);
INSERT INTO `session` VALUES ('f1180527572a4a1e872f0e7b98848344', 1, 1528968843645);
INSERT INTO `session` VALUES ('f4970418dc3547209c5aa2ab21aee54f', 1, 1545472172407);
INSERT INTO `session` VALUES ('f538e022c9134baea27f1c5a5374cf4e', 1, 1528951222466);

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
  `client` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `requestTime` datetime(0) NULL DEFAULT NULL COMMENT '请求时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10421 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of visitor
-- ----------------------------
INSERT INTO `visitor` VALUES (3006, '183.64.28.18', 'http://blog.styg.site/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:43:26');
INSERT INTO `visitor` VALUES (3007, '183.64.28.18', 'http://blog.styg.site/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:43:26');
INSERT INTO `visitor` VALUES (3008, '127.0.0.1', 'http://localhost:1013/', 'GET', 'curl/7.29.0', '2018-06-14 17:43:29');
INSERT INTO `visitor` VALUES (3009, '183.64.28.18', 'http://blog.styg.site/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:43:38');
INSERT INTO `visitor` VALUES (3010, '183.64.28.18', 'http://blog.styg.site/blog/detail/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:44:00');
INSERT INTO `visitor` VALUES (3011, '183.64.28.18', 'http://blog.styg.site/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:44:04');
INSERT INTO `visitor` VALUES (3012, '183.64.28.18', 'http://blog.styg.site/blog/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:44:08');
INSERT INTO `visitor` VALUES (3013, '183.64.28.18', 'http://blog.styg.site/login', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:44:18');
INSERT INTO `visitor` VALUES (3014, '183.64.28.18', 'http://blog.styg.site/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36', '2018-06-14 17:44:18');
INSERT INTO `visitor` VALUES (10024, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 11:09:36');
INSERT INTO `visitor` VALUES (10025, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 11:09:31');
INSERT INTO `visitor` VALUES (10026, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 11:38:56');
INSERT INTO `visitor` VALUES (10027, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 11:50:42');
INSERT INTO `visitor` VALUES (10028, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:04:49');
INSERT INTO `visitor` VALUES (10029, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:04:39');
INSERT INTO `visitor` VALUES (10030, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:07:41');
INSERT INTO `visitor` VALUES (10031, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:08:30');
INSERT INTO `visitor` VALUES (10032, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:09:07');
INSERT INTO `visitor` VALUES (10033, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:10:35');
INSERT INTO `visitor` VALUES (10034, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:12:24');
INSERT INTO `visitor` VALUES (10035, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:13:17');
INSERT INTO `visitor` VALUES (10036, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:17:41');
INSERT INTO `visitor` VALUES (10037, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:18:15');
INSERT INTO `visitor` VALUES (10038, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:19:40');
INSERT INTO `visitor` VALUES (10039, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:20:28');
INSERT INTO `visitor` VALUES (10040, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:21:40');
INSERT INTO `visitor` VALUES (10041, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:22:08');
INSERT INTO `visitor` VALUES (10042, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:26:02');
INSERT INTO `visitor` VALUES (10043, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:27:25');
INSERT INTO `visitor` VALUES (10044, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:28:51');
INSERT INTO `visitor` VALUES (10045, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:31:52');
INSERT INTO `visitor` VALUES (10046, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:32:10');
INSERT INTO `visitor` VALUES (10047, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:32:06');
INSERT INTO `visitor` VALUES (10048, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:32:04');
INSERT INTO `visitor` VALUES (10049, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:33:49');
INSERT INTO `visitor` VALUES (10050, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:34:34');
INSERT INTO `visitor` VALUES (10051, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 12:35:06');
INSERT INTO `visitor` VALUES (10052, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:05:58');
INSERT INTO `visitor` VALUES (10053, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:06:00');
INSERT INTO `visitor` VALUES (10054, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:10:09');
INSERT INTO `visitor` VALUES (10055, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:11:04');
INSERT INTO `visitor` VALUES (10056, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:13:59');
INSERT INTO `visitor` VALUES (10057, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:13:58');
INSERT INTO `visitor` VALUES (10058, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:14:00');
INSERT INTO `visitor` VALUES (10059, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:15:33');
INSERT INTO `visitor` VALUES (10060, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:15:31');
INSERT INTO `visitor` VALUES (10061, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:16:40');
INSERT INTO `visitor` VALUES (10062, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:17:08');
INSERT INTO `visitor` VALUES (10063, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:17:02');
INSERT INTO `visitor` VALUES (10064, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:19:25');
INSERT INTO `visitor` VALUES (10065, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:22:13');
INSERT INTO `visitor` VALUES (10066, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:23:35');
INSERT INTO `visitor` VALUES (10067, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:24:00');
INSERT INTO `visitor` VALUES (10068, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:25:46');
INSERT INTO `visitor` VALUES (10069, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:26:39');
INSERT INTO `visitor` VALUES (10070, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:27:27');
INSERT INTO `visitor` VALUES (10071, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:27:29');
INSERT INTO `visitor` VALUES (10072, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:31:08');
INSERT INTO `visitor` VALUES (10073, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:32:03');
INSERT INTO `visitor` VALUES (10074, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:34:32');
INSERT INTO `visitor` VALUES (10075, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:35:54');
INSERT INTO `visitor` VALUES (10076, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 13:36:44');
INSERT INTO `visitor` VALUES (10077, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:02:53');
INSERT INTO `visitor` VALUES (10078, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:04:08');
INSERT INTO `visitor` VALUES (10079, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:05:19');
INSERT INTO `visitor` VALUES (10080, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:06:17');
INSERT INTO `visitor` VALUES (10081, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:07:36');
INSERT INTO `visitor` VALUES (10082, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:08:16');
INSERT INTO `visitor` VALUES (10083, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:10:08');
INSERT INTO `visitor` VALUES (10084, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:12:03');
INSERT INTO `visitor` VALUES (10085, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:13:56');
INSERT INTO `visitor` VALUES (10086, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:15:10');
INSERT INTO `visitor` VALUES (10087, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:17:13');
INSERT INTO `visitor` VALUES (10088, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:18:41');
INSERT INTO `visitor` VALUES (10089, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:19:00');
INSERT INTO `visitor` VALUES (10090, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:21:28');
INSERT INTO `visitor` VALUES (10091, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:22:54');
INSERT INTO `visitor` VALUES (10092, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:23:01');
INSERT INTO `visitor` VALUES (10093, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:27:20');
INSERT INTO `visitor` VALUES (10094, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:28:01');
INSERT INTO `visitor` VALUES (10095, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:29:22');
INSERT INTO `visitor` VALUES (10096, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:30:03');
INSERT INTO `visitor` VALUES (10097, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:31:33');
INSERT INTO `visitor` VALUES (10098, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:32:15');
INSERT INTO `visitor` VALUES (10099, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:33:07');
INSERT INTO `visitor` VALUES (10100, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:34:52');
INSERT INTO `visitor` VALUES (10101, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:35:00');
INSERT INTO `visitor` VALUES (10102, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:36:06');
INSERT INTO `visitor` VALUES (10103, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:37:41');
INSERT INTO `visitor` VALUES (10104, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:38:02');
INSERT INTO `visitor` VALUES (10105, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:39:01');
INSERT INTO `visitor` VALUES (10106, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:42:51');
INSERT INTO `visitor` VALUES (10107, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:43:24');
INSERT INTO `visitor` VALUES (10108, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:44:12');
INSERT INTO `visitor` VALUES (10109, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:45:44');
INSERT INTO `visitor` VALUES (10110, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:46:22');
INSERT INTO `visitor` VALUES (10111, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:47:49');
INSERT INTO `visitor` VALUES (10112, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:48:02');
INSERT INTO `visitor` VALUES (10113, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:49:28');
INSERT INTO `visitor` VALUES (10114, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:50:32');
INSERT INTO `visitor` VALUES (10115, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:55:22');
INSERT INTO `visitor` VALUES (10116, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 14:57:10');
INSERT INTO `visitor` VALUES (10117, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:01:34');
INSERT INTO `visitor` VALUES (10118, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:02:06');
INSERT INTO `visitor` VALUES (10119, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:03:01');
INSERT INTO `visitor` VALUES (10120, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:04:12');
INSERT INTO `visitor` VALUES (10121, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:05:02');
INSERT INTO `visitor` VALUES (10122, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:08:07');
INSERT INTO `visitor` VALUES (10123, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:09:11');
INSERT INTO `visitor` VALUES (10124, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 15:12:38');
INSERT INTO `visitor` VALUES (10125, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:12:46');
INSERT INTO `visitor` VALUES (10126, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:13:18');
INSERT INTO `visitor` VALUES (10127, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:14:04');
INSERT INTO `visitor` VALUES (10128, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:14:00');
INSERT INTO `visitor` VALUES (10129, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:15:23');
INSERT INTO `visitor` VALUES (10130, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:16:05');
INSERT INTO `visitor` VALUES (10131, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:16:29');
INSERT INTO `visitor` VALUES (10132, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:20:10');
INSERT INTO `visitor` VALUES (10133, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:21:20');
INSERT INTO `visitor` VALUES (10134, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:21:28');
INSERT INTO `visitor` VALUES (10135, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:21:28');
INSERT INTO `visitor` VALUES (10136, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:24:13');
INSERT INTO `visitor` VALUES (10137, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:28:06');
INSERT INTO `visitor` VALUES (10138, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:29:44');
INSERT INTO `visitor` VALUES (10139, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:30:59');
INSERT INTO `visitor` VALUES (10140, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:31:00');
INSERT INTO `visitor` VALUES (10141, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:32:20');
INSERT INTO `visitor` VALUES (10142, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:43:48');
INSERT INTO `visitor` VALUES (10143, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:44:07');
INSERT INTO `visitor` VALUES (10144, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:44:48');
INSERT INTO `visitor` VALUES (10145, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:45:58');
INSERT INTO `visitor` VALUES (10146, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:46:29');
INSERT INTO `visitor` VALUES (10147, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:47:07');
INSERT INTO `visitor` VALUES (10148, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:47:09');
INSERT INTO `visitor` VALUES (10149, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:48:04');
INSERT INTO `visitor` VALUES (10150, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:48:40');
INSERT INTO `visitor` VALUES (10151, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:55:41');
INSERT INTO `visitor` VALUES (10152, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:56:12');
INSERT INTO `visitor` VALUES (10153, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:57:18');
INSERT INTO `visitor` VALUES (10154, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:57:16');
INSERT INTO `visitor` VALUES (10155, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 15:58:10');
INSERT INTO `visitor` VALUES (10156, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:00:08');
INSERT INTO `visitor` VALUES (10157, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:01:59');
INSERT INTO `visitor` VALUES (10158, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:02:01');
INSERT INTO `visitor` VALUES (10159, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:03:36');
INSERT INTO `visitor` VALUES (10160, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:04:19');
INSERT INTO `visitor` VALUES (10161, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:05:36');
INSERT INTO `visitor` VALUES (10162, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:06:20');
INSERT INTO `visitor` VALUES (10163, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:07:43');
INSERT INTO `visitor` VALUES (10164, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:08:32');
INSERT INTO `visitor` VALUES (10165, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:09:43');
INSERT INTO `visitor` VALUES (10166, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:10:50');
INSERT INTO `visitor` VALUES (10167, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:11:17');
INSERT INTO `visitor` VALUES (10168, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:12:55');
INSERT INTO `visitor` VALUES (10169, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:13:30');
INSERT INTO `visitor` VALUES (10170, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:14:32');
INSERT INTO `visitor` VALUES (10171, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:15:07');
INSERT INTO `visitor` VALUES (10172, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:18:04');
INSERT INTO `visitor` VALUES (10173, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:19:11');
INSERT INTO `visitor` VALUES (10174, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:21:03');
INSERT INTO `visitor` VALUES (10175, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 16:21:40');
INSERT INTO `visitor` VALUES (10176, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:47:52');
INSERT INTO `visitor` VALUES (10177, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:49:13');
INSERT INTO `visitor` VALUES (10178, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:50:18');
INSERT INTO `visitor` VALUES (10179, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:50:23');
INSERT INTO `visitor` VALUES (10180, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:51:11');
INSERT INTO `visitor` VALUES (10181, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36', '2019-04-17 16:52:25');
INSERT INTO `visitor` VALUES (10182, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:00:09');
INSERT INTO `visitor` VALUES (10183, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:00:11');
INSERT INTO `visitor` VALUES (10184, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:01:22');
INSERT INTO `visitor` VALUES (10185, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:02:48');
INSERT INTO `visitor` VALUES (10186, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:03:21');
INSERT INTO `visitor` VALUES (10187, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:18:22');
INSERT INTO `visitor` VALUES (10188, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:33:41');
INSERT INTO `visitor` VALUES (10189, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:34:12');
INSERT INTO `visitor` VALUES (10190, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:35:05');
INSERT INTO `visitor` VALUES (10191, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:37:41');
INSERT INTO `visitor` VALUES (10192, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:43:37');
INSERT INTO `visitor` VALUES (10193, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:45:35');
INSERT INTO `visitor` VALUES (10194, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:46:46');
INSERT INTO `visitor` VALUES (10195, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:51:16');
INSERT INTO `visitor` VALUES (10196, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:52:52');
INSERT INTO `visitor` VALUES (10197, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:53:01');
INSERT INTO `visitor` VALUES (10198, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:54:54');
INSERT INTO `visitor` VALUES (10199, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:55:45');
INSERT INTO `visitor` VALUES (10200, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 17:57:55');
INSERT INTO `visitor` VALUES (10201, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 18:01:37');
INSERT INTO `visitor` VALUES (10202, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 18:02:27');
INSERT INTO `visitor` VALUES (10203, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 18:03:06');
INSERT INTO `visitor` VALUES (10204, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36', '2019-04-17 18:04:26');
INSERT INTO `visitor` VALUES (10205, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:01:34');
INSERT INTO `visitor` VALUES (10206, '127.0.0.1', 'http://127.0.0.1:1013/article/tag/1', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:01:53');
INSERT INTO `visitor` VALUES (10207, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:02:10');
INSERT INTO `visitor` VALUES (10208, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:02:32');
INSERT INTO `visitor` VALUES (10209, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:02:24');
INSERT INTO `visitor` VALUES (10210, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:03:04');
INSERT INTO `visitor` VALUES (10211, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:03:07');
INSERT INTO `visitor` VALUES (10212, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:05:44');
INSERT INTO `visitor` VALUES (10213, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:07:58');
INSERT INTO `visitor` VALUES (10214, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:09:15');
INSERT INTO `visitor` VALUES (10215, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:11:10');
INSERT INTO `visitor` VALUES (10216, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:12:16');
INSERT INTO `visitor` VALUES (10217, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:13:17');
INSERT INTO `visitor` VALUES (10218, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:14:23');
INSERT INTO `visitor` VALUES (10219, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 20:15:14');
INSERT INTO `visitor` VALUES (10220, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:05:58');
INSERT INTO `visitor` VALUES (10221, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:07:39');
INSERT INTO `visitor` VALUES (10222, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:11:15');
INSERT INTO `visitor` VALUES (10223, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:12:20');
INSERT INTO `visitor` VALUES (10224, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:13:23');
INSERT INTO `visitor` VALUES (10225, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:15:17');
INSERT INTO `visitor` VALUES (10226, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:17:01');
INSERT INTO `visitor` VALUES (10227, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:19:11');
INSERT INTO `visitor` VALUES (10228, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:20:21');
INSERT INTO `visitor` VALUES (10229, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:21:02');
INSERT INTO `visitor` VALUES (10230, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:22:40');
INSERT INTO `visitor` VALUES (10231, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:24:13');
INSERT INTO `visitor` VALUES (10232, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:25:20');
INSERT INTO `visitor` VALUES (10233, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:26:24');
INSERT INTO `visitor` VALUES (10234, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:27:11');
INSERT INTO `visitor` VALUES (10235, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:28:12');
INSERT INTO `visitor` VALUES (10236, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:29:22');
INSERT INTO `visitor` VALUES (10237, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:36:47');
INSERT INTO `visitor` VALUES (10238, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:37:22');
INSERT INTO `visitor` VALUES (10239, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:37:25');
INSERT INTO `visitor` VALUES (10240, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:37:28');
INSERT INTO `visitor` VALUES (10241, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:38:18');
INSERT INTO `visitor` VALUES (10242, '127.0.0.1', 'http://127.0.0.1:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:39:29');
INSERT INTO `visitor` VALUES (10243, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:39:30');
INSERT INTO `visitor` VALUES (10244, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:40:06');
INSERT INTO `visitor` VALUES (10245, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:40:07');
INSERT INTO `visitor` VALUES (10246, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:41:55');
INSERT INTO `visitor` VALUES (10247, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:41:02');
INSERT INTO `visitor` VALUES (10248, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:42:11');
INSERT INTO `visitor` VALUES (10249, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:42:30');
INSERT INTO `visitor` VALUES (10250, '127.0.0.1', 'http://127.0.0.1:1013/search', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:42:13');
INSERT INTO `visitor` VALUES (10251, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:42:25');
INSERT INTO `visitor` VALUES (10252, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:43:25');
INSERT INTO `visitor` VALUES (10253, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:43:23');
INSERT INTO `visitor` VALUES (10254, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:50:47');
INSERT INTO `visitor` VALUES (10255, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:51:10');
INSERT INTO `visitor` VALUES (10256, '127.0.0.1', 'http://127.0.0.1:1013/article', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:51:24');
INSERT INTO `visitor` VALUES (10257, '127.0.0.1', 'http://127.0.0.1:1013/article/404', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:51:28');
INSERT INTO `visitor` VALUES (10258, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:51:37');
INSERT INTO `visitor` VALUES (10259, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:51:15');
INSERT INTO `visitor` VALUES (10260, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:52:02');
INSERT INTO `visitor` VALUES (10261, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:52:50');
INSERT INTO `visitor` VALUES (10262, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:53:06');
INSERT INTO `visitor` VALUES (10263, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:53:02');
INSERT INTO `visitor` VALUES (10264, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:53:07');
INSERT INTO `visitor` VALUES (10265, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:54:54');
INSERT INTO `visitor` VALUES (10266, '127.0.0.1', 'http://127.0.0.1:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:55:39');
INSERT INTO `visitor` VALUES (10267, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:55:34');
INSERT INTO `visitor` VALUES (10268, '127.0.0.1', 'http://127.0.0.1:1013/login', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:55:39');
INSERT INTO `visitor` VALUES (10269, '127.0.0.1', 'http://127.0.0.1:1013/login/doLogin', 'POST', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:55:50');
INSERT INTO `visitor` VALUES (10270, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 21:55:01');
INSERT INTO `visitor` VALUES (10271, '127.0.0.1', 'http://127.0.0.1:1013/article/48', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:02:06');
INSERT INTO `visitor` VALUES (10272, '127.0.0.1', 'http://127.0.0.1:1013/article/48', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:03:15');
INSERT INTO `visitor` VALUES (10273, '127.0.0.1', 'http://127.0.0.1:1013/article/48', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:06:30');
INSERT INTO `visitor` VALUES (10274, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:06:38');
INSERT INTO `visitor` VALUES (10275, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:07:01');
INSERT INTO `visitor` VALUES (10276, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:08:20');
INSERT INTO `visitor` VALUES (10277, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:16:36');
INSERT INTO `visitor` VALUES (10278, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:18:14');
INSERT INTO `visitor` VALUES (10279, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:18:02');
INSERT INTO `visitor` VALUES (10280, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:19:11');
INSERT INTO `visitor` VALUES (10281, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:20:23');
INSERT INTO `visitor` VALUES (10282, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:21:11');
INSERT INTO `visitor` VALUES (10283, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:22:27');
INSERT INTO `visitor` VALUES (10284, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:24:03');
INSERT INTO `visitor` VALUES (10285, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:25:04');
INSERT INTO `visitor` VALUES (10286, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:27:09');
INSERT INTO `visitor` VALUES (10287, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:28:19');
INSERT INTO `visitor` VALUES (10288, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:29:34');
INSERT INTO `visitor` VALUES (10289, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:30:07');
INSERT INTO `visitor` VALUES (10290, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:31:22');
INSERT INTO `visitor` VALUES (10291, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:35:42');
INSERT INTO `visitor` VALUES (10292, '127.0.0.1', 'http://127.0.0.1:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:36:58');
INSERT INTO `visitor` VALUES (10293, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:36:57');
INSERT INTO `visitor` VALUES (10294, '127.0.0.1', 'http://127.0.0.1:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-17 22:36:02');
INSERT INTO `visitor` VALUES (10295, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:23:01');
INSERT INTO `visitor` VALUES (10296, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:26:40');
INSERT INTO `visitor` VALUES (10297, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:27:14');
INSERT INTO `visitor` VALUES (10298, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:28:44');
INSERT INTO `visitor` VALUES (10299, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:32:21');
INSERT INTO `visitor` VALUES (10300, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:35:41');
INSERT INTO `visitor` VALUES (10301, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:37:49');
INSERT INTO `visitor` VALUES (10302, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:38:40');
INSERT INTO `visitor` VALUES (10303, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:38:40');
INSERT INTO `visitor` VALUES (10304, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:38:07');
INSERT INTO `visitor` VALUES (10305, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:39:45');
INSERT INTO `visitor` VALUES (10306, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:39:45');
INSERT INTO `visitor` VALUES (10307, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:40:13');
INSERT INTO `visitor` VALUES (10308, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/doLogin', 'POST', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:40:10');
INSERT INTO `visitor` VALUES (10309, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:41:10');
INSERT INTO `visitor` VALUES (10310, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:41:37');
INSERT INTO `visitor` VALUES (10311, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:41:31');
INSERT INTO `visitor` VALUES (10312, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:42:27');
INSERT INTO `visitor` VALUES (10313, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:46:12');
INSERT INTO `visitor` VALUES (10314, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:47:17');
INSERT INTO `visitor` VALUES (10315, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:48:58');
INSERT INTO `visitor` VALUES (10316, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:49:49');
INSERT INTO `visitor` VALUES (10317, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:49:46');
INSERT INTO `visitor` VALUES (10318, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:49:46');
INSERT INTO `visitor` VALUES (10319, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:50:22');
INSERT INTO `visitor` VALUES (10320, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:52:06');
INSERT INTO `visitor` VALUES (10321, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:52:02');
INSERT INTO `visitor` VALUES (10322, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:52:03');
INSERT INTO `visitor` VALUES (10323, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/47', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:52:09');
INSERT INTO `visitor` VALUES (10324, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:53:41');
INSERT INTO `visitor` VALUES (10325, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:54:09');
INSERT INTO `visitor` VALUES (10326, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:54:32');
INSERT INTO `visitor` VALUES (10327, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:54:07');
INSERT INTO `visitor` VALUES (10328, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:55:03');
INSERT INTO `visitor` VALUES (10329, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:56:10');
INSERT INTO `visitor` VALUES (10330, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:57:52');
INSERT INTO `visitor` VALUES (10331, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:57:21');
INSERT INTO `visitor` VALUES (10332, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-18 21:57:51');
INSERT INTO `visitor` VALUES (10333, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 20:46:32');
INSERT INTO `visitor` VALUES (10334, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:00:00');
INSERT INTO `visitor` VALUES (10335, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:01:40');
INSERT INTO `visitor` VALUES (10336, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:01:42');
INSERT INTO `visitor` VALUES (10337, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:04:29');
INSERT INTO `visitor` VALUES (10338, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:05:18');
INSERT INTO `visitor` VALUES (10339, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:05:20');
INSERT INTO `visitor` VALUES (10340, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:10:03');
INSERT INTO `visitor` VALUES (10341, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:10:04');
INSERT INTO `visitor` VALUES (10342, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:13:10');
INSERT INTO `visitor` VALUES (10343, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:13:11');
INSERT INTO `visitor` VALUES (10344, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:19:21');
INSERT INTO `visitor` VALUES (10345, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:19:22');
INSERT INTO `visitor` VALUES (10346, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:21:48');
INSERT INTO `visitor` VALUES (10347, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:21:51');
INSERT INTO `visitor` VALUES (10348, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:22:01');
INSERT INTO `visitor` VALUES (10349, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:24:13');
INSERT INTO `visitor` VALUES (10350, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:26:54');
INSERT INTO `visitor` VALUES (10351, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:26:24');
INSERT INTO `visitor` VALUES (10352, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:27:15');
INSERT INTO `visitor` VALUES (10353, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:27:17');
INSERT INTO `visitor` VALUES (10354, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:32:08');
INSERT INTO `visitor` VALUES (10355, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:32:09');
INSERT INTO `visitor` VALUES (10356, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:33:13');
INSERT INTO `visitor` VALUES (10357, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:33:14');
INSERT INTO `visitor` VALUES (10358, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:51:07');
INSERT INTO `visitor` VALUES (10359, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 21:56:11');
INSERT INTO `visitor` VALUES (10360, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:33:26');
INSERT INTO `visitor` VALUES (10361, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:33:28');
INSERT INTO `visitor` VALUES (10362, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:34:07');
INSERT INTO `visitor` VALUES (10363, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:35:24');
INSERT INTO `visitor` VALUES (10364, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:36:54');
INSERT INTO `visitor` VALUES (10365, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:36:45');
INSERT INTO `visitor` VALUES (10366, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:37:18');
INSERT INTO `visitor` VALUES (10367, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:38:19');
INSERT INTO `visitor` VALUES (10368, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:38:27');
INSERT INTO `visitor` VALUES (10369, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:41:15');
INSERT INTO `visitor` VALUES (10370, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:41:02');
INSERT INTO `visitor` VALUES (10371, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:45:06');
INSERT INTO `visitor` VALUES (10372, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:45:20');
INSERT INTO `visitor` VALUES (10373, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:50:46');
INSERT INTO `visitor` VALUES (10374, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:51:59');
INSERT INTO `visitor` VALUES (10375, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:52:06');
INSERT INTO `visitor` VALUES (10376, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:53:47');
INSERT INTO `visitor` VALUES (10377, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:55:22');
INSERT INTO `visitor` VALUES (10378, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:57:09');
INSERT INTO `visitor` VALUES (10379, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:58:25');
INSERT INTO `visitor` VALUES (10380, '0:0:0:0:0:0:0:1', 'http://localhost:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:58:16');
INSERT INTO `visitor` VALUES (10381, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:59:30');
INSERT INTO `visitor` VALUES (10382, '0:0:0:0:0:0:0:1', 'http://localhost:1013/article/14', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-19 23:59:34');
INSERT INTO `visitor` VALUES (10383, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:00:21');
INSERT INTO `visitor` VALUES (10384, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:02:02');
INSERT INTO `visitor` VALUES (10385, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:03:25');
INSERT INTO `visitor` VALUES (10386, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:04:08');
INSERT INTO `visitor` VALUES (10387, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:05:36');
INSERT INTO `visitor` VALUES (10388, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:06:23');
INSERT INTO `visitor` VALUES (10389, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:07:03');
INSERT INTO `visitor` VALUES (10390, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:07:47');
INSERT INTO `visitor` VALUES (10391, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:08:37');
INSERT INTO `visitor` VALUES (10392, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 00:08:37');
INSERT INTO `visitor` VALUES (10393, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:20:40');
INSERT INTO `visitor` VALUES (10394, '127.0.0.1', 'http://127.0.0.1:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:20:54');
INSERT INTO `visitor` VALUES (10395, '127.0.0.1', 'http://127.0.0.1:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:21:32');
INSERT INTO `visitor` VALUES (10396, '127.0.0.1', 'http://127.0.0.1:1013/my', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:21:50');
INSERT INTO `visitor` VALUES (10397, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:21:33');
INSERT INTO `visitor` VALUES (10398, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:21:58');
INSERT INTO `visitor` VALUES (10399, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:23:24');
INSERT INTO `visitor` VALUES (10400, '127.0.0.1', 'http://127.0.0.1:1013/article/15', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:23:27');
INSERT INTO `visitor` VALUES (10401, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:24:45');
INSERT INTO `visitor` VALUES (10402, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:24:44');
INSERT INTO `visitor` VALUES (10403, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:24:05');
INSERT INTO `visitor` VALUES (10404, '127.0.0.1', 'http://127.0.0.1:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:24:06');
INSERT INTO `visitor` VALUES (10405, '0:0:0:0:0:0:0:1', 'http://localhost:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:37:27');
INSERT INTO `visitor` VALUES (10406, '0:0:0:0:0:0:0:1', 'http://localhost:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:37:36');
INSERT INTO `visitor` VALUES (10407, '127.0.0.1', 'http://127.0.0.1:1013/login/qqCallback', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:38:34');
INSERT INTO `visitor` VALUES (10408, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:38:35');
INSERT INTO `visitor` VALUES (10409, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:41:57');
INSERT INTO `visitor` VALUES (10410, '127.0.0.1', 'http://127.0.0.1:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:42:05');
INSERT INTO `visitor` VALUES (10411, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:42:00');
INSERT INTO `visitor` VALUES (10412, '127.0.0.1', 'http://127.0.0.1:1013/login', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:42:05');
INSERT INTO `visitor` VALUES (10413, '127.0.0.1', 'http://127.0.0.1:1013/login/doLogin', 'POST', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:42:20');
INSERT INTO `visitor` VALUES (10414, '127.0.0.1', 'http://127.0.0.1:1013/login/captcha', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:43:59');
INSERT INTO `visitor` VALUES (10415, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:43:47');
INSERT INTO `visitor` VALUES (10416, '127.0.0.1', 'http://127.0.0.1:1013/login', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:43:59');
INSERT INTO `visitor` VALUES (10417, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:44:13');
INSERT INTO `visitor` VALUES (10418, '127.0.0.1', 'http://127.0.0.1:1013/login/doLogin', 'POST', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:44:13');
INSERT INTO `visitor` VALUES (10419, '127.0.0.1', 'http://127.0.0.1:1013/', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:45:15');
INSERT INTO `visitor` VALUES (10420, '127.0.0.1', 'http://127.0.0.1:1013/login/qqLogin', 'GET', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '2019-04-20 09:45:38');

SET FOREIGN_KEY_CHECKS = 1;
