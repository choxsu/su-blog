package com.choxsu.common;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.choxsu.common.entity.MappingKit;
import com.choxsu.common.es.CommonUtil;
import com.choxsu.common.es.EsPlugin;
import com.choxsu.common.interceptor.VisitorInterceptor;
import com.choxsu.common.interceptor.WebStatInterceptor;
import com.choxsu.web.admin.common.interceptor.ShiroInterceptor;
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
public class StartConfig extends JFinalConfig {

    private static final Logger logger = LoggerFactory.getLogger(StartConfig.class);

    /**
     * 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
     */
    private static Prop p = PropKit.use("sblog_config_dev.txt")
            .appendIfExists("sblog_config_pro.txt");

    private WallFilter wallFilter;


    public static void main(String[] args) {
        logger.info("程序开始 启动中...");
        logger.info("启动webAppDir:{}", "to-blog/src/main/webapp");
        logger.info("启动端口：{}", 8080);
        logger.info("上下文路径context:{}", "/");
        JFinal.start("to-blog/src/main/webapp", 8080, "/");
    }

    @Override
    public void configConstant(Constants me) {
        logger.info("init constants");
        me.setDevMode(p.getBoolean("devMode", false));
        me.setJsonFactory(MixedJsonFactory.me());
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
        logger.info("init config engine");
        me.setDevMode(p.getBoolean("engineDevMode", false));
        me.addSharedFunction("/view/common/layout.html");
        me.addSharedFunction("/view/common/paginate.html");
        me.addSharedFunction("/view/common/cy.html");
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
        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        MappingKit.mapping(arp);
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
        me.add(new VisitorInterceptor());
        me.add(new WebStatInterceptor("*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"));
        me.add(new ShiroInterceptor());
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
}
