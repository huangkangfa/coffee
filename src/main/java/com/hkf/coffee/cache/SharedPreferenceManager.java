package com.hkf.coffee.cache;

import com.hkf.coffee.cache.base.BooleanSPCache;
import com.hkf.coffee.cache.base.StringSPCache;

/**
 * Created by huangkangfa on 2017/3/15 0015.
 */
public class SharedPreferenceManager {
    private static BooleanSPCache booleanCache=new BooleanSPCache();
    private static StringSPCache stringCache=new StringSPCache();

    public static BooleanSPCache getBooleanCache(){
        return booleanCache;
    }

    public static StringSPCache getStringCache(){
        return stringCache;
    }
}
