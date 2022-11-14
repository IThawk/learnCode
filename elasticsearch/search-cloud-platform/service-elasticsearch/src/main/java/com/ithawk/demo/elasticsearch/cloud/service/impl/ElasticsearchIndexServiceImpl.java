package com.ithawk.demo.elasticsearch.cloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.elasticsearch.cloud.commons.pojo.CommonEntity;
import com.ithawk.demo.elasticsearch.cloud.service.ElasticsearchIndexService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class: ElasticsearchIndexServiceImpl
 * @Package com.itheima.service.impl
 * @Description: 索引操作实现类
 * @Company: http://www.itheima.com/
 */
@Service("ElasticsearchIndexServiceImpl")
public class ElasticsearchIndexServiceImpl implements ElasticsearchIndexService {
    @Resource
    private RestHighLevelClient client;
    private static final int START_OFFSET = 0;
    private static final int MAX_COUNT = 5;

    /*
     * @Description: 新增索引+setting+映射+自定义分词器pinyin
     * setting可以为空（自定义分词器pinyin在setting中）
     * 映射可以为空
     * @Method: addIndexAndMapping
     * @Param: [commonEntity]
     * @Update:
     * @since: 1.0.0
     * @Return: boolean
     *
     */
    public boolean addIndexAndMapping(CommonEntity commonEntity) throws Exception {
        //设置setting的map
        Map<String, Object> settingMap = new HashMap<String, Object>();
        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(commonEntity.getIndexName());
        //获取前端参数
        Map<String, Object> map = commonEntity.getMap();
        //循环外层的settings和mapping
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if ("settings".equals(entry.getKey())) {
                if (entry.getValue() instanceof Map && ((Map) entry.getValue()).size() > 0) {
                    request.settings((Map<String, Object>) entry.getValue());
                }
            }
            if ("mapping".equals(entry.getKey())) {
                if (entry.getValue() instanceof Map && ((Map) entry.getValue()).size() > 0) {
                    request.mapping((Map<String, Object>) entry.getValue());
                }

            }
        }
        //创建索引操作客户端
        IndicesClient indices = client.indices();
        //创建响应对象
        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        //得到响应结果
        return response.isAcknowledged();

    }

}
