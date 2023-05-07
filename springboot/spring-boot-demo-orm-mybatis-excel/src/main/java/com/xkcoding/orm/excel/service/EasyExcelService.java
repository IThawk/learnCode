package com.xkcoding.orm.excel.service;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.xkcoding.orm.excel.listener.CmsProjectInfoListener;
import com.xkcoding.orm.excel.utils.EasyexcelUtils;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EasyExcelService {

    public void importToDatabase(){
        File file = EasyexcelUtils.readFile(EasyexcelUtils.getPath()+File.separator + "demo.xlsx");
        // 写法4
        String fileName = EasyexcelUtils.getPath() + File.separator + "/CMS-DEMO.xlsx";
        // 一个文件一个reader
        try (ExcelReader excelReader = EasyExcel.read(fileName).build()) {
            // 构建一个sheet 这里可以指定名字或者no
            ReadSheet sheet1 =
                    EasyExcel.readSheet(0).headRowNumber(1).registerReadListener(new CmsProjectInfoListener()).build();
            // 读取一个sheet
            excelReader.read(sheet1);

            excelReader.finish();

        }
    }
}
