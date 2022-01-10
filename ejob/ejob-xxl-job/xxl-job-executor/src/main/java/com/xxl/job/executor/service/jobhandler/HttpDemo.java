package com.xxl.job.executor.service.jobhandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import net.sf.json.JSONObject;

/**
 * 首页
 * http://gjjzx.changsha.gov.cn/hdjl/xjlist.html?siteId=151&appId=22&order=2
 */
public class HttpDemo {
    public static JSONObject sendGet(String url, String param) {
        JSONObject jsonObject = null;
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            connection.setRequestProperty("Expires", "0");
            // 建立实际的连接
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
        return jsonObject;
    }

    public static void main(String[] args) {
        //发送 GET 请求
        JSONObject jsonFile = HttpDemo.sendGet("http://hd.changsha.gov.cn/IGI/nbhd/openGovmsgbox.do?method=listGovmsgboxs&siteId=151&order=2&appId=22&pageIndex=2&pageSize=15&orderBy=finishTime_desc", "");
        System.out.println("datas:" + jsonFile.getString("datas"));
    }
}
