package com.ithawk.demo.elasticsearch.cloud.service.impl;

import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.cloud.commons.utils.SearchTools;
import com.ithawk.demo.elasticsearch.cloud.service.ElasticsearchDocumentService;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestion;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Class: ElasticsearchDocumentServiceImpl
 * @Package com.itheima.service.impl
 * @Description: 文档操作实现类
 * 注意：核心的代码暂时不做抽取，多写几遍可以加深印象
 * @Company: http://www.itheima.com/
 */
@Service("ElasticsearchDocumentServiceImpl")
@RefreshScope
public class ElasticsearchDocumentServiceImpl implements ElasticsearchDocumentService {
    private static final Logger logger = LoggerFactory
            .getLogger(ElasticsearchDocumentServiceImpl.class);
    @Resource
    private RestHighLevelClient client;

    /*
     * @Description: 全文检索
     * 使用matchQuery在执行查询时，搜索的词会被分词器分词(重要)
     * @Method: searchMatch
     * @Param: [indexName, key, value]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.search.SearchHit[]
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * >>>>>>>1、构建远程查询
     * >>>>>>>2、构建查询请求
     * >>>>>>>3、构建查询条件
     * >>>>>>>4、设置高亮
     * >>>>>>>5、设置分页
     * >>>>>>>   加入SearchRequest
     * >>>>>>>6、处理高亮
     *
     */
    public SearchResponse matchQuery(CommonEntity commonEntity) throws Exception {
        //定义返回值
        SearchResponse searchResponse = null;
        //创建查询请求
        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        //获取客户端查询条件
        getClientConditions(commonEntity, searchSourceBuilder);
        //高亮显示
        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(commonEntity.getHighlight()));
        //分页
        int pageNumber = commonEntity.getPageNumber();
        int pageSize = commonEntity.getPageSize();
        //下标计算
        int dest = (pageNumber - 1) * pageSize;
        searchSourceBuilder.from(dest);
        searchSourceBuilder.size(pageSize);
        //将查询条件放到请求中
        searchRequest.source(searchSourceBuilder);
        //远程查询
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //处理高亮
        SearchTools.setHighResultForCleintUI(searchResponse, commonEntity.getHighlight());
        return searchResponse;
    }


    /*
     * @Description:结构化搜索
     * @Method: termQuery
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.action.search.SearchResponse
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * 1、构建远程查询
     * 2、定义响应
     * 3、定义查询请求
     * 3、定义查询构建器
     * 4、定义解析器--构建器解析
     * 5、定义高亮
     * 6、定义分页
     * 7、定义排序
     *    加入到SearchRequest
     * 8、高亮渲染
     *
     */
    public SearchResponse termQuery(CommonEntity commonEntity) throws Exception {
        //构建查询响应
        SearchResponse searchResponse = null;
        //查询请求
        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
        //构建查询条件构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        //解析器
        XContentParser xContentParser = SearchTools.getXContentParser(commonEntity);
        //查询api
        searchSourceBuilder.parseXContent(xContentParser);
        //高亮
        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(commonEntity.getHighlight()));
        //分页
        int pageNumber = commonEntity.getPageNumber();
        int pageSize = commonEntity.getPageSize();
        int dest = (pageNumber - 1) * pageSize;
        //设置下标
        searchSourceBuilder.from(dest);
        searchSourceBuilder.size(pageSize);
        //排序
        sort(commonEntity, searchSourceBuilder);
        //设置到查询请求
        searchRequest.source(searchSourceBuilder);
        long time = System.currentTimeMillis();
        //远程查询
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("执行耗时>>>>>>>>" + (System.currentTimeMillis() - time));
        //渲染
        SearchTools.setHighResultForCleintUI(searchResponse, commonEntity.getHighlight());
        return searchResponse;
    }


    /*
     * @Description: 自动补全 根据用户的输入联想到可能的词或者短语
     * @Method: suggester
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.action.search.SearchResponse
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * 1、定义远程查询
     * 2、定义查询请求（评分排序）
     * 3、定义自动完成构建器（设置前台建议参数）
     * 4、将自动完成构建器加入到查询构建器
     * 5、将查询构建器加入到查询请求
     * 6、获取自动建议的值（数据结构处理）
     */
    public List<String> cSuggest(CommonEntity commonEntity) throws Exception {
        List<String> list = new ArrayList<String>();
        //返回响应
        SearchResponse searchResponse = null;
        //查询请求
        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
        //查询构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //定义自动建议构建器
        CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder(commonEntity.getSuggestFileld());
        completionSuggestionBuilder.text(commonEntity.getSuggestValue());
        completionSuggestionBuilder.skipDuplicates(true);
        completionSuggestionBuilder.size(commonEntity.getSuggestCount());
        //将自动建议构建器加入到查询构建器
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggest", completionSuggestionBuilder));
        //加入到查询请求
        searchRequest.source(searchSourceBuilder);
        //执行远程查询
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        CompletionSuggestion completionSuggestion = searchResponse.getSuggest().getSuggestion("czbk-suggest");
//         List<CompletionSuggestion.Entry.Option> vList= completionSuggestion.getEntries().get(0).getOptions();

        List<CompletionSuggestion.Entry.Option> vList = completionSuggestion.getEntries().get(0).getOptions();

        if (!CollectionUtils.isEmpty(vList)) {
            vList.forEach(item -> list.add(item.getText().toString()));
        }

        return list;
    }


    /*
     * @Description: 拼写纠错
     * @Method: psuggest
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.List<java.lang.String>
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * 1、定义远程查询
     * 2、定义查询请求（评分排序）
     * 3、定义自动纠错构建器（设置前台建议参数）
     * 4、将拼写纠错构建器加入到查询构建器
     * 5、将查询构建器加入到查询请求
     * 6、获取拼写纠错的值（数据结构处理）
     */
    @Override
    public String pSuggest(CommonEntity commonEntity) throws Exception {
        String str = "";
        //返回响应
        SearchResponse searchResponse = null;
        //查询请求
        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
        //查询构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //纠错构建器
        PhraseSuggestionBuilder phraseSuggestionBuilder = new PhraseSuggestionBuilder(commonEntity.getSuggestFileld());
        phraseSuggestionBuilder.text(commonEntity.getSuggestValue());
        phraseSuggestionBuilder.size(1);
        //将自动建议构建器加入到查询构建器
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggest", phraseSuggestionBuilder));
        //加入到查询请求
        searchRequest.source(searchSourceBuilder);
        //执行远程查询
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        PhraseSuggestion phraseSuggestion = searchResponse.getSuggest().getSuggestion("czbk-suggest");

        List<PhraseSuggestion.Entry.Option> vList = phraseSuggestion.getEntries().get(0).getOptions();

        if (!CollectionUtils.isEmpty(vList) && vList.get(0).getText() != null) {
            str = vList.get(0).getText().toString();
        }


        return str;
    }


    /*
     * @Description: 搜索推荐（当输入的关键词过多的时候系统进行推荐）
     * @Method: tSuggest
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.List<java.lang.String>
     * 1、定义远程查询
     * 2、定义查询请求（评分排序）
     * 3、定义搜索推荐构建器（设置前台建议参数）
     * 4、将搜索推荐构建器加入到查询构建器
     * 5、将查询构建器加入到查询请求
     * 6、获取搜索推荐的值（数据结构处理）
     */

    public String tSuggest(CommonEntity commonEntity) throws Exception {
        String str = "";
        //返回响应
        SearchResponse searchResponse = null;
        //查询请求
        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
        //查询构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //定义词条建议器
        TermSuggestionBuilder termSuggestionBuilder = new TermSuggestionBuilder(commonEntity.getSuggestFileld());
        termSuggestionBuilder.text(commonEntity.getSuggestValue());
        termSuggestionBuilder.analyzer("ik_smart");
        //设置字符串距离算法，使用ngram
        termSuggestionBuilder.stringDistance(TermSuggestionBuilder.StringDistanceImpl.NGRAM);
        //指定查询长度（这个长度一定小于等于实际输入）
        termSuggestionBuilder.minWordLength(4);
        //加入查询构建器
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggestion", termSuggestionBuilder));
        //放到查询请求
        searchRequest.source(searchSourceBuilder);
        //执行远程查询
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //处理查询结果
        TermSuggestion termSuggestion = searchResponse.getSuggest().getSuggestion("czbk-suggestion");
        List<TermSuggestion.Entry.Option> vList = termSuggestion.getEntries().get(0).getOptions();
        if (!CollectionUtils.isEmpty(vList) && vList.get(0).getText() != null) {
            str = vList.get(0).getText().toString();
        }
        return str;
    }


    /*
     * @Description: 获取前端的查询条件
     * @Method: getClientConditions
     * @Param: [commonEntity, searchSourceBuilder]
     * @Update:
     * @since: 1.0.0
     * @Return: void
     *
     */
    private void getClientConditions(CommonEntity commonEntity, SearchSourceBuilder searchSourceBuilder) {
        //循环前端的查询条件
        for (Map.Entry<String, Object> m : commonEntity.getMap().entrySet()) {
            if (StringUtils.isNotEmpty(m.getKey()) && m.getValue() != null) {
                String key = m.getKey();
                String value = String.valueOf(m.getValue());
                //构造请求体中“query”:{}部分的内容 ,QueryBuilders静态工厂类，方便构造queryBuilder
                //将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
                searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
                logger.info("search for the keyword:" + value);
            }
        }
    }


    /*
     * @Description: 批量新增文档,可自动创建索引、自动创建映射
     * @Method: bulkAddDoc
     * @Param: [indexName, map]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.rest.RestStatus
     *
     */
    @Override
    public RestStatus bulkAddDoc(CommonEntity commonEntity) throws Exception {
        //通过索引构建批量请求对象
        BulkRequest bulkRequest = new BulkRequest(commonEntity.getIndexName());
        //循环前台list文档数据
        for (int i = 0; i < commonEntity.getList().size(); i++) {
            bulkRequest.add(new IndexRequest().source(XContentType.JSON, SearchTools.mapToObjectGroup(commonEntity.getList().get(i))));
        }
        //执行批量新增
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.status();
    }

    /*
     * @Description: 排序
     * @Method: sort
     * @Param: [commonEntity, searchSourceBuilder]
     * @Update:
     * @since: 1.0.0
     * @Return: void
     *
     */
    private void sort(CommonEntity commonEntity, SearchSourceBuilder searchSourceBuilder) {
        String sortField = commonEntity.getSortField();
        if (StringUtils.isNotEmpty(sortField)) {
            //排序,获取前端的order by子句，不区分大小写，参数为空则默认desc
            SortOrder sortOrder = SearchTools.getSortOrder(commonEntity.getSortOrder());
            //执行排序
            searchSourceBuilder.sort(new FieldSortBuilder(commonEntity.getSortField()).order(sortOrder));
        }
    }


