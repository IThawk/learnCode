package com.gupaoedu.vip;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket=null;

        socket=new Socket("localhost",8080);

        User user=new User();
        user.setAge(18);
        user.setName("Mic");

        ObjectOutputStream out=new ObjectOutputStream
                (socket.getOutputStream());
        out.writeObject(user);

        socket.close();


    }
}
