package com.kaikeba.conversion;

public class StringTypeHandler implements TypeHandler{
    @Override
    public Object handleValue(String[] valueArray) {
        return valueArray[0];
    }
}
