package com.ithawk.demo.springboot.starter.autoconfiguration.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.config.XxlJobProperties;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * HTTP任务
 *
 * @author qingshan
 */
@Component
public class HttpJobHandler {
    private static Logger logger = LoggerFactory.getLogger(HttpJobHandler.class);

    @Autowired
    private XxlJobProperties xxlJobProperties;

    /**
     * TODO 后续添加 路由，分片 等功能
     * 跨平台Http任务
     * 参数示例：
     * "url: http://www.baidu.com\n" +
     * "method: get\n" +
     * "data: content\n";
     */
    @XxlJob("httpJobHandler")
    public ReturnT<String> httpJobHandler(String param) throws Exception {

        // param parse
        if (param == null || param.trim().length() == 0) {
            XxlJobLogger.log("param[" + param + "] invalid.");
            return new ReturnT(ReturnT.FAIL_CODE, "param[" + param + "] invalid.");
        }
        String[] httpParams = param.split("\n");
        String url = null;
        String method = null;
        String data = null;
        for (String httpParam : httpParams) {
            if (httpParam.startsWith("url:")) {
                url = httpParam.substring(httpParam.indexOf("url:") + 4).trim();
            }
            if (httpParam.startsWith("method:")) {
                method = httpParam.substring(httpParam.indexOf("method:") + 7).trim().toUpperCase();
            }
            if (httpParam.startsWith("data:")) {
                data = httpParam.substring(httpParam.indexOf("data:") + 5).trim();
            }
            if (httpParam.startsWith("logId:")) {
                String logId = httpParam.substring(httpParam.indexOf("logId:") + 6).trim();
                String key = StringUtils.isNotBlank(xxlJobProperties.getKey()) ? xxlJobProperties.getKey() : EncryptUtils.ECB_EKY_INNER;
                String logIdEn = EncryptUtils.innerEncrypt(key, logId);
                url = url + "?logId=" + logIdEn;
            }
        }

        // param valid
        if (url == null || url.trim().length() == 0) {
            XxlJobLogger.log("url[" + url + "] invalid.");
            return new ReturnT(ReturnT.FAIL_CODE, "url[" + url + "] invalid.");
        }
        if (method == null || !Arrays.asList("GET", "POST").contains(method)) {
            XxlJobLogger.log("method[" + method + "] invalid.");
            return new ReturnT(ReturnT.FAIL_CODE, "method[" + method + "] invalid.");
        }

        // request
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            // connection
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();

            // connection setting
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");

            // do connection
            connection.connect();

            // data
            if (data != null && data.trim().length() > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(data.getBytes("UTF-8"));
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            // valid StatusCode
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }

            // result
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            String responseMsg = result.toString();

            XxlJobLogger.log(responseMsg);
            return new ReturnT(ReturnT.SUCCESS_CODE, responseMsg);
        } catch (Exception e) {
            XxlJobLogger.log(e);
            return new ReturnT(ReturnT.FAIL_CODE, e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e2) {
                XxlJobLogger.log(e2);
            }
        }

    }

}
