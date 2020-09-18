package io.dreamstudio.elasticjob.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Ricky Fung
 */
@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JobScheduled {

    /**
     * 作业名称
     * @return
     */
    String jobName();

    /**
     * cron表达式，用于控制作业触发时间
     * @return
     */
    String cron() default "";

    /**
     * 作业分片总数
     * @return
     */
    int shardingTotalCount() default 1;

    /**
     * 分片序列号和参数用等号分隔，多个键值对用逗号分隔
     * <p>分片序列号从0开始，不可大于或等于作业分片总数<p>
     * <p>如：<p>
     * <p>0=a,1=b,2=c<p>
     * @return
     */
    String shardingItemParameters() default "";

    /**
     * 作业自定义参数
     * <p>作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业<p>
     * <p>例：每次获取的数据量、作业实例从数据库读取的主键等<p>
     * @return
     */
    String jobParameter() default "";

    /**
     * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
     * @return
     */
    boolean failover() default false;

    /**
     * 是否开启错过任务重新执行
     * @return
     */
    boolean misfire() default false;

    /**
     * 作业是否禁止启动,可用于部署作业时，先禁止启动，部署结束后统一启动
     * @return
     */
    boolean disabled() default false;

    boolean overwrite() default false;

    /**
     * 作业描述信息
     * @return
     */
    String description() default "";

    boolean streamingProcess() default false;

    String scriptCommandLine() default "";

    boolean monitorExecution() default true;

    int monitorPort() default -1;

    int maxTimeDiffSeconds() default -1;

    String jobShardingStrategyClass() default "";


}
