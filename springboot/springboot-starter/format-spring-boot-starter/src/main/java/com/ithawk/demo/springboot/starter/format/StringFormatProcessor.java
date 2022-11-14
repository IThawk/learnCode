package com.ithawk.demo.springboot.starter.format;

import java.util.Objects;

public class StringFormatProcessor implements FormatProcessor {


    @Override
    public <T> String format(T obj) {
        return "StringFormatProcessor:" + Objects.toString(obj);
    }
}
