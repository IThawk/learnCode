package com.xkcoding.orm.mybatis.plus.task;

import com.xxl.job.admin.core.util.JacksonUtil;
import com.xxl.job.http.config.XxlJobBean;
import com.xxl.job.http.config.XxlJobMethod;
import com.xxl.job.http.util.ReturnT;

import java.util.Map;

@XxlJobBean("MyTask")
public class MyTask {


    /**
     * 无入参
     * @return ReturnT 返回信息会记录到定时任务的记录日志中
     */
    @XxlJobMethod(triggerStatus = 1)
    public ReturnT<String> main() {
        System.out.println("333333333333333333333333333333");
        return new ReturnT<>("定时任务请求OK");
    }

    /**
     * 有入参定时任务，只能是 POST ，支持多环境配置参数
     *
     * @param req
     * @return ReturnT 返回信息会记录到定时任务的记录日志中
     */
    @XxlJobMethod(triggerStatus = 1, method = "POST", reqBody = "h2:{\"a\":\"b\"}|dev:{\"a\":\"b\"}|uat:{\"c\":\"d\"}|prd:{\"e\":\"f\"}")
    public ReturnT<String> postMain(Map<String, Object> req) {
        System.out.println("333333333333333333333333333333" + JacksonUtil.writeValueAsString(req));
        return new ReturnT<>("post 定时任务请求OK");
    }

    @XxlJobMethod(triggerStatus = 1, method = "POST", reqBody = "{\"a1\":\"b1\"}")
    public ReturnT<String> postMain1(Map<String, Object> req) {
        System.out.println("333333333333333333333333333333" + JacksonUtil.writeValueAsString(req));
        return new ReturnT<>("post 定时任务请求OK ");
    }
}
