package com.xkcoding.orm.mybatis.plus.config;

import com.xkcoding.orm.mybatis.plus.entity.User;
import com.xkcoding.orm.mybatis.plus.service.UserService;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.http.handler.ScheduleResultResponse;
import com.xxl.job.http.handler.ScheduleScheduleService;
import com.xxl.job.http.handler.ScheduledThreadPoolExecutorTools;
import com.xxl.job.http.util.ReturnT;
import com.xxl.job.http.config.XxlJobHttpProperties;
import com.xxl.job.http.util.XxlJobRemotingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;

//**处理示例代码
//@Configuration
public class XxlJobHttpHandlerFromDbConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserService userService;
    @Autowired
    XxlJobInfoDao xxlJobInfoDao;

    @Autowired
    ScheduledThreadPoolExecutorTools scheduledThreadPoolExecutorTools;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        XxlJobHttpProperties xxlJobProperties = contextRefreshedEvent.getApplicationContext().getBean(XxlJobHttpProperties.class);

        if (!StringUtils.isEmpty(xxlJobProperties.getJobWebUrl())) {
            List<User> userList = userService.list();
            try {
                scheduledThreadPoolExecutorTools.scheduleCaller(new ScheduleScheduleService() {
                    @Override
                    public ScheduleResultResponse doScheduleDelay() {
                        XxlJobInfo xxlJobInfo = new XxlJobInfo();
                        xxlJobInfo.setJobGroup(xxlJobProperties.getJobGroup());
                        xxlJobInfo.setJobCron("0 * * * * ?");
                        xxlJobInfo.setJobDesc("测试自动添加");
                        xxlJobInfo.setExecutorHandler("httpJobHandler");
                        xxlJobInfo.setAuthor("XXL");

                        ReturnT<String> returnT = XxlJobRemotingUtil.postBody(xxlJobProperties.getJobWebUrl()+"/jobinfo/initAdd",
                                "", 5000, xxlJobInfo, String.class);
                        System.out.println("1111111111111111111");
                        return new ScheduleResultResponse();
                    }

                    @Override
                    public String getOthSerialNo() {
                        return null;
                    }

                    @Override
                    public ScheduleResultResponse doFinalScheduleDelay() {
                        return null;
                    }

                    @Override
                    public ScheduleResultResponse doFirstScheduleDelay() {
                        return null;
                    }


                }, 1, 2, 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }


            System.out.println("fffffffff");
        }

        System.out.println("其他容器后置处理");

    }
}
