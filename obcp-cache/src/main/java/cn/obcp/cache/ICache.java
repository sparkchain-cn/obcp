package cn.obcp.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICache {
	final long WEEK_SECONDS = 7 * 24 * 60 * 60;
	
	void set(String key, Object value);

	void set(String key, Object value, long expireTime);

	void lpush(String key, Object value);

	void rpush(String key, Object value);

	<T> T lpop(String key, Class<T> clazz);

	<T> T rpop(String key, Class<T> clazz);

	<T> List<T> listGet(String key, Class<T> clazz);

	Long listSize(String key);

	void hashSet(String hashKey, String key, Object val);

	<T> Map<String, T> hashGet(String hashKey, Class<T> clazz);

	<T> T hashGet(String hashKey, String key, Class<T> clazz);

	void hashDel(String hashKey, String key);

	long size(String key);

	boolean exists(final String key);

	<T> T get(String key, Class<T> clazz);

	Set<String> keys(String pattern);

	String get(String key);

	void delete(String key);
}
