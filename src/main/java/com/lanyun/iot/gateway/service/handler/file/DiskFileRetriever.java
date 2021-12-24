package com.lanyun.iot.gateway.service.handler.file;

import com.lanyun.iot.gateway.model.exception.DeviceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 17:04 2018/10/7
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
@Component
public class DiskFileRetriever {


    private static final long MAX_LEN = 1024 * 1024;

    @Cacheable(value = "upgradeFilePathCache", key = "#root.args")
    public byte[] getFileContentByPath(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new DeviceException("file not exist: " + filePath);
            }
            if (file.isDirectory()) {
                throw new DeviceException("file is a directory: " + filePath);
            }
            if (file.length() > MAX_LEN) {
                throw new DeviceException("file " + filePath + " length " + file.length() + " is great than the max limit " + MAX_LEN);
            }
            //
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(fis, baos);
            fis.close();
            return baos.toByteArray();
        } catch (IOException e) {
            log.error("IOException", e);
            return null;
        }
    }

    @Cacheable(value = "upgradeFileUrlCache", key = "#root.args")
    public byte[] getFileContentByUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            // 打开和URL之间的连接
            URLConnection conn = url.openConnection();
            // 设置通用的请求属性
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(80000);
            conn.setReadTimeout(80000);
            //Post 请求不能使用缓存
            conn.setUseCaches(false);
            //调用URLConnection对象提供的connect方法连接远程服务
            conn.connect();

            //通过URL打开了通道，拿到了输入流对象
            InputStream inStream = conn.getInputStream();
            //创建缓冲流对象加强功能
            //BufferedInputStream bis =new BufferedInputStream(inStream);

            //byte[] buffer = new byte[1204];
            int count = conn.getContentLength();
            byte[] bytes = new byte[count];
            int readCount = 0;
            while (readCount < count) {
                readCount += inStream.read(bytes, readCount, count - readCount);
            }
            //关闭流
            inStream.close();
            return bytes;

        } catch (Exception e) {
            e.printStackTrace();
            return  null ;
        }
    }

}
