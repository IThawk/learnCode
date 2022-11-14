package com.ithawk.demo.ejob.springboot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KillServerUtils {
    private static Set<?> ports = new HashSet<>();

    public static void main(String[] args) throws InterruptedException {
        // 输入逻辑
        System.out.println("请输入需要kill的进程端口号，如果有多个以逗号分隔:");
        System.out.print("Please input port here: ");
        Scanner sc = new Scanner(System.in);
        // 不接受空格
        String input = sc.next();
        sc.close();
        // 校验逻辑
        String[] split = input.split(",");
        Set<Integer> ports = new HashSet<>();
        for (String pidStr : split) {
            try {
                int pid = Integer.parseInt(pidStr);
                ports.add(pid);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                //System.out.println(e.getMessage());
                System.out.println("输入的端口号格式不正确，请重新输入!");
                System.out.print("是否重新进行?(y/n): ");
                if ("y".equalsIgnoreCase(sc.next())) {
                    clearConsole();
                    main(args);
                } else {
                    System.out.println("程序结束~");
                    Thread.sleep(1000);
                    System.exit(0);
                }
            }
        }
        // 执行逻辑
        KillServerUtils server = new KillServerUtils();
        server.ports = ports;
        for (Integer pid : ports) {
            server.startKillPort(pid);
        }
        System.out.println("执行成功，程序即将推出...");
        Thread.sleep(2000);
        System.exit(0);
    }

    /**
     * 清空控制台
     */
    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException e) {
            System.out.println("清除控制台出错");
        }
    }

    /**
     * 主要开始方法
     *
     * @param port 端口号
     */
    public static void startKillPort(int port) {
        Runtime runtime = Runtime.getRuntime();
        try {
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                // 查找进程号
                Process p = runtime.exec(String.format("cmd /c netstat -ano | findstr %d", port));
                InputStream is = p.getInputStream();
                List<String> read = read(is, "UTF-8");
                if (read.size() == 0) {
                    System.out.printf("找不到 %d 端口号的进程，继续执行...%n", port);
                } else {
                    System.out.printf("找到 %d 个进程，准备清理...%n", read.size());
                    toSetAndKill(read);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean exPort(int port) {
        Runtime runtime = Runtime.getRuntime();
        try {
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                // 查找进程号
                Process p = runtime.exec(String.format("cmd /c netstat -ano | findstr %d", port));
                InputStream is = p.getInputStream();
                List<String> read = read(is, "UTF-8");
                if (read.size() == 0) {
                    System.out.printf("找不到 %d 端口号的进程，继续执行...%n", port);
                    return true;
                } else {
                    System.out.printf("找到 %d 个进程，准备清理...%n", read.size());
                    return false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 读取输入流的每一行代码
     *
     * @param in      InputStream
     * @param charset 字符集
     * @return 每一行数据的List
     */
    private static List<String> read(InputStream in, String charset) throws IOException {
        List<String> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        String line;
        while ((line = reader.readLine()) != null) {
            boolean validPort = validPort(line);
            if (validPort) {
                data.add(line);
            }
        }
        reader.close();
        return data;
    }

    /**
     * 使用正则表达式执行是否为指定端口，findstr会将包含的所有端口都找出来 例如：80也会包含8083
     *
     * @param str 每一行
     * @return 端口是否有效
     */
    private static boolean validPort(String str) {
        Pattern pattern = Pattern.compile("^ *[a-zA-Z]+ +\\S+");
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()) return false;
        String find = matcher.group();
        int lIndex = find.lastIndexOf(":");
        find = find.substring(lIndex + 1);
        int port;
        try {
            port = Integer.parseInt(find);
        } catch (NumberFormatException e) {
            System.out.println("查找到错误端口: " + find);
            return false;
        }

        return ports.contains(port);
    }

    /**
     * 将每一行进程信息的List转换为只有端口号的Set(去重)
     *
     * @param data 包含有全部端口信息的每一行数据list
     */
    private static void  toSetAndKill(List<String> data) {
        Set<Integer> pids = new HashSet<>();
        for (String line : data) {
            int lIndex = line.lastIndexOf(" ");
            String spid = line.substring(lIndex + 1).trim();
            int pid;
            try {
                pid = Integer.parseInt(spid);
                pids.add(pid);
            } catch (NumberFormatException e) {
                System.out.println("获取进程号错误: " + spid);
            }
            kill(pids);
        }
    }

    /**
     * 一次性kill所有端口
     *
     * @param pids 端口集合
     */
    private static void kill(Set<Integer> pids) {
        for (Integer pid : pids) {
            try {
                Process process = Runtime.getRuntime().exec(String.format("taskkill /F /pid %d", pid));
                InputStream is = process.getInputStream();

                String txt = readTxt(is, "GBK");
                System.out.println(txt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static String readTxt(InputStream in, String charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

}