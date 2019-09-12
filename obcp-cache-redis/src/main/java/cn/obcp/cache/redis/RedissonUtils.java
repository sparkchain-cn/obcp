package cn.obcp.cache.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class RedissonUtils {

	@Autowired
	private RedissonClient redisson;

	private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;

	/**
	 * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
	 *
	 * @param key
	 * @param value
	 */
	public void setByObject(String key, Object value) {
		RBucket<String> rBucket = redisson.getBucket(key);
		rBucket.set(value.toString(), WEEK_SECONDS, TimeUnit.SECONDS);
	}

	/**
	 * 将 key，value 存放到redis数据库中
	 *
	 * @param key
	 * @param time
	 * @param value
	 */
//	public void setByObject(String key, Long time, Object value) {
//		RBucket<Object> rBucket = redisson.getBucket(key);
//		rBucket.set(value.toString(), time, TimeUnit.SECONDS);
//	}
//
//	
	public void setByObject(String key, Object value, Long time) {
		RBucket<Object> rBucket = redisson.getBucket(key);
		rBucket.set(value.toString(), time, TimeUnit.SECONDS);
	}
	
	public void setByString(String key, String value) {
		RBucket<String> rBucket = redisson.getBucket(key, StringCodec.INSTANCE);
		rBucket.set(value);
	}
	
	public void setByString(String key, String value, Long time) {
		RBucket<String> rBucket = redisson.getBucket(key, StringCodec.INSTANCE);
		rBucket.set(value, time, TimeUnit.SECONDS);
	}
	
	public void set(String key, String value, Long time) {
		RBucket<Object> rBucket = redisson.getBucket(key);
		rBucket.set(value, time, TimeUnit.SECONDS);
	}

	/**
	 * 获取 key 对应的字符串
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		RBucket<Object> bucket = redisson.getBucket(key);
		return bucket.get() == null ? null : bucket.get().toString();
	}

	public String getByString(String key) {
		RBucket<String> bucket = redisson.getBucket(key, StringCodec.INSTANCE);
		return bucket.get() == null ? null : bucket.get();
	}
	public Object getByObject(String key) {
		RBucket<Object> bucket = redisson.getBucket(key);
		return bucket.get() == null ? null : bucket.get();
	}

	/**
     * 删除KEY
     * @param key
     * @return
     */
    public boolean delKey(String key) {
         return redisson.getBucket(key).delete();
    }
    
	/**
	 * 以 追加map 存放到redis数据库中
	 *
	 * @param key
	 * @param mapKey
	 * @param value
	 */
	public void addByMap(String mapKey, String key, Object value) {
		RMap<String, Object> map = redisson.getMap(mapKey);
		map.put(key, value);
	}

	/**
	 * add  String Code map
	 * @param mapKey
	 * @param key
	 * @param value
	 */
	public void addByMapsStringCodec(String mapKey, String key, Object value) {
		RMap<String, Object> map = redisson.getMap(mapKey,new StringCodec());
		map.put(key, value);
	}
	
	/**
	 * 以覆盖 map 存放到redis数据库中
	 *
	 * @param key
	 * @param mapKey
	 * @param value
	 */
	public void setByMap(String mapKey, String key, Object value) {
		RMap<String, Object> map = redisson.getMap(mapKey);
		map.clear();
		map.put(key, value);
	}

	/**
	 * 以追加形式 map 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void addAllByMap(String key, Map<String, Object> value) {
		RMap<String, Object> map = redisson.getMap(key);
		map.putAll(value);
	}

	/**
	 * 以覆盖形式 map 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void setAllByMap(String key, Map<String, Object> value) {
		RMap<String, Object> map = redisson.getMap(key);
		map.clear();
		map.putAll(value);
	}

	/**
	 * 获取 key 对应的map
	 *
	 * @param key
	 * @return
	 */
	public Map getMap(String key) {
		RMap map = redisson.getMap(key);
		return map;
	}

	/**
	 * 以 Set 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void addBySet(String key, Object value) {
		RSet<Object> set = redisson.getSet(key);
		set.add(value);
	}
	
	public void addBySetString(String key, String value) {
		RSet<String> set = redisson.getSet(key,new StringCodec());
		set.add(value);
	}

	/**
	 * 以 Set 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void setBySet(String key, Object value) {
		RSet<Object> set = redisson.getSet(key);
		set.clear();
		set.add(value);
	}

	/**
	 * 以 Set 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void addAllBySet(String key, Collection value) {
		RSet<Object> set = redisson.getSet(key);
		set.addAll(value);
	}

	/**
	 * 以 Set 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void setAllBySet(String key, Collection value) {
		RSet<Object> set = redisson.getSet(key);
		set.clear();
		set.addAll(value);
	}

	/**
	 * 获取set
	 *
	 * @param key
	 */
	public Set getSet(String key) {
		RSet set = redisson.getSet(key);
		return set;
	}

	/**
	 * 获取Set String
	 * @param key
	 * @return
	 */
	public Set<String> getSetString(String key) {
		RSet<String> set = redisson.getSet(key,new StringCodec());
		return set;
	}
    
    /**
     * 删除Set中值
     * @param key
     * @param val
     * @return
     */
    public boolean delSetVal(String key,Object val) {
        return redisson.getSet(key).remove(val);
   }
    

    /**
     * 删除String Set中值
     * @param key
     * @param val
     * @return
     */
	public boolean delSetStringVal(String key,String val) {
        return redisson.getSet(key,new StringCodec()).remove(val);
	}
    
	/**
	 * 以追加 List 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void addByList(String key, Object value) {
		RList list = redisson.getList(key);
		list.add(value);
	}

	/**
	 * 以覆盖 List 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void setByList(String key, Object value) {
		RList list = redisson.getList(key);
		list.clear();
		list.add(value);
	}

	/**
	 * 以 追加List 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void addAllByList(String key, List value) {
		RList list = redisson.getList(key);
		list.addAll(value);
	}

	/**
	 * 以覆盖 List 存放到redis数据库中
	 *
	 * @param key
	 * @param value
	 */
	public void setAllByList(String key, List value) {
		RList list = redisson.getList(key);
		list.clear();
		list.addAll(value);
	}

	/**
	 * 获取 List
	 *
	 * @param key
	 */
	public List getList(String key) {
		RList list = redisson.getList(key);
		return list;
	}

	public void del(String key) {
		RBucket<Object> bucket = redisson.getBucket(key);
		bucket.delete();
	}
}
