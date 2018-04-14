package com.choxsu.elastic.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author chox su
 * @date 2017/12/21 11:27
 */


@Configuration
@Slf4j
@EnableElasticsearchRepositories(basePackages = {"com.choxsu.elastic.repository"})
public class ElasticsearchConfig {

    @Value("${elasticsearch.clusterName}")
    private String clusterName;
    @Value("${elasticsearch.hostNode}")
    private String host;

    @Bean
    public TransportClient transportClient() throws UnknownHostException {
        // 设置集群名称
        Settings settings = Settings
                .builder()
                .put("cluster.name", clusterName)
                .build();

        TransportClient transportClient = TransportClient
                .builder()
                .settings(settings)
                .build();

        String[] nodes = host.split(",");
        for (String node : nodes) {
            if (node.length() > 0) {
                String[] hostPort = node.split(":");
                InetAddress inetAddress = InetAddress.getByName(hostPort[0]);
                int port = Integer.parseInt(hostPort[1]);
                transportClient.addTransportAddress(new InetSocketTransportAddress(inetAddress, port));
            }
        }
        return transportClient;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(transportClient());
    }

}
