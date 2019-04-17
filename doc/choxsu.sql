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

 Date: 17/04/2019 11:06:40
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'ChoxSu', 'choxsu@gmail.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-18 09:00:19', '175.12.244.105', '0/1.jpg', 0);
INSERT INTO `account` VALUES (2, '管理员', 'admin@styg.site', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', '8wCSdUV6EJBvPg9zDkphts9JAHFNyO6t', 1, '2018-04-19 10:19:11', '175.12.244.105', '0/1.jpg', 0);
INSERT INTO `account` VALUES (3, 'test', 'test@test.com', 'f3f7f982d422ba1e3569170091584211bd333d378951548712440065832fe613', 'RS_xQw8fhclJqZU2iDPYqa8EYyF9T6pc', 1, '2018-09-27 12:04:25', '183.64.28.18', 'x.jpg', 0);
INSERT INTO `account` VALUES (4, 'test1', 'test1@test.com', 'b7cf9ab8832c2caeee7753efd1e70787b0fd72f17539f2e730e58ba01063b5ca', 'CR3yZ3xuDO2EB4jiEtj4HhwY0tpuq_-y', 1, '2018-12-21 22:11:14', '0:0:0:0:0:0:0:1', 'x.jpg', 0);

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
INSERT INTO `blog` VALUES (14, 1, '关于我', '<h5 id=\"h5-u7F51u540D\"><a name=\"网名\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>网名</h5><p>ChoxSu</p>\n<h5 id=\"h5-u7231u597D\"><a name=\"爱好\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>爱好</h5><p>爬山、电影、游戏</p>\n<h5 id=\"h5-u7B7Eu540D\"><a name=\"签名\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>签名</h5><p>没有思考，人生的路会越走越难！</p>\n<h5 id=\"h5-u5730u70B9\"><a name=\"地点\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>地点</h5><p>重庆</p>\n<h5 id=\"h5-u6027u522B\"><a name=\"性别\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>性别</h5><p>男</p>\n<h5 id=\"h5-u535Au5BA2u5730u5740\"><a name=\"博客地址\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>博客地址</h5><p><a href=\"http://118.24.122.21&quot;\">戳我查看博客</a></p>\n<h5 id=\"h5-u7B80u4ECB\"><a name=\"简介\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>简介</h5><p>我的博客，重点是记录我的技术总结，让更多人解决问题，学习知识，没有批评就没有进步。</p>\n', '##### 网名\nChoxSu\n##### 爱好\n爬山、电影、游戏\n#####签名\n没有思考，人生的路会越走越难！\n#####地点\n重庆\n#####性别\n男\n##### 博客地址\n[戳我查看博客](http://118.24.122.21\")\n##### 简介\n我的博客，重点是记录我的技术总结，让更多人解决问题，学习知识，没有批评就没有进步。', '2018-12-22 16:07:12', '2018-12-22 16:31:17', 21, 0, 0, 'about', 0, 1, NULL);
INSERT INTO `blog` VALUES (15, 1, 'JFinal ActiveRecordPlugin插件事物交给Spring管理', '<p>最近在SpringBoot中使用JFinal的ActiveRecordPlugin插件，虽然事物可以直接通过注解<code><a href=\"https://github.com/Before\" title=\"&#64;Before\" class=\"at-link\">@Before</a>(Tx.class)</code>来解决，但是后面项目的需要将事物交给spring来管理，具体实现看下去</p>\n<h3 id=\"h3-u601Du8DEF\"><a name=\"思路\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>思路</h3><p>使用spring AOP代理,这里使用springboot来实现，spring同理</p>\n<h4 id=\"h4-maven-\"><a name=\"maven 依赖\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>maven 依赖</h4><pre><code>        &lt;dependency&gt;&lt;!-- spring boot aop starter依赖 --&gt;\n            &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;\n            &lt;artifactId&gt;spring-boot-starter-aop&lt;/artifactId&gt;\n        &lt;/dependency&gt;\n        &lt;!-- 数据源 --&gt;\n        &lt;dependency&gt;\n            &lt;groupId&gt;com.zaxxer&lt;/groupId&gt;\n            &lt;artifactId&gt;HikariCP&lt;/artifactId&gt;\n        &lt;/dependency&gt;\n</code></pre><h6 id=\"h6--jfinal\"><a name=\"感谢   如梦技术的代码片段  ,   JFinal\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>感谢 <a href=\"https://gitee.com/596392912/codes\">如梦技术的代码片段</a> , <a href=\"http://jfinal.com\">JFinal</a></h6><h4 id=\"h4-jfinaltxaop\"><a name=\"JFinalTxAop\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>JFinalTxAop</h4><pre><code>package com.choxsu.elastic.config;\n\nimport com.jfinal.kit.LogKit;\nimport com.jfinal.plugin.activerecord.ActiveRecordException;\nimport com.jfinal.plugin.activerecord.Config;\nimport com.jfinal.plugin.activerecord.DbKit;\nimport com.jfinal.plugin.activerecord.NestedTransactionHelpException;\nimport com.jfinal.plugin.activerecord.tx.TxConfig;\nimport org.aspectj.lang.ProceedingJoinPoint;\nimport org.aspectj.lang.annotation.Around;\nimport org.aspectj.lang.annotation.Aspect;\nimport org.aspectj.lang.annotation.Pointcut;\nimport org.aspectj.lang.reflect.MethodSignature;\nimport org.springframework.stereotype.Component;\n\nimport java.lang.reflect.Method;\nimport java.sql.Connection;\nimport java.sql.SQLException;\n\n/**\n * @author choxsu\n * @date 2018/4/13\n */\n@Aspect\n@Component\npublic class JFinalTxAop {\n\n\n    /**\n     * 自定义JFinal 事物注解\n     * value中的意思解释\n     *\n     * @annotation 表示注解只能支持方法上\n     * @within 表示注解在类下面所有的方法 ， 暂时不使用这种方式\n     */\n    @Pointcut(&quot;@annotation(com.choxsu.elastic.config.JFinalTx)&quot;)\n    private void method() {\n    }\n\n    @Around(value = &quot;method()&quot;, argNames = &quot;pjp&quot;)\n    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {\n        Object retVal = null;\n        Config config = getConfigWithTxConfig(pjp);\n        if (config == null)\n            config = DbKit.getConfig();\n\n        Connection conn = config.getThreadLocalConnection();\n        // Nested transaction support\n        if (conn != null) {\n            try {\n                if (conn.getTransactionIsolation() &lt; getTransactionLevel(config))\n                    conn.setTransactionIsolation(getTransactionLevel(config));\n                retVal = pjp.proceed();\n                return retVal;\n            } catch (SQLException e) {\n                throw new ActiveRecordException(e);\n            }\n        }\n\n        Boolean autoCommit = null;\n        try {\n            conn = config.getConnection();\n            autoCommit = conn.getAutoCommit();\n            config.setThreadLocalConnection(conn);\n            conn.setTransactionIsolation(getTransactionLevel(config));// conn.setTransactionIsolation(transactionLevel);\n\n            conn.setAutoCommit(false);\n            retVal = pjp.proceed();\n            conn.commit();\n        } catch (NestedTransactionHelpException e) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            LogKit.logNothing(e);\n        } catch (Throwable t) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            throw t instanceof RuntimeException ? (RuntimeException) t : new ActiveRecordException(t);\n        } finally {\n            try {\n                if (conn != null) {\n                    if (autoCommit != null)\n                        conn.setAutoCommit(autoCommit);\n                    conn.close();\n                }\n            } catch (Throwable t) {\n                // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown\n                LogKit.error(t.getMessage(), t);\n            } finally {\n                // prevent memory leak\n                config.removeThreadLocalConnection();\n            }\n        }\n        return retVal;\n    }\n\n    /**\n     * 获取配置的事务级别\n     *\n     * @param config\n     * @return\n     */\n    protected int getTransactionLevel(Config config) {\n        return config.getTransactionLevel();\n    }\n\n    /**\n     * @param pjp\n     * @return Config\n     */\n    public static Config getConfigWithTxConfig(ProceedingJoinPoint pjp) {\n        MethodSignature ms = (MethodSignature) pjp.getSignature();\n        Method method = ms.getMethod();\n        TxConfig txConfig = method.getAnnotation(TxConfig.class);\n        if (txConfig == null)\n            txConfig = pjp.getTarget().getClass().getAnnotation(TxConfig.class);\n\n        if (txConfig != null) {\n            Config config = DbKit.getConfig(txConfig.value());\n            if (config == null)\n                throw new RuntimeException(&quot;Config not found with TxConfig: &quot; + txConfig.value());\n            return config;\n        }\n        return null;\n    }\n}\n</code></pre><h4 id=\"h4-jfinaltx\"><a name=\"JFinalTx\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>JFinalTx</h4><pre><code>package com.choxsu.elastic.config;\n\n/**\n * @author choxsu\n */\n\nimport java.lang.annotation.*;\n\n/**\n * Jfinal事物交给spring管理注解\n * 目前只支持用在方法上\n */\n@Inherited\n@Target({ElementType.METHOD})\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface JFinalTx {\n\n}\n</code></pre><h3 id=\"h3-u4F7Fu7528\"><a name=\"使用\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>使用</h3><h4 id=\"h4-testcontroller\"><a name=\"TestController\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>TestController</h4><pre><code>package com.choxsu.elastic.controller;\n\nimport com.choxsu.elastic.service.TestService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n/**\n * @author choxsu\n */\n@RestController\n@RequestMapping(value = {&quot;/test/v1&quot;})\npublic class TestController {\n\n\n    @Autowired\n    private TestService testService;\n\n    @GetMapping(value = &quot;/testTran&quot;)\n    public Object testTran(){\n\n\n        return testService.testTran();\n    }\n}\n</code></pre><h4 id=\"h4-testservice\"><a name=\"TestService\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>TestService</h4><pre><code>package com.choxsu.elastic.service;\n\nimport com.choxsu.elastic.config.JFinalTx;\nimport com.jfinal.kit.Ret;\nimport com.jfinal.plugin.activerecord.Db;\nimport com.jfinal.plugin.activerecord.Record;\nimport org.springframework.stereotype.Service;\n\n\n/**\n * @author choxsu\n */\n@Service\npublic class TestService {\n\n    /**\n     * 事物测试\n     *\n     * @return\n     */\n    @JFinalTx\n    public Object testTran() {\n        Record record = new Record();\n        record.set(&quot;id&quot;, 10);\n        Db.save(&quot;test&quot;, record);\n        if (true) {\n            throw new RuntimeException(&quot;test&quot;);\n        }\n        return Ret.by(&quot;msg&quot;, &quot;success&quot;);\n    }\n}\n</code></pre><h5 id=\"h5-sql-\"><a name=\"sql执行了\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>sql执行了</h5><pre><code>Sql: insert into `test`(`id`) values(?)\n</code></pre><h5 id=\"h5-u4F46u662Fu6570u636Eu5E93u6CA1u6709u6570u636E\"><a name=\"但是数据库没有数据\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>但是数据库没有数据</h5><p><img src=\"https://upload-images.jianshu.io/upload_images/7463793-cb026e4ac9652bef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"image.png\"></p>\n<p>到此证明事物拦截成功，可以使用spring来管理ActiveRecordPlugin的事物了</p>\n<h4 id=\"h4--code-throw-new-runtimeexception-quot-test-quot-code-\"><a name=\"去掉<code>throw new RuntimeException(&quot;test&quot;);</code>的效果\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>去掉<code>throw new RuntimeException(&quot;test&quot;);</code>的效果</h4><h5 id=\"h5-sql\"><a name=\"sql\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>sql</h5><pre><code>Sql: insert into `test`(`id`) values(?)\n</code></pre><h5 id=\"h5-u6570u636Eu5E93u7ED3u679C\"><a name=\"数据库结果\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>数据库结果</h5><p><img src=\"https://upload-images.jianshu.io/upload_images/7463793-afcb50b9767dbadc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" alt=\"image.png\"></p>\n', '最近在SpringBoot中使用JFinal的ActiveRecordPlugin插件，虽然事物可以直接通过注解`@Before(Tx.class)`来解决，但是后面项目的需要将事物交给spring来管理，具体实现看下去\n### 思路\n使用spring AOP代理,这里使用springboot来实现，spring同理\n\n\n#### maven 依赖\n```\n        <dependency><!-- spring boot aop starter依赖 -->\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-aop</artifactId>\n        </dependency>\n        <!-- 数据源 -->\n        <dependency>\n            <groupId>com.zaxxer</groupId>\n            <artifactId>HikariCP</artifactId>\n        </dependency>\n```\n###### 感谢 [如梦技术的代码片段](https://gitee.com/596392912/codes) , [JFinal](http://jfinal.com)\n#### JFinalTxAop \n```\npackage com.choxsu.elastic.config;\n\nimport com.jfinal.kit.LogKit;\nimport com.jfinal.plugin.activerecord.ActiveRecordException;\nimport com.jfinal.plugin.activerecord.Config;\nimport com.jfinal.plugin.activerecord.DbKit;\nimport com.jfinal.plugin.activerecord.NestedTransactionHelpException;\nimport com.jfinal.plugin.activerecord.tx.TxConfig;\nimport org.aspectj.lang.ProceedingJoinPoint;\nimport org.aspectj.lang.annotation.Around;\nimport org.aspectj.lang.annotation.Aspect;\nimport org.aspectj.lang.annotation.Pointcut;\nimport org.aspectj.lang.reflect.MethodSignature;\nimport org.springframework.stereotype.Component;\n\nimport java.lang.reflect.Method;\nimport java.sql.Connection;\nimport java.sql.SQLException;\n\n/**\n * @author choxsu\n * @date 2018/4/13\n */\n@Aspect\n@Component\npublic class JFinalTxAop {\n\n\n    /**\n     * 自定义JFinal 事物注解\n     * value中的意思解释\n     *\n     * @annotation 表示注解只能支持方法上\n     * @within 表示注解在类下面所有的方法 ， 暂时不使用这种方式\n     */\n    @Pointcut(\"@annotation(com.choxsu.elastic.config.JFinalTx)\")\n    private void method() {\n    }\n\n    @Around(value = \"method()\", argNames = \"pjp\")\n    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {\n        Object retVal = null;\n        Config config = getConfigWithTxConfig(pjp);\n        if (config == null)\n            config = DbKit.getConfig();\n\n        Connection conn = config.getThreadLocalConnection();\n        // Nested transaction support\n        if (conn != null) {\n            try {\n                if (conn.getTransactionIsolation() < getTransactionLevel(config))\n                    conn.setTransactionIsolation(getTransactionLevel(config));\n                retVal = pjp.proceed();\n                return retVal;\n            } catch (SQLException e) {\n                throw new ActiveRecordException(e);\n            }\n        }\n\n        Boolean autoCommit = null;\n        try {\n            conn = config.getConnection();\n            autoCommit = conn.getAutoCommit();\n            config.setThreadLocalConnection(conn);\n            conn.setTransactionIsolation(getTransactionLevel(config));// conn.setTransactionIsolation(transactionLevel);\n\n            conn.setAutoCommit(false);\n            retVal = pjp.proceed();\n            conn.commit();\n        } catch (NestedTransactionHelpException e) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            LogKit.logNothing(e);\n        } catch (Throwable t) {\n            if (conn != null) try {\n                conn.rollback();\n            } catch (Exception e1) {\n                LogKit.error(e1.getMessage(), e1);\n            }\n            throw t instanceof RuntimeException ? (RuntimeException) t : new ActiveRecordException(t);\n        } finally {\n            try {\n                if (conn != null) {\n                    if (autoCommit != null)\n                        conn.setAutoCommit(autoCommit);\n                    conn.close();\n                }\n            } catch (Throwable t) {\n                // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown\n                LogKit.error(t.getMessage(), t);\n            } finally {\n                // prevent memory leak\n                config.removeThreadLocalConnection();\n            }\n        }\n        return retVal;\n    }\n\n    /**\n     * 获取配置的事务级别\n     *\n     * @param config\n     * @return\n     */\n    protected int getTransactionLevel(Config config) {\n        return config.getTransactionLevel();\n    }\n\n    /**\n     * @param pjp\n     * @return Config\n     */\n    public static Config getConfigWithTxConfig(ProceedingJoinPoint pjp) {\n        MethodSignature ms = (MethodSignature) pjp.getSignature();\n        Method method = ms.getMethod();\n        TxConfig txConfig = method.getAnnotation(TxConfig.class);\n        if (txConfig == null)\n            txConfig = pjp.getTarget().getClass().getAnnotation(TxConfig.class);\n\n        if (txConfig != null) {\n            Config config = DbKit.getConfig(txConfig.value());\n            if (config == null)\n                throw new RuntimeException(\"Config not found with TxConfig: \" + txConfig.value());\n            return config;\n        }\n        return null;\n    }\n}\n```\n#### JFinalTx \n```\npackage com.choxsu.elastic.config;\n\n/**\n * @author choxsu\n */\n\nimport java.lang.annotation.*;\n\n/**\n * Jfinal事物交给spring管理注解\n * 目前只支持用在方法上\n */\n@Inherited\n@Target({ElementType.METHOD})\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface JFinalTx {\n\n}\n```\n### 使用\n#### TestController \n```\npackage com.choxsu.elastic.controller;\n\nimport com.choxsu.elastic.service.TestService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n/**\n * @author choxsu\n */\n@RestController\n@RequestMapping(value = {\"/test/v1\"})\npublic class TestController {\n\n\n    @Autowired\n    private TestService testService;\n\n    @GetMapping(value = \"/testTran\")\n    public Object testTran(){\n\n\n        return testService.testTran();\n    }\n}\n\n```\n#### TestService\n```\npackage com.choxsu.elastic.service;\n\nimport com.choxsu.elastic.config.JFinalTx;\nimport com.jfinal.kit.Ret;\nimport com.jfinal.plugin.activerecord.Db;\nimport com.jfinal.plugin.activerecord.Record;\nimport org.springframework.stereotype.Service;\n\n\n/**\n * @author choxsu\n */\n@Service\npublic class TestService {\n\n    /**\n     * 事物测试\n     *\n     * @return\n     */\n    @JFinalTx\n    public Object testTran() {\n        Record record = new Record();\n        record.set(\"id\", 10);\n        Db.save(\"test\", record);\n        if (true) {\n            throw new RuntimeException(\"test\");\n        }\n        return Ret.by(\"msg\", \"success\");\n    }\n}\n\n```\n##### sql执行了\n```\nSql: insert into `test`(`id`) values(?)\n```\n##### 但是数据库没有数据\n![image.png](https://upload-images.jianshu.io/upload_images/7463793-cb026e4ac9652bef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)\n\n到此证明事物拦截成功，可以使用spring来管理ActiveRecordPlugin的事物了\n\n#### 去掉`throw new RuntimeException(\"test\");`的效果\n##### sql\n```\nSql: insert into `test`(`id`) values(?)\n```\n##### 数据库结果\n![image.png](https://upload-images.jianshu.io/upload_images/7463793-afcb50b9767dbadc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)', '2018-06-14 12:09:34', '2018-12-22 16:36:33', 185, 0, 0, 'blog', 0, 1, NULL);
INSERT INTO `blog` VALUES (47, 1, 'JFinal ActiveRecordPlugin插件事物交给Spring管理1', '<p>侧意识一片文庄啊啊 啊</p>\n', '侧意识一片文庄啊啊 啊', '2018-12-22 17:25:28', '2018-12-22 17:25:28', 1, 0, 0, 'blog', 0, 1, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 10024 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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

SET FOREIGN_KEY_CHECKS = 1;
