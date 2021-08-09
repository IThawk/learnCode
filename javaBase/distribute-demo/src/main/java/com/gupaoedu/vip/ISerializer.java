package com.gupaoedu.vip;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface ISerializer {

    <T> byte[] serialize(T obj);


    <T> T deserialize(byte[] data,Class<T> clazz);
}
