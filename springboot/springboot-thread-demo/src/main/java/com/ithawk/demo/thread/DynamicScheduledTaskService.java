package com.ithawk.demo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DynamicScheduledTaskService {

    private static final Logger log = LoggerFactory.getLogger(DynamicScheduledTaskService.class);
    private final DynamicScheduledTaskRegistrar dynamicScheduledTaskRegistrar = new DynamicScheduledTaskRegistrar();
    /**
     * 新增任务
     * @param taskName
     * @param cron
     */
    public void add(String taskName,String cron){
        Boolean result = dynamicScheduledTaskRegistrar.addCronTask(taskName,cron,() -> print(taskName));
        log.info("定时任务添加结果：" + result);
    }
    /**
     * 取消任务
     * @param taskName
     */
    public void cancel(String taskName){
        dynamicScheduledTaskRegistrar.cancelCronTask(taskName);
    }
    private void print(String taskName){
        log.info(taskName+"开始");
        try{
            Thread.sleep(9000L);
            log.info(taskName+"结束111");
        }catch (Exception ex){
        }
        log.info(taskName+"结束");
    }
}
