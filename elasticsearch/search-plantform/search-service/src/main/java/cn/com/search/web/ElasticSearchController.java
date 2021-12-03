package cn.com.search.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.search.core.Result;
import cn.com.search.core.ResultGenerator;
import cn.com.search.model.ReadBooks;
import cn.com.search.service.ElasticSearchService;
import cn.com.search.vo.BookSearchParam;
import cn.com.search.vo.BookSearchResultVo;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/es")
@Slf4j
public class ElasticSearchController {
	@Resource
	ElasticSearchService esSeachService;

	@GetMapping("/search")
	public Result search(@RequestParam(required = false) String keyWord) {

		BookSearchParam bookSearchParam = new BookSearchParam();
		bookSearchParam.setDesc("童话故事");		//我指定去描述字段查询我们的结果
		List<BookSearchResultVo> books = esSeachService.queryDocumentByParam("book", "_doc", bookSearchParam);
		return ResultGenerator.genSuccessResult(books);
	}
	
	@GetMapping("/searchByRest")
	public Result searchByRest(@RequestParam(required = false) String keyWord) {

		BookSearchParam bookSearchParam = new BookSearchParam();
		bookSearchParam.setDesc("童话故事");
		bookSearchParam.setHighlightedField("discription");
		List<BookSearchResultVo> books = esSeachService.queryByRest("book", "_doc", bookSearchParam);
		return ResultGenerator.genSuccessResult(books);
	}
	
	@GetMapping("/searchByHttp")
	public Result searchByHttp(@RequestParam(required = false) String keyWord) {

		BookSearchParam bookSearchParam = new BookSearchParam();
		bookSearchParam.setDesc("童话故事");
		bookSearchParam.setHighlightedField("discription");
		List<BookSearchResultVo> books = esSeachService.queryByLowRequest("book", "_doc", bookSearchParam);
		return ResultGenerator.genSuccessResult(books);
	}
}
