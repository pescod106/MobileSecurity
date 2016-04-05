package com.pescod.mobliesecurity.utils;

import android.app.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pescod on 4/5/2016.
 * 下载的工具类：下载文件的路径；下载文件后保存的路径；关心进度条；上下文
 */
public class DownloadUtil {

    /**
     * 下载一个文件
     * @param urlPath 路径
     * @param filePath 保存到本地的文件路径
     * @param pd 进度条对话框
     * @return 下载后的apk
     */
    public static File getFile(String urlPath, String filePath, ProgressDialog pd){
        try{
            File file = new File(filePath);
            URL url = new URL(urlPath);
            FileOutputStream fos = new FileOutputStream(file);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            //获取要下载的apk的文件总长度
            int max = conn.getContentLength();
            //将进度条的的最大值设置为要下载的文件的总长度
            pd.setMax(max);
            //获取要下载的文件的输入流
            InputStream is = conn.getInputStream();
            //设置一个缓冲区
            byte[] buffer = new byte[1024];
            int len = 0;
            int process = 0;
            while ((len = is.read(buffer))!=-1){
                fos.write(buffer,0,len);
                //每读取依次输入流，就刷新一次下载进度
                process += len;
                pd.setProgress(process);
                //设置睡眠时间，便于观察下载进度
                Thread.sleep(1);
            }
            //刷新缓存数据到文件中
            fos.flush();
            //关流
            fos.close();
            is.close();
            return file;
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取一个路径中的文件名
     * @param urlPath
     * @return
     */
    public static String getFileName(String urlPath){
        return urlPath.substring(urlPath.lastIndexOf("/")+1,urlPath.length());
    }
}
