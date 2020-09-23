package myclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author: qingshan
 * @Date: 2019/10/9 11:50
 * @Description: 咕泡学院，只为更好的你
 */
public class MyClient {
    private Socket socket;
    private OutputStream write;
    private InputStream read;

    public MyClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        write = socket.getOutputStream();
        read = socket.getInputStream();
    }

    public void set(String key, String val) throws IOException {
        StringBuffer sb = new StringBuffer();
        // 代表3个参数
        sb.append("*3").append("\r\n");
        // 第一个参数（get）的长度
        sb.append("$3").append("\r\n");
        // 第一个参数的内容
        sb.append("SET").append("\r\n");

        // 第二个参数key的长度
        sb.append("$").append(key.getBytes().length).append("\r\n");
        // 第二个参数key的内容
        sb.append(key).append("\r\n");
        // 第三个参数value的长度
        sb.append("$").append(val.getBytes().length).append("\r\n");
        // 第三个参数value的内容
        sb.append(val).append("\r\n");

        write.write(sb.toString().getBytes());
        byte[] bytes = new byte[1024];
        read.read(bytes);
        System.out.println("-------------set-------------");
        System.out.println(new String(bytes));
    }

    public void get(String key) throws IOException {
        StringBuffer sb = new StringBuffer();
        // 代表2个参数
        sb.append("*2").append("\r\n");
        // 第一个参数(get)的长度
        sb.append("$3").append("\r\n");
        // 第一个参数的内容
        sb.append("GET").append("\r\n");

        // 第二个参数长度
        sb.append("$").append(key.getBytes().length).append("\r\n");
        // 第二个参数内容
        sb.append(key).append("\r\n");

        write.write(sb.toString().getBytes());
        byte[] bytes = new byte[1024];
        read.read(bytes);
        System.out.println("-------------get-------------");
        System.out.println(new String(bytes));
    }

    public static void main(String[] args) throws IOException {
        MyClient client = new MyClient("192.168.8.202", 6379);
        client.set("qingshan", "2673");
        client.get("qingshan");
    }

}
