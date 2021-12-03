package com.search.mgr.service.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.search.mgr.configurer.ElasticSearchConfig;
import com.search.mgr.model.ReadBookPd;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.mgr.configurer.ElasticSearchManger;
import com.search.mgr.service.ElasticService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ElasticServiceImpl implements ElasticService {

	@Resource
	ElasticSearchManger elasticSearchManger;


	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Override
	public Boolean add(Map<String, Object> doc, String index, String type) throws Exception {
		if (doc != null) {
			try {
				XContentBuilder json = XContentFactory.jsonBuilder().startObject();
				Iterator<String> iterator = doc.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object object = doc.get(key);
					if (object instanceof Integer)
						json.field(key, Integer.valueOf(object.toString()));
					else if (object instanceof Long)
						json.field(key, Long.valueOf(object.toString()));
					else if (object instanceof String)
						json.field(key, object.toString());
					else
						json.field(key, object);
				}
				json.endObject();
				elasticSearchManger.client.prepareIndex(index, type).setSource(json).get();
				return true;
			} catch (Exception e) {
				log.error("数据插入es失败,index={},type={}", index, type, e);
			}
		}
		return false;
	}

	@Override
	public Boolean adds(List<Map<String, Object>> docs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addBulkIn(List<Map<String, Object>> datas, String index, String type) {
		try {
			BulkRequestBuilder bulkRequest = elasticSearchManger.client.prepareBulk();
			for (Map<String, Object> data : datas) {
				bulkRequest.add(elasticSearchManger.client.prepareIndex(index, type, data.get("bookId").toString())
						.setSource(data));
			}
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			System.out.println(bulkResponse.buildFailureMessage());
			return true;
		} catch (Exception e) {
			log.error("数据插入es失败,index={},type={}", index, type, e);
		}
		return false;
	}

	@Override
	public Boolean delete(String index) {
		DeleteRequest request = new DeleteRequest(index);
		elasticSearchManger.client.delete(request);
		return null;
	}

	@Override
	public ReadBookPd findById(Integer id) {

		try {
			// 1.	构建GetRequest请求。
			GetRequest getRequest = new GetRequest("book1", id + "");

			// 2.	使用RestHighLevelClient.get发送GetRequest请求，并获取到ES服务器的响应。
			GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

			// 3.	将ES响应的数据转换为JSON字符串
			String json = getResponse.getSourceAsString();

			// 4.	并使用FastJSON将JSON字符串转换为JobDetail类对象
			ReadBookPd jobDetail = JSONObject.parseObject(json, ReadBookPd.class);

			// 5.	记得：单独设置ID
			jobDetail.setId(id);
			return jobDetail;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public Object createIndex() throws IOException {

		IndexRequest indexRequest = new IndexRequest("car");
		indexRequest.id("1");
		ReadBookPd readBookPd = new ReadBookPd();
		readBookPd.setAuthor("test");
		indexRequest.source(JSON.toJSONString(readBookPd), XContentType.JSON);
		IndexResponse indexResponse = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
		System.out.println(indexResponse);
		return "OK";
	}

}
