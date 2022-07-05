package com.ithawk.demo.ejob.springboot.entity;

import lombok.Data;

@Data
public class JobConfigurationBean {

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


    private final String description;


    private boolean disabled;

    private boolean overwrite;

    private String label;

    private boolean staticSharding;
}