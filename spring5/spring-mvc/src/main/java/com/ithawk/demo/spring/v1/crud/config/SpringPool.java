package com.ithawk.demo.spring.v1.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * 配置spring的线程池
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-11-25 20:28
 */
@Configuration
public class SpringPool {


    /**
     * <p>
     *     <!-- Spring线程池 -->
     *     <bean id="taskExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
     *         <!-- 核心线程数 -->
     *         <property name="corePoolSize" value="5" />
     *         <!-- 线程池维护线程的最大数量 -->
     *         <property name="maxPoolSize" value="10" />
     *         <!-- 允许的空闲时间, 默认60秒 -->
     *         <property name="keepAliveSeconds" value="60" />
     *         <!-- 任务队列 -->
     *         <property name="queueCapacity" value="50" />
     *         <!-- 线程超过空闲时间限制，均会退出直到线程数量为0 -->
     *         <property name="allowCoreThreadTimeOut" value="true"/>
     *         <!-- 对拒绝task的处理策略 -->
     *         <property name="rejectedExecutionHandler">
     *             <bean class="java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy" />
     *         </property>
     *     </bean>
     * </p>
     * @description: //TODO
     * @author IThawk
     * @date 2020/11/25 20:39
     * @param:
     * @return org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     */
    @Bean(value = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor makeThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setMaxPoolSize(6);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        return threadPoolTaskExecutor;
    }
}
