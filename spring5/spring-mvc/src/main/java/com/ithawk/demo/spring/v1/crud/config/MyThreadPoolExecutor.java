package com.ithawk.demo.spring.v1.crud.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

@Component
public class MyThreadPoolExecutor {

    private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    public void init() {
        threadPoolExecutor = new ThreadPoolExecutor(2, 5, 50,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20));
    }


    /**
     * 容器销毁
     */
    @PreDestroy
    public void destroy() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }
    }


    public Object execute(Callable<Object> callable) throws ExecutionException, InterruptedException {
        Future future = threadPoolExecutor.submit(callable);
        return future.get();
    }

    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);

    }
}
