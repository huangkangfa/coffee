package com.hkf.coffee.cache.base;

/**
 * 布尔类型缓存
 */
public class BooleanSPCache extends BaseSPCache {

	public static void put(String key, boolean value) {
		cache().edit().putBoolean(key, value).commit();
	}
	/** 获取缓存 */
	public static boolean get(String key, boolean defValue) {
		return cache().getBoolean(key, defValue);
	}

	/**
	 * 获取缓存
	 * @param key
	 * @param defValue
     * @return
     */
	public static boolean is(String key, boolean defValue) {
		return cache().getBoolean(key, defValue);
	}
}
