package com.ithawk.demo.spring.transaction.service;

import com.ithawk.demo.spring.transaction.MailSendEvent;
import com.ithawk.demo.spring.transaction.MailSendEvent1;
import com.ithawk.demo.spring.transaction.dao.MemberDao;
import com.ithawk.demo.spring.transaction.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom.
 */
@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberDao memberDao;


    @Transactional(readOnly = true)
    public List<Member> queryAll() throws Exception {
        return memberDao.selectAll();
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean add(Member member) throws Exception {
        boolean r = memberDao.insert(member);
        throw new Exception("自定义异常");
//        return r;
    }

    public boolean remove(long id) throws Exception {
        boolean r = memberDao.delete(id);
        throw new Exception("自定义异常");
//		return r;
    }

    public boolean modify(long id, String name) throws Exception {
        return memberDao.update(id, name);
    }

    public boolean login(long id, String name) throws Exception {
        boolean modify = this.modify(id, name);
//		throw new Exception("测试无事务");
        return modify;
    }


    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");


        Thread runnable = new Thread() {
            @Override
            public void run() {
                MailSendEvent event = new MailSendEvent(applicationContext, "1");
                System.out.println("1");
                applicationContext.publishEvent(event);

            }
        };
        runnable.start();

        Thread runnable1 = new Thread() {
            @Override
            public void run() {
                MailSendEvent event2 = new MailSendEvent(applicationContext, "2");
                System.out.println("2");
                applicationContext.publishEvent(event2);
            }
        };
        runnable1.start();


        Thread runnable2 = new Thread() {
            @Override
            public void run() {
                MailSendEvent1 event1 = new MailSendEvent1(applicationContext, "2");
                System.out.println("3");
                applicationContext.publishEvent(event1);
            }
        };
        runnable2.start();

        System.out.println("事件发布完成");
        while (true) ;
    }


}
