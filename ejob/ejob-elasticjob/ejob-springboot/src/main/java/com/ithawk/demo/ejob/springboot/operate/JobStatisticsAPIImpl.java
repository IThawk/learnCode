package com.ithawk.demo.ejob.springboot.operate;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.ithawk.demo.ejob.springboot.common.JobStatus;
import com.ithawk.demo.ejob.springboot.entity.JobConfigurationBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.infra.handler.sharding.JobInstance;
import org.apache.shardingsphere.elasticjob.infra.pojo.JobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.lite.internal.instance.InstanceService;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobRegistry;
import org.apache.shardingsphere.elasticjob.lite.internal.server.ServerStatus;
import org.apache.shardingsphere.elasticjob.lite.internal.storage.JobNodePath;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.infra.yaml.YamlEngine;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Job statistics API implementation class.
 */
@Component
@Slf4j
public final class JobStatisticsAPIImpl  {

    private final CoordinatorRegistryCenter regCenter;

    public JobStatisticsAPIImpl(CoordinatorRegistryCenter regCenter) {
        this.regCenter = regCenter;
    }



    public int getJobsTotalCount() {
        return regCenter.getChildrenKeys("/").size();
    }


    public Collection<JobConfigurationBean> getAllJobsBriefInfo() {
        List<String> jobNames = regCenter.getChildrenKeys("/");
        List<JobConfigurationBean> result = new ArrayList<>(jobNames.size());
        for (String each : jobNames) {
            JobConfigurationBean jobBriefInfo = getJobBriefInfo(each);
            if (null != jobBriefInfo) {
                result.add(jobBriefInfo);
            }
        }
        Collections.sort(result);
        return result;
    }


    public JobConfigurationBean getJobBriefInfo(final String jobName) {
        JobNodePath jobNodePath = new JobNodePath(jobName);
        JobConfigurationBean result = new JobConfigurationBean();
        result.setJobName(jobName);
        String jobConfigYaml = regCenter.get(jobNodePath.getConfigNodePath());
        if (null == jobConfigYaml) {
            return null;
        }
        JobConfiguration jobConfig = YamlEngine.unmarshal(jobConfigYaml, JobConfigurationPOJO.class).toJobConfiguration();

        result.setDescription(jobConfig.getDescription());
        result.setCron(jobConfig.getCron());
        result.setInstanceCount(getJobInstanceCount(jobName));
        result.setShardingTotalCount(jobConfig.getShardingTotalCount());
        result.setStatus(getJobStatus(jobName));
        return result;
    }

    private JobStatus getJobStatus(final String jobName) {
        JobNodePath jobNodePath = new JobNodePath(jobName);
        List<String> instances = regCenter.getChildrenKeys(jobNodePath.getInstancesNodePath());
        if (instances.isEmpty()) {
            return JobStatus.CRASHED;
        }
        if (isAllDisabled(jobNodePath)) {
            return JobStatus.DISABLED;
        }
        if (isHasShardingFlag(jobNodePath, instances)) {
            return JobStatus.SHARDING_FLAG;
        }
        return JobStatus.OK;
    }

    private boolean isAllDisabled(final JobNodePath jobNodePath) {
        List<String> serversPath = regCenter.getChildrenKeys(jobNodePath.getServerNodePath());
        int disabledServerCount = 0;
        for (String each : serversPath) {
            if (JobStatus.DISABLED.name().equals(regCenter.get(jobNodePath.getServerNodePath(each)))) {
                disabledServerCount++;
            }
        }
        return disabledServerCount == serversPath.size();
    }

    private boolean isHasShardingFlag(final JobNodePath jobNodePath, final List<String> instances) {
        Set<String> shardingInstances = new HashSet<>();
        for (String each : regCenter.getChildrenKeys(jobNodePath.getShardingNodePath())) {
            String instanceId = regCenter.get(jobNodePath.getShardingNodePath(each, "instance"));
            if (null != instanceId && !instanceId.isEmpty()) {
                shardingInstances.add(instanceId);
            }
        }
        return !instances.containsAll(shardingInstances) || shardingInstances.isEmpty();
    }

