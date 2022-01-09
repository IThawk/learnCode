package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author qingshan
 */
@Component
public class LifeCycleJobHandler {
    private static Logger logger = LoggerFactory.getLogger(LifeCycleJobHandler.class);

    /**
     * 生命周期任务示例：任务初始化与销毁时，支持自定义相关逻辑；
     */
    @XxlJob(value = "demoJobHandler2", init = "myInit", destroy = "myDestroy")
    public ReturnT<String> demoJobHandler2(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        return ReturnT.SUCCESS;
    }
    public void myInit(){
        logger.info("【qingshan】init");
    }
    public void myDestroy(){
        logger.info("【qingshan】destory");
    }


}
