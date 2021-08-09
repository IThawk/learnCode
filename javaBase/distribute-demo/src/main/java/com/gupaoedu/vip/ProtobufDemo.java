package com.gupaoedu.vip;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ProtobufDemo {

    public static void main(String[] args) {
        UserProtos.User user=UserProtos.User.newBuilder().
                setAge(300).setName("Mic").build();

        byte[] bytes=user.toByteArray();
        System.out.println(bytes.length);

        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]+" ");
        }
        //10 3 77 105 99 16 18

    }
}
