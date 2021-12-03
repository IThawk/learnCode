package com.ithawk.demo.elasticsearch.cloud.analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.spi.LoggerContextShutdownAware;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Class: AnalysisApplication
 * @Package com
 * @Description: 分析入口
 * @Company:
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
@EnableOpenApi //swagger
public class AnalysisApplication {
    /**
     * @Description全文检索入口
     * @Param  [args]
     * @Date
     * @Return      void
     * @Exception
     *
     */
    public static void main(String[] args) {
        SpringApplication.run(AnalysisApplication.class, args);
    }


}
