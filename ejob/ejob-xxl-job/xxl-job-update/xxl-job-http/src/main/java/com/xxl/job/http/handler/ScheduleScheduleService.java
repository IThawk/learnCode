package com.xxl.job.http.handler;


public interface ScheduleScheduleService {

    /**
     * 延迟处理业务逻辑
     * @return
     */
    ScheduleResultResponse doScheduleDelay();

    /**
     * 日志埋点
     * @return
     */
    String getOthSerialNo();

    /**
     * 超过重试次数进行最后处理
     * @return
     */
    ScheduleResultResponse doFinalScheduleDelay();


    /**
     *首次进行最后处理
     * @return
     */
    ScheduleResultResponse doFirstScheduleDelay();
}
