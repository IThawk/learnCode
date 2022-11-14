package com.kaikeba.conversion;

public class IntegerTypeHandler implements TypeHandler{
    @Override
    public Object handleValue(String[] valueArray) {
        return Integer.parseInt(valueArray[0]);
    }
}
