//package org.apache.ibatis.plugin;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CatMybatisPlusConfig {
//    private static final Logger logger = LoggerFactory.getLogger(CatMybatisPlusConfig.class);
//
//    public CatMybatisPlusConfig(){
//        logger.info("CatMybatisPlusConfig cons invoke...");
//    }
//
//    @Bean
//    public CatMybatisPlugin catMybatisPluginInterceptor() {
//        CatMybatisPlugin catMybatisPlugin = new CatMybatisPlugin();
//        return catMybatisPlugin;
//    }
//
//}
