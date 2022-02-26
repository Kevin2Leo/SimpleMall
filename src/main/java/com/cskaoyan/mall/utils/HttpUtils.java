package com.cskaoyan.mall.utils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 获取请求 参数，并将其转换成json字符串的形式
 */
public class HttpUtils {

    //静态方法 直接使用类名调用
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        //请求参数 在请求体中,需要获得请求体 获取请求体的方法
        ServletInputStream inputStream = request.getInputStream();

        //输入流变成字符串  ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int length = 0;
        byte[] bytes =new byte[1024];
        while ((length = inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,length);
        }
        String requestBody = outputStream.toString("utf-8");
        return requestBody;
    }
}
