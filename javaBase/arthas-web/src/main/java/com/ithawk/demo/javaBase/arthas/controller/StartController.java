package com.ithawk.demo.javaBase.arthas.controller;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.javaBase.arthas.bean.JavaProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping
@Slf4j
public class StartController {

    @Value("${asdir}")
    private String asdir;

    /**
     * @param
     * @description: 获取Java进程号信息
     * @return: java.util.List<com.ithawk.demo.javaBase.arthas.bean.JavaProcess>
     * @author IThawk
     * @date: 2021/12/30 16:46
     */
    @GetMapping("/jps")
    @ResponseBody
    public List<JavaProcess> getJps() throws IOException {
        Process exec1 = Runtime.getRuntime().exec("jps");
        log.info("jps" + JSON.toJSONString(exec1));
        InputStream inputStream = exec1.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        List<JavaProcess> processList = new ArrayList<>();
        while ((s = br.readLine()) != null) {
            JavaProcess javaProcess = new JavaProcess();
            String[] process = s.split(" ");
            javaProcess.setProcessId(process[0]);
            javaProcess.setProcessName(process.length > 1 ? process[1] : "");
            processList.add(javaProcess);
            sb.append(s + "\n\r");
        }
        log.info(JSON.toJSONString(processList));
        log.info("jps : " + JSON.toJSONString(sb.toString()));
        return processList;

    }

    @RequestMapping("/start")
    @ResponseBody
    public String startArthas(@RequestParam("id") String id) throws IOException {
        boolean linux = isLinux();
        Process exec;
        log.info(asdir);
        if (linux) {
            exec = Runtime.getRuntime().exec("arthas.sh " + id + " --target-ip 0.0.0.0");
        } else {
            exec = Runtime.getRuntime().exec(asdir + "as.bat " + id + " --target-ip 0.0.0.0");
        }
        InputStream inputStream = exec.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + "\n\r");
        }
        log.info("arthas 启动成功！" + JSON.toJSONString(sb.toString()));
        log.info("arthas 启动成功！" + JSON.toJSONString(exec));
        return sb.toString();
    }

    @RequestMapping("html")
    public String html(String one) throws IOException {
        return "index";
    }

    static boolean isLinux() {
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