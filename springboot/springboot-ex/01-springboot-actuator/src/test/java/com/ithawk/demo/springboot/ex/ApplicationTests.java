package com.ithawk.demo.springboot.ex;


import com.ithawk.demo.springboot.ex.service.SomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private SomeService service;

    /**
     * web接口测试
     */
    @Autowired
    private MockMvc mvc;

    @Test
    public void contextLoads() {
        service.doSome();
    }


    @Test
    public void getList() throws Exception {
        // 测试状态码
        mvc.perform(MockMvcRequestBuilders.get("/some"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
