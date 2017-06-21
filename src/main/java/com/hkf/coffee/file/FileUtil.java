package com.hkf.coffee.file;

import android.content.Context;

import com.hkf.coffee.file.base.OnUpdateListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件工具类
 *
 * 删除创建文件或文件夹
 * 剪切或复制文件或文件夹至指定地方
 * 获取手机缓存地址
 * 大小转换成平常可读的样式
 *
 * Created by Administrator on 2016/11/8 0008.
 */
public class FileUtil {
    /** 网络下载file到指定目录*/
    public static void downFile(String fileName, String url_, File path, Context context, OnUpdateListener listener) {
        if (!path.exists()) {
            path.mkdir();
        }
        try {
            File file = new File(path.getAbsolutePath() + "/" + fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            boolean flag = (context == null);
            URL url = new URL(url_);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            long length = conn.getContentLength();
            long count = 0;
            InputStream in = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[4000];
            int d = -1;
            int jd = 0;
            while ((d = in.read(buf)) != -1) {
                fos.write(buf, 0, d);
                count += d;
                jd = (int) (count * 100 / length);
                if (!flag) {
                    //进度信息反馈
                    listener.updateChange(jd);
                }
            }
            fos.close();
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**下载获取文本*/
    public static String getStringFromInternet(URL url) {
        String s = "";
        try {
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is, "gbk"));
            String line=null;
            while((line=br.readLine())!=null){
                s+=line;
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**指定文件重命名*/
    public static boolean changeFileName(File directory, String fromName,
                                         String toName) {
        try {
            File from = new File(directory, fromName);
            File to = new File(directory, toName);
            from.renameTo(to);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**指定文件或目录 删除*/
    public static boolean deleteFileOrDirectory(File file, boolean flag) {
        return delete(file, flag);
    }
    private static boolean delete(File file, boolean flag) {
        try {
            if (file.isFile()) {
                file.delete();
                return true;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    file.delete();
                    return true;
                }
                for (File f : files) {
                    delete(f, false);
                }
                if (flag == false)
                    file.delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**读取文件*/
    public static String readFile(File f) {
        if (!f.exists()||f.isDirectory()) {
            return null;
        }
        String result = null;
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,
                    "gbk"));
            String line = null;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**读取文件*/
    public static String readFile(File f,String readType) {
        if (!f.exists()||f.isDirectory()) {
            return null;
        }
        String result = null;
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,readType));
            String line = null;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**写入文件*/
    public static boolean writeFile(File f, String data) {
        try {
            fileIsExist(f);
            OutputStream os = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(f);
            pw.write(data);
            pw.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean writeFile(File f, InputStream data) {
        try {
            fileIsExist(f);
            OutputStream os = new FileOutputStream(f);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = data.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**判定文件是否存在，不存在则创建*/
    public static void fileIsExist(File f) {
        try {
            if (f.exists()) {
                if (f.isDirectory()) {
                    f.delete();
                    f.createNewFile();
                }
            } else f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}