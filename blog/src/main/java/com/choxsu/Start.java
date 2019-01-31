package com.choxsu;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.choxsu._admin.auth.AdminAuthKit;
import com.choxsu._admin.permission.PermissionDirective;
import com.choxsu._admin.role.RoleDirective;
import com.choxsu.common.entity._MappingKit;
import com.choxsu.common.handler.UrlSeoHandler;
import com.choxsu.common.interceptor.LoginSessionInterceptor;
import com.choxsu.common.pageview.VisitorInterceptor;
import com.choxsu.common.redis.RedisClusterPlugin;
import com.choxsu.kit.DruidKit;
import com.choxsu.routes.AdminRoutes;
import com.choxsu.routes.ApiRoutes;
import com.choxsu.routes.FrontRoutes;
import com.jfinal.config.*;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.plugin.redis.serializer.JdkSerializer;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author choxsu
 */
public class Start extends JFinalConfig {


    /**
     * 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
     */
    private static Prop p;
    private WallFilter wallFilter;
    public static final String defaultName = "Redis";

    @Override
    public void configConstant(Constants me) {
        loadConfig();
        me.setDevMode(p.getBoolean("devMode", false));
        me.setJsonFactory(MixedJsonFactory.me());
        me.setInjectDependency(true);

    }

    // 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
    static void loadConfig() {
        if (p == null) {
            p = PropKit.use("chosu-blog-config-dev.properties").appendIfExists("chosu-blog-config-pro.properties");
        }
    }

    @Override
    public void configRoute(Routes me) {
        me.add(new FrontRoutes());
        me.add(new AdminRoutes());
        me.add(new ApiRoutes());
    }

    @Override
    public void configEngine(Engine me) {
        me.setDevMode(p.getBoolean("engineDevMode", false));
        me.addSharedFunction("/_view/common/layout.html");
        me.addSharedFunction("/_view/common/_paginate.html");

        me.addDirective("role", RoleDirective.class);
        me.addDirective("permission", PermissionDirective.class);
        me.addDirective("perm", PermissionDirective.class);        // 配置一个别名指令

        // 添加角色、权限 shared method
        me.addSharedMethod(AdminAuthKit.class);

        me.addSharedFunction("/_view/_admin/common/__admin_layout.html");
        me.addSharedFunction("/_view/_admin/common/_admin_paginate.html");
    }

    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin druidPlugin = getDruidPlugin();
        // 加强数据库安全
        wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        druidPlugin.addFilter(wallFilter);
        // 添加 StatFilter 才会有统计数据
        druidPlugin.addFilter(new StatFilter());
        me.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        _MappingKit.mapping(arp);
        arp.setShowSql(p.getBoolean("devMode", false));
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.addSqlTemplate("/sql/all_sqls.sql");
        me.add(arp);
        //缓存插件
        me.add(new EhCachePlugin());
        //定时任务
        me.add(new Cron4jPlugin(p));
        //Redis缓存
        RedisPlugin redisPlugin = getRedisPlugin();
        redisPlugin.setSerializer(new JdkSerializer());
        me.add(redisPlugin);
    }

    private RedisPlugin getRedisPlugin() {
        String host = p.get("redis.host");
        int port = p.getInt("redis.port");
        String password = p.get("redis.password").trim();
        return new RedisPlugin(defaultName, host, port, 300000, password);
    }

    private void setJedisCluster(Plugins me) {
        //配置集群节点
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort(p.get("redis.host"), p.getInt("redis.port")));
        //配置连接池，最大连接数等...
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setLifo(true);
        poolConfig.setBlockWhenExhausted(true);
        // 创建插件对象replicas
        //test 自定义的集合的名字  1212 密码 需要所有节点的密码一致  30000超时时间   6最多重定向次数
        RedisClusterPlugin redisClusterPlugin = new RedisClusterPlugin("test", jedisClusterNode, p.get("redis.password"), 30000, 6,
                poolConfig);
        me.add(redisClusterPlugin);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new LoginSessionInterceptor());
        me.add(new VisitorInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(DruidKit.getDruidStatViewHandler()); // druid 统计页面功能
        me.add(new UrlSeoHandler()); //文章列表 和 详情的SEO优化
    }


    /**
     * 抽取成独立的方法，例于 _Generator 中重用该方法，减少代码冗余
     */
    public static DruidPlugin getDruidPlugin() {
        String url = p.get("jdbcUrl");
        String user = p.get("user");
        String password = p.get("password").trim();
        return new DruidPlugin(url, user, password);
    }

    @Override
    public void onStart() {
        // 让 druid 允许在 sql 中使用 union
        wallFilter.getConfig().setSelectUnionCheck(false);
    }


    @Override
    public void onStop() { }

    public static void main(String[] args) {
        UndertowServer.start(Start.class);
    }


}
