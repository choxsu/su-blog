package com.choxsu.common.es;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.inject.Singleton;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chox su
 * @date 2018/03/02 10:03
 */
public class CommonUtil {

    private static CommonUtil commonUtil = null;

    private TransportClient client = null;

    private static Prop p = PropKit.use("sblog_config_dev.txt")
            .appendIfExists("sblog_config_pro.txt");

    private CommonUtil() {

    }

    public static CommonUtil newInstance() {
        if (commonUtil == null) {
            return new CommonUtil();
        }
        return commonUtil;
    }

    /**
     * 获取客户端
     *
     * @return TransportClient
     * @throws UnknownHostException host not find exception
     */
    private TransportClient getClient() throws UnknownHostException {
        if (client != null) {
            return client;
        }
        // 设置集群名称
        Settings settings = Settings
                .builder()
                .put("cluster.name", p.get("cluster_name", "choxsu-cs"))
                .build();
        client = new PreBuiltTransportClient(settings);
        String hosts = p.get("elasticsearch_hosts");
        if (StrKit.isBlank(hosts)) {
            throw new NullPointerException("hosts is not null");
        }
        String[] nodes = hosts.split(",");
        for (String node : nodes) {
            if (node.length() > 0) {
                String[] hostPort = node.split(":");
                InetAddress inetAddress = InetAddress.getByName(hostPort[0]);
                int port = Integer.parseInt(hostPort[1]);
                client.addTransportAddress(new TransportAddress(inetAddress, port));
            }
        }
        return client;

    }
}
