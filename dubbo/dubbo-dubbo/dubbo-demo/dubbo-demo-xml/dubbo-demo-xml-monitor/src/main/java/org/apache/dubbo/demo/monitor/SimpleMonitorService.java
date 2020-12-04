package org.apache.dubbo.demo.monitor;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.monitor.MonitorService;

import java.util.List;

public class SimpleMonitorService implements MonitorService {
    @Override
    public void collect(URL statistics) {
        System.out.println("监控1。。。");
    }

    @Override
    public List<URL> lookup(URL query) {
        System.out.println("监控2。。。");
        return null;
    }
}
