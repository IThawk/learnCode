package com.gupaoedu.vip;

import java.io.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JavaSerializerWithFile implements ISerializer{


    @Override
    public <T> byte[] serialize(T obj) {
        try {
            ObjectOutputStream outputStream=
                    new ObjectOutputStream(new FileOutputStream
                            (new File("user")));

            outputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            ObjectInputStream objectInputStream=
                    new ObjectInputStream(new FileInputStream
                            (new File("user")));
            return (T) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