    private int getJobInstanceCount(final String jobName) {
        return regCenter.getChildrenKeys(new JobNodePath(jobName).getInstancesNodePath()).size();
    }


    public Collection<JobConfigurationBean> getJobsBriefInfo(final String ip) {
        List<String> jobNames = regCenter.getChildrenKeys("/");
        List<JobConfigurationBean> result = new ArrayList<>(jobNames.size());
        for (String each : jobNames) {
            JobConfigurationBean jobBriefInfo = getJobBriefInfoByJobNameAndIp(each, ip);
            if (null != jobBriefInfo) {
                result.add(jobBriefInfo);
            }
        }
        Collections.sort(result);
        return result;
    }

    private JobConfigurationBean getJobBriefInfoByJobNameAndIp(final String jobName, final String ip) {
        if (!regCenter.isExisted(new JobNodePath(jobName).getServerNodePath(ip))) {
            return null;
        }
        JobConfigurationBean result = new JobConfigurationBean();
        result.setJobName(jobName);
        result.setStatus(getJobStatusByJobNameAndIp(jobName, ip));
        result.setInstanceCount(getJobInstanceCountByJobNameAndIP(jobName, ip));
        return result;
    }

    private JobStatus getJobStatusByJobNameAndIp(final String jobName, final String ip) {
        JobNodePath jobNodePath = new JobNodePath(jobName);
        String status = regCenter.get(jobNodePath.getServerNodePath(ip));
        if ("DISABLED".equalsIgnoreCase(status)) {
            return JobStatus.DISABLED;
        } else {
            return JobStatus.OK;
        }
    }

    private int getJobInstanceCountByJobNameAndIP(final String jobName, final String ip) {
        int result = 0;
        JobNodePath jobNodePath = new JobNodePath(jobName);
        List<String> instances = regCenter.getChildrenKeys(jobNodePath.getInstancesNodePath());
        for (String each : instances) {
            JobInstance jobInstance = YamlEngine.unmarshal(regCenter.get(jobNodePath.getInstanceNodePath(each)), JobInstance.class);
            if (ip.equals(jobInstance.getServerIp())) {
                result++;
            }
        }
        return result;
    }


    public void trigger(final String jobName) {
        Preconditions.checkNotNull(jobName, "Job name cannot be null");
        new InstanceService(regCenter, jobName).triggerAllInstances();
    }


    public void disable(final String jobName, final String serverIp) {
        disableOrEnableJobs(jobName, serverIp, true);
    }


    public void enable(final String jobName, final String serverIp) {
        disableOrEnableJobs(jobName, serverIp, false);
    }

    private void disableOrEnableJobs(final String jobName, final String serverIp, final boolean disabled) {
        Preconditions.checkArgument(null != jobName || null != serverIp, "At least indicate jobName or serverIp.");
        if (null != jobName && null != serverIp) {
            persistDisabledOrEnabledJob(jobName, serverIp, disabled);
        } else if (null != jobName) {
            JobNodePath jobNodePath = new JobNodePath(jobName);
            for (String each : regCenter.getChildrenKeys(jobNodePath.getServerNodePath())) {
                if (disabled) {
                    regCenter.persist(jobNodePath.getServerNodePath(each), ServerStatus.DISABLED.name());
                } else {
                    regCenter.persist(jobNodePath.getServerNodePath(each), ServerStatus.ENABLED.name());
                }
            }
        } else {
            List<String> jobNames = regCenter.getChildrenKeys("/");
            for (String each : jobNames) {
                if (regCenter.isExisted(new JobNodePath(each).getServerNodePath(serverIp))) {
                    persistDisabledOrEnabledJob(each, serverIp, disabled);
                }
            }
        }
    }

