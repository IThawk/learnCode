package com.ithawk.demo.spring.transaction.service;

import com.ithawk.demo.spring.transaction.MailSendEvent;
import com.ithawk.demo.spring.transaction.MailSendEvent1;
import com.ithawk.demo.spring.transaction.dao.MemberDao;
import com.ithawk.demo.spring.transaction.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     可以看出，默认的值是Propagation.REQUIRED；其他的还有：
 *
 * 1>PROPAGATION_REQUIRED:支持当前事务，假设当前没有事务。就新建一个事务。
 *
 * 2>PROPAGATION_SUPPORTS:支持当前事务，假设当前没有事务，就以非事务方式运行。
 *
 * 3>PROPAGATION_MANDATORY:支持当前事务，假设当前没有事务，就抛出异常。
 *
 * 4>PROPAGATION_REQUIRES_NEW:新建事务，假设当前存在事务。把当前事务挂起。
 *
 * 5>PROPAGATION_NOT_SUPPORTED:以非事务方式运行操作。假设当前存在事务，就把当前事务挂起。
 *
 * 6>PROPAGATION_NEVER:以非事务方式运行，假设当前存在事务，则抛出异常。
 *
 * 7>PROPAGATION_NESTED:如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。
 * </p>
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

    /**
     * 没有添加事务，异常不会回滚
     * @param id
     * @return
     * @throws Exception
     */
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

    /**
     * 这个会回滚
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean remove1(long id) throws Exception {
        boolean r = memberDao.delete(id);
        throw new Exception("自定义异常");
    }


    /**
     * 这个不会回滚
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean remove2(long id) throws Exception {
        boolean r = memberDao.delete(id);
        return r;
    }

    /**
     * 部分回滚
     * @param id
     * @return
     * @throws Exception
     */
    public boolean removes(long id) throws Exception {
        remove(id);
        remove1(3);
        throw new Exception("自定义异常");
    }

    /**
     * 全部回滚
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removets(long id) throws Exception {
        remove(id);
        remove1(3);
        throw new Exception("自定义异常");
    }


    /**
     * 事务传播行为
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class,propagation= Propagation.REQUIRES_NEW)
    public boolean removetpropagationsREQUIRES_NEW(long id) throws Exception {
        remove2(id);
        remove1(3);
        throw new Exception("自定义异常");
    }

    /**
     * 事务传播行为
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class,propagation= Propagation.REQUIRES_NEW)
    public boolean removetpropagations(long id) throws Exception {
        remove(id);
        remove1(3);
        throw new Exception("自定义异常");
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
