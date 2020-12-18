package com.ithawk.redis.demo.datatype;

import org.apache.commons.io.FileUtils;
import com.ithawk.redis.demo.util.JedisUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.charset.Charset;


/**
 * redis 中上传数据
 * 放了一张图片进去
 */
public class BytesTest {
    public static void main(String[] args) throws IOException {
        System.out.println(Charset.defaultCharset());
        File file = new File("D:/1.png");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        String str = new String(bytes);
        System.out.println(str);
        encoder.encode(bytes);
        JedisUtil.getJedisUtil().set("mybytes", encoder.encode(bytes));


        //获取图片 需要解码
        String byte1 = JedisUtil.getJedisUtil().get("mybytes");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgbyte = decoder.decodeBuffer(byte1);
        OutputStream os = new FileOutputStream("D:/2.png");
        os.write(imgbyte, 0, imgbyte.length);
        os.flush();
        os.close();
    }


}
