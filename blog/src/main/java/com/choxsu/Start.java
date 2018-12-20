package com.choxsu;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.choxsu._admin.auth.AdminAuthKit;
import com.choxsu._admin.common.AdminRoutes;
import com.choxsu._admin.permission.PermissionDirective;
import com.choxsu._admin.role.RoleDirective;
import com.choxsu.common.entity._MappingKit;
import com.choxsu.common.handler.UrlSeoHandler;
import com.choxsu.common.interceptor.LoginSessionInterceptor;
import com.choxsu.common.pageview.VisitorInterceptor;
import com.choxsu.common.kit.DruidKit;
import com.choxsu.common.routes.ApiRoutes;
import com.choxsu.common.routes.FrontRoutes;
import com.jfinal.config.*;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;

import java.sql.Connection;

/**
 * @author choxsu
 */
public class Start extends JFinalConfig {


    /**
     * 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
     */
    private static Prop p;

    private WallFilter wallFilter;

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
            p = PropKit.use("su-blog-config-dev.properties").appendIfExists("su-blog-config-pro.properties");
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

        //me.addSharedFunction("/_view/_admin/common/__admin_layout.html");
        //me.addSharedFunction("/_view/_admin/common/_admin_paginate.html");
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
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new LoginSessionInterceptor());
        me.add(new VisitorInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(DruidKit.getFilterHandler("/druid"));
        me.add(new UrlSeoHandler());
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
    public void afterJFinalStart() {
        // 让 druid 允许在 sql 中使用 union
        wallFilter.getConfig().setSelectUnionCheck(false);
    }

    @Override
    public void beforeJFinalStop() {
        //停止之前执行
    }


    public static void main(String[] args) {
        UndertowServer.start(Start.class);
    }


}
