package com.ithawk.demo.springboot.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureMockMvc
public class SpringBootRedisCacheApplicationTest {


    @Autowired MockMvc mvc;

    @Test
    public void exampleTest() throws Exception {
       String re = mvc.perform(get("/cache/get"))
                .andExpect(status().isOk())
//                .andExpect(content().string("OK"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(re);
    }

    @Test
    public void cacheGetTest() throws Exception {
        mvc.perform(get("/cache/get"))
                .andExpect(status().isOk());
        mvc.perform(get("/cache/get1"))
                .andExpect(status().isOk());
        mvc.perform(get("/cache/get2"))
                .andExpect(status().isOk());
    }

    @Test
    public void cacheUpdateTest() throws Exception {
        mvc.perform(get("/cache/up"))
                .andExpect(status().isOk());
    }

    @Test
    public void cacheDelTest() throws Exception {
        mvc.perform(get("/cache/del"))
                .andExpect(status().isOk());
    }

    @Test
    public void cacheDelAllUserTest() throws Exception {
        mvc.perform(get("/cache/delUser"))
                .andExpect(status().isOk());
    }
    @Test
    public void cacheDelAllTest() throws Exception {
        mvc.perform(get("/cache/delAll"))
                .andExpect(status().isOk());
    }
}
