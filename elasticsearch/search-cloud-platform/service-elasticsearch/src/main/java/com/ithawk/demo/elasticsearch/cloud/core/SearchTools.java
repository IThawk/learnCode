package com.ithawk.demo.elasticsearch.cloud.core;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Class: SearchTools 查询服务工具类
 * @Package
 * @Description:
 */
public class SearchTools<T> {

    public static <T> Page<T> searchResponseToPage(SearchResponse searchResponse, Pageable pageable,Class<?> t) {
        long aSize = searchResponse.getHits().getTotalHits().value;
//        logger.info("数据总数量为>>>" + aSize);
        long cSize = searchResponse.getHits().getHits().length;
//        logger.info("本次获取数据量为>>>" + cSize);
        List<T> esProducts = new ArrayList<>();
        for (SearchHit searchHit1 : searchResponse.getHits()) {
            T esProduct = (T) JSON.parseObject(searchHit1.getSourceAsString(),t);
            System.out.println(JSON.toJSONString(esProduct));
            esProducts.add(esProduct);
        }
        return new PageImpl(esProducts, pageable, aSize);
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
    public static void getClientConditions(Map<String, Object> commonEntity, SearchSourceBuilder searchSourceBuilder) {
        //循环前端的查询条件
        for (Map.Entry<String, Object> m : commonEntity.entrySet()) {
            if (!StringUtils.isEmpty(m.getKey()) && m.getValue() != null) {
                String key = m.getKey();
                String value = String.valueOf(m.getValue());
                //构造请求体中“query”:{}部分的内容 ,QueryBuilders静态工厂类，方便构造queryBuilder
                //将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
                searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
//                logger.info("search for the keyword:" + value);
            }
        }
    }


    /*
     * @Description: 高亮前端显示组装，SearchResponse传递引用
     * 为什么二次处理高亮？
     * 原因：被设置的高亮列，es自动放到了highlight属性中；这个属性渲染了高亮的着色
     * 数据传输的时候，我们需要将它取出来
     * 覆盖到我们的_source中
     * @Method: setHighResult
     * @Param: [searchResponse, commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *
     *
     */
    public static void setHighResultForCleintUI(SearchResponse searchResponse, String highlightField) {
        if (!StringUtils.isEmpty(highlightField)) {
            for (SearchHit hit : searchResponse.getHits()) {
                //获取高亮字段map
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                //获取到具体的高亮列
                HighlightField highlightFieldName = highlightFields.get(highlightField);
                //getSourceAsMap拿到具体的数据
                Map<String, Object> source = hit.getSourceAsMap();
                if (highlightFieldName != null) {
                    //获取渲染后的文本
                    Text[] fragments = highlightFieldName.fragments();
                    String name = "";
                    for (Text text : fragments) {
                        name += text;
                    }
                    source.put(highlightField, name);   //高亮字段替换掉原本的内容
                }
            }


        }
    }
    /*
     * @Description: 获取高亮构建器
     * @Method:
     * @Param:
     * @Update:
     * @since: 1.0.0
     * @Return:
     *
     */

    public static HighlightBuilder getHighlightBuilder(String highlightField) {

        // 设置高亮,使用默认的highlighter高亮器,默认em斜体
        HighlightBuilder highlightBuilder = new HighlightBuilder(); //生成高亮查询器
        highlightBuilder.field(highlightField);      //高亮查询字段
        highlightBuilder.requireFieldMatch(false);     //如果要多个字段高亮,这项要为false
        highlightBuilder.preTags("<span style= " +
                "color:red;font-weight:bold;font-size:15px;" +
                ">");   //高亮设置
        highlightBuilder.postTags("</span>");
        //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        highlightBuilder.fragmentSize(800000); //最大高亮分片数
        highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段
        return highlightBuilder;
    }


    /*
     * @Description: 获取排序  DESC  ASC 前端不区分大小写，默认返回DESC
     * @Method: getSortOrder
     * @Param: [sortOrder]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.search.sort.SortOrder
     *
     */
    public static SortOrder getSortOrder(String sortOrder) {
        SortOrder so = null;
        sortOrder = StringUtils.isEmpty(sortOrder) ? "" : sortOrder.toLowerCase();
        switch (sortOrder) {
            case "desc":
                so = SortOrder.DESC;
                break;
            case "asc":
                so = SortOrder.ASC;
                break;
            default:
                so = SortOrder.DESC;
                break;
        }
        return so;
    }


    /*
     * @Description: MAP转数组
     * @Method: mapToObjectGropu
     * @Param: [data]
     * @Update:
     * @since: 1.0.0
     * @Return: java.lang.Object[]
     *
     */
    public static Object[] mapToObjectGroup(Map<String, Object> data) {
        List<Object> args = new ArrayList<Object>();
        if (data != null) {
            data.forEach((key, value) -> {
                args.add(key);
                args.add(value);
            });
        }

        return args.toArray();
    }

    /*
     * @Description: 根据客户端传来的查询参数（标准的DSL语句）构建XContentParser
     * @Method: getXContentParser
     * @Param: []
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.common.xcontent.XContentParser
     *
     */
//    public static XContentParser getXContentParser(CommonEntity commonEntity) throws IOException {
//
//        //构建SearchModule对象置 ,通过构造器注册解析器、建议器、排序等
//        SearchModule searchModule = new SearchModule(Settings.EMPTY, false, Collections.emptyList());
//        //获取注册成功的注册解析器、建议器、排序
//        NamedXContentRegistry registry = new NamedXContentRegistry(searchModule.getNamedXContents());
//        //将前端传来的DSL参数通过解析解解析
//        XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(registry, LoggingDeprecationHandler.INSTANCE, JSONObject.toJSONString(commonEntity.getMap()));
//        return parser;
//    }

    /*
     * @Description: 将查询出来的数据放到本地局部线程变量中
     * @Method: setResponseThreadLocal
     * @Param: [response]
     * @Update:
     * @since: 1.0.0
     * @Return: void
     *
     */
//    public static void setResponseThreadLocal(SearchResponse response) {
//        //查询出来的数据
//        SearchHit[] sh = response.getHits().getHits();
//        //定义list用来接收所有Resource下面的结果集
//        List<JSONObject> list = new ArrayList<JSONObject>();
//        if (sh != null) {
//            for (SearchHit hit : sh) {
//                list.add(JSONObject.parseObject(hit.getSourceAsString()));
//            }
//            //将数据放入到本地线程
//            ResponseThreadLocal.set(list);
//        }
//    }

}