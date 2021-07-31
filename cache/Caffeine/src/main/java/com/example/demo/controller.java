//package com.example.demo;
//
//import com.alibaba.fastjson.JSON;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
///**
// * @Author: xinzhifu
// * @Description:
// */
//@Controller
//@RequestMapping
//public class controller {
//
//    @Value("${asdir}")
//    private String asdir;
//
//    @RequestMapping("/jps")
//    @ResponseBody
//    public String getJps() throws IOException {
//        Process exec1 = Runtime.getRuntime().exec("jps");
//        System.out.println("jps" + JSON.toJSONString(exec1));
//        InputStream inputStream = exec1.getInputStream();
//        InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
//        BufferedReader br = new BufferedReader(reader);
//        String s = null;
//        StringBuffer sb = new StringBuffer();
//        while ((s = br.readLine()) != null) {
//            sb.append(s + "\n\r");
//        }
//        System.out.println();
//        System.out.println("arthas 启动成功！" + JSON.toJSONString(sb.toString()));
//        return sb.toString();
//
//    }
//
//    @RequestMapping("/start")
//    @ResponseBody
//    public String getJps(String id) throws IOException {
//        boolean linux = isLinux();
//        Process exec;
//        if (linux) {
//            exec = Runtime.getRuntime().exec("/data/arthas.sh");
//        } else {
//            System.out.println(asdir);
//            exec = Runtime.getRuntime().exec(asdir + " "+ id);
//        }
//        InputStream inputStream = exec.getInputStream();
//        InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
//        BufferedReader br = new BufferedReader(reader);
//        String s = null;
//        StringBuffer sb = new StringBuffer();
//        while ((s = br.readLine()) != null) {
//            sb.append(s + "\n\r");
//        }
//        System.out.println();
//        System.out.println("arthas 启动成功！" + JSON.toJSONString(sb.toString()));
//        System.out.println("arthas 启动成功！" + JSON.toJSONString(exec));
//        return sb.toString();
//    }
//
//    //TODO 结束进程
//    @RequestMapping("html")
//    public String html(String one) throws IOException {
//
//        return "index";
//    }
//
//    static boolean isLinux() {
//        String osName = System.getProperties().getProperty("os.name");
//        System.out.println(osName);
//        boolean linux = false;
//        if (osName.equals("Linux")) {
//            linux = true;
//            System.out.println("linux");
//        } else if (osName.contains("Windows")) {
//            System.out.println("Windows");
//        } else {
//            System.out.println("other");
//        }
//        return linux;
//    }
//}