package com.xkcoding.orm.excel.utils;

import cn.hutool.core.date.DateTime;
import com.xkcoding.orm.excel.PoiExecl;
import com.xkcoding.orm.excel.entity.ExcelData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class POIExcelUtils {
    public static ExcelData readExcel(String path, List<Class<?>> clazzs) {
        try {
			/*// 如果需要通过URL获取资源的加上以下的代码，不需要的省略就行
			URL url = new URL(strURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(3*1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 获取输入流
			InputStream inputStream = conn.getInputStream();
			......*/

            // 获取文件输入流
            InputStream inputStream = new FileInputStream(path);
            // 定义一个org.apache.poi.ss.usermodel.Workbook的变量
            Workbook workbook = null;
            // 截取路径名 . 后面的后缀名，判断是xls还是xlsx
            // 如果这个判断不对，就把equals换成 equalsIgnoreCase()
            String suffix = path.substring(path.lastIndexOf(".")+1);
            if (suffix.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (suffix.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            }

            return read(workbook, clazzs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ExcelData read(Workbook workbook, List<Class<?>> clazzs) {
        try {

            Map<String, Map<String, Object>> classMaps = new HashMap<>();

            Map<String, List<Object>> excelList = new HashMap<>();
            clazzs.forEach(clazz -> {
                if (!clazz.isAnnotationPresent(PoiExecl.class)) {
                    return;
                }
                List<Object> dataList = new ArrayList<>();
                PoiExecl poiExeclClass = clazz.getAnnotation(PoiExecl.class);
                Map<String, Object> classMap = readClassConfig(clazz);
                classMaps.put(poiExeclClass.title(), classMap);
                // 获取第一张表
                Sheet sheet = workbook.getSheet(poiExeclClass.title());
                // sheet.getPhysicalNumberOfRows()获取总的行数
                // 循环读取每一行
                readOneSheet(clazz, dataList, classMap, sheet);
                excelList.put(poiExeclClass.title(), dataList);
            });
            return new ExcelData(excelList, classMaps);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Map<String, Object> readClassConfig(Class<?> clazz) {

        Map<String, Object> classMap = new HashMap<>();
        classMap.put("class", clazz);
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(PoiExecl.class)) {
                    PoiExecl poiExecl = field.getAnnotation(PoiExecl.class);
                    classMap.put(poiExecl.title(), field.getName());
                }
            }
        }
        return classMap;
    }


    private static void readOneSheet(Class<?> clazz, List<Object> dataList,
                                     Map<String, Object> classMap,
                                     Sheet sheet) {
        Map<Integer, String> rowMap = readOneSheetConfig(sheet);
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

            // 循环读取每一个格
            Row row = sheet.getRow(i);
            Object instance = null;
            Map<String, Field> classField = new HashMap<>();

            try {
                instance = clazz.newInstance();
                Class<?> instanceClazz = instance.getClass();

                for (; instanceClazz != Object.class; instanceClazz = instanceClazz.getSuperclass()) {
                    Field[] fields = instanceClazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(PoiExecl.class)) {

                            classField.put(field.getName(), field);
                        }
                    }
                }
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }


            // row.getPhysicalNumberOfCells()获取总的列数
            for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
                // 获取数据，但是我们获取的cell类型
//代码上的内容自己根据实际需要自己调整就可以，这里只是展示一个样式···~
                Cell cell = row.getCell(index);
                // 转换为字符串类型
                cell.setCellType(CellType.STRING);
                // 获取得到字符串
                String name = cell.getStringCellValue();

                String filedTitle = rowMap.get(index);
                if (Objects.isNull(classMap.get(filedTitle))){
                    log.error("实体类配置有问题");
                    continue;
                }
                String filedName = classMap.get(filedTitle).toString();
                Field field = classField.get(filedName);
                if (Objects.isNull(classMap.get(field))){
                    log.error("实体类配置有问题");
                    continue;
                }
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, instance, name);

            }
            dataList.add(instance);

        }
    }

    private static Map<Integer, String> readOneSheetConfig(Sheet sheet) {
        Row row = sheet.getRow(0);
        Map<Integer, String> rowMap = new HashMap<>();
        // row.getPhysicalNumberOfCells()获取总的列数
        for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
            // 获取数据，但是我们获取的cell类型
//代码上的内容自己根据实际需要自己调整就可以，这里只是展示一个样式···~
            Cell cell = row.getCell(index);
            // 转换为字符串类型
            cell.setCellType(CellType.STRING);
            // 获取得到字符串
            String name = cell.getStringCellValue();
            rowMap.put(index, name);
        }
        return rowMap;
    }

    private static String PATH = "/Users/lixin/Desktop/";//自己输出的路径

    public static void write(String[] args) throws Exception {
        //时间
        long begin = System.currentTimeMillis();

        //创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNumber = 0; rowNumber < 65536; rowNumber++) {
            //创建行
            Row row = sheet.createRow(rowNumber);
            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                //创建列
                Cell cell = row.createCell(cellNumber);
                cell.setCellValue(cellNumber);
            }
        }
        System.out.println("结束！");
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "用户信息表-XLS.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("时间为：" + (double) (end - begin) / 1000);//2.262s
    }

    public static String getValue(Cell cell) {
        //匹配类型数据
        String cellValue = "";
        if (cell != null) {
            CellType cellType = cell.getCellType();

            switch (cellType) {
                case STRING: //字符串
                    System.out.print("[String类型]");
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN: //布尔类型
                    System.out.print("[boolean类型]");
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK: //空
                    System.out.print("[BLANK类型]");
                    break;
                case NUMERIC: //数字（日期、普通数字）
                    System.out.print("[NUMERIC类型]");
                    if (HSSFDateUtil.isCellDateFormatted(cell)) { //日期
                        System.out.print("[日期]");
                        Date date = cell.getDateCellValue();
                        cellValue = new DateTime(date).toString("yyyy-MM-dd");
                    } else {
                        //不是日期格式，防止数字过长
                        System.out.print("[转换为字符串输出]");
                        cell.setCellType(CellType.STRING);
                        cellValue = cell.toString();
                    }
                    break;
                case ERROR:
                    System.out.print("[数据类型错误]");
                    break;
            }

        }
        return cellValue;
    }
}
 
