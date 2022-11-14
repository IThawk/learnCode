package com.ithawk.demo.springboot.starter.autoconfiguration;


import com.ithawk.demo.springboot.starter.format.FormatProcessor;
import com.ithawk.demo.springboot.starter.format.JsonFormatProcessor;
import com.ithawk.demo.springboot.starter.format.StringFormatProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 定义实现类
 */
@Configuration
public class FormatAutoConfiguration {

    /**
     *
     * @return
     *
     */

    //metadata-auto....  如果没有com.alibaba.fastjson.JSON这个类，就默认加载stringFormat这个实体类
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    @Bean
    @Primary
    public FormatProcessor stringFormat(){
        return new StringFormatProcessor();
    }

    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
    @Bean
    public FormatProcessor jsonFormat(){
        return new JsonFormatProcessor();
    }

}
