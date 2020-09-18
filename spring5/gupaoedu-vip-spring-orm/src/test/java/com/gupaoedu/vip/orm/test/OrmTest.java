package com.gupaoedu.vip.orm.test;

import com.gupaoedu.vip.orm.demo.dao.MemberDao;
import com.gupaoedu.vip.orm.demo.dao.OrderDao;
import com.gupaoedu.vip.orm.demo.entity.Member;
import com.gupaoedu.vip.orm.demo.entity.Order;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Tom.
 */
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrmTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmdd");

    @Autowired private MemberDao memberDao;

    @Autowired private OrderDao orderDao;

    //ORM（对象关系映射 Object Relation Mapping）
    //Hibernate/Spring JDBC/MyBatis/JPA 一对多、多对多、一对一

    //Hibernate 全自动档  不需要写一句SQL语句
    //MyBatis 半自动（手自一体） 支持简单的映射，复杂关系，需要自己写SQL
    //Spring JDBC 全手动挡，所有的SQL都要自己写，它帮我们设计了一套标准  模板模式

    //为什么有了MyBatis我还要自己的手写ORM框架呢？
    //1、用MyBatis，我可控性无法保证
    //2、我有不敢用Hibernate，高级玩家玩的，
    //3、没有时间自己从0到1写一个ORM框架
    //4、站在巨人的肩膀上再升级，做二次开发

    //约定优于配置
    //1、先制定顶层接口,参数返回值全部统一
    // List<?> Page<?>  select(QueryRule queryRule)
    // Int   delete(T entity) entity中的ID不能为空，如果ID为空，其他条件不能为空，都为空不予执行
    // ReturnId  insert(T entity) 只要entity不等于null
    // Int  update(T entity) entity中的ID不能为空，如果ID为空，其他条件不能为空，都为空不予执行

    //基于JDBC封装了一套
    //基于Redis封装了一套
    //基于MongoDB
    //基于ElasticSearch
    //基于Hive
    //基于HBase

    //QueryRule

    @Test
    public void testSelectAllForMember(){
        try {
            List<Member> result = memberDao.selectAll();
            System.out.println(Arrays.toString(result.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void testInsertMember(){
        try {
            for (int age = 25; age < 35; age++) {
                Member member = new Member();
                member.setAge(age);
                member.setName("Tom");
                member.setAddr("Hunan Changsha");
                memberDao.insert(member);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
//	@Ignore
    public void testInsertOrder(){
        try {
            Order order = new Order();
            order.setMemberId(1L);
            order.setDetail("历史订单");
            Date date = sdf.parse("20180201123456");
            order.setCreateTime(date.getTime());
            orderDao.insertOne(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
