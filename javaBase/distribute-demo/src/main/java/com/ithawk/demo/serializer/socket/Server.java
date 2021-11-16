package com.ithawk.demo.serializer.socket;

import com.ithawk.demo.serializer.bean.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @className Server
 * @description:   网络编程server
 * @author IThawk
 * @date 2021/11/13 17:23
 */
public class Server {



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket=null;

        serverSocket=new ServerSocket(8080);

        Socket socket=serverSocket.accept(); //建立好连接

        ObjectInputStream objectInputStream=
                new ObjectInputStream(socket.getInputStream());

        User user=(User)objectInputStream.readObject();

        System.out.println(user);

    }
}
