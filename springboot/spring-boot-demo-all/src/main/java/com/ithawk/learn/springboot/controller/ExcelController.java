package com.ithawk.learn.springboot.controller;

import com.ithawk.learn.springboot.entity.PageData;
import com.ithawk.learn.springboot.service.ExcelService;
import com.ithawk.learn.springboot.utils.EmailUtil;
import com.ithawk.learn.springboot.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-12 9:04
 */
@RestController
public class ExcelController {


    @Autowired
    private ExcelService excelService;
    @GetMapping(value = "getExcel")
    public void getExcelByName(HttpServletResponse response,@RequestParam(value = "name") String name) {

        HSSFWorkbook wb  = excelService.makeExcelByName(name+".xls");
        try {
            this.setResponseHeader(response, name+".xls");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 导出报表
     *
     * @return
     */
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws Exception {
        //获取数据
        List<PageData> list = new ArrayList<>();
        PageData pageData1 = PageData.builder().id(1L).username("老吴")
                                        .password("1222")
                                        .createTime(new Date())
                                        .phone("1234567")
                                        .build();
        list.add(pageData1);
        //excel标题
        String[] title = {"用户ID", "用户名称", "用户密码", "用户手机", "创建时间"};

        //excel文件名
        String fileName = "用户信息表" + System.currentTimeMillis() + ".xls";

        //sheet名
        String sheetName = "用户信息表";

        String[][] content = new String[list.size()][5];

        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            PageData obj = list.get(i);
            content[i][0] = obj.getId().toString();
            content[i][1] = obj.getUsername();
            content[i][2] = obj.getPassword();
            content[i][3] = obj.getPhone();
            content[i][4] = obj.getCreateTime().toString();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = EmailUtil.getHSSFWorkbook(sheetName, title, content, null);
        File file = new File(fileName);// 创建excel文件对象
        FileOutputStream fop = null;
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            file = new File("f:\\"+fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fop = new FileOutputStream(file);
            wb.write(fop);
            fop.flush();
            fop.close();
            System.out.println("Excel文件创建成功！\nExcel文件的存放路径为：" + file.getAbsolutePath());

            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送响应流方法
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
