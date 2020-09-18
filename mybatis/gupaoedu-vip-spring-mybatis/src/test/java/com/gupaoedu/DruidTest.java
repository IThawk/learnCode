package com.gupaoedu;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @Author: qingshan
 * @Date: 2019/3/14 20:08
 * @Description: 咕泡学院，只为更好的你
 */
public class DruidTest {
    public static void main(String[] args) throws Exception {
        //密码明文
        String password = "123456";
        System.out.println("密码[ "+password+" ]的加密信息如下：\n");

        String [] keyPair = ConfigTools.genKeyPair(512);
        //私钥
        String privateKey = keyPair[0];
        //公钥
        String publicKey = keyPair[1];
        //用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);

        System.out.println("privateKey:"+privateKey);
        System.out.println("publicKey:"+publicKey);
        System.out.println("password:"+password);
        String decryptPassword=ConfigTools.decrypt(publicKey, password);
        System.out.println("decryptPassword："+decryptPassword);

    }
}
