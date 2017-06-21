package com.hkf.coffee.intent;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class IntentManager {
    /**
     * 跳转界面 附带过场动画
     * @param context
     * @param startAnim
     * @param endAnim
     */
    public static void goAnimActivity(Context context,Intent intent,int startAnim,int endAnim) {
        ((Activity) context).overridePendingTransition(startAnim, endAnim);
        context.startActivity(intent);
    }
    public static void goAnimActivityWithReturn(Context context,Intent intent,int startAnim,int endAnim, int requstCode) {
        ((Activity) context).overridePendingTransition(startAnim, endAnim);
        ((Activity) context).startActivityForResult(intent, requstCode);
    }

    /**
     * 隐式跳转
     */
    public static void goIntent(Context context, String... data) {
        //创建一个隐式的 Intent 对象：Action 动作
        Intent intent = new Intent();
        //设置 Intent 的动作为清单中指定的action
        intent.setAction(data[0]);
        if (data.length > 1) {
            //添加与清单中相同的自定义category
            intent.addCategory(data[1]);
        }
        context.startActivity(intent);
    }
    public static void goIntentWithReturn(Context context, int requstCode, String... data) {
        Intent intent = new Intent();
        intent.setAction(data[0]);
        if (data.length > 1) {
            intent.addCategory(data[1]);
        }
        ((Activity) context).startActivityForResult(intent, requstCode);
    }

    /**
     * 显式跳转
     */
    public static void goIntent(Context context, Class t) {
        Intent it = new Intent(context, t);
        context.startActivity(it);
    }

    public static void goIntentWithReturn(Context context, Class t, int requstCode) {
        Intent it = new Intent(context, t);
        ((Activity) context).startActivityForResult(it, requstCode);
    }

    /**
     * 跳转至指定网页
     */
    public static void goWebIntent(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void goWebIntentWithReturn(Context context, String url, int requstCode) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ((Activity) context).startActivityForResult(intent, requstCode);
    }


    /**
     * 打开手机自带的移动网络设置
     */
    public static void goSysMobileNetWork(Context context) {
        Intent netSettingsIntent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        context.startActivity(netSettingsIntent);
    }

    /**
     * 打开手机自带的WiFi网络设置
     */
    public static void goSysWiFiNetWork(Context context) {
        Intent wifiSettingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(wifiSettingsIntent);
    }

    /**
     * 电话拨号
     */
    //跳转至拨号界面
    public static void goSysTelIntent(Context context, String tel) {
        Uri uri = Uri.parse("tel:" + tel);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    //直接拨打电话
    public static void goSysCallTelIntent(Context context, String tel) {
        Uri uri = Uri.parse("tel:" + tel);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送短信
     */
    //跳转至短信-不指定人
    public static void goSysSmsIntent(Context context, String str) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("sms_body", str);
        intent.setType("vnd.android-dir/mms-sms");
        context.startActivity(intent);
    }

    //跳转至短信-指定特定的人
    public static void goSysSmsIntentWithSomeone(Context context, String who, String str) {
        Uri uri = Uri.parse("smsto:" + who);//指定接收者
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", str);
        context.startActivity(intent);
    }

    /**
     * 发送邮件
     */
    public static void goSysEmailIntent(Context context, String where, String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + where));
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(intent);
    }

    /**
     * 调用系统音乐播放器
     */
    public static void goSysMusicPlayerIntent(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setDataAndType(uri, "audio/mp3");
        context.startActivity(intent);
    }

    /**
     * 调用系统视频播放器
     */
    public static void goSysVideoPlayerIntent(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setDataAndType(uri, "video/mp4");
        context.startActivity(intent);
    }

    /**
     * 调用系统搜索
     */
    public static void goSysSearchIntent(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, str);
        context.startActivity(intent);
    }

    /**
     * 获取系统图片或者拍摄照片
     */
    private static File file;  //拍照图片缓存地址
    private static String avatarPath; //图片文件地址
    //跳转至获取图片或照片
    public static void goSysBitmapIntent(Context context, int requstCode) {
        //创建打开照相机功能的intent
        File dir = new File(Environment.getExternalStorageDirectory(), "image_cache");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(dir, System.currentTimeMillis() + ".jpg");
        Uri uri = Uri.fromFile(file);
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        photoIntent.putExtra("type", 1);
        avatarPath = file.getAbsolutePath();

        //创建一个打开图库的intent
        Intent storeIntent = new Intent(Intent.ACTION_PICK);
        storeIntent.setType("image/*");
        storeIntent.putExtra("type", 0);
        storeIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        //创建intent chooser
        Intent chooser = Intent.createChooser(photoIntent, "请选择");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{storeIntent});

        //发送chooser
        ((Activity) context).startActivityForResult(chooser, requstCode);
    }
    //onActivity反馈获取图片Uri
    public static Uri onActivityResult_SysBitmapIntent(Context context,Intent data) {
        Uri uri=null;
        if (data == null||data.getData().toString().contains("file:")) {
            //拍照选择的头像文件
            uri=Uri.parse(avatarPath);
        } else if (data.getData().toString().contains("content:")) {
            //是图像在图库中的地址
            uri = data.getData();
            ContentResolver cr = context.getContentResolver();
            Cursor c = cr.query(uri, null, null, null, null);
            c.moveToNext();
            //该图像在sd卡上地址
            avatarPath = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            Bitmap bm = null;
            try {
                bm = MediaStore.Images.Media.getBitmap(cr, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            c.close();
            if(bm!=null)
                uri=Uri.parse(avatarPath);
        }
        File f=new File(uri.toString());
        return f.exists()?uri:null;
    }

}
