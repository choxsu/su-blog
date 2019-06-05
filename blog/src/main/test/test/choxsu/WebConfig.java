package test.choxsu;

import com.jfinal.plugin.druid.DruidPlugin;

public class WebConfig {


    public static DruidPlugin createDruidPlugin() {
        String url = "jdbc:mysql://git.yuansuju.com:3306/dmo?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useSSL=false";
        String username = "root";
        String password = "Xihe123456";
        return new DruidPlugin(url, username, password);
    }
}
