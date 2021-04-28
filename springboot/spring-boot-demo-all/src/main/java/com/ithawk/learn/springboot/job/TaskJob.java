package com.ithawk.learn.springboot.job;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.ithawk.learn.springboot.entity.ShareEmailDetail;
import com.ithawk.learn.springboot.service.OrmUserService;
import com.ithawk.learn.springboot.service.ShareEmailService;
import com.ithawk.learn.springboot.service.UserService;
import com.ithawk.learn.springboot.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 *
 * @package: com.ithawk.learn.springboot.job
 * @description: 定时任务
 * @author: IThawk
 * @date: Created in 2018/11/22 19:09
 * @version: V1.0
 */
@Component
@Slf4j
public class TaskJob {

    @Autowired
    private UserService userService;

    @Autowired
    private OrmUserService ormUserService;

    @Autowired
    private ShareEmailService shareEmailService;

    /**
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        log.info("【job1】开始执行：{}", DateUtil.formatDateTime(new Date()));
        String s = JSON.toJSONString(userService.selectAllUser());
        System.out.println(s);
        log.info("【job1】结束执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     *
     * @description: 从启动时间开始，间隔 2s 执行,发送邮件
     * @author IThawk
     * @date 2020/4/11 22:16
     * @param: null
     * @return
     */
    @Scheduled(fixedRate = 2000)
    public void shareEmail() {

        log.info("【shareEmail】开始执行：{}", DateUtil.formatDateTime(new Date()));

        List<ShareEmailDetail> shareEmailDetails = shareEmailService.selectAllShareEmailDetail();

        if (!shareEmailDetails.isEmpty()) {
            for (ShareEmailDetail shareEmailDetail : shareEmailDetails) {
                if (ShareEmailDetail.USER.equals(shareEmailDetail.getSendType())) {
                    EmailUtil.makeExcelEmail(userService, shareEmailDetail);
                } else if (ShareEmailDetail.ORM_USER.equals(shareEmailDetail.getSendType())) {
                    EmailUtil.makeExcelEmail(ormUserService, shareEmailDetail);
                }
            }
        }
    }

    /**
     * 从启动时间开始，延迟 5s 后间隔 4s 执行
     * 固定等待时间
     */
    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
    public void job3() {
        log.info("【job3】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }
}
