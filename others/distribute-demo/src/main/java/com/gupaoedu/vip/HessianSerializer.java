package com.gupaoedu.vip;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class HessianSerializer implements ISerializer{
    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        HessianOutput hessianOutput=new HessianOutput(outputStream);
        try {
            hessianOutput.writeObject(obj);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream inputStream=new ByteArrayInputStream(data);
        HessianInput hessianInput=new HessianInput(inputStream);
        try {
            return (T)hessianInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
