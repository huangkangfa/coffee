package com.hkf.coffee.http;

import com.hkf.coffee.jars.nohttp.NoHttp;
import com.hkf.coffee.jars.nohttp.download.DownloadListener;
import com.hkf.coffee.jars.nohttp.download.DownloadQueue;

/**
 * Created by huangkangfa on 2017/6/18.
 */

public class DownLoadManager {
    private static DownLoadManager mDownLoadManager;
    private DownloadQueue queue;

    public static DownLoadManager getInstance() {
        if (mDownLoadManager == null) {
            synchronized (DownLoadManager.class) {
                if (mDownLoadManager == null) {
                    mDownLoadManager = new DownLoadManager();
                }
            }
        }
        return mDownLoadManager;
    }

    private DownLoadManager(){
        queue=NoHttp.newDownloadQueue();
    }

    public void download(int what,String url,String fileDir,String fileName,DownloadListener listener){
        queue.add(what, NoHttp.createDownloadRequest(url, fileDir, fileName, true, true), listener);
    }
    public void download(int what,String url,String fileDir,String fileName,boolean isRange,boolean isDeleteOld,DownloadListener listener){
        queue.add(what, NoHttp.createDownloadRequest(url, fileDir, fileName, isRange, isDeleteOld), listener);
    }
}
