package com.gupaoedu.vip;

import java.util.concurrent.atomic.AtomicReference;

public class Test {
    static String finals = "D";

    static class A {

        B b;

        public A(B j) {
            this.b = j;
        }

        public B getA() {
            return b;
        }

        public void setA(B a) {
            this.b = a;
        }
    }

    static class B {
        A a;

        public B(A j) {
            this.a = j;
        }

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }
    }

    public static String getFinals() {
        return finals;
    }

    public static String setFinals(String finalsl) {
        finals = finalsl;
        return finalsl;
    }

    /**
     * public static Integer valueOf(int i) {
     * if (i >= IntegerCache.low && i <= IntegerCache.high)
     * return IntegerCache.cache[i + (-IntegerCache.low)];
     * return new Integer(i);
     * }
     *
     * @param args
     */
    public static void main(String[] args) {

        A a = new A(null);

        B b = new B(null);

        a.setA(b);
        b.setA(a);

        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println(i1.hashCode());
        System.out.println(i2.hashCode());
        System.out.println(i2 == i1);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    setFinals("333" + finalI);
                    System.out.println(finals);
                }
            };
            thread.start();
        }
    }
}
