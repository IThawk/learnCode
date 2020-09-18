package com.gupaoedu.operate;

import com.gupaoedu.config.ElasticJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: qingshan
 * @Date: 2019/9/7 16:39
 * @Description: 咕泡学院，只为更好的你
 */
public class JobController {
@Autowired
private ElasticJobConfig elasticJobConfig;

@RequestMapping("/addJob")
    public void addJob() {
        int shardingTotalCount = 2;
        // elasticJobConfig.addSimpleJobScheduler(new SimpleJobDemo().getClass(),"* * * * * ?",shardingTotalCount,"0=A,1=B");
    }




}
