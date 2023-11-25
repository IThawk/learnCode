package com.xxl.job.http.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定义实现加载的配置类
 */
@ConfigurationProperties(prefix = XxlJobHttpProperties.HELLO_FORMAT_PREFIX)
public class XxlJobHttpProperties {

    public static final String HELLO_FORMAT_PREFIX = "xxl.job";

    private String jobWebUrl;

    private int jobGroup;

    private String scanPackage;

    private String baseUrl;

    private String executorIp;

    /*加密key*/
    private String key;


    public String getJobWebUrl() {
        return jobWebUrl;
    }

    public void setJobWebUrl(String jobWebUrl) {
        this.jobWebUrl = jobWebUrl;
    }

    public int getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(int jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getExecutorIp() {
        return executorIp;
    }

    public void setExecutorIp(String executorIp) {
        this.executorIp = executorIp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
