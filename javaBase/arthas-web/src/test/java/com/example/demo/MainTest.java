package com.example.demo;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainTest {

    public static void main(String[] args) throws IOException {
        boolean linux = isLinux();
        if (linux) {
            Process exec = Runtime.getRuntime().exec("/data/arthas.sh");

       /* InputStream inputStream = exec.getInputStream();

        InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + "\r\n");
        }
        s = sb.toString();*/

            System.out.println("arthas 启动成功！" + JSON.toJSONString(exec));
        } else {

            Process exec1 = Runtime.getRuntime().exec("jps");
            System.out.println("jps" + JSON.toJSONString(exec1));
            Process exec = Runtime.getRuntime().exec("D:/workspace/language/project/arthas-web-master/data/as.bat 22328");
            InputStream inputStream = exec1.getInputStream();

            InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            StringBuffer sb = new StringBuffer();
            while ((s = br.readLine()) != null) {
                sb.append(s + "\n\r");
            }
            System.out.println();
            System.out.println("arthas 启动成功！" + JSON.toJSONString(sb.toString()));
            System.out.println("arthas 启动成功！" + JSON.toJSONString(exec));
        }

    }

    private static boolean isLinux() {
        String osName = System.getProperties().getProperty("os.name");
        System.out.println(osName);
        boolean linux = false;
        if (osName.equals("Linux")) {
            linux = true;
            System.out.println("linux");
        } else if (osName.contains("Windows")) {
            System.out.println("Windows");
        } else {
            System.out.println("other");
        }
        return linux;
    }

}
