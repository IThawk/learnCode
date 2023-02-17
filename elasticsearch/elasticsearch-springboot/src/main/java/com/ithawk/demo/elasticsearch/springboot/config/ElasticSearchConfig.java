package com.ithawk.demo.elasticsearch.springboot.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class ElasticSearchConfig {

    private List<String> uris;

    @Value("${spring.elasticsearch.rest.test}")
    public String test;

    @Value("#{'${spring.elasticsearch.rest.urls}'.split(',')}")
    public List<String> uris1;
    @Autowired
    ElasticsearchRestClientProperties elasticsearchRestClientProperties;
    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    /**
     * 自定义elasticsearch的查询client
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(ElasticsearchRestClientProperties.class)
    public RestHighLevelClient esRestClient() {
        uris = elasticsearchRestClientProperties.getUris();
        List<HttpHost> httpHosts = IntStream.range(0, uris.size())
                .mapToObj(i -> {
                    String[] s = uris.get(i).split("://");
                    String scheme = s[0];
                    String[] s1 = s[1].split(":");
                    String host = s1[0];
                    int port = Integer.parseInt(s1[1]);
                    return new HttpHost(host, port, scheme);
                }).collect(Collectors.toList());

        return new RestHighLevelClient(
                RestClient.builder(httpHosts.stream().toArray(HttpHost[]::new)));
    }
}
