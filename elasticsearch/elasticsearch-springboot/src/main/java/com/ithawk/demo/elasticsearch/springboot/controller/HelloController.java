package com.ithawk.demo.elasticsearch.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.elasticsearch.springboot.pojo.Stu;
import com.ithawk.demo.elasticsearch.springboot.utils.JsonUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class HelloController {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @RequestMapping("hello")
    public Object hello() {
        return "OK";
    }

    /**
     * 创建索引
     */
    @RequestMapping("createIndex")
    public Object createIndex() {
        esTemplate.indexOps(Stu.class).create();
        return "OK";
    }

    /**
     * 删除索引
     */
    @RequestMapping("deleteIndex")
    public Object deleteIndex() {
        esTemplate.indexOps(Stu.class).delete();
        return "OK";
    }

    /**
     * 判断索引是否存在
     */
    @RequestMapping("existIndex")
    public Object existIndex() {
        return esTemplate.indexOps(Stu.class).exists();
    }

    /**
     * 新增文档数据
     */
    @RequestMapping("addDoc")
    public Object addDoc() {

        Stu stu0 = new Stu(10010l, "imooc", 18, 100.5f, true);
        esTemplate.save(stu0);

        Stu stu1 = new Stu(10011l, "风间影月", 20, 88.5f, true);
        Stu stu2 = new Stu(10012l, "慕课网", 22, 108.5f, false);

        ArrayList<Stu> stuList = new ArrayList<>();
        stuList.add(stu1);
        stuList.add(stu2);
        esTemplate.save(stuList);

        return "OK";
    }

    /**
     * 根据文档id删除数据
     */
    @RequestMapping("deleteDoc")
    public Object deleteDoc(String docId) {
        esTemplate.delete(docId, Stu.class);
        return "OK";
    }

    /**
     * 查询文档数据
     * http://localhost:18090/getDoc?docId=10010
     */
    @RequestMapping("getDoc")
    public Object getDoc(@RequestParam("docId") String docId) {

        Object o = esTemplate.get(docId, Stu.class);
        System.out.println(JSON.toJSONString(o));
        return o;
    }

    /**
     * 修改文档数据
     */
    @RequestMapping("updateDoc")
    public Object updateDoc() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "abc");
        map.put("money", 55.66f);

        Document doc = Document.from(map);

        UpdateQuery updateQuery = UpdateQuery
                .builder("10012")
                .withDocument(doc)
                .build();

        IndexCoordinates indexCoordinates = IndexCoordinates.of("stu");
        esTemplate.update(updateQuery, indexCoordinates);
        return "OK";
    }

    /******************** 分词搜索 ********************/

    /**
     * 初始化数据
     */
    @RequestMapping("init")
    public Object init() {

        esTemplate.indexOps(Stu.class).delete();
        esTemplate.indexOps(Stu.class).create();

        Stu stu0 = new Stu(10010l, "imooc", 18, 100.5f, true);
        Stu stu1 = new Stu(10011l, "风间影月", 20, 88.5f, true);
        Stu stu2 = new Stu(10012l, "慕课网", 22, 96.5f, false);
        Stu stu3 = new Stu(10013l, "可爱的漂亮的小哥哥", 26, 108.5f, false);
        Stu stu4 = new Stu(10014l, "美丽的祖国", 28, 108.6f, true);
        Stu stu5 = new Stu(10015l, "美丽的漂亮的小姐姐", 16, 18.5f, false);
        Stu stu6 = new Stu(10016l, "完美的慕课网", 29, 100.5f, true);

        ArrayList<Stu> stuList = new ArrayList<>();
        stuList.add(stu0);
        stuList.add(stu1);
        stuList.add(stu2);
        stuList.add(stu3);
        stuList.add(stu4);
        stuList.add(stu5);
        stuList.add(stu6);
        esTemplate.save(stuList);

        return "OK";
    }

    /**
     * 搜索数据
     */
    @RequestMapping("searchStu")
    public Object searchStu(String name) {
        //分页查询数据
        Pageable pageable = PageRequest.of(0, 10);
        //排序
        SortBuilder sortBuilder = new FieldSortBuilder("money")
                .order(SortOrder.DESC);

        //搜索添加
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", name))
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();
        SearchHits<Stu> searchHits = esTemplate.search(query, Stu.class);
        return searchHits.getSearchHits();
    }

    /**
     * 高亮搜索
     */
    @RequestMapping("highlight")
    public Object highlight(String name) {
        String preTag = "<font color='red'>";
        String postTag = "</font>";

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", name))
                .withHighlightFields(new HighlightBuilder.Field("name")
                        .preTags(preTag)
                        .postTags(postTag))
                .build();
        SearchHits<Stu> searchHits = esTemplate.search(query, Stu.class);
        List<SearchHit<Stu>> searchHitList = searchHits.getSearchHits();
        List<Map<String, Object>> hlList = new ArrayList<>();
        for (SearchHit h : searchHitList) {

            List<String> highlightField = h.getHighlightField("name");
            String nameValue = highlightField.get(0);

            String originalJson = JsonUtils.objectToJson(h.getContent());
            JsonParser jj = new GsonJsonParser();
            Map<String, Object> myHighLight = jj.parseMap(originalJson);
            // 用高亮的搜索结果覆盖原字段值
            myHighLight.put("name", nameValue);
            System.out.println(myHighLight);
            hlList.add(myHighLight);
        }
        return hlList;
    }
}
