package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * HTTP任务
 *
 * @author qingshan
 */
@Component
public class HttpJobHandler {
    private static Logger logger = LoggerFactory.getLogger(HttpJobHandler.class);

    /**
     * 跨平台Http任务
     *  参数示例：
     *      "url: http://www.baidu.com\n" +
     *      "method: get\n" +
     *      "data: content\n";
     */
    @XxlJob("httpJobHandler")
    public ReturnT<String> httpJobHandler(String param) throws Exception {

        // param parse
        if (param==null || param.trim().length()==0) {
            XxlJobLogger.log("param["+ param +"] invalid.");
            return ReturnT.FAIL;
        }
        String[] httpParams = param.split("\n");
        String url = null;
        String method = null;
        String data = null;
        for (String httpParam: httpParams) {
            if (httpParam.startsWith("url:")) {
                url = httpParam.substring(httpParam.indexOf("url:") + 4).trim();
            }
            if (httpParam.startsWith("method:")) {
                method = httpParam.substring(httpParam.indexOf("method:") + 7).trim().toUpperCase();
            }
            if (httpParam.startsWith("data:")) {
                data = httpParam.substring(httpParam.indexOf("data:") + 5).trim();
            }
        }

        // param valid
        if (url==null || url.trim().length()==0) {
            XxlJobLogger.log("url["+ url +"] invalid.");
            return ReturnT.FAIL;
        }
        if (method==null || !Arrays.asList("GET", "POST").contains(method)) {
            XxlJobLogger.log("method["+ method +"] invalid.");
            return ReturnT.FAIL;
        }

        // request
        URLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            // connection
            URL realUrl = new URL(url);
            JSONObject jsonObject = null;
            String result = "";
            BufferedReader in = null;
            try {
                String urlNameString = url + "?" + param;
                // 打开和URL之间的连接
                connection = realUrl.openConnection();
                connection.connect();
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                jsonObject = JSONObject.fromObject(result);
            } catch (Exception e) {
                System.out.println("发送GET请求出现异常！" + e);
                e.printStackTrace();
            }
            // 使用finally块来关闭输入流
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            String responseMsg = result.toString();
            JSONObject jsonFile = JSONObject.fromObject(result);
            String write = jsonFile.getString("datas");
            XxlJobLogger.log(write);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
                XxlJobLogger.log(e2);
            }
        }

    }

}
