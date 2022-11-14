package com.ithawk.demo.pattern.adapter.poweradapter;

/**
 *
 */
public class PowerAdapterTest {
    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter(new AC220());
        dc5.outoupDC5V();
    }
}
