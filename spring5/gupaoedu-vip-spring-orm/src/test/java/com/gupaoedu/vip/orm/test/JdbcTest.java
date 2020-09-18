package com.gupaoedu.vip.orm.test;

import com.gupaoedu.vip.orm.demo.entity.Member;
import com.gupaoedu.vip.orm.demo.entity.Order;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Created by Tom on 2019/4/20.
 */
public class JdbcTest {

    public static void main(String[] args) {
        Member condition = new Member();
        condition.setName("Tom");
        condition.setAge(19);
        List<?> result =  select(condition);
        System.out.println(Arrays.toString(result.toArray()));
    }

    private static List<?> select(Object condition) {

        List<Object> result = new ArrayList<>();

        Class<?> entityClass = condition.getClass();

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //1、加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //2、建立连接
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gp-vip-spring-db-demo?characterEncoding=UTF-8&rewriteBatchedStatements=true","root","123456");

            //根据类名找属性名
            Map<String,String> columnMapper = new HashMap<String,String>();
            //根据属性名找字段名
            Map<String,String> fieldMapper = new HashMap<String,String>();
            Field[] fields =  entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if(field.isAnnotationPresent(Column.class)){
                    Column column = field.getAnnotation(Column.class);
                    String columnName = column.name();
                    columnMapper.put(columnName,fieldName);
                    fieldMapper.put(fieldName,columnName);
                }else {
                    //默认就是字段名属性名一致
                    columnMapper.put(fieldName, fieldName);
                    fieldMapper.put(fieldName,fieldName);
                }
            }

            //3、创建语句集
            Table table = entityClass.getAnnotation(Table.class);
            String sql = "select * from " + table.name();

            StringBuffer where = new StringBuffer(" where 1=1 ");
            for (Field field : fields) {
                Object value =field.get(condition);
                if(null != value){
                    if(String.class == field.getType()) {
                        where.append(" and " + fieldMapper.get(field.getName()) + " = '" + value + "'");
                    }else{
                        where.append(" and " + fieldMapper.get(field.getName()) + " = " + value + "");
                    }
                    //其他的，在这里就不一一列举，下半截我们手写ORM框架会完善
                }
            }
            System.out.println(sql + where.toString());
            pstm =  con.prepareStatement(sql + where.toString());

            //4、执行语句集
            rs = pstm.executeQuery();

            //元数据？
            //保存了处理真正数值以外的所有的附加信息
            int columnCounts = rs.getMetaData().getColumnCount();
            while (rs.next()){
                Object instance = entityClass.newInstance();
                for (int i = 1; i <= columnCounts; i++) {
                    //实体类 属性名，对应数据库表的字段名
                    //可以通过反射机制拿到实体类的说有的字段

                    //从rs中取得当前这个游标下的类名
                    String columnName = rs.getMetaData().getColumnName(i);
                    //有可能是私有的
                    Field field = entityClass.getDeclaredField(columnMapper.get(columnName));
                    field.setAccessible(true);
                    field.set(instance,rs.getObject(columnName));
                }

                result.add(instance);


            }

            //5、获取结果集
        }catch (Exception e){
            e.printStackTrace();
        }
        //6、关闭结果集、关闭语句集、关闭连接
        finally {
            try {
                rs.close();
                pstm.close();
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }


//    private static List<Member> select(String sql) {
//        List<Member> result = new ArrayList<>();
//        Connection con = null;
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        try {
//            //1、加载驱动类
//            Class.forName("com.mysql.jdbc.Driver");
//            //2、建立连接
//            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gp-vip-spring-db-demo","root","123456");
//            //3、创建语句集
//            pstm =  con.prepareStatement(sql);
//            //4、执行语句集
//            rs = pstm.executeQuery();
//            while (rs.next()){
//                Member instance = new Member();
//                instance.setId(rs.getLong("id"));
//                instance.setName(rs.getString("name"));
//                instance.setAge(rs.getInt("age"));
//                instance.setAddr(rs.getString("addr"));
//                result.add(instance);
//            }
//            //5、获取结果集
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //6、关闭结果集、关闭语句集、关闭连接
//        finally {
//            try {
//                rs.close();
//                pstm.close();
//                con.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }



//    private static List<Member> select(String sql) {
//        List<Member> result = new ArrayList<>();
//        Connection con = null;
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        try {
//            //1、加载驱动类
//            Class.forName("com.mysql.jdbc.Driver");
//            //2、建立连接
//            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gp-vip-spring-db-demo","root","123456");
//            //3、创建语句集
//            pstm =  con.prepareStatement(sql);
//            //4、执行语句集
//            rs = pstm.executeQuery();
//            while (rs.next()){
//                Member instance = mapperRow(rs,rs.getRow());
//                result.add(instance);
//            }
//            //5、获取结果集
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //6、关闭结果集、关闭语句集、关闭连接
//        finally {
//            try {
//                rs.close();
//                pstm.close();
//                con.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    private static Member mapperRow(ResultSet rs, int i) throws Exception {
//        Member instance = new Member();
//        instance.setId(rs.getLong("id"));
//        instance.setName(rs.getString("name"));
//        instance.setAge(rs.getInt("age"));
//        instance.setAddr(rs.getString("addr"));
//        return instance;
//    }

}
