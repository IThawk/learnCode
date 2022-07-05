package com.ithawk.demo.ejob.springboot.operate;


import com.ithawk.demo.ejob.springboot.config.ElasticJobConfig;
import com.ithawk.demo.ejob.springboot.entity.JobConfigurationBean;
import com.ithawk.demo.ejob.springboot.job.MySimpleJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobRegistry;
import org.apache.shardingsphere.elasticjob.lite.internal.storage.JobNodePath;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: IThawk
 */
public class JobController {


    @Autowired
    ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    TracingConfiguration tracingConfig;
    @PostMapping("/addJob")
    public String addJob(@RequestBody JobConfigurationBean jobConfiguration) {


        JobConfiguration addJobConfiguration = JobConfiguration.newBuilder(jobConfiguration.getJobName(),jobConfiguration.getShardingTotalCount())
                .jobParameter(jobConfiguration.getJobParameter())
                .shardingItemParameters(jobConfiguration.getShardingItemParameters())
                .failover(jobConfiguration.isFailover())
                .misfire(jobConfiguration.isMisfire())
                .overwrite(jobConfiguration.isOverwrite())
                .addExtraConfigurations(tracingConfig)
                .build();

        new ScheduleJobBootstrap(zookeeperRegistryCenter,new MySimpleJob(), addJobConfiguration).schedule();
        return "OK";
    }

    @DeleteMapping("/{jobName:.+}")
    public String deleteJob(@PathVariable("jobName") final String jobName) {
        disableOrEnableJobs(jobName,false);
        // 去除定时任务
        JobRegistry.getInstance().getJobScheduleController(jobName).shutdown();

        zookeeperRegistryCenter.remove("/"+jobName);
        return "OK";
    }

    private void disableOrEnableJobs(final String jobName,  final boolean disabled) {
        JobNodePath jobNodePath = new JobNodePath(jobName);
        String shardingDisabledNodePath = jobNodePath.getServerNodePath();

        if (disabled) {
            zookeeperRegistryCenter.persist(shardingDisabledNodePath, "");
        } else {
            zookeeperRegistryCenter.remove(shardingDisabledNodePath);
        }
    }

    private void disableOrEnableJobs(final String jobName, final String item, final boolean disabled) {
        JobNodePath jobNodePath = new JobNodePath(jobName);
        String shardingDisabledNodePath = jobNodePath.getShardingNodePath(item, "disabled");

        if (disabled) {
            zookeeperRegistryCenter.persist(shardingDisabledNodePath, "");
        } else {
            zookeeperRegistryCenter.remove(shardingDisabledNodePath);
        }
    }
//
//    /**
//     * Remove job configuration.
//     *
//     * @param jobName job name
//     */
//    @DeleteMapping("/{jobName:.+}")
//    public ResponseResult<Boolean> removeJob(@PathVariable("jobName") final String jobName) {
//        jobAPIService.getJobConfigurationAPI().removeJobConfiguration(jobName);
//        return "OK";
//    }


}
