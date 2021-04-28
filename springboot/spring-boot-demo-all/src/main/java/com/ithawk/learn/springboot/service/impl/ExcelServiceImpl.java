package com.ithawk.learn.springboot.service.impl;

import com.ithawk.learn.springboot.entity.OrmUser;
import com.ithawk.learn.springboot.entity.ShareEmailDetail;
import com.ithawk.learn.springboot.service.ExcelService;
import com.ithawk.learn.springboot.service.OrmUserService;
import com.ithawk.learn.springboot.utils.EmailUtil;
import com.ithawk.learn.springboot.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-12 9:08
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private OrmUserService ormUserService;

    @Override
    public HSSFWorkbook makeExcelByName(String name) {
//        List<OrmUser> list = ormUserService.selectAllUser();
//        String[][] content = ormUserService.makeContent(list);
//        //excel标题
//        String[] title = (String[]) ormUserService.getColumn().toArray();
//        //excel文件名
//        String fileName = "用户信息表" + System.currentTimeMillis() + ".xls";
//        //sheet名
//        String sheetName = "用户信息表";
//        //创建HSSFWorkbook
//        HSSFWorkbook wb = FileUtil.getHSSFWorkbook(sheetName, title, content, null);
//        return wb;

        return EmailUtil.makeExcelEmail(ormUserService,new ShareEmailDetail());
    }
}
