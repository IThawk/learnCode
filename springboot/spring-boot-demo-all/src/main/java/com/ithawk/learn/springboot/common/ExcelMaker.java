package com.ithawk.learn.springboot.common;

import com.ithawk.learn.springboot.entity.ShareEmailDetail;

import java.util.List;

/**
 *
 * @author IThawk
 * @version V1.0
 * @description: 邮件生成接口
 * @date 2020-04-11 20:44
 */
public interface ExcelMaker<T> {

    /**
     *
     * @description: 获取表格中需要的数据
     * @author IThawk
     * @date 2020/4/11 22:13
     * @param: email
     * @return java.util.List<T>
     */
    List<T> getData(ShareEmailDetail email);

    /**
     *
     * @description: 获取表格的列
     * @author IThawk
     * @date 2020/4/11 22:14
     * @param:
     * @return java.util.List<java.lang.String>
     */
    List<String> getColumn();

    /*
     *
     * @description: 表格中添加数据
     * @author IThawk
     * @date 2020/4/11 22:14
     * @param: t
     * @return void
     */
    void addData(List<T> t);


    String[][] makeContent(List<T> t);
}
