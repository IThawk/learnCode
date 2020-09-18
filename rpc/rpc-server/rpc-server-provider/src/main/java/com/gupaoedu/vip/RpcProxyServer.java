package com.gupaoedu.vip;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RpcProxyServer {


   /* ExecutorService executorService= Executors.newCachedThreadPool();

    public void publisher(Object service,int port){

        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            while(true){//不断接受请求
                Socket socket=serverSocket.accept();//BIO
                //每一个socket 交给一个processorHandler来处理
                executorService.execute(new ProcessorHandler(socket,service));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }*/
}