//    /*
//     * @Description: 全文检索
//     * 使用matchQuery在执行查询时，搜索的词会被分词器分词
//     * @Method: searchMatch
//     * @Param: [indexName, key, value]
//     * @Update:
//     * @since: 1.0.0
//     * @Return: org.elasticsearch.search.SearchHit[]
//     *
//     */
//    public SearchResponse matchQuery(CommonEntity commonEntity) throws Exception {
//        //构建查询响应
//        SearchResponse response = null;
//        //构建查询请求用来完成和搜索文档，聚合，建议等相关的任何操作同时也提供了各种方式来完成对查询结果的高亮操作。
//        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
//        //构建DSL请求体;trackTotalHits如果不设置true，查询数据最大值还是10000
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
//        //获取前端的查询条件（Map查询条件）
//        getClientConditions(commonEntity, searchSourceBuilder);
//        //高亮设置
//        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(commonEntity.getHighlight()));
//        //前端页码
//        int pageNumber = commonEntity.getPageNumber();
//        //前端每页数量
//        int pageSize = commonEntity.getPageSize();
//        //计算查询的下标
//        int dest = (pageNumber - 1) * pageSize;
//        searchSourceBuilder.from(dest);
//        //每页数量
//        searchSourceBuilder.size(pageSize);
//        //查询条件对象放入请求对象中
//        searchRequest.source(searchSourceBuilder);
//        //方法执行开始时间
//        long startTime = System.currentTimeMillis();
//        System.out.println("开始Elasticsearch查询...");
//        //执行远程查询,使用RequestOptions.DEFAULT用来构建一个默认缓冲区限制为100MB（源码为DEFAULT_BUFFER_LIMIT = 100 * 1024 * 1024），和header为空、WarningsHandler为空
//        //的参数选项
//        response = client.search(searchRequest, RequestOptions.DEFAULT);
//        //计算远程查询耗时
//        System.out.println("结束Elasticsearch查询总耗时:" + (System.currentTimeMillis() - startTime) + "毫秒");
//        //处理高亮
//        SearchTools.setHighResultForCleintUI(response, commonEntity.getHighlight());
//        return response;
//
//    }


    /*
     * @Description:结构化搜索
     * @Method: termQuery
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.action.search.SearchResponse
     *
     */
