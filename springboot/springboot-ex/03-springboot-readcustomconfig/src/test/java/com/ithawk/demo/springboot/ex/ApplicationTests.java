package com.ithawk.demo.springboot.ex;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationObjectSupport;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { Application.class},
        initializers = {ConfigFileApplicationContextInitializer.class}
)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class ApplicationTests  {

    @Autowired
    private WebApplicationContext context;

    /**
     * web接口测试
     */
    private MockMvc mockMvc;
    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }
    @Test
    public void getList() throws Exception {
        // 测试状态码
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/school"));
        System.out.println(resultActions.andReturn().getResponse());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}

