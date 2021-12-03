package cn.com.search.service;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

import cn.com.search.vo.BookSearchParam;
import cn.com.search.vo.BookSearchResultVo;

public interface ElasticSearchService {
	
	public SearchResponse query();

	public List<BookSearchResultVo> queryDocumentByParam(String indices, String type, BookSearchParam param);
	
	public List<BookSearchResultVo> queryByRest(String indices, String type, BookSearchParam param);
	
	public List<BookSearchResultVo> queryByLowRequest(String indices, String type, BookSearchParam param);
	
	
}
