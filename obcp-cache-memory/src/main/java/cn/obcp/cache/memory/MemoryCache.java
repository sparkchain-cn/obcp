package cn.obcp.cache.memory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.obcp.cache.ICache;
import net.jodah.expiringmap.ExpiringMap;

public class MemoryCache implements ICache {
	public static ExpiringMap<String, Object> cacheMap = ExpiringMap.builder().variableExpiration().build();

	@Override
	public void set(String key, Object value) {
		
	}

	@Override
	public void set(String key, Object value, long expireTime) {

	}

	@Override
	public void lpush(String key, Object value) {

	}

	@Override
	public void rpush(String key, Object value) {

	}

	@Override
	public <T> T lpop(String key, Class<T> clazz) {

		return null;
	}

	@Override
	public <T> T rpop(String key, Class<T> clazz) {

		return null;
	}

	@Override
	public <T> List<T> listGet(String key, Class<T> clazz) {

		return null;
	}

	@Override
	public Long listSize(String key) {

		return null;
	}

	@Override
	public void hashSet(String hashKey, String key, Object val) {

	}

	@Override
	public <T> Map<String, T> hashGet(String hashKey, Class<T> clazz) {

		return null;
	}

	@Override
	public <T> T hashGet(String hashKey, String key, Class<T> clazz) {

		return null;
	}

	@Override
	public void hashDel(String hashKey, String key) {

	}

	@Override
	public long size(String key) {

		return 0;
	}

	@Override
	public boolean exists(String key) {

		return false;
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {

		return null;
	}

	@Override
	public Set<String> keys(String pattern) {

		return null;
	}

	@Override
	public String get(String key) {

		return null;
	}

	@Override
	public void delete(String key) {

	}
}
