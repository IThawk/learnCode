package com.xxl.job.core.util;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.codec.Base64;
import sun.security.action.GetPropertyAction;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;

public class EncryptUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptUtils.class);

    public final static String ECB_EKY_INNER = "112121212222";

    public static String sm4EcbEncrypt(String key, String data) {
        byte[] keyByte = Base64.decode(key);
        byte[] enByte = null;
        try {
            enByte = SM4Utils.encrypt_Ecb_Padding(keyByte, data.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            LOGGER.error("sm4EcbEncrypt error ：{}", ExceptionUtils.getStackTrace(e));
        }
        return enByte == null ? null : Base64.encode(enByte);
    }


    public static String sm4EcbDecrypt(String key, String data) {
        byte[] keyByte = Base64.decode(key);
        byte[] filestr = Base64.decode(data);
        byte[] deByte = null;
        try {
            deByte = SM4Utils.decrypt_Ecb_Padding(keyByte, filestr);
        } catch (Exception e) {
            LOGGER.error("sm4EcbDecrypt error ：{}", ExceptionUtils.getStackTrace(e));
        }
        return deByte == null ? null : new String(deByte);
    }


    //内部加密
    public static String innerEncrypt(String key, String data){

        String encodeStr = sm4EcbEncrypt(key, data);
        try {
            String charSet = AccessController.doPrivileged(new GetPropertyAction("file.encoding"));

            encodeStr = URLEncoder.encode(encodeStr, charSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    //内部解密
    public static String innerDecrypt( String key, String data) throws UnsupportedEncodingException {
        String charSet = AccessController.doPrivileged(new GetPropertyAction("file.encoding"));

        data= URLDecoder.decode(data, charSet);

        return sm4EcbDecrypt(key,data);
    }
}