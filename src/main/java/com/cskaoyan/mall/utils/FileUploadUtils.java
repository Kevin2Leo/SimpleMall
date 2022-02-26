package com.cskaoyan.mall.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class FileUploadUtils {

    public static Map<String, Object> parseRequest(HttpServletRequest request){
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        //利用这个factory拿到文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //可以解决上传的文件中文名乱码
        upload.setHeaderEncoding("utf-8");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //前端页面提交的每一个input，对应这里面的每一个FileItem
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iterator = items.iterator();
            while (iterator.hasNext()){
                FileItem item = iterator.next();
                //我们要对表单数据和上传的文件区别对待
                if(item.isFormField()){
                    processFormField(item, map);
                }else {
                    //上传的文件
                    processUploadFile(item, map, request);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void processUploadFile(FileItem item, Map<String, Object> map, HttpServletRequest request) {
        String fieldName = item.getFieldName();
        String fileName = item.getName();
        String s = UUID.randomUUID().toString();
        //filename后面是由文件后缀名的
        fileName = s + "-" + fileName;
        String contentType = item.getContentType();
        boolean isInMemory = item.isInMemory();
        long sizeInBytes = item.getSize();
        System.out.println(fieldName + ":" + fileName + ":" + contentType + ":" + isInMemory + ":" + sizeInBytes);
        int hashCode = fileName.hashCode();
        String hexString = Integer.toHexString(hashCode);
        char[] chars = hexString.toCharArray();
        String basePath = "image";
        for (char aChar : chars) {
            basePath = basePath + "/" + aChar;
        }
        String relativePath = basePath + "/" + fileName;
        String absolutePath = request.getServletContext().getRealPath(relativePath);
        File file = new File(absolutePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            item.write(file);
            map.put(fieldName, relativePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //对于普通的form表单数据，下面两个属性已经足够用了
    private static void processFormField(FileItem item, Map<String, Object> map) {
        String fieldName = item.getFieldName();
        String value = null;
        try {
            value = item.getString("utf-8");
            map.put(fieldName, value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(fieldName + ":" + value);
    }
}
