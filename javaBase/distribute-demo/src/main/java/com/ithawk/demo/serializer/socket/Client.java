package com.ithawk.demo.serializer.socket;

import com.ithawk.demo.serializer.bean.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author IThawk
 * @className Client
 * @description: 网络编程client,需要启动 server
 * @date 2021/11/13 17:21
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = null;

        socket = new Socket("localhost", 8080);

        User user = new User();
        user.setAge(18);
        user.setName("IThawk");

        ObjectOutputStream out = new ObjectOutputStream
                (socket.getOutputStream());
        out.writeObject(user);

        socket.close();


    }
}
