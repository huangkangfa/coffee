package com.hkf.coffee.others.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public class ReadWriteLockUtil {
    private ReadWriteLock rwl = null; //读写锁对象
    private ReadWriteListener listener=null; //具体的读写操作

    public ReadWriteLockUtil(ReadWriteListener listener){
        rwl = new ReentrantReadWriteLock(); //读写锁对象
        this.listener=listener;
    }

    //写操作
    public boolean write(Object... w){
        rwl.writeLock().lock();// 取到写锁
        try {
            return listener.write(w);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            rwl.writeLock().unlock();// 释放写锁
        }
    }
    //读操作
    public Object read(Object... r){
        rwl.readLock().lock();// 取到读锁
        try {
            return listener.read(r);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            rwl.readLock().unlock();// 释放读锁
        }
    }
}
