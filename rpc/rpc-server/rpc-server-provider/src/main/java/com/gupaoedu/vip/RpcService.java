package com.gupaoedu.vip;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */

@Target(ElementType.TYPE) //类/接口
@Retention(RetentionPolicy.RUNTIME)
@Component //被spring进行扫描？
public @interface RpcService {

    Class<?> value(); //拿到服务的接口

    /**
     * 版本号
     */
    String version() default "";

}
