package com.hkf.coffee.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hkf.coffee.BaseApplication;
import com.hkf.coffee.dialog.DialogManager;
import com.hkf.coffee.dialog.base.OnClick;
import com.hkf.coffee.file.InstallUtil;
import com.hkf.coffee.jars.nohttp.Headers;
import com.hkf.coffee.jars.nohttp.download.DownloadListener;
import com.hkf.coffee.others.MessageUtil;

import java.io.File;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class UpdateManager {
    private Context mContext;
    //提示语
    private String updateMsg = "检测到有新版本，是否更新？";
    //返回的安装包url
    private static String url;
    //更新提示框
    private DialogManager mDialogManager;
    /* 下载包安装路径 */
    private static String savePath = "/sdcard/Android/data/"+ BaseApplication.getContext().getPackageName()+"/cache";
    private static String saveFileName = "test.apk";
    /* 进度条与通知ui刷新的handler和msg常量 */
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    //下载管理器
    private DownLoadManager manager;
    //下载标识
    public static int what=1;

    private UpdateManager() {}

    public UpdateManager(Context context, String url) {
        this(context, url, saveFileName);
    }

    public UpdateManager(Context context, String url, String saveFileName) {
        this.mContext = context;
        this.url = url;
        this.saveFileName = saveFileName;
        manager=DownLoadManager.getInstance();
        mDialogManager=new DialogManager(mContext);
    }

    /**
     * 更新提示修改
     * @param msg
     */
    public void setUpdateMsg(String msg){
        this.updateMsg=msg;
    }

    /**
     * 展示更新提示框
     */
    public void showUpdateDialog() {
        mDialogManager.showAlertDialog(updateMsg,false, new OnClick() {
            @Override
            public void onClick(Object obj) {
                showDownloadDialog();
            }

            @Override
            public void onCancel(Object obj) {

            }
        });
    }

    /**
     * 展示下载提示框
     */
    private void showDownloadDialog() {
        mDialogManager.hideAlertDialog();
        mDialogManager.setProgressDialog("正在下载中，请稍后...",true,false);
        mDialogManager.showProgressDialog();
        downloadApk();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mDialogManager.setProgress("正在下载中，请稍后..."+(int)msg.obj+"%");
                    break;
                case DOWN_OVER:
                    mDialogManager.hideProgressDialog();
                    InstallUtil.installapk(mContext,savePath +File.separator+ saveFileName);
                    break;
            }
        }

        ;
    };

    /**
     * 下载apk，并安装
     */
    private void downloadApk() {
        manager.download(what, url, savePath, saveFileName, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {

            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {
                if (progress > 99) {
                    MessageUtil.sendMessage(mHandler, DOWN_OVER, 100);
                } else {
                    MessageUtil.sendMessage(mHandler, DOWN_UPDATE, progress);
                }
            }

            @Override
            public void onFinish(int what, String filePath) {

            }

            @Override
            public void onCancel(int what) {

            }
        });
    }
}
