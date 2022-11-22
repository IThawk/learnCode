//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ithawk.ipfs.demo.config;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;


@Configuration
@ConditionalOnClass({Web3j.class})
@EnableConfigurationProperties({Web3jProperties.class})
public class Web3jAutoConfiguration {
    private static Log log = LogFactory.getLog(Web3jAutoConfiguration.class);
    @Autowired
    private Web3jProperties properties;

    public Web3jAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public Web3j web3j() {
        Web3jService web3jService = this.buildService(this.properties.getClientAddress());
        log.info("Building service for endpoint: " + this.properties.getClientAddress());
        return Web3j.build(web3jService);
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "web3j",
        name = {"admin-client"},
        havingValue = "true"
    )
    public Admin admin() {
        Web3jService web3jService = this.buildService(this.properties.getClientAddress());
        log.info("Building admin service for endpoint: " + this.properties.getClientAddress());
        return Admin.build(web3jService);
    }

    private Web3jService buildService(String clientAddress) {
        Object web3jService;
        if (clientAddress != null && !clientAddress.equals("")) {
            if (clientAddress.startsWith("http")) {
                web3jService = new HttpService(clientAddress, this.createOkHttpClient(), false);
            } else if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
                web3jService = new WindowsIpcService(clientAddress);
            } else {
                web3jService = new UnixIpcService(clientAddress);
            }
        } else {
            web3jService = new HttpService(this.createOkHttpClient());
        }

        return (Web3jService)web3jService;
    }

    private OkHttpClient createOkHttpClient() {
        Builder builder = new Builder();
        configureLogging(builder);
        this.configureTimeouts(builder);
        return builder.build();
    }

    private void configureTimeouts(Builder builder) {
        Long tos = this.properties.getHttpTimeoutSeconds();
        if (tos != null) {
            builder.connectTimeout(tos, TimeUnit.SECONDS);
            builder.readTimeout(tos, TimeUnit.SECONDS);
            builder.writeTimeout(tos, TimeUnit.SECONDS);
        }

    }

    private static void configureLogging(Builder builder) {
        if (log.isDebugEnabled()) {
            Log var10002 = log;
            var10002.getClass();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(var10002::debug);
            logging.setLevel(Level.BODY);
            builder.addInterceptor(logging);
        }

    }

    @Bean
    @ConditionalOnBean({Web3j.class})
    Web3jHealthIndicator web3jHealthIndicator(Web3j web3j) {
        return new Web3jHealthIndicator(web3j);
    }
}
