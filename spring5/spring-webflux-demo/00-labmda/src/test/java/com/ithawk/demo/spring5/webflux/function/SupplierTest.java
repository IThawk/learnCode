package com.ithawk.demo.spring5.webflux.function;

import org.junit.Test;

import java.util.function.Supplier;

public class SupplierTest {

    @Test
    public void test01() {
        Supplier<String> supp = () -> "Lambda";
        System.out.println(supp.get());
    }

}
