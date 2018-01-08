package com.choxsu.common;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.choxsu.common.entity.MapperKit;
import com.choxsu.common.interceptor.VisitorInterceptor;
import com.choxsu.common.kit.DruidKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
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
    private static Prop p = PropKit.use("sblog_config_dev.txt")
            .appendIfExists("sblog_config_pro.txt");
    private static final Log logger = Log.getLog(Start.class);

    private WallFilter wallFilter;


    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 8080, "/");

    }

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(p.getBoolean("devMode", false));
        me.setJsonFactory(MixedJsonFactory.me());
    }

    @Override
    public void configRoute(Routes me) {
        System.out.println("初始化路由");
        me.add(new FrontRoutes());
        me.add(new AdminRoutes());
    }

    @Override
    public void configEngine(Engine me) {
        System.out.println("初始化模板引擎");
        me.setDevMode(p.getBoolean("engineDevMode", false));
        me.addSharedFunction("/view/common/layout.html");
        me.addSharedFunction("/view/common/paginate.html");
    }

    @Override
    public void configPlugin(Plugins me) {
        System.out.println("初始化插件");
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
        MapperKit.mapper(arp);
        arp.setShowSql(p.getBoolean("devMode", false));
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.addSqlTemplate("/sql/all_sqls.sql");
        me.add(arp);

        me.add(new EhCachePlugin());


    }

    @Override
    public void configInterceptor(Interceptors me) {
        System.out.println("初始化拦截器");
        me.add(new VisitorInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        System.out.println("初始化handler");
        me.add(DruidKit.getFilterHandler("/assets/druid/"));

    }


    /**
     * 抽取成独立的方法，例于 _Generator 中重用该方法，减少代码冗余
     */
    public static DruidPlugin getDruidPlugin() {
        String url = p.get("jdbcUrl");
        String user = p.get("user");
        String password = p.get("password").trim();
        logger.info("============================================================");
        logger.info("url:" + url + "\nuser:" + user + "\npassword:" + password);
        logger.info("============================================================");
        return new DruidPlugin(url, user, password);
    }
}
