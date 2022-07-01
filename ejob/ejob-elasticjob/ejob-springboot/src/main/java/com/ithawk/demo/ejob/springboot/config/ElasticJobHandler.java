package com.ithawk.demo.ejob.springboot.config;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobRegistry;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobScheduleController;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

@Component
public class ElasticJobHandler {

    @Autowired
    @Qualifier("setUpEventTraceDataSource")
    DataSource dataSource;

    @Autowired
    ZookeeperRegistryCenter zookeeperRegistryCenter;

    public void addJob(String jobName, String cron) {
        TracingConfiguration tracingConfiguration = new TracingConfiguration<>("RDB", dataSource);
        new ScheduleJobBootstrap(zookeeperRegistryCenter, new SimpleJob() {
            @Override
            public void execute(ShardingContext shardingContext) {
                System.out.printf("dddddd");
            }
        }, JobConfiguration.newBuilder(jobName, 1)
                .addExtraConfigurations(tracingConfiguration)
                .jobListenerTypes("myElasticJobListener").build()
        ).schedule();
    }

    public void updateJob(String jobName, String cron) {
        JobScheduleController jobScheduleController = JobRegistry.getInstance().getJobScheduleController(jobName);
        if (Objects.nonNull(jobScheduleController)) {
            jobScheduleController.scheduleJob(cron, "GMT+8:00");
        }

    }

    public void removeJob(String jobName) {
        JobScheduleController jobScheduleController = JobRegistry.getInstance().getJobScheduleController(jobName);
        if (Objects.nonNull(jobScheduleController)) {
            jobScheduleController.shutdown();
            zookeeperRegistryCenter.remove("/" + jobName);
        }
    }
}
