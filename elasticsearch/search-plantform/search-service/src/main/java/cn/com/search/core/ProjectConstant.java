package cn.com.search.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String BASE_PACKAGE = "cn.com.search";//项目基础包名称，根据自己公司的项目修改
    
    public static final String REDIS_PRE = "search-service_";

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";//Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名
    
    
    public static final String TOKEN_HEADER_KEY_NAME = "token";//token在request header中的key
    public static final int TOKEN_OFFSET = 2*60*60;//token的有效时间 2小时
    public static final int CHECK_OFFSET= 60*5;	//验证码2分钟有效
    public static final int REFRESHTOKEN_OFFSET = 7*24*60*60;//refresh token的保存时间最长7天
    public static final int OLD_REFRESHTOKEN_OFFSET = 2*60;//在使用refresh token刷新token时，保存原来的refresh token的时间
    
    public static final int VERIFY_CODE_SIZE = 4;	//验证码长度
    
    /**
     * 统一线程池
     * 核心线程10个
     * 最大线程1000个
     */
    public final static ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 1000, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));
    
}
