package com.ithawk.demo.springboot.starterdemo.jmxdemo;

public class Mechine implements MechineMBean{


    @Override
    public int getCpuCore() {

        return Runtime.getRuntime().availableProcessors();
    }

    @Override
    public long getFreeMemory() {

        return Runtime.getRuntime().freeMemory();

    }

    @Override
    public void shutdown() {
        System.exit(0);
    }
}
