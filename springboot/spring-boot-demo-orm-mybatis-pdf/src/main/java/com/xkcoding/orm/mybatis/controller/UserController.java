package com.xkcoding.orm.mybatis.controller;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xkcoding.orm.mybatis.entity.User;
import com.xkcoding.orm.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "post")
    public int addUser(@RequestBody User user) {
        return userMapper.saveUser(user);
    }


    /**
     * 导出对账单
     *
     * @return
     */
    @GetMapping("/exportSupplierFeeData")
    public void exportSupplierFeeData(HttpServletRequest request, HttpServletResponse response) {

        List<User> users = userMapper.selectAllUser();
        try {
            response.setHeader("Content-Disposition", "attachment;filename=PDF.pdf");
            makePDFObject(Arrays.asList("用户名", "密码", "邮箱"), Arrays.asList("name", "password", "email"), users, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String File_Path = "D:/";


    public static void main(String[] args) {
        createPDF();
    }

    /**
     * 生成PDF
     *
     * @throws Exception
     */
    public static void createPDF() {
        // 设置字体(由于自己缺少这个包)
       /* BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);*/
        // 第一步，创建document对象


        //下面代码设置页面横置
        //rectPageSize = rectPageSize.rotate();
        try {
            makePDF(Arrays.asList("d", "t"), Arrays.asList("d", "t"), Arrays.asList("d", "t"), new FileOutputStream(File_Path + "PDF.pdf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("PDF生成成功!");
    }


    private static <T> void makePDFObject(List<String> title, List<String> column, List<T> list, OutputStream outputStream) {
        Rectangle rectPageSize = new Rectangle(PageSize.A4);
        //创建document对象并指定边距
        Document doc = new Document(rectPageSize, 50, 50, 50, 50);
        Document document = new Document();
        try {
            // 第二步,将Document实例和文件输出流用PdfWriter类绑定在一起
            //从而完成向Document写，即写入PDF文档
            PdfWriter.getInstance(document, outputStream);
            //第3步,打开文档
            document.open();
            //第3步,向文档添加文字. 文档由段组成
            document.add(new Paragraph("Hello World"));
            document.add(new Paragraph("世界你好"));
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
                "UniGB-UCS2-H", false);
            Font font = new Font(bfChinese, 10, Font.NORMAL);

            PdfPCell cell = new PdfPCell();
            PdfPTable titleTable = new PdfPTable(title.size());
            for (int j = 0; j < title.size(); j++) {

                cell = new PdfPCell();
                cell.addElement(new Phrase(title.get(j), font));
                titleTable.addCell(cell);
            }
            document.add(titleTable);
            for (int k = 0; k < list.size(); k++) {
                PdfPTable table = new PdfPTable(title.size());
                T t = list.get(k);
                for (int j = 0; j < title.size(); j++) {

                    Field field = t.getClass().getDeclaredField(column.get(j));
                    field.setAccessible(true);
                    Object o = field.get(t);
                    cell = new PdfPCell();
                    cell.addElement(new Phrase(o.toString(), font));
                    table.addCell(cell);

                }
                document.add(table);


            }
            document.close();
//            writer.close();
//            document.add(table);
        } catch (DocumentException | NoSuchFieldException | IllegalAccessException | IOException de) {
            System.err.println(de.getMessage());
        }
        //关闭document
        document.close();
    }

    private static <T> void makePDF(List<String> title, List<String> column, List<T> list, OutputStream outputStream) {
        Rectangle rectPageSize = new Rectangle(PageSize.A4);
        //创建document对象并指定边距
        Document doc = new Document(rectPageSize, 50, 50, 50, 50);
        Document document = new Document();
        try {
            // 第二步,将Document实例和文件输出流用PdfWriter类绑定在一起
            //从而完成向Document写，即写入PDF文档
            PdfWriter.getInstance(document, outputStream);
            //第3步,打开文档
            document.open();
            //第3步,向文档添加文字. 文档由段组成
            document.add(new Paragraph("Hello World"));
            document.add(new Paragraph("世界你好"));
            Font font = new Font();
            PdfPCell cell = new PdfPCell();
            for (int k = 1; k < 1000; k++) {
                PdfPTable table = new PdfPTable(4);

                if (k % 1000 == 0)
                    System.out.println("Row ::" + k);

                cell = new PdfPCell();
                cell.addElement(new Phrase(String.valueOf(k) + "_1", font));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Phrase(String.valueOf(k) + "_2", font));
                table.addCell(cell);

                cell = new PdfPCell();
                cell.addElement(new Phrase(String.valueOf(k) + "_3", font));
                table.addCell(cell);

                cell = new PdfPCell();
                PdfPTable inner_table = new PdfPTable(4);
                PdfPCell inner_cell = null;
                for (int j = 1; j < 5; j++) {
                    inner_cell = new PdfPCell();
                    inner_cell.addElement(new Phrase(String.valueOf(k) + "_" + j, font));
                    inner_table.addCell(inner_cell);
                }
                cell.addElement(inner_table);
                table.addCell(cell);
                document.add(table);
            }
            document.close();
//            writer.close();
//            document.add(table);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
        //关闭document
        document.close();
    }

}
