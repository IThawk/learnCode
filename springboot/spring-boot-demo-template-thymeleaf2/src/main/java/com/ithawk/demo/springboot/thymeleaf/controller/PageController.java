package com.ithawk.demo.springboot.thymeleaf.controller;

import com.ithawk.demo.springboot.thymeleaf.mapper.UserMapper;
import com.ithawk.demo.springboot.thymeleaf.model.PageInfo;
import com.ithawk.demo.springboot.thymeleaf.model.TableDataInfo;
import com.ithawk.demo.springboot.thymeleaf.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户页面
 * </p>
 */
@Controller
@RequestMapping("/system/pageInfo")
@Slf4j
public class PageController {


    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo login() {

        return getDataTable(Arrays.asList(new PageInfo("123","1212","1222")));
    }
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode("0000");
        rspData.setRows(list);
        rspData.setTotal(list.size());
        return rspData;
    }


}
