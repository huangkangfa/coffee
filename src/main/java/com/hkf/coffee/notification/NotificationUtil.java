package com.hkf.coffee.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class NotificationUtil {
    private static NotificationManager mNotificationManager;

    public static void sendDefaultNotification(Context context,int smallIcon,int largeIcon,String title,String content,Class intentClass,int requestCode,int flag){
        sendDefaultNotification(context,smallIcon,largeIcon,title,content,intentClass,true,false,true,requestCode,flag);
    }

    /**
     * 发送默认通知栏
     * @param context 上下文
     * @param smallIcon 小图标
     * @param largeIcon 大图标
     * @param title 标题
     * @param content 文本内容
     * @param intentClass 跳转目的地
     * @param showTime 是否显示展示时间
     * @param ongoing 通知栏是否常驻
     * @param autoCancel 点击后是否自动清除
     * @param requestCode 请求编号
     * @param flag 标识编号
     */
    public static void sendDefaultNotification(Context context,int smallIcon,int largeIcon,String title,String content,Class intentClass,boolean showTime,boolean ongoing,boolean autoCancel,int requestCode,int flag){
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setContentTitle(title)   //设置标题
                .setContentText(content) //设置内容
                .setShowWhen(showTime)  //设置不显示时间
                .setOngoing(ongoing)  //设置常驻通知
                .setAutoCancel(autoCancel); //点击后自动清除
        if(largeIcon!=-1){
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),largeIcon)); //如果不设置大图标  那么小图标会代替大图标的位置
        }
        if(smallIcon!=-1){
            builder.setSmallIcon(smallIcon);  //设置默认图标
        }
        //点击处理
        Intent it=new Intent(context,intentClass);
        it.addCategory(Intent.CATEGORY_LAUNCHER);
        it.setAction(Intent.ACTION_MAIN);
        PendingIntent pI=PendingIntent.getActivity(context,requestCode,it,flag);
        builder.setContentIntent(pI);
        mNotificationManager.notify(requestCode,builder.build());
    }

}










