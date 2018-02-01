package com.choxsu.elastic.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chox su
 * @date 2017/12/21 11:27
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;
    @Value("${elasticsearch.host}")
    private String host;

    @Bean
    public TransportClient transportClient() throws UnknownHostException {
        // 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", clusterName)
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        String[] nodes = host.split(",");
        for (String node : nodes) {
            if (node.length() > 0) {
                String[] hostPort = node.split(":");
                InetAddress inetAddress = InetAddress.getByName(hostPort[0]);
                int port = Integer.parseInt(hostPort[1]);
                transportClient.addTransportAddress(new TransportAddress(inetAddress, port));
            }
        }
        return transportClient;
    }

}
