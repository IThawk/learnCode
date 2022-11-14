package com.ithawk.demo.ejob.springboot.operate;


import com.ithawk.demo.ejob.springboot.entity.JobConfigurationBean;
import com.ithawk.demo.ejob.springboot.job.MySimpleJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: IThawk
 */
@RestController
public class JobController {


    @Autowired
    ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    TracingConfiguration tracingConfig;

    @Autowired
    JobStatisticsAPIImpl jobStatisticsAPI;


    @GetMapping("/job/all")
    public Collection<JobConfigurationBean> allJob() {
        return jobStatisticsAPI.getAllJobsBriefInfo();

    }

    @GetMapping("/job/{jobName:.+}")
    public JobConfigurationBean getJob(@PathVariable("jobName") final String jobName) {

        return jobStatisticsAPI.getJobBriefInfo(jobName);
    }

    @PostMapping("/job")
    public String addJob(@RequestBody JobConfigurationBean jobConfiguration) {


        JobConfiguration addJobConfiguration = JobConfiguration.newBuilder(jobConfiguration.getJobName(), jobConfiguration.getShardingTotalCount())
                .jobParameter(jobConfiguration.getJobParameter())
                .shardingItemParameters(jobConfiguration.getShardingItemParameters())
                .failover(jobConfiguration.isFailover())
                .misfire(jobConfiguration.isMisfire())
                .overwrite(jobConfiguration.isOverwrite())
                .addExtraConfigurations(tracingConfig)
                .jobListenerTypes("myElasticJobListener")
                .build();

        new ScheduleJobBootstrap(zookeeperRegistryCenter, new MySimpleJob(), addJobConfiguration).schedule();
        return "OK";
    }

    @DeleteMapping("/job/{jobName:.+}")
    public String deleteJob(@PathVariable("jobName") final String jobName) {
        jobStatisticsAPI.deleteJob(jobName);
        return "OK";
    }

}
