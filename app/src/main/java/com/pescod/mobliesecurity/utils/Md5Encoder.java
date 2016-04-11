package com.pescod.mobliesecurity.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pescod on 4/7/2016.
 */
public class Md5Encoder {
    public static String encode(String password){
        try{
            //获取到数字消息的摘要器
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //执行加密操作
            byte[] result = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            //将每个byte字节的数据转换成十六进制的数据
            for (int i=0;i<result.length;i++){
                int number = result[i]&0xff;
                //将十进制的number转化成十六进制的数据
                String str = Integer.toHexString(number);
                if (str.length()==1){//判断加密后的字符长度，如果长度为1，则在该字符前面补0
                    stringBuilder.append("0");
                    stringBuilder.append(str);
                }else{
                    stringBuilder.append(str);
                }
            }
            return stringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return "";
        }
    }
}
