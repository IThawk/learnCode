package com.ithawk.mgr.core;

/**
* @Class: ResultEnum
* @Package
* @Description: 应用层操作提示
* @Company:
*/
public enum TipsEnum {
    CREATE_INDEX_SUCCESS("创建索引成功！"),
    CREATE_INDEX_FAIL("创建索引失败！"),
    DELETE_INDEX_SUCCESS("删除索引成功！"),
    DELETE_INDEX_FAIL("删除索引失败！"),
    OPEN_INDEX_SUCCESS("打开索引成功！"),
    OPEN_INDEX_FAIL("打开索引失败！"),
    CLOSE_INDEX_SUCCESS("关闭索引成功！"),
    CLOSE_INDEX_FAIL("关闭索引失败！"),
    ALIAS_INDEX_SUCCESS("索引别名设置成功！"),
    ALIAS_INDEX_FAIL("索引别名设置失败！"),
    EXISTS_INDEX_SUCCESS("索引是否存在查询成功！"),
    EXISTS_INDEX_FAIL("引是否存在查询失败！"),


    CREATE_DOC_SUCCESS("创建文档成功！"),
    CREATE_DOC_FAIL("创建文档失败！"),
    BATCH_CREATE_DOC_SUCCESS("批量创建文档成功！"),
    BATCH_CREATE_DOC_FAIL("批量创建文档失败！"),
    UPDATE_DOC_SUCCESS("修改文档成功！"),
    UPDATE_DOC_FAIL("修改文档失败！"),
    GET_DOC_SUCCESS("查询文档成功！"),
    BATCH_GET_DOC_FAIL("批量查询文档失败！"),
    BATCH_GET_DOC_SUCCESS("批量查询文档成功！"),
    GET_DOC_FAIL("查询文档失败！"),
    DELETE_DOC_SUCCESS("删除文档成功！"),
    DELETE_DOC_FAIL("删除文档失败！"),
    CSUGGEST_GET_DOC_FAIL("自动补全获取失败！"),
    CSUGGEST_GET_DOC_SUCCESS("自动补全获取成功！"),

    PSUGGEST_GET_DOC_FAIL("拼写纠错获取失败！"),
    PSUGGEST_GET_DOC_SUCCESS("拼写纠错获取成功！"),

    TSUGGEST_GET_DOC_FAIL("搜索推荐获取失败！"),
    TSUGGEST_GET_DOC_SUCCESS("搜索推荐获取成功！"),

    HOTWORDS_GET_DOC_FAIL("搜索热词获取失败！"),
    HOTWORDS_GET_DOC_SUCCESS("搜索热词获取成功！"),
    METRICAGG_GET_DOC_FAIL("指标聚合处理失败！"),
    METRICAGG_GET_DOC_SUCCESS("指标聚合处理成功！"),

    BUCKETAGG_GET_DOC_FAIL("桶聚合处理失败！"),
    BUCKETAGG_GET_DOC_SUCCESS("桶聚合处理成功！"),


    INDEX_DEFAULT("索引创建失败！");

    private String message;

    public String getMessage() {
        return message;
    }

    TipsEnum(String message) {
        this.message = message;

    }

}
