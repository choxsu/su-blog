package com.choxsu;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.choxsu._admin.auth.AdminAuthKit;
import com.choxsu._admin.common.AdminRoutes;
import com.choxsu._admin.permission.PermissionDirective;
import com.choxsu._admin.role.RoleDirective;
import com.choxsu.common.auto.AopControllerFactory;
import com.choxsu.common.base.dialect.BaseMysqlDialect;
import com.choxsu.common.entity._MappingKit;
import com.choxsu.common.es.EsPlugin;
import com.choxsu.common.interceptor.LoginSessionInterceptor;
import com.choxsu.common.interceptor.VisitorInterceptor;
import com.choxsu.common.kit.DruidKit;
import com.choxsu.common.routes.ApiRoutes;
import com.choxsu.common.routes.FrontRoutes;
import com.jfinal.config.*;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.server.undertow.UndertowConfig;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author choxsu
 */
public class Start extends JFinalConfig {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);


    /**
     * 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
     */
    private static Prop p = PropKit.appendIfExists("su_blog_config_pro.properties");

    private WallFilter wallFilter;

    @Override
    public void configConstant(Constants me) {
        logger.info("init constants");
        me.setDevMode(p.getBoolean("devMode", false));
        me.setJsonFactory(MixedJsonFactory.me());
//        me.setI18nDefaultBaseName("i18n");
        me.setControllerFactory(new AopControllerFactory());

    }

    @Override
    public void configRoute(Routes me) {

        logger.info("init route");
        me.add(new FrontRoutes());
        me.add(new AdminRoutes());
        me.add(new ApiRoutes());
    }

    @Override
    public void configEngine(Engine me) {
        //me.setBaseTemplatePath("webapp");
        //me.setToClassPathSourceFactory();
        logger.info("init config engine");
        me.setDevMode(true);
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
        logger.info("init plugins");
        DruidPlugin druidPlugin = getDruidPlugin();
        // 加强数据库安全
        wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        druidPlugin.addFilter(wallFilter);
        // 添加 StatFilter 才会有统计数据
        druidPlugin.addFilter(new StatFilter());
        me.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

        arp.setDialect(new BaseMysqlDialect());

        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        _MappingKit.mapping(arp);
        arp.setShowSql(p.getBoolean("devMode", false));
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.addSqlTemplate("/sql/all_sqls.sql");
        me.add(arp);
        me.add(new EsPlugin(p.get("elasticsearch_hosts"), p.get("cluster_name", "choxsu-cs")));
        me.add(new EhCachePlugin());


    }

    @Override
    public void configInterceptor(Interceptors me) {
        logger.info("init interceptor");
        me.add(new LoginSessionInterceptor());
        me.add(new VisitorInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        logger.info("init handler");
        me.add(DruidKit.getFilterHandler("/druid"));

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
        //TODO
        logger.info("jfinal start after action(jfinal启动完成之后执行)");
    }

    @Override
    public void beforeJFinalStop() {
        //TODO
        logger.info("jfinal start stop before(jfinal停止之前执行)");
    }


    public static void main(String[] args) {
        UndertowServer.start(Start.class, 8000, false);
    }


}
