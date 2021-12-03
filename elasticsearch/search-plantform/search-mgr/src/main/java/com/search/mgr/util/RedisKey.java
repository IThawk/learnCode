package com.search.mgr.util;

public class RedisKey {

	private static String KEY_USER = "search-mgr_user_%d";// 用户信息key

	private static String cache_key = "search_mgr_%s:%s";

	public static String cache_keys(String biz, String key) {
		return String.format(cache_key, biz, key);
	}

	public static String key_user(Integer userId) {
		return String.format(KEY_USER, userId);
	}
}
