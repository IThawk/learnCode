package com.xkcoding.orm.excel.service;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.xkcoding.orm.excel.entity.CmsProjectInfo;
import com.xkcoding.orm.excel.entity.ExcelData;
import com.xkcoding.orm.excel.listener.CmsProjectInfoListener;
import com.xkcoding.orm.excel.utils.EasyexcelUtils;
import com.xkcoding.orm.excel.utils.POIExcelUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

@Service
public class PoiExcelService {

    public ExcelData importToDatabase(){

        // 写法4
        String fileName = EasyexcelUtils.getPath() + File.separator + "/CMS-DEMO.xlsx";
        ExcelData excelData =  POIExcelUtils.readExcel(fileName, Arrays.asList(CmsProjectInfo.class));

        return excelData;
    }
}