//    public SearchResponse termQuery(CommonEntity commonEntity) throws Exception {

//        //构建查询响应
//        SearchResponse response = null;
//        //构建查询请求用来完成和搜索文档，聚合，建议等相关的任何操作同时也提供了各种方式来完成对查询结果的高亮操作。
//        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
//        //构建DSL请求体trackTotalHits如果不设置true，查询数据最大值还是10000
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
//        //将前端的dsl查询转化为XContentParser
//        XContentParser parser = SearchTools.getXContentParser(commonEntity);
//        //将parser解析成功查询API
//        searchSourceBuilder.parseXContent(parser);
//        //高亮设置
//        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(commonEntity.getHighlight()));
//        //前端页码
//        int pageNumber = commonEntity.getPageNumber();
//        //前端每页数量
//        int pageSize = commonEntity.getPageSize();
//        //计算查询的下标
//        int dest = (pageNumber - 1) * pageSize;
//        searchSourceBuilder.from(dest);
//        //每页数量
//        searchSourceBuilder.size(pageSize);
//        //排序
//        sort(commonEntity, searchSourceBuilder);
//        //查询条件对象放入请求对象中
//        searchRequest.source(searchSourceBuilder);
//        //方法执行开始时间
//        long startTime = System.currentTimeMillis();
//        System.out.println("开始Elasticsearch查询...");
//        //执行远程查询
//        response = client.search(searchRequest, RequestOptions.DEFAULT);
//        //计算远程查询耗时
//        System.out.println("结束Elasticsearch查询总耗时:" + (System.currentTimeMillis() - startTime) + "毫秒");
//        //处理高亮
//        SearchTools.setHighResultForCleintUI(response, commonEntity.getHighlight());
//        return response;
//    }
    /*
     * @Description: 自动补全 根据用户的输入联想到可能的词或者短语
     * @Method: suggester
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.action.search.SearchResponse
     *
     */
