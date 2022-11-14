package cn.com.search.configurer;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 */
@Configuration
public class ESConfig {

    @Bean
    public TransportClient getClient(){
        TransportClient transportClient = null;
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name","my-es").build();
            transportClient = new PreBuiltTransportClient(settings);
            TransportAddress firstAddress = new TransportAddress(InetAddress.getByName("39.99.193.2"),Integer.parseInt("19300"));
            TransportAddress secondAddress = new TransportAddress(InetAddress.getByName("39.99.193.2"),Integer.parseInt("29300"));
            transportClient.addTransportAddress(firstAddress);
            transportClient.addTransportAddress(secondAddress);
        }catch (Exception e){
            e.printStackTrace();
        }
        return transportClient;
    }
}
