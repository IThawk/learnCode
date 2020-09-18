package com.gupaoedu.crud.rowmapper;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.RowMapper;

/**
 * @Author: qingshan
 * @Date: 2019/3/31 15:57
 * @Description: 咕泡学院，只为更好的你
 */
public class BaseRowMapper<T> implements RowMapper<T> {

    private Class<?> targetClazz;
    private HashMap<String, Field> fieldMap;

    public BaseRowMapper(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
        fieldMap = new HashMap<>();
        Field[] fields = targetClazz.getDeclaredFields();
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
    }

    @Override
    public T mapRow(ResultSet rs, int arg1) throws SQLException {
        T obj = null;

        try {
            obj = (T) targetClazz.newInstance();

            final ResultSetMetaData metaData = rs.getMetaData();
            int columnLength = metaData.getColumnCount();
            String columnName = null;

            for (int i = 1; i <= columnLength; i++) {
                columnName = metaData.getColumnName(i);
                Class fieldClazz = fieldMap.get(camel(columnName)).getType();
                Field field = fieldMap.get(camel(columnName));
                field.setAccessible(true);

                // fieldClazz == Character.class || fieldClazz == char.class
                if (fieldClazz == int.class || fieldClazz == Integer.class) { // int
                    field.set(obj, rs.getInt(columnName));
                } else if (fieldClazz == boolean.class || fieldClazz == Boolean.class) { // boolean
                    field.set(obj, rs.getBoolean(columnName));
                } else if (fieldClazz == String.class) { // string
                    field.set(obj, rs.getString(columnName));
                } else if (fieldClazz == float.class) { // float
                    field.set(obj, rs.getFloat(columnName));
                } else if (fieldClazz == double.class || fieldClazz == Double.class) { // double
                    field.set(obj, rs.getDouble(columnName));
                } else if (fieldClazz == BigDecimal.class) { // bigdecimal
                    field.set(obj, rs.getBigDecimal(columnName));
                } else if (fieldClazz == short.class || fieldClazz == Short.class) { // short
                    field.set(obj, rs.getShort(columnName));
                } else if (fieldClazz == Date.class) { // date
                    field.set(obj, rs.getDate(columnName));
                } else if (fieldClazz == Timestamp.class) { // timestamp
                    field.set(obj, rs.getTimestamp(columnName));
                } else if (fieldClazz == Long.class || fieldClazz == long.class) { // long
                    field.set(obj, rs.getLong(columnName));
                }

                field.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String camel(String str) {
        Pattern pattern = Pattern.compile("_(\\w)");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            matcher.appendTail(sb);
        }else {
            return sb.toString();
        }
        return camel(sb.toString());
    }
}