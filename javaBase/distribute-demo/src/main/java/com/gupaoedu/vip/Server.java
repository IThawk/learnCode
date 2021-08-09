package com.gupaoedu.vip;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
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
