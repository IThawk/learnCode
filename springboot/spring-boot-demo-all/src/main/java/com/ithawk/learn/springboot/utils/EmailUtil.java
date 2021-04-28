package com.ithawk.learn.springboot.utils;

import com.ithawk.learn.springboot.common.ExcelMaker;
import com.ithawk.learn.springboot.entity.OrmUser;
import com.ithawk.learn.springboot.entity.ShareEmailDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-11 18:16
 */
@Slf4j
public class EmailUtil {



    /*
     *
     * @description: //TODO
     * @author IThawk
     * @date 2020/4/11 22:09
     * @param: excelMaker
     * @param: email
     * @return void
     */
    public static <T> HSSFWorkbook makeExcelEmail(ExcelMaker<T> excelMaker, ShareEmailDetail email) {
//        List<T> t = excelMaker.getData(email);
//        System.out.println(t);
//        List columns = excelMaker.getColumn();
//        System.out.println(columns);
//        excelMaker.addData(t);

        List<T> t = excelMaker.getData(email);
//        List<OrmUser> list = ormUserService.selectAllUser();
        String[][] content = excelMaker.makeContent(t);
        //excel标题
        String[] title = (String[]) excelMaker.getColumn().toArray();
        //excel文件名
        String fileName = "用户信息表" + System.currentTimeMillis() + ".xls";
        //sheet名
        String sheetName = "用户信息表";
        //创建HSSFWorkbook
        HSSFWorkbook wb = getHSSFWorkbook(sheetName, title, content, null);
        return wb;
    }

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

//        XSSFCellStyle xssfCellStyle = new XSSFCellStyle();
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        int a = values.length;
        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
}
