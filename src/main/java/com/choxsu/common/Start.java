package com.choxsu.common;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.choxsu.common.entity.MappingKit;
import com.choxsu.common.interceptor.VisitorInterceptor;
import com.choxsu.common.interceptor.WebStatInterceptor;
import com.choxsu.common.kit.DruidKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
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
    private static Prop p = PropKit.use("sblog_config_dev.txt")
            .appendIfExists("sblog_config_pro.txt");

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
        me.addSharedFunction("/view/common/cy.html");
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
        MappingKit.mapping(arp);
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
        me.add(new WebStatInterceptor("*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"));
    }

    @Override
    public void configHandler(Handlers me) {
        System.out.println("初始化handler");
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
}
