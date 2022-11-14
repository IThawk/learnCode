package org.apache.dubbo.demo.monitor;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.monitor.MonitorService;

import java.util.Arrays;
import java.util.List;

public class SimpleMonitorService implements MonitorService {
    private URL statistics;

    @Override
    public void collect(URL statistics) {
        System.out.println("监控1。。。");
        this.statistics = statistics;
    }

    @Override
    public List<URL> lookup(URL query) {
        System.out.println("监控2。。。");
        return Arrays.asList(statistics);
    }
}
