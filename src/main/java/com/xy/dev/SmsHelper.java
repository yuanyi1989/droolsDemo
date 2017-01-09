package com.xy.dev;

/**
 * Created by 袁意 on 2017/1/5.
 */
public class SmsHelper {

    public static void sendSMS(String phone, String content){
        System.out.println("向手机用户发送短信，号码："+phone+";内容："+content);
    }
}
