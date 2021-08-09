package com.gupaoedu.vip;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class XStreamSerializer implements ISerializer{

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
