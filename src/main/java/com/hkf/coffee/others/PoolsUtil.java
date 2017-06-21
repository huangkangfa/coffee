package com.hkf.coffee.others;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class PoolsUtil {
    private ExecutorService pools;
    public final static int newCachedThreadPool=0;
    public final static int newFixedThreadPool=1;
    public final static int newScheduledThreadPool=2;
    public final static int SingleThreadExecutor =3;

    public PoolsUtil(int... data){
        if(data==null){
            pools = Executors.newCachedThreadPool();
        }else{
            switch (data[0]){
                case newCachedThreadPool:
                    pools = Executors.newCachedThreadPool();
                    break;
                case newFixedThreadPool:
                    pools = Executors.newFixedThreadPool(data[1]);
                    break;
                case newScheduledThreadPool:
                    pools = Executors.newScheduledThreadPool(data[1]);
                    break;
                case SingleThreadExecutor:
                    pools = Executors.newSingleThreadExecutor();
                    break;
            }
        }
    }

    //添加线程任务
    public void addTask(Runnable runnable){
        pools.execute(runnable);
    }

    //当前池内任务完成后关闭
    public void close(){
        pools.shutdown();
    }

    //尝试立马关闭
    public void closeNow(){
        pools.shutdownNow();
    }
}
