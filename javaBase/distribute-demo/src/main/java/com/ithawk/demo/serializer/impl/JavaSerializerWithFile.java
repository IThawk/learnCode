package com.ithawk.demo.serializer.impl;

import com.ithawk.demo.serializer.ISerializer;

import java.io.*;


public class JavaSerializerWithFile implements ISerializer {


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
