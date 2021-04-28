package com.xkcoding.orm.mybatis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    static String enfileName = "i18n/message.EN.properties";
    static String cnfileName = "i18n/message.CN.properties";
    static String wnfileName = "i18n/message.WN.properties";
    private static Properties propsCN;
    private static Properties propsEN;
    private static Properties propsWN;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        logger.info("开始加载properties文件内容.......");
        propsCN = new Properties();
        propsEN = new Properties();
        propsWN = new Properties();
        InputStream in1 = null;
        InputStream in2 = null;
        InputStream in3 = null;

        try {
//　　　　　　　<!--第一种，通过类加载器进行获取properties文件流-->
            in1 = PropertyUtil.class.getClassLoader().getResourceAsStream(enfileName);
//　　　　　　  第二种，通过类进行获取properties文件流
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            propsEN.load(in1);
            in2 = PropertyUtil.class.getClassLoader().getResourceAsStream(cnfileName);

            //解決中文问题
            Resource[] resources1 = new Resource[]{new InputStreamResource(in2)};

            for (Resource resource : resources1) {
                PropertiesLoaderUtils.fillProperties(propsCN, new EncodedResource(resource, "UTF-8"));
            }

            in3 = PropertyUtil.class.getClassLoader().getResourceAsStream(wnfileName);
            Resource[] resources2 = new Resource[]{new InputStreamResource(in3)};

            for (Resource resource : resources2) {
                PropertiesLoaderUtils.fillProperties(propsWN, new EncodedResource(resource, "UTF-8"));
            }

        } catch (FileNotFoundException e) {
            logger.error("jdbc.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in1) {
                    in1.close();
                }
                if (null != in2) {
                    in2.close();
                }
                if (null != in3) {
                    in3.close();
                }
                propsEN.setProperty("COMPLETE", "COMPLETE");
                propsCN.setProperty("COMPLETE", "完成");
                propsWN.setProperty("COMPLETE", "完成");
            } catch (IOException e) {
                logger.error("jdbc.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + propsCN);
    }

    public static String getProperty(int model, String key) throws UnsupportedEncodingException {
        if (null == propsCN || null == propsEN || null == propsWN) {
            loadProps();
        }
        switch (model) {
            case 0:
                return propsEN.getProperty(key);
            case 1:
                return propsCN.getProperty(key);
            default:
                return propsWN.getProperty(key);
        }
    }

    public static String getProperty(int model, String key, String defaultValue) {
        if (null == propsCN || null == propsEN || null == propsWN) {
            loadProps();
        }
        switch (model) {
            case 0:
                return propsEN.getProperty(key, defaultValue);
            case 2:
                return propsCN.getProperty(key, defaultValue);
            default:
                return propsWN.getProperty(key, defaultValue);
        }

    }
}
