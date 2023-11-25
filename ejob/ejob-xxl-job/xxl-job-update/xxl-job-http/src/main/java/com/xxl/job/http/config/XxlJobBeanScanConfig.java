package com.xxl.job.http.config;

import com.xxl.job.http.handler.ScheduleResultResponse;
import com.xxl.job.http.handler.ScheduleScheduleService;
import com.xxl.job.http.handler.ScheduledThreadPoolExecutorTools;
import com.xxl.job.http.model.XxlJobInfo;
import com.xxl.job.http.util.ReturnT;
import com.xxl.job.http.util.XxlJobRemotingUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component
public class XxlJobBeanScanConfig implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobBeanScanConfig.class);
    @Autowired
    private XxlJobHttpProperties xxlJobHttpProperties;

    private ApplicationContext applicationContext;

    @Autowired
    private ScheduledThreadPoolExecutorTools scheduledThreadPoolExecutorTools;

    public String getActiveProfile() {
        String[] activeProfiles = getActiveProfiles();
        return !StringUtils.isEmpty(activeProfiles) ? activeProfiles[0] : "";
    }

    public String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    @PostConstruct
    public void xxlJobBeanScanConfig() {
        if (StringUtils.isEmpty(xxlJobHttpProperties.getScanPackage())) {
            return;
        }
        String env = getActiveProfile();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(XxlJobBean.class));
        Set<BeanDefinition> candidates = provider.findCandidateComponents(xxlJobHttpProperties.getScanPackage());
        for (BeanDefinition definition : candidates) {
            try {
                Class clz = Class.forName(definition.getBeanClassName());
                XxlJobBean xxlJobBean = (XxlJobBean) clz.getAnnotation(XxlJobBean.class);

                try {
                    Method[] methods = clz.getDeclaredMethods();

                    for (Method method : methods) {
                        if (!method.isAnnotationPresent(XxlJobMethod.class)) {
                            continue;
                        }
                        XxlJobMethod xxlJobMethod = method.getAnnotation(XxlJobMethod.class);
                        scheduledThreadPoolExecutorTools.scheduleCaller(new ScheduleScheduleService() {
                            @Override
                            public ScheduleResultResponse doScheduleDelay() {
                                try {
                                    XxlJobInfo xxlJobInfo = new XxlJobInfo();
                                    String taskName = StringUtils.isEmpty(xxlJobBean.value()) ?
                                            clz.getName() : xxlJobBean.value();
                                    String methodName = StringUtils.isEmpty(xxlJobMethod.value()) ?
                                            method.getName() : xxlJobBean.value();

                                    String reqMethod = StringUtils.isEmpty(xxlJobMethod.method()) ?
                                            "GET" : xxlJobMethod.method();

                                    String corn = StringUtils.isEmpty(xxlJobMethod.corn()) ?
                                            xxlJobBean.corn() : xxlJobMethod.corn();
                                    xxlJobInfo.setJobGroup(xxlJobHttpProperties.getJobGroup());
                                    xxlJobInfo.setJobCron(corn);
                                    xxlJobInfo.setJobDesc(taskName + "_" + methodName + "-" + env);
                                    xxlJobInfo.setExecutorHandler(xxlJobBean.executorHandler());
                                    xxlJobInfo.setAuthor("SYSTEM_SCAN");
                                    xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
                                    xxlJobInfo.setExecutorRouteStrategy("ROUND");

                                    String baseUrl = StringUtils.isEmpty(xxlJobMethod.baseUrl()) ?
                                            xxlJobBean.baseUrl() : xxlJobMethod.baseUrl();
                                    String params;
                                    if (StringUtils.isEmpty(baseUrl)) {
                                        params = "url:" + xxlJobHttpProperties.getBaseUrl() + "/xxlJob/" + taskName
                                                + "/" + methodName + "\n" + "method:" + reqMethod;
                                    } else {
                                        //多环境配置信息
                                        /*
                                         * dev:http://127.0.0.1:8080/test|uat:http://127.0.0.1:8080/test|prd:http://127.0.0.1:8080/test
                                         */
                                        if (baseUrl.contains("|")) {
                                            String[] urls = baseUrl.split("\\|");
                                            if (baseUrl.contains(env + ":")) {
                                                for (String url : urls) {
                                                    if (url.contains(env + ":")) {
                                                        baseUrl = url.split(env + ":")[1];
                                                        break;
                                                    }
                                                }
                                            } else {
                                                //防止配置出现问题
                                                baseUrl = xxlJobHttpProperties.getBaseUrl();
                                            }
                                        }

                                        params = "url:" + baseUrl + "/xxlJob/" + taskName + "/" + methodName + "\n" +
                                                "method:" + reqMethod;
                                    }
                                    if (!StringUtils.isEmpty(xxlJobMethod.reqBody())) {
                                        String reqBody = xxlJobMethod.reqBody();
                                        //多环境配置信息
                                        /*
                                         * dev:{"A":"B"}|uat:{"A":"B"}|prd:{"A":"B"}
                                         */
                                        if (xxlJobMethod.reqBody().contains("|")) {
                                            if (xxlJobMethod.reqBody().contains(env + ":")) {
                                                String[] reqBodys = xxlJobMethod.reqBody().split("\\|");

                                                for (String reqBody1 : reqBodys) {
                                                    if (reqBody1.contains(env + ":")) {
                                                        reqBody = reqBody1.split(env + ":")[1];
                                                        break;
                                                    }
                                                }
                                            } else {
                                                reqBody = "{}";
                                            }
                                        }
                                        params = params + "\n" + "data:" + reqBody;
                                    }
                                    xxlJobInfo.setExecutorParam(params);
                                    xxlJobInfo.setGlueType("BEAN");
                                    xxlJobInfo.setTriggerStatus(xxlJobMethod.triggerStatus());
                                    ReturnT<String> returnT = XxlJobRemotingUtil.postBody(
                                            xxlJobHttpProperties.getJobWebUrl() + "/jobinfo/initAdd",
                                            "", 5000, xxlJobInfo, String.class);
                                    logger.info("扫描定时任务" + taskName + "_" + methodName + "完成");
                                    return new ScheduleResultResponse();
                                } catch (Exception e) {
                                    logger.error("扫描定时任务:{}", ExceptionUtils.getStackTrace(e));
                                    return new ScheduleResultResponse(ScheduleResultResponse.ERROR_CODE, "执行失败");
                                }

                            }

                            @Override
                            public String getOthSerialNo() {
                                return null;
                            }

                            @Override
                            public ScheduleResultResponse doFinalScheduleDelay() {
                                return null;
                            }

                            @Override
                            public ScheduleResultResponse doFirstScheduleDelay() {
                                return null;
                            }


                        }, 1, 2, 1);

                    }
                } catch (InterruptedException e) {
                    logger.error("扫描定时任务完成:{}", ExceptionUtils.getStackTrace(e));
                } catch (ExecutionException e) {
                    logger.error("扫描定时任务完成:{}", ExceptionUtils.getStackTrace(e));
                }


            } catch (ClassNotFoundException e) {
                logger.error("扫描定时任务完成:{}", ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        /*
         * dev:http://127.0.0.1:8080/test|uat:http://127.0.0.1:8080/test|prd:http://127.0.0.1:8080/test
         */
        String baseUrl = "dev:http://127.0.0.1:8080/test|uat:http://127.0.0.1:8080/test|prd:http://127.0.0.1:8080/test";
        String env = "dev";
        if (baseUrl.contains("|")) {
            String[] urls = baseUrl.split("\\|");
            if (baseUrl.contains(env + ":")) {
                for (String url : urls) {
                    if (url.contains(env + ":")) {
                        baseUrl = url.split(env + ":")[1];
                        break;
                    }
                }
            }
        }

        System.out.println(baseUrl);
    }
}



