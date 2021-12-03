package com.search.mgr.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.search.mgr.service.ReIndexService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookReIndexSchedule {

	@Resource
	ReIndexService reIndexService;

	@Scheduled(cron = "0 0 0/1 * * ? ")
	public void reIndexBooks() {
		log.info("定时重建books索引启动");
		boolean flag = reIndexService.reIndexBooks();
		log.info("定时重建books索引完成，状态为{}", flag);
	}
}