//public List<String> cSuggest(CommonEntity commonEntity) throws Exception {
//
//    //定义返回
//    List<String> suggestList = new ArrayList<>();
//    //构建查询请求
//    SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
//    //通过查询构建器定义评分排序
//    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//    searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//    //构造搜索建议语句,搜索条件字段
//    CompletionSuggestionBuilder completionSuggestionBuilder =new CompletionSuggestionBuilder(commonEntity.getSuggestFileld());
//    //搜索关键字
//    completionSuggestionBuilder.prefix(commonEntity.getSuggestValue());
//    //去除重复
//    completionSuggestionBuilder.skipDuplicates(true);
//    //匹配数量
//    completionSuggestionBuilder.size(commonEntity.getSuggestCount());
//    searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggest", completionSuggestionBuilder));
//    //czbk-suggest为返回的字段，所有返回将在czbk-suggest里面，可写死,sort按照评分排序
//    searchRequest.source(searchSourceBuilder);
//    //定义查找响应
//    SearchResponse suggestResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//    //定义完成建议对象
//    CompletionSuggestion completionSuggestion = suggestResponse.getSuggest().getSuggestion("czbk-suggest");
//    List<CompletionSuggestion.Entry.Option> optionsList = completionSuggestion.getEntries().get(0).getOptions();
//    //从optionsList取出结果
//    if (!CollectionUtils.isEmpty(optionsList)) {
//        optionsList.forEach(item -> suggestList.add(item.getText().toString()));
//    }
//    return suggestList;
//}
//
//
//
//    /*
//     * @Description: 拼写纠错
//     * @Method: psuggest
//     * @Param: [commonEntity]
//     * @Update:
//     * @since: 1.0.0
//     * @Return: java.util.List<java.lang.String>
//     *
//     */
//    @Override
//    public String pSuggest(CommonEntity commonEntity) throws Exception {
//        //定义返回
//        String pSuggestString = new String();
//        //定义查询请求
//        SearchRequest searchRequest = new SearchRequest(commonEntity.getIndexName());
//        //定义查询条件构建器
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        //定义排序器
//        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//        //构造短语建议器对象（参数为匹配列）
//        PhraseSuggestionBuilder pSuggestionBuilder = new PhraseSuggestionBuilder(commonEntity.getSuggestFileld());
//        //搜索关键字（被纠错的值）
//        pSuggestionBuilder.text(commonEntity.getSuggestValue());
//        //匹配数量
//        pSuggestionBuilder.size(1);
//        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggest", pSuggestionBuilder));
//        searchRequest.source(searchSourceBuilder);
//        //定义查找响应
//        SearchResponse suggestResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        //定义短语建议对象
//        PhraseSuggestion phraseSuggestion = suggestResponse.getSuggest().getSuggestion("czbk-suggest");
//        //获取返回数据
//        List<PhraseSuggestion.Entry.Option> optionsList = phraseSuggestion.getEntries().get(0).getOptions();
//        //从optionsList取出结果
//        if (!CollectionUtils.isEmpty(optionsList) &&optionsList.get(0).getText()!=null) {
//            pSuggestString = optionsList.get(0).getText().string().replaceAll(" ","");
//        }
//        return pSuggestString;
//    }
//
//
//    /*
//     * @Description: 搜索推荐（当输入的关键词过多的时候系统进行推荐）
//     * @Method: tSuggest
//     * @Param: [commonEntity]
//     * @Update:
//     * @since: 1.0.0
//     * @Return: java.util.List<java.lang.String>
//     *
//     */
//    public String tSuggest(CommonEntity commonEntity) throws Exception {
//        //定义返回
//        String tSuggestString = new String();
//        //定义查询请求
//        SearchRequest  searchRequest=new SearchRequest(commonEntity.getIndexName());
//        //定义查询构建器
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//
//        //构造词条建议语句,搜索条件字段
////        TermSuggestionBuilder termSuggestiontextBuilder = SuggestBuilders.termSuggestion(commonEntity.getSuggestFileld());
//        TermSuggestionBuilder  termSuggestiontextBuilder=new TermSuggestionBuilder(commonEntity.getSuggestFileld());
//        //搜索关键字
//        termSuggestiontextBuilder.text(commonEntity.getSuggestValue());
//        //输入的建议词分词
//        termSuggestiontextBuilder.analyzer("ik_smart");
//        //建议文本术语必须包含的最小长度。默认值为4。（旧名称“ min_word_len”已弃用）
//        termSuggestiontextBuilder.minWordLength(2);
//        //用于比较建议术语的相似程度的字符串距离实现
//        termSuggestiontextBuilder.stringDistance(TermSuggestionBuilder.StringDistanceImpl.NGRAM);
//        //将词条建议器加入到查询构建器中
//        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggest",termSuggestiontextBuilder));
//        //将查询构建器加入到查询请求中
//        searchRequest.source(searchSourceBuilder);
//        //定义查找响应
//        SearchResponse suggestResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        //定义完成建议对象
//        TermSuggestion termSuggestion = suggestResponse.getSuggest().getSuggestion("czbk-suggest");
//        //获取返回数据
//        List<TermSuggestion.Entry.Option> optionsList = termSuggestion.getEntries().get(0).getOptions();
//        //从optionsList取出结果
//        if (!CollectionUtils.isEmpty(optionsList) && optionsList.get(0).getText()!=null) {
//            tSuggestString = optionsList.get(0).getText().toString();
//        }
//        return tSuggestString;
//    }

}
