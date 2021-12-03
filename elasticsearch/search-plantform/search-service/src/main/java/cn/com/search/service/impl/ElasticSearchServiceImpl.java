package cn.com.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.management.Query;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import cn.com.search.service.ElasticSearchService;
import cn.com.search.vo.BookSearchParam;
import cn.com.search.vo.BookSearchResultVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
	@Autowired
	TransportClient client;

	@Autowired
	RestHighLevelClient highLevelClient;

	public SearchResponse query() {
		return null;
	}
	public List<BookSearchResultVo> queryByLowRequest(String indices, String type, BookSearchParam param) {
		Request request = new Request("GET", "/book/_search");
		//怎么解决动态添加呢？
		//只能使用JsonObject Json gson
		//此种方式虽然麻烦 但一定可以解决你的问题
		String reqJson = "{\n" + 
				"  \"query\": {\n" + 
				"    \"bool\": {\n" + 
				"      \"should\": [\n" + 
				"        {\n" + 
				"          \"match\": {\n" + 
				"            \"discription\":\""+param.getDesc()+"\" \n" + 
				"          }\n" + 
				"        }\n" + 
				"      ], \n" + 
				"      \"filter\": [\n" + 
				"        {\n" + 
				"          \"range\": {\n" + 
				"            \"commentNum\": {\n" + 
				"              \"lte\": 2000,\n" + 
				"              \"gte\": 1\n" + 
				"            }\n" + 
				"          }\n" + 
				"        },\n" + 
				"        {\n" + 
				"          \"term\": {\n" + 
				"            \"author\":\"王德启，王晶/著\"\n" + 
				"          }\n" + 
				"        }\n" + 
				"      ]\n" + 
				"    }\n" + 
				"  }\n" + 
				"}\n" + 
				"";
		request.setJsonEntity(reqJson);
		try {
			System.out.println(reqJson);
			Response response = highLevelClient.getLowLevelClient().performRequest(request);	//用低版本的client
			String responStr = EntityUtils.toString(response.getEntity());
			System.out.println(responStr);
			JSONObject jsonObject = JSONObject.parseObject(responStr);
			JSONArray array = jsonObject.getJSONObject("hits").getJSONArray("hits");
			List<BookSearchResultVo> bookSearchResultVos = new ArrayList<BookSearchResultVo>();
			for(int i = 0 ; i < array.size() ; i++) {
				JSONObject jsObject = array.getJSONObject(i);
				Integer bookId = jsObject.getInteger("_id");
				BookSearchResultVo bookSearchResultVo = new BookSearchResultVo();
				bookSearchResultVo.setId(bookId);
				bookSearchResultVos.add(bookSearchResultVo);
			}
			return bookSearchResultVos;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<BookSearchResultVo> queryByRest(String indices, String type, BookSearchParam param) {

		SearchRequest searchRequest = new SearchRequest(indices);
		searchRequest.types(new String[] {type});
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(convertParam(param));
		if (param.getHighlightedField() != null) {
			sourceBuilder.highlighter(new HighlightBuilder().field(param.getHighlightedField()).preTags("<h1 style='red'>")
					.postTags("</h1>"));
		}
		log.info("对index={}，type={}进行检索，query={}", indices, type, param.toString());
		
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse;
		try {
			searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			log.info("对index={}，type={}进行检索，query={},检索到结果数为{}条", indices, type, param.toString(),
					searchResponse.getHits().getTotalHits());
			return convertResponse(searchResponse, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<BookSearchResultVo> queryDocumentByParam(String indices, String type, BookSearchParam param) {
		SearchRequestBuilder builder = buildRequest(indices, type);		//我采用的时 transport形式查询

		builder.setQuery(convertParam(param));
		builder.setFrom(param.getPageIndex()).setSize(param.getPageSize());
		if (param.getHighlightedField() != null) {
			builder.highlighter(new HighlightBuilder().field(param.getHighlightedField()).preTags("<h1 style='yellow'>")	//这个标签是我自定义
					.postTags("</h1>"));
		}
		log.info("对index={}，type={}进行检索，query={}", indices, type, param.toString());
		SearchResponse resp = builder.get();
		log.info("对index={}，type={}进行检索，query={},检索到结果数为{}条", indices, type, param.toString(),
				resp.getHits().getTotalHits());
		return convertResponse(resp, param);
	}

	public SearchRequestBuilder buildRequest(String indices, String type) {
		return client.prepareSearch(indices).setTypes(type);
	}

	private BoolQueryBuilder convertParam(BookSearchParam param) {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if (StringUtils.hasText(param.getBookName())) {
			boolQueryBuilder.must(QueryBuilders.termQuery("name", param.getBookName()));
		}
		if (param.getPrice() != null) {
			boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(param.getPrice()));
		}
		if (StringUtils.hasText(param.getDesc())) {
			boolQueryBuilder.must(QueryBuilders.queryStringQuery("discription:" + param.getDesc()));
		}
		return boolQueryBuilder;
	}

	public List<BookSearchResultVo> convertResponse(SearchResponse response, BookSearchParam searchParam) {
		List<BookSearchResultVo> list = Lists.newArrayList();
		if (response != null && response.getHits() != null) {
			String result = org.apache.commons.lang3.StringUtils.EMPTY;
			BookSearchResultVo e = new BookSearchResultVo();
			for (SearchHit hit : response.getHits()) {
				result = hit.getSourceAsString();
				Map<String, HighlightField> map = hit.getHighlightFields();
				if (StringUtils.hasText(result)) {
					e = JSONObject.parseObject(result, BookSearchResultVo.class);
				}
				if (e != null) {
					if (map != null) {
						HighlightField field = map.get(searchParam.getHighlightedField());
						if (field != null) {
							String str = "";
							for (Text text : field.fragments()) {
								str += text.string();
							}
							Map<String, String> highlightMap = new HashMap<>();
							highlightMap.put(field.getName(), str);
							e.setDiscription(str);
						}
					}
					list.add(e);
				}
			}
		}
		return list;
	}

}
