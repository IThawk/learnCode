package com.baiqi.elasticsearch.es_sql;

import org.elasticsearch.xpack.sql.jdbc.EsDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *  图灵学院-白起老师
 * 使用JDBC来操作ES
 */
public class JdbcExample {
    public static void main(String[] args) throws Exception {
        // 1. 加载ES驱动
        Class.forName(EsDriver.class.getName());

        // 2. 建立连接
        Connection connection = DriverManager.getConnection("jdbc:es://http://192.168.21.130:9200");

        // 3. 准备SQL语句
        String sql = "select payway, count(*) cnt from order_idx group by payway";

        // 4. 使用PreparedStatement执行SQL
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5. 遍历结果
        while(resultSet.next()) {
            byte payway = resultSet.getByte("payway");
            long cnt = resultSet.getLong("cnt");

            System.out.println("支付方式：" + payway + " 订单数量：" + cnt);
        }

        // 6. 关闭连接
        resultSet.close();
        connection.close();
    }
}
