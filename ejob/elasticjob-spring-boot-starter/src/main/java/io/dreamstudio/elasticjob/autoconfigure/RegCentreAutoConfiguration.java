package io.dreamstudio.elasticjob.autoconfigure;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import io.dreamstudio.elasticjob.autoconfigure.properties.RegCenterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ricky Fung
 */
@Configuration
@ConditionalOnClass(ZookeeperRegistryCenter.class)
@EnableConfigurationProperties(RegCenterProperties.class)
public class RegCentreAutoConfiguration {

    @Autowired
    private RegCenterProperties regCenterProperties;

    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter registryCenter() {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(regCenterProperties.getServerLists(),
                regCenterProperties.getNamespace());
        zkConfig.setBaseSleepTimeMilliseconds(regCenterProperties.getBaseSleepTimeMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(regCenterProperties.getConnectionTimeoutMilliseconds());
        zkConfig.setDigest(regCenterProperties.getDigest());
        zkConfig.setMaxRetries(regCenterProperties.getMaxRetries());
        zkConfig.setMaxSleepTimeMilliseconds(regCenterProperties.getMaxSleepTimeMilliseconds());
        zkConfig.setSessionTimeoutMilliseconds(regCenterProperties.getSessionTimeoutMilliseconds());
        return new ZookeeperRegistryCenter(zkConfig);
    }

}
