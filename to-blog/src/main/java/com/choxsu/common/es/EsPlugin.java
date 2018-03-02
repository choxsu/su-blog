package com.choxsu.common.es;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chox su
 * @date 2018/03/02 10:58
 */
@Slf4j
public class EsPlugin implements IPlugin {

    private String hosts;
    private String clusterName;
    private volatile boolean isStarted = false;
    private static TransportClient client = null;

    public EsPlugin(String hosts, String clusterName) {
        this.hosts = hosts;
        this.clusterName = clusterName;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public boolean start() {
        if (isStarted) {
            return true;
        }
        // 设置集群名称
        Settings settings = Settings
                .builder()
                .put("cluster.name", clusterName)
                .build();
        client = new PreBuiltTransportClient(settings);
        if (StrKit.isBlank(hosts)) {
            log.error("hosts:{} is not null", hosts);
            throw new NullPointerException("hosts is not null");
        }
        String[] nodes = hosts.split(",");
        try {
            for (String node : nodes) {
                if (node.length() > 0) {
                    String[] hostPort = node.split(":");
                    InetAddress inetAddress = InetAddress.getByName(hostPort[0]);
                    int port = Integer.parseInt(hostPort[1]);
                    client.addTransportAddress(new TransportAddress(inetAddress, port));
                }
            }
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        isStarted = true;
        return true;
    }

    @Override
    public boolean stop() {
        if (client != null) {
            client.close();
        }
        isStarted = false;
        return true;
    }

    public static TransportClient getClient() {
        return client;
    }
}
