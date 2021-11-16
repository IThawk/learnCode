package com.ithawk.demo.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.serializer.ISerializer;


public class FastJsonSeriliazer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {

        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T)JSON.parseObject(new String(data),clazz);
    }
}
