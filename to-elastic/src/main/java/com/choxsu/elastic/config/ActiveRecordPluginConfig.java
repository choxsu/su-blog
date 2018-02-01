package com.choxsu.elastic.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.choxsu.elastic.entity._MappingKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.sql.Connection;

/**
 * @author chox su
 * @date 2017/11/29 10:16
 */
@Configuration
public class ActiveRecordPluginConfig {

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    @Autowired
    private ActiveRecordPlugin activeRecordPlugin;

    @Bean
    public ActiveRecordPlugin initActiveRecordPlugin() {
        if (isExistsStart()){
            return null;
        }
        DruidPlugin druidPlugin = new DruidPlugin(url, username, password);
        // 加强数据库安全
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        druidPlugin.addFilter(wallFilter);
        // 添加 StatFilter 才会有统计数据
        druidPlugin.addFilter(new StatFilter());
        // 必须手动调用start
        druidPlugin.start();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        _MappingKit.mapping(arp);
        arp.setShowSql(true);
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.addSqlTemplate("/sql/all_sqls.sql");
        // 必须手动调用start
        arp.start();
        // test =========== begin //
        {
            Boolean value = Boolean.FALSE;
            if (value != null){
                System.out.println(value);
            }
        }
        {
            Boolean value = Boolean.TRUE;
            if (value != null){
                System.out.println(value);
            }
        }
        // test =========== end //
        return arp;
    }

    private boolean isExistsStart() {
        return activeRecordPlugin != null;
    }


}
