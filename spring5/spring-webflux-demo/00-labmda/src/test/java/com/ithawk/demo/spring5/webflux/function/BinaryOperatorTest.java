package com.ithawk.demo.spring5.webflux.function;

import org.junit.Test;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class BinaryOperatorTest {

    @Test
    public void test01() {
        BinaryOperator<Integer> bo = (x, y) -> x * y;
        System.out.println(bo.apply(3, 5));   // 15
    }

    @Test
    public void test02() {
        BinaryOperator<Integer> bo = (x, y) -> x * y;
        Function<Integer, String> up = n -> "结果为 " + n;
        // 将(3,5)应用于bo上，再将bo的运算结果作为up的参数进行运算
        System.out.println(bo.andThen(up).apply(3, 5));    // 结果为 15
    }

    @Test
    public void test03() {
        Student s3 = new Student("张三", 23);
        Student s4 = new Student("李四", 24);

        StudentComparator studentComparator = new StudentComparator();
        Student minStu = BinaryOperator.minBy(studentComparator).apply(s3, s4);
        Student maxStu = BinaryOperator.maxBy(studentComparator).apply(s3, s4);

        System.out.println(minStu);   // s3
        System.out.println(maxStu);   // s4
    }
}
