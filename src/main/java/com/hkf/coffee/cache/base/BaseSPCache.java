package com.hkf.coffee.cache.base;

import android.content.SharedPreferences;

import com.hkf.coffee.BaseApplication;
import com.hkf.coffee.phone.InfoUtil;

/**
 * 缓存管理(基于SharedPreferences封装)
 * @author wanglei 2015年8月3日 上午10:55:50
 */
public class BaseSPCache {
    private static SharedPreferences sharedPreferences;

    protected static SharedPreferences cache() {
        if (sharedPreferences == null) {
            sharedPreferences = BaseApplication.getContext().getSharedPreferences(InfoUtil.getPackageName(), BaseApplication.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}
