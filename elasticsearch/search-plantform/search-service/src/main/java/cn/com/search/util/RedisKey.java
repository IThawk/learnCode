package cn.com.search.util;

public class RedisKey {

	private static String KEY_USER = "search-admin_user_%d";//用户信息key
	
	public static String key_user(Integer userId) {
		return String.format(KEY_USER, userId);
	}
}
