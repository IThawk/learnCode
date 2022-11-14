package com.ithawk.demo.ejob.springboot.entity;

import com.ithawk.demo.ejob.springboot.common.JobStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class JobConfigurationBean implements Serializable, Comparable<JobConfigurationBean>  {

    private String jobName;

    private String cron;

    private String timeZone;

    private int shardingTotalCount;

    private String shardingItemParameters;

    private String jobParameter;

    private boolean monitorExecution;

    private boolean failover;

    private boolean misfire;

    private int maxTimeDiffSeconds;


    private  String description;


    private boolean disabled;

    private boolean overwrite;

    private String label;

    private boolean staticSharding;
    private JobStatus status;
    private int instanceCount;

    public JobConfigurationBean() {

    }

    @Override
    public int compareTo(final JobConfigurationBean o) {
        return getJobName().compareTo(o.getJobName());
    }

}