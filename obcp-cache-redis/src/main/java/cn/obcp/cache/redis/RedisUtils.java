package cn.obcp.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;

import cn.obcp.cache.ICache;

//@Service
public class RedisUtils implements ICache{

	private StringRedisTemplate redisTemplate;

	public RedisUtils(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
	 *
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		long startTime = System.nanoTime(); // 获取开始时间
		if (value instanceof String) {
			redisTemplate.opsForValue().set(key, (String) value, WEEK_SECONDS,
					TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, JSON.toJSONString(value),
					WEEK_SECONDS, TimeUnit.SECONDS);
		}
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
	}

	/**
	 * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void set(String key, Object value, long expireTime) {
		long startTime = System.nanoTime(); // 获取开始时间
		if (value instanceof String) {
			redisTemplate.opsForValue().set(key, (String) value, expireTime,
					TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, JSON.toJSONString(value),
					expireTime, TimeUnit.SECONDS);
		}
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
	}

	/**
	 * 判断 key 是否在 redis 数据库中
	 *
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		long startTime = System.nanoTime(); // 获取开始时间
		boolean b = redisTemplate.hasKey(key);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return b;
	}

	/**
	 * 获取与 key 对应的对象
	 *
	 * @param key
	 * @param clazz
	 *            目标对象类型
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {
		long startTime = System.nanoTime(); // 获取开始时间

		String s = get(key);
		if (s == null) {
			return null;
		}
		T t = JSON.parseObject(s, clazz);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return t;
	}

	/**
	 * 该方法可能会有性能上的问题
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		long startTime = System.nanoTime(); // 获取开始时间
		Set set = redisTemplate.keys(pattern);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return set;
	}

	/**
	 * 获取 key 对应的字符串
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		long startTime = System.nanoTime(); // 获取开始时间
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 删除 key 对应的 value
	 *
	 * @param key
	 */
	public void delete(String key) {
		long startTime = System.nanoTime(); // 获取开始时间
		redisTemplate.delete(key);
	}

	public void setByIndex(String key, Object value, long index) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		ops.set(index, JSON.toJSONString(value));
	}

	public void lpush(String key, Object value) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		ops.leftPush(JSON.toJSONString(value));
	}

	public void rpush(String key, Object value) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		ops.rightPush(JSON.toJSONString(value));
	}

	public <T> T lpop(String key, Class<T> clazz) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		String s = ops.leftPop();
		if (s == null) {
			return null;
		}
		return JSON.parseObject(s, clazz);
	}

	public <T> T rpop(String key, Class<T> clazz) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		String s = ops.rightPop();
		if (s == null) {
			return null;
		}
		return JSON.parseObject(s, clazz);
	}

	public <T> List<T> range(String key, Class<T> clazz, long start, long end) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		List<String> rawList = ops.range(start, end);
		List<T> list = new ArrayList<>();
		for (String s : rawList) {
			list.add(JSON.parseObject(s, clazz));
		}
		return list;
	}

	public <T> List<T> listPage(String key, Class<T> clazz, long start,
			long end) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		List<String> rawList = ops.range(start, end);
		List<T> list = new ArrayList<>();
		for (String s : rawList) {
			list.add(JSON.parseObject(s, clazz));
		}
		return list;
	}

	public <T> List<T> listGet(String key, Class<T> clazz) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		List<String> rawList = ops.range(0, ops.size());
		List<T> list = new ArrayList<>();
		for (String s : rawList) {
			list.add(JSON.parseObject(s, clazz));
		}
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return list;
	}

	public Long listSize(String key) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		long size = ops.size();
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return size;
	}

	public void hashSet(String hashKey, String key, Object val) {

		long startTime = System.nanoTime(); // 获取开始时间

		BoundHashOperations<String, String, Object> operations = redisTemplate
				.boundHashOps(hashKey);
		operations.put(key, val);

		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
	}
	public void hashInc(String hashKey, String key, long deta) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundHashOperations<String, String, Object> operations = redisTemplate
				.boundHashOps(hashKey);
		operations.increment(key, deta);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");

	}

	// HINCRBY myhash field 1

	public <T> Map<String, T> hashGet(String hashKey, Class<T> clazz) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundHashOperations<String, String, T> operations = redisTemplate
				.boundHashOps(hashKey);
		Map<String, T> data = operations.entries();
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return data;
	}

	public boolean hashExists(String hashKey, String key) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundHashOperations<String, String, Object> operations = redisTemplate
				.boundHashOps(hashKey);
		Object obj = operations.get(key);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		if (obj == null) {
			return false;
		}

		return true;
	}

	public <T> T hashGet(String hashKey, String key, Class<T> clazz) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundHashOperations<String, String, T> operations = redisTemplate
				.boundHashOps(hashKey);
		T obj = operations.get(key);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return obj;
	}

	public void hashDel(String hashKey, String key) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundHashOperations<String, String, Object> operations = redisTemplate
				.boundHashOps(hashKey);
		operations.delete(key);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");

	}

	public long size(String key) {
		long startTime = System.nanoTime(); // 获取开始时间
		BoundListOperations<String, String> ops = redisTemplate
				.boundListOps(key);
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
		return ops.size();
	}
}