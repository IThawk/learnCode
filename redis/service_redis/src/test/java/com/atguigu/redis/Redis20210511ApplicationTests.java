package com.atguigu.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
class Redis20210511ApplicationTests
{

    @Test
    void contextLoads()
    {
    }


    @Test
    public void t1()
    {
        System.out.println(new Date().getTime());
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(new Date().getTime());

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
    }

    @Test
    public void t2()
    {
        System.out.println(Math.sqrt(4));
    }
}
