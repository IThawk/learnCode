package com.xxl.job.http.handler;


public abstract class AbstractScheduleScheduleService implements ScheduleScheduleService {

    /**
     * 超过重试次数精选最后处理
     *
     * @return
     */
    public ScheduleResultResponse doFinalScheduleDelay() {
        return new ScheduleResultResponse();
    }

    /**
     * 超过重试次数精选最后处理
     *
     * @return
     */
    public ScheduleResultResponse doFirstScheduleDelay() {
        return new ScheduleResultResponse();
    }
}
