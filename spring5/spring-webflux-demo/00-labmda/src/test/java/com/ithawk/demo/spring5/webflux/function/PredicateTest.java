package com.ithawk.demo.spring5.webflux.function;

import org.junit.Test;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class PredicateTest {

    @Test
    public void test01() {
        Predicate<Integer> pre = i -> i>8;
        IntPredicate intPre = i -> i<3;
        DoublePredicate doublePre = n -> n<5;

        System.out.println(pre.test(9));  // true
        System.out.println(pre.test(7));  // false

        System.out.println(intPre.test(9));  // false
        System.out.println(intPre.test(2));  // true

        System.out.println(doublePre.test(2));  // true
        System.out.println(doublePre.test(6));  // false
    }

    @Test
    public void test02() {
        Predicate<Integer> gt8 = i -> i>8;
        Predicate<Integer> lt3 = i -> i<3;

        System.out.println(gt8.and(lt3).test(9));  // false
        System.out.println(gt8.or(lt3).test(9));  // true
        System.out.println(gt8.negate().test(9));  // false
    }

    @Test
    public void test03() {
        System.out.println(Predicate.isEqual("Hello").test("hello"));  // false
        System.out.println(Predicate.isEqual("Hello").test("Hello"));  // true
    }

    @Test
    public void test04() {
        Predicate<String> lt3 = (i) -> i.equals("j");
        System.out.println(lt3.test("k"));
        System.out.println(Predicate.isEqual("Hello").test("hello"));  // false
        System.out.println(Predicate.isEqual("Hello").test("Hello"));  // true
    }
}
