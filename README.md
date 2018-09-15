## 前言
  不说太多，往下看
## 项目介绍
  基于JFinal编写的博客系统、后台权限管理，Elastic全文检索；基于SpringBoot集成实现Elastic全文检索,Redis ，CMS管理系统
## 模块说明
### to-blog模块
  主要是一个博客的实现，效果[请移步](http://www.styg.site "ChoxSu博客社区"); 使用了Java语言来开发的，后端框架使用了[JFinal](http://jfinal.com) 来实现
    
  后台管理在地址后面输入  /login 或 /admin, 登录密码是做了加密传输，主要使用了非对称加密的方式
  来保证密码的安全性，具体参照代码里的注释来做，实在搞不懂，请留言，
     
  后台管理实现了权限管理，没用使用第三方权限框架，全手写权限框架，包括权限、角色、用户，更多请参考code;
  
  配置文件中使用了ES的相关配置，这个是测试ES时候留下的，如果不用，那就不用配置该项

### to--elastic模块
 to-elastic是ElasticSearch全文检索DEMO,如有需要可以作参考，在这里不在做过多的说明 
### to-morning模块
 这个模块主要是做一个全新前后端分离的CMS管理系统，包括PC,PC端会做成响应式的布局，  
 兼容大部分的手机或者平板；后端会使用SpringBoot来实现；前后端的分离采用接口的方式来实现，
 前端主要是用Vue js 来实现页面的数据交互; 

## 技术选型

### 后端技术:
技术 | 名称 | 官网
----|------|----
Spring Framework | 容器  | [http://projects.spring.io/spring-framework/](http://projects.spring.io/spring-framework/)
JFinal | MVC框架  | [http://jfinal.com/](http://jfinal.com/)
Spring Boot | 容器 |  [http://spring.io/projects/spring-boot/](http://spring.io/projects/spring-boot/)
SpringMVC | MVC框架  | [http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc)
Druid | 数据库连接池  | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
Redis | 分布式缓存数据库  | [https://redis.io/](https://redis.io/)
Solr & Elasticsearch | 分布式全文搜索引擎  | [http://lucene.apache.org/solr/](http://lucene.apache.org/solr/) [https://www.elastic.co/](https://www.elastic.co/)
Ehcache | 进程内缓存框架  | [http://www.ehcache.org/](http://www.ehcache.org/)
Log4J | 日志组件  | [http://logging.apache.org/log4j/1.2/](http://logging.apache.org/log4j/1.2/)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)
### 前端技术:
技术 | 名称 | 官网
----|------|----
Vue | 渐进式js框架  | [http://vuejs.org/](http://vuejs.org/)
Element | 基于vue的框架  | [https://element.eleme.io/](https://element.eleme.io/)
Iview | 基于vue的框架  | [https://www.iviewui.com/](https://www.iviewui.com/)
webpack |前端打包工具  | [https://webpack.js.org/](https://webpack.js.org/)
jQuery | 函式库  | [http://jquery.com/](http://jquery.com/)
Bootstrap | 前端框架  | [http://getbootstrap.com/](http://getbootstrap.com/)
Bootstrap-table | Bootstrap数据表格  | [http://bootstrap-table.wenzhixin.net.cn/](http://bootstrap-table.wenzhixin.net.cn/)
Editor.md | Markdown编辑器  | [https://github.com/pandao/editor.md](https://github.com/pandao/editor.md)
socket.io.js | SocketIO插件  | [https://socket.io/](https://socket.io/)



