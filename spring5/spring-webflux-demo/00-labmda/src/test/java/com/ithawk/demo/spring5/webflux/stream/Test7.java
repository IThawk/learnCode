package com.ithawk.demo.spring5.webflux.stream;

import org.apache.commons.collections.MapUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Test7 {
    private List<Student> students;
    @Before
    public void before() {
        Student student0 = new Student("周零", "清华大学", "男", 20, 95.5);
        Student student1 = new Student("郑一", "清华大学", "女", 22, 98.5);
        Student student2 = new Student("吴二", "北京大学", "男", 21, 95.5);
        Student student3 = new Student("张三", "复旦大学", "男", 24, 90.5);
        Student student4 = new Student("李四", "清华大学", "女", 22, 92.5);
        Student student5 = new Student("王五", "北京大学", "男", 20, 93.5);
        Student student6 = new Student("赵六", "清华大学", "男", 23, 96.5);
        Student student7 = new Student("钱七", "复旦大学", "女", 22, 97.5);
        Student student8 = new Student("秦八", "复旦大学", "男", 21, 99.5);
        Student student9 = new Student("段九", "北京大学", "女", 20, 92.5);

        students = Arrays.asList(student0,student1,student2,student3,
                student4,student5,student6,student7,student8,student9);
    }

    // 获取所有学生姓名列表
    @Test
    public void test01() {
        List<String> names = students.stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(names);
    }

    // 获取所有参赛院校名单
    @Test
    public void test02() {
        Set<String> schools = students.stream()
                .map(Student::getSchool)
                .collect(Collectors.toSet());
        System.out.println(schools);
    }

    // 获取所有参赛院校名单
    @Test
    public void test03() {
        Set<String> schools = students.stream()
                .map(Student::getSchool)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(schools);
    }

    // 获取各个学校的学生（按照学校进行分组）
    @Test
    public void test04() {
        Map<String, List<Student>> schoolGroup = students.stream()
                .collect(Collectors.groupingBy(Student::getSchool));

        // 显示格式可读性很差
        // System.out.println(schoolGroup);

        // 使用工具类显示map中的key-value
        MapUtils.verbosePrint(System.out, "学校", schoolGroup);

        // 获取key为“清华大学”的value，即获取所有清华大学的选手
        System.out.println(schoolGroup.get("清华大学"));
    }

    // 统计各个学校参赛选手人数
    @Test
    public void test05() {
        Map<String, Long> schoolCount = students.stream()
                .collect(Collectors.groupingBy(Student::getSchool,
                                               Collectors.counting())
                        );

        System.out.println(schoolCount);

        // 获取清华大学选手的人数
        System.out.println(schoolCount.get("清华大学"));
    }

    // 按照性别对所有参赛选手分组
    @Test
    public void test06() {
        Map<String, List<Student>> genderGroup = students.stream()
                .collect(Collectors.groupingBy(Student::getGender));

        MapUtils.verbosePrint(System.out, "性别", genderGroup);

        // 获取所有男生
        System.out.println(genderGroup.get("男"));
    }

    // 按照性别对所有参赛选手分组
    @Test
    public void test07() {
        Map<Boolean, List<Student>> genderGroup = students.stream()
                .collect( Collectors.partitioningBy(s -> "男".equals(s.getGender())) );

        MapUtils.verbosePrint(System.out, "性别", genderGroup);

        // 获取所有男生
        System.out.println(genderGroup.get(true));
    }

    // 以95为标准按照成绩将所有参赛选手分组，分为大于95的组及不大于95的组
    @Test
    public void test08() {
        Map<Boolean, List<Student>> genderGroup = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getScore() > 95));

        MapUtils.verbosePrint(System.out, "95划分成绩", genderGroup);

        // 获取所有成绩大于95的学生
        System.out.println(genderGroup.get(true));
    }

    // 以95为标准按照成绩将所有参赛选手分组，分为大于95的组及不大于95的组
    // 对这两组分别计算其平均分
    @Test
    public void test09() {
        Map<Boolean, Double> scoreGroupAvg = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getScore() > 95,
                                                   Collectors.averagingDouble(Student::getScore))
                        );

        System.out.println(scoreGroupAvg);

        // 获取所有成绩大于95的所有学生的平均分
        System.out.println(scoreGroupAvg.get(true));
    }

    // 获取成绩相关的统计数据
    @Test
    public void test10() {
        DoubleSummaryStatistics scoreSummary = students.stream()
                .collect(Collectors.summarizingDouble(Student::getScore));

        System.out.println(scoreSummary);
        // 输出成绩的数量
        System.out.println("成绩个数：" + scoreSummary.getCount());
        // 输出成绩中的最小值
        System.out.println("最小成绩：" + scoreSummary.getMax());

    }
}
