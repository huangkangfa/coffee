package com.hkf.coffee.others.lock;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 采用读写锁的方式
 * 保证读写分离
 * 写写分离   读读可以共存
 * Created by huangkangfa on 2017/5/12 0012.
 */
public class SafeList<K, T> implements ReadWriteListener {
    private final static int add = 0;//添加一个设备
    private final static int addAll = 1;//添加一组设备
    private final static int removeByOrder = 2;//根据编号删除设备
    private final static int removeByKey = 3;//根据Key删除设备
    private final static int getByKey = 4;//根据Key获取设备
    private final static int getByOrder = 5;//根据序号获取设备

    //操作标识
    private int flag = -1;

    //数据源
    private LinkedHashMap<K, T> data;
    private ReadWriteLockUtil util;

    public SafeList() {
        data = new LinkedHashMap<K, T>();
        util = new ReadWriteLockUtil(this);
    }

    //获取数据长度
    public int size() {
        return data.size();
    }

    //添加一个设备
    public boolean add(K k, T t) {
        flag = add;
        return util.write(k, t);
    }

    //添加一组设备
    public boolean addAll(LinkedHashMap<K, T> kt) {
        flag = addAll;
        return util.write(kt);
    }

    //根据编号删除设备
    public boolean removeByOrder(int num) {
        flag = removeByOrder;
        return util.write(num);
    }

    //根据key删除设备
    public boolean removeByKey(K k) {
        flag = removeByKey;
        return util.write(k);
    }

    //读取指定key的数据
    public T getByKey(K k) {
        flag = getByKey;
        return (T) util.read(k);
    }

    //读取指定序号的数据
    public T getByOrder(int num) {
        flag = getByKey;
        return (T) util.read(num);
    }

    @Override
    public boolean write(Object... obj) {
        try {
            switch (flag) {
                case add:
                    data.put((K) obj[0], (T) obj[1]);
                    break;
                case addAll:
                    for (Map.Entry x : ((LinkedHashMap<K, T>) obj[0]).entrySet()) {
                        data.put((K) x.getKey(), (T) x.getValue());
                    }
                    break;
                case removeByOrder:
                    K k = null;
                    int num = (int) obj[0];
                    for (Map.Entry x : data.entrySet()) {
                        if (num == 0) {
                            k = (K) x.getKey();
                        } else {
                            num--;
                        }
                    }
                    if (k == null) {
                        return false;
                    }
                    data.remove(k);
                    break;
                case removeByKey:
                    data.remove((K) obj[0]);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Object read(Object... obj) {
        T result = null;
        try {
            switch (flag) {
                case getByKey:
                    result = data.get((K) obj[0]);
                    break;
                case getByOrder:
                    int num = (int) obj[0];
                    for (Map.Entry x : data.entrySet()) {
                        if (num < 0) {
                            break;
                        } else if (num == 0) {
                            result = (T) x.getValue();
                            break;
                        } else {
                            num--;
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
