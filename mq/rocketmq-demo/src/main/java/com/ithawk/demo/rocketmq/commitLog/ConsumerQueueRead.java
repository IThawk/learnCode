package com.ithawk.demo.rocketmq.commitLog;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

public class ConsumerQueueRead {
    public static void main(String[] args)throws Exception {
        String path = "/Users/hadoop/store/consumequeue/TopicTest/1/00000000000006000000";

        ByteBuffer buffer = read(path);
        while (true){
            long offset = buffer.getLong();
            long size = buffer.getInt();
            long code = buffer.getLong();
            if (size==0){
                break;
            }
            System.out.println("消息长度:"+size+" 消息偏移量:" +offset);
        }
        System.out.println("--------------------------");
    }

    public static ByteBuffer read(String path)throws Exception{
        File file = new File(path);
        FileInputStream fin = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fin.read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer;
    }

}