    private void persistDisabledOrEnabledJob(final String jobName, final String serverIp, final boolean disabled) {
        JobNodePath jobNodePath = new JobNodePath(jobName);
        String serverNodePath = jobNodePath.getServerNodePath(serverIp);
        if (disabled) {
            regCenter.persist(serverNodePath, ServerStatus.DISABLED.name());
        } else {
            regCenter.persist(serverNodePath, ServerStatus.ENABLED.name());
        }
    }


    public void shutdown(final String jobName, final String serverIp) {
        Preconditions.checkArgument(null != jobName || null != serverIp, "At least indicate jobName or serverIp.");
        if (null != jobName && null != serverIp) {
            JobNodePath jobNodePath = new JobNodePath(jobName);
            for (String each : regCenter.getChildrenKeys(jobNodePath.getInstancesNodePath())) {
                JobInstance jobInstance = YamlEngine.unmarshal(regCenter.get(jobNodePath.getInstanceNodePath(each)), JobInstance.class);
                if (serverIp.equals(jobInstance.getServerIp())) {
                    regCenter.remove(jobNodePath.getInstanceNodePath(each));
                }
            }
        } else if (null != jobName) {
            JobNodePath jobNodePath = new JobNodePath(jobName);
            for (String each : regCenter.getChildrenKeys(jobNodePath.getInstancesNodePath())) {
                regCenter.remove(jobNodePath.getInstanceNodePath(each));
            }
        } else {
            List<String> jobNames = regCenter.getChildrenKeys("/");
            for (String job : jobNames) {
                JobNodePath jobNodePath = new JobNodePath(job);
                List<String> instances = regCenter.getChildrenKeys(jobNodePath.getInstancesNodePath());
                for (String each : instances) {
                    JobInstance jobInstance = YamlEngine.unmarshal(regCenter.get(jobNodePath.getInstanceNodePath(each)), JobInstance.class);
                    if (serverIp.equals(jobInstance.getServerIp())) {
                        regCenter.remove(jobNodePath.getInstanceNodePath(each));
                    }
                }
            }
        }
    }


    public void remove(final String jobName, final String serverIp) {
        shutdown(jobName, serverIp);
        if (null != jobName && null != serverIp) {
            regCenter.remove(new JobNodePath(jobName).getServerNodePath(serverIp));
        } else if (null != jobName) {
            JobNodePath jobNodePath = new JobNodePath(jobName);
            List<String> servers = regCenter.getChildrenKeys(jobNodePath.getServerNodePath());
            for (String each : servers) {
                regCenter.remove(jobNodePath.getServerNodePath(each));
            }
        } else if (null != serverIp) {
            List<String> jobNames = regCenter.getChildrenKeys("/");
            for (String each : jobNames) {
                regCenter.remove(new JobNodePath(each).getServerNodePath(serverIp));
            }
        }
    }

    public void updateJobConfiguration(final JobConfigurationPOJO jobConfig) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(jobConfig.getJobName()), "jobName can not be empty.");
        Preconditions.checkArgument(jobConfig.getShardingTotalCount() > 0, "shardingTotalCount should larger than zero.");
        JobNodePath jobNodePath = new JobNodePath(jobConfig.getJobName());
        regCenter.update(jobNodePath.getConfigNodePath(), YamlEngine.marshal(jobConfig));
    }

    public void removeJobConfiguration(final String jobName) {

        regCenter.remove("/" + jobName);
    }

    public void deleteJob(String jobName) {

        disableOrEnableJobs(jobName,null,false);
        // 去除定时任务
        if (Objects.nonNull(JobRegistry.getInstance().getJobScheduleController(jobName))) {
            JobRegistry.getInstance().getJobScheduleController(jobName).shutdown();
        } else {
            log.warn("job {} not exist", jobName);
        }
        regCenter.remove("/" + jobName);
    }
}
