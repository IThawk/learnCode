package com.ithawk.demo.netty.springboot.dubbo.server;


import com.alibaba.fastjson.JSON;
import com.ithawk.demo.netty.springboot.dubbo.registry.RegistryCenter;
import com.ithawk.demo.netty.springboot.dubbo.utils.CommandLineUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot中CommandLineRunner的作用
 * > 平常开发中有可能需要实现在项目启动后执行的功能，
 * SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中
 * <p>
 * # 如果有多个类实现CommandLineRunner接口，如何保证顺序
 * > SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，
 * 如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序
 *
 * @Order 注解的执行优先级是按value值从小到大顺序。
 * <p>
 * CommandLineRunner和ApplicationRunner的执行顺序：
 * 在spring boot程序中，我们可以使用不止一个实现CommandLineRunner和ApplicationRunner的bean。
 * 为了有序执行这些bean的run()方法，可以使用@Order注解或Ordered接口。
 * 例子中我们创建了两个实现CommandLineRunner接口的bean和两个实现ApplicationRunner接口的bean。
 * 我们使用@Order注解按顺序执行这四个bean。
 * 如果是spring容器加载成功之后就需要实现 implements   ApplicationListener<ContextRefreshedEvent>
 */
@SpringBootApplication(scanBasePackages = "com.ithawk.demo.netty.springboot.dubbo")
public class RpcServerStarter implements CommandLineRunner {

    @Autowired
    private RpcServer server;

    @Autowired
    private RegistryCenter center;

    /**
     * 在启动的program argument中添加启动参数 ：-h 127.0.0.1 -p 9999
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(args));
        SpringApplication.run(RpcServerStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(JSON.toJSONString(args));
        CommandLine commandLine = CommandLineUtil.buildCommandlineOptions(args);
        String host = "127.0.0.1";
        if (commandLine.hasOption('h')) {
            host = commandLine.getOptionValue('h');
            System.out.println(host);
        }
        String port = "9999";
        if (commandLine.hasOption('p')) {
            String p = commandLine.getOptionValue('p');
            try {
                Integer.parseInt(p);
                port = p;
            } catch (Exception e) {
                System.out.println("输入端口参数必须是数值");
            }

            System.out.println(port);
        }
        server.setCenter(center);
        server.setServiceAddress(host + ":" + port);
        server.publish("com.ithawk.demo.netty.springboot.dubbo.service");
        server.start();
    }
}
