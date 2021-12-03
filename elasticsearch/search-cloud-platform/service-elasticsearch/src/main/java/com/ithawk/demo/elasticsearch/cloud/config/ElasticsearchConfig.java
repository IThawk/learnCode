package com.ithawk.demo.elasticsearch.cloud.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Class: ElasticsearchConfig
 * @Package com.itheima.config
 * @Description: 配置类：构建Elasticsearch客户端连接配置
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch") //读取配置文件：elasticsearch 下的配置
public class ElasticsearchConfig {
    //es集群ip
    private String cluster_host;
    //es集群节点一端口
    private Integer eNode1_port;
//    //es集群节点二端口
//    private Integer eNode2_port;
//    //es集群节点三端口
//    private Integer eNode3_port;
    /**
     * 超时时间设为5分钟
     */
    private static final int TIME_OUT = 5 * 60 * 1000;
    /*
     * @Description:
     * @Method: Es高阶客户端构建器
     * @Param:
     * @Update:
     * @since: 1.0.0
     * @Return:
     *
     */

    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(
                new HttpHost(cluster_host, eNode1_port, "http")
//                new HttpHost(cluster_host, eNode2_port, "http"),
//                new HttpHost(cluster_host, eNode3_port, "http")
        );

    }

    /*
     * @Description: 构建Es高阶客户端
     * @Method: highLevelClient
     * @Param: [restClientBuilder]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.client.RestHighLevelClient
     *
     */
    @Bean(destroyMethod = "close")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        restClientBuilder.setRequestConfigCallback(
                new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(
                            RequestConfig.Builder requestConfigBuilder) {
                        return requestConfigBuilder.setSocketTimeout(TIME_OUT);
                    }
                });

        return new RestHighLevelClient(restClientBuilder);
    }

    public String getCluster_host() {
        return cluster_host;
    }

    public void setCluster_host(String cluster_host) {
        this.cluster_host = cluster_host;
    }

    public Integer geteNode1_port() {
        return eNode1_port;
    }

    public void seteNode1_port(Integer eNode1_port) {
        this.eNode1_port = eNode1_port;
    }

//    public Integer geteNode2_port() {
//        return eNode2_port;
//    }
//
//    public void seteNode2_port(Integer eNode2_port) {
//        this.eNode2_port = eNode2_port;
//    }
//
//    public Integer geteNode3_port() {
//        return eNode3_port;
//    }
//
//    public void seteNode3_port(Integer eNode3_port) {
//        this.eNode3_port = eNode3_port;
//    }

}
