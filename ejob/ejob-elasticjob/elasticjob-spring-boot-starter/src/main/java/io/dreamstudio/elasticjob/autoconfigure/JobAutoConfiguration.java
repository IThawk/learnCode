package io.dreamstudio.elasticjob.autoconfigure;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.script.ScriptJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import io.dreamstudio.elasticjob.annotation.JobScheduled;
import io.dreamstudio.elasticjob.annotation.EnableElasticJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ricky Fung
 */
@Configuration
@ConditionalOnClass({SimpleJob.class, DataflowJob.class})
@ConditionalOnBean(annotation = EnableElasticJob.class)
@AutoConfigureAfter(RegCentreAutoConfiguration.class)
public class JobAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String prefix = "elasticjob";

    @Autowired
    private CoordinatorRegistryCenter registryCenter;

    @Autowired
    private ApplicationContext applicationContext;

    private Environment environment;

    private final AtomicInteger counter = new AtomicInteger();

    @PostConstruct
    public void init() throws Exception {
        this.environment = this.applicationContext.getEnvironment();
        logger.info("scan JobScheduled Annotation start");
        //扫描目标类
        Map<String, Object> beanMap = this.applicationContext.getBeansWithAnnotation(JobScheduled.class);
        if (beanMap != null && beanMap.size() > 0) {
            for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
                this.initElasticJobBean(entry.getKey(), entry.getValue());
            }
        }
    }

    private void initElasticJobBean(String beanName, Object bean) throws BeansException {
        Class<?> clz = bean.getClass();
        JobScheduled jobScheduled = clz.getAnnotation(JobScheduled.class);

        String jobClass = clz.getName();
        String jobName = jobScheduled.jobName();
        String cron = getEnvironmentStringValue(jobName, "cron", jobScheduled.cron());

        int shardingTotalCount = getEnvironmentIntValue(jobName, "shardingTotalCount", jobScheduled.shardingTotalCount());
        String shardingItemParameters = getEnvironmentStringValue(jobName, "shardingItemParameters", jobScheduled.shardingItemParameters());
        String jobParameter = getEnvironmentStringValue(jobName, "jobParameter", jobScheduled.jobParameter());

        String description = getEnvironmentStringValue(jobName, "description", jobScheduled.description());

        String jobShardingStrategyClass = getEnvironmentStringValue(jobName, "jobShardingStrategyClass", jobScheduled.jobShardingStrategyClass());
        String scriptCommandLine = getEnvironmentStringValue(jobName, "scriptCommandLine", jobScheduled.scriptCommandLine());

        boolean failover = getEnvironmentBooleanValue(jobName, "failover", jobScheduled.failover());
        boolean misfire = getEnvironmentBooleanValue(jobName, "misfire", jobScheduled.misfire());
        boolean overwrite = getEnvironmentBooleanValue(jobName, "overwrite", jobScheduled.overwrite());
        boolean disabled = getEnvironmentBooleanValue(jobName, "disabled", jobScheduled.disabled());
        boolean monitorExecution = getEnvironmentBooleanValue(jobName, "monitorExecution", jobScheduled.monitorExecution());
        boolean streamingProcess = getEnvironmentBooleanValue(jobName, "streamingProcess", jobScheduled.streamingProcess());

        int monitorPort = getEnvironmentIntValue(jobName, "monitorPort", jobScheduled.monitorPort());
        int maxTimeDiffSeconds = getEnvironmentIntValue(jobName, "maxTimeDiffSeconds", jobScheduled.maxTimeDiffSeconds());

        // 核心配置
        JobCoreConfiguration coreConfig =
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters)
                        .description(description)
                        .failover(failover)
                        .jobParameter(jobParameter)
                        .misfire(misfire)
                        .build();

        // 不同类型的任务配置处理
        LiteJobConfiguration jobConfig = null;
        JobTypeConfiguration typeConfig = null;
        if (SimpleJob.class.isAssignableFrom(clz)) {
            typeConfig = new SimpleJobConfiguration(coreConfig, jobClass);
        } else if (DataflowJob.class.isAssignableFrom(clz)) {
            typeConfig = new DataflowJobConfiguration(coreConfig, jobClass, streamingProcess);
        } else if (ScriptJob.class.isAssignableFrom(clz)) {
            typeConfig = new ScriptJobConfiguration(coreConfig, scriptCommandLine);
        }

        jobConfig = LiteJobConfiguration.newBuilder(typeConfig)
                .overwrite(overwrite)
                .disabled(disabled)
                .monitorPort(monitorPort)
                .monitorExecution(monitorExecution)
                .maxTimeDiffSeconds(maxTimeDiffSeconds)
                .jobShardingStrategyClass(jobShardingStrategyClass)
                .build();

        // 构建SpringJobScheduler对象来初始化任务
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
        factory.setInitMethodName("init");
        factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        if (ScriptJob.class.isAssignableFrom(clz)) {
            factory.addConstructorArgValue(null);
        } else {
            factory.addConstructorArgValue(bean);
        }
        factory.addConstructorArgValue(registryCenter);
        factory.addConstructorArgValue(jobConfig);
        factory.addConstructorArgValue(Collections.emptyList());

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        String jobSchedulerBeanName = String.format("%s_Scheduler_%s", jobClass, counter.incrementAndGet());
        while (beanFactory.containsBeanDefinition(jobSchedulerBeanName)) {
            jobSchedulerBeanName = String.format("%s_Scheduler_%s", jobClass, counter.incrementAndGet());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Add JobScheduler bean:{} for job:{}", jobSchedulerBeanName, jobClass);
        }
        beanFactory.registerBeanDefinition(jobSchedulerBeanName, factory.getBeanDefinition());
        beanFactory.getBean(jobSchedulerBeanName);
    }


    /**
     * 获取配置中的任务属性值，environment没有就用注解中的值
     * @param jobName		任务名称
     * @param fieldName		属性名称
     * @param defaultValue  默认值
     * @return
     */
    private String getEnvironmentStringValue(String jobName, String fieldName, String defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return value;
        }
        return defaultValue;
    }

    private int getEnvironmentIntValue(String jobName, String fieldName, int defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return Integer.valueOf(value);
        }
        return defaultValue;
    }

    private long getEnvironmentLongValue(String jobName, String fieldName, long defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return Long.valueOf(value);
        }
        return defaultValue;
    }

    private boolean getEnvironmentBooleanValue(String jobName, String fieldName, boolean defaultValue) {
        String key = prefix + jobName + "." + fieldName;
        String value = environment.getProperty(key);
        if (StringUtils.hasText(value)) {
            return Boolean.valueOf(value);
        }
        return defaultValue;
    }

}
