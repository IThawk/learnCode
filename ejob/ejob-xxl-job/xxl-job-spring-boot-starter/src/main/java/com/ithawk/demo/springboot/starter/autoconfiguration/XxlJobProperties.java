package com.ithawk.demo.springboot.starter.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 定义实现加载的配置类
 */
@ConfigurationProperties(prefix = XxlJobProperties.HELLO_FORMAT_PREFIX)
public class XxlJobProperties {

    public static final String HELLO_FORMAT_PREFIX = "xxl.job";

    private Map<String, Object> info;

    private Map<String, String> datasource;
    private Map<String, String> old;
    private Map<String, Object>  executor;

    private String i18n;
    private Map<String, Object> admin;

    private String logretentiondays;

    private Map<String, Object> triggerpool;

    private Map<String, Object> login;

    public Map<String, String> getDatasource() {
        if (CollectionUtils.isEmpty(old)){
            return datasource;
        }
        old.forEach((k,v)->{
            datasource.put(k,v);
        });
        return datasource;
    }

    public Map<String, String> getOld() {
        return old;
    }

    public void setOld(Map<String, String> old) {
        this.old = old;
    }

    public void setDatasource(Map<String, String> datasource) {
        this.datasource = datasource;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public String getLogretentiondays() {
        return logretentiondays;
    }

    public void setLogretentiondays(String logretentiondays) {
        this.logretentiondays = logretentiondays;
    }

    public Map<String, Object> getTriggerpool() {
        return triggerpool;
    }

    public void setTriggerpool(Map<String, Object> triggerpool) {
        this.triggerpool = triggerpool;
    }

    public Map<String, Object> getLogin() {
        return login;
    }

    public void setLogin(Map<String, Object> login) {
        this.login = login;
    }

    public Map<String, Object> getExecutor() {
        return executor;
    }

    public void setExecutor(Map<String, Object> executor) {
        this.executor = executor;
    }

    public Map<String, Object> getAdmin() {
        return admin;
    }

    public void setAdmin(Map<String, Object> admin) {
        this.admin = admin;
    }
}
