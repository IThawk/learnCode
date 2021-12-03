package com.search.mgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.search.mgr.model.ReadBookPd;
import com.search.mgr.service.ElasticService;
import com.search.mgr.service.ReIndexService;
import com.search.mgr.service.ReadBookPdService;
import com.search.mgr.service.RedisService;
import com.search.mgr.util.RedisKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReIndexServiceImpl implements ReIndexService {

	@Resource
	RedisService redisService;
	@Resource
	ReadBookPdService pdService;
	@Resource
	ElasticService elasticService;

	@Override
	public boolean reIndexBooks() {
		log.info("开始对books索引进行全量重建");
		String updateCore = redisService.get(RedisKey.cache_keys("cores", "update"), String.class);
		if(StringUtils.isEmpty(updateCore)) updateCore = "book1";
		String currentCore = redisService.get(RedisKey.cache_keys("cores", "current"), String.class);
		if(StringUtils.isEmpty(currentCore)) currentCore = "book";
		log.info("当前备份的索引集合为{}，正在服务中的索引集合为{}", updateCore, currentCore);

		// 如果数据量达到上亿级那则需要引入大数据处理系统，hadoop，进行离线索引重建
		int total = pdService.getBookCount();
		int size = 100;
		int page = total / size;
		if (page > 100)
			page = 100;
		if (total % size != 0)
			page++;
		log.info("books数据库中记录为{}，按size={}，page={}", total, size, page);
		ThreadPoolExecutor executorService = 
	    		new ThreadPoolExecutor(10, 100, 100l, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
		for (int i = 1; i <= page; i++) {
			final int p = i;
			final String core = updateCore;
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					List<ReadBookPd> bookPds = pdService.getPageList(p, size);
					if(bookPds != null && bookPds.size() > 0) {
						List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
						for(ReadBookPd bookPd : bookPds) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("bookId", bookPd.getId());
							map.put("bookName", bookPd.getName());
							map.put("bookEnName", bookPd.getEnName());
							map.put("author", bookPd.getAuthor());
							map.put("imgurl", bookPd.getImgurl());
							map.put("createTime", bookPd.getCreateTime().getTime());
							map.put("status", bookPd.getStatus());
							map.put("discription", bookPd.getDiscription());
							map.put("price", bookPd.getPrice());
							map.put("category", bookPd.getCategory());
							map.put("commentNum", bookPd.getCommentNum());
							//如果自己定义了score字段，这里就会有一个计算得分的模块
							//map.put("score","1231");
							datas.add(map);
						}
						//
						elasticService.addBulkIn(datas, core, "_doc");
						log.info("books的page={}索引重建成功",p);
					}					
				}
			});
		}
		try {
			executorService.shutdown();
			while(!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
				log.info("等待索引重建完成.....");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("对books索引全量重建完成,进行集合的切换");
		redisService.set(RedisKey.cache_keys("cores", "update"), currentCore);
		redisService.set(RedisKey.cache_keys("cores", "current"), updateCore);

		log.info("切换成功,当前备份的索引集合为{}，正在服务中的索引集合为{}", currentCore, updateCore);
		return true;
	}

}
