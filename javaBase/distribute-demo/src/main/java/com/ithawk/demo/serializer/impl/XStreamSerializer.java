package com.ithawk.demo.serializer.impl;

import com.ithawk.demo.serializer.ISerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class XStreamSerializer implements ISerializer {

    XStream xStream=new XStream(new DomDriver());

    @Override
    public <T> byte[] serialize(T obj) {

        return xStream.toXML(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T)xStream.fromXML(new String(data));
    }
}
