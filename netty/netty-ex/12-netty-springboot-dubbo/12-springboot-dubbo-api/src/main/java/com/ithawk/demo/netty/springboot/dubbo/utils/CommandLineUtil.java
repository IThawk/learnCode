package com.ithawk.demo.netty.springboot.dubbo.utils;

import org.apache.commons.cli.*;

public class CommandLineUtil {


    //从启动命令中获取 启动参数。
    public static CommandLine buildCommandlineOptions(final Options options, String[] args) {
        //添加一个option hasArg:是否从启动中获取
        Option opt = new Option("p", "port", true, "bind port");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("n", "name", true,
                "Name ");
        opt.setRequired(false);
        options.addOption(opt);


        opt = new Option("h", "host", true,
                "hostName ");
        opt.setRequired(false);
        options.addOption(opt);
        CommandLineParser parser = new PosixParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return commandLine;
    }

    public static CommandLine buildCommandlineOptions(String[] args) {
        Options options = new Options();
        //添加一个option hasArg:是否从启动中获取
        Option opt = new Option("p", "port", true, "bind port");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("n", "name", true,
                "Name ");
        opt.setRequired(false);
        options.addOption(opt);


        opt = new Option("h", "host", true,
                "hostName ");
        opt.setRequired(false);
        options.addOption(opt);
        CommandLineParser parser = new PosixParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return commandLine;
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(new Option("c", "help", true, "Print help"));
        CommandLine commandLine = buildCommandlineOptions(options, args);
        if (commandLine.hasOption('h')) {
            String file = commandLine.getOptionValue('h');
            System.out.println(file);
        }


    }
}

