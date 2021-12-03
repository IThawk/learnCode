package com.search.mgr.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String BASE_PACKAGE = "com.search.mgr";//项目基础包名称，根据自己公司的项目修改
    
    public static final String REDIS_PRE = "search-mgr_";

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";//Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名
    
    /**
     * 统一线程池
     * 核心线程10个
     * 最大线程1000个
     */
    static BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1000);
    public final static ThreadPoolExecutor executorService = 
    		new ThreadPoolExecutor(10, 100, 100l, TimeUnit.SECONDS, blockingQueue);
    
}
