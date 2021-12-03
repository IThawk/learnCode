package ${basePackage};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
* @Class: ServiceApplication
* @Package
* @Description:
*/
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableOpenApi //swagger
@MapperScan("${basePackage}")
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }


}
