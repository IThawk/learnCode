package com.xkcoding.orm.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.xkcoding.orm.excel.entity.CmsProjectInfo;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Consumer;

/**
 * The persistent class for the CMS_PROJECT_INFO database table.
 *  CMS_PROJECT_INFO
 */

public class CmsProjectInfoListener implements ReadListener<CmsProjectInfo> {

    /**
     * Defuault single handle the amount of data
     */
    public static int BATCH_COUNT = 100;
    /**
     * Temporary storage of data
     */
    private List<CmsProjectInfo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * consumer
     */
    private Consumer<List<CmsProjectInfo>> consumer;

    /**
     * Single handle the amount of data
     */
    private int batchCount;

    @Override
    public void invoke(CmsProjectInfo data, AnalysisContext context) {
        cachedDataList.add(data);
        if (cachedDataList.size() >= batchCount) {
            consumer.accept(cachedDataList);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(batchCount);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            consumer.accept(cachedDataList);

            //
        }
    }

}