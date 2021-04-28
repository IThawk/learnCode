package com.ithawk.demo.springboot.starterdemo.jmxdemo;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class JMXMain {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, IOException {
        MBeanServer beanServer= ManagementFactory.getPlatformMBeanServer();
        ObjectName on=new ObjectName("com.gupaoedu.example.springbootstarterdemo.jmxdemo.Mechine:type=mechine");
        MechineMBean mechineMBean=new Mechine();
        beanServer.registerMBean(mechineMBean,on);
        System.in.read();
    }
}
