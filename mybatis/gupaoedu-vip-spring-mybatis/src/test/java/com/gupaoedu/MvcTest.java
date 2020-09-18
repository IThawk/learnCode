package com.gupaoedu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:src/main/resources/spring-mvc.xml"})
public class MvcTest {
    @Autowired
    WebApplicationContext context;

    //模拟MVC请求
    MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();

        /*MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码：" + pi.getPageNum());
        System.out.println("总页码：" + pi.getPages());
        System.out.println("总记录数：" + pi.getTotal());
        System.out.println("在页面中需要连续显示的页码");
        int[] nums = pi.getNavigatepageNums();
        for (int i : nums) {
            System.out.print(" " + i);
        }

        //获取员工数据
        List<Employee> list = pi.getList();
        for (Employee e : list
                ) {
            System.out.println("ID: " + e.getdId() + "==>Name: " + e.getEmpName());
        }*/

        System.out.println(result.getResponse().getContentAsString());

    }
}
