package com.ithawk.demo.ejob.springboot.operate;


import com.ithawk.demo.ejob.springboot.config.ElasticJobConfig;
import com.ithawk.demo.ejob.springboot.job.MySimpleJob;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: IThawk
 */
public class JobController {
@Autowired
private ElasticJobConfig elasticJobConfig;

@RequestMapping("/addJob")
    public void addJob() {
        int shardingTotalCount = 2;
         elasticJobConfig.addSimpleJobScheduler(new MySimpleJob().getClass(),"* * * * * ?",shardingTotalCount,"0=A,1=B");
    }

    @RequestMapping("/deleteJob")
    public void deleteJob() {
        int shardingTotalCount = 2;
        elasticJobConfig.addSimpleJobScheduler(new MySimpleJob().getClass(),"* * * * * ?",shardingTotalCount,"0=A,1=B");


        // 去除定时任务
        JobRegistry.getInstance().getJobScheduleController("f").shutdown();

        JobRegistry.getInstance().getJobScheduleController("f");
    }


}
