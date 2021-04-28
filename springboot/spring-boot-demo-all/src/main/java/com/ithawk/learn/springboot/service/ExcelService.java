package com.ithawk.learn.springboot.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-12 9:07
 */
public interface ExcelService {

    HSSFWorkbook makeExcelByName(String name);
}
