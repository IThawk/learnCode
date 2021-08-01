package com.ithawk.demo.spi.jdk;

import com.ithawk.demo.spi.jdk.service.SomeService;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SPITest {
    public static void main(String[] args) {
        ServiceLoader<SomeService> loader = ServiceLoader.load(SomeService.class);
        Iterator<SomeService> it = loader.iterator();
        while (it.hasNext()) {
            SomeService service = it.next();
            service.hello();
        }
    }
}
