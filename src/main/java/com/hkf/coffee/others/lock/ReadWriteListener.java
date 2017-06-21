package com.hkf.coffee.others.lock;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public interface ReadWriteListener {
    public boolean write(Object... obj);
    public Object read(Object... obj);
}
