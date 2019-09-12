package cn.obcp.user.shiro.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

public class RedisManager extends WorkAloneRedisManager implements IRedisManager {

	//private static final String DEFAULT_HOST = "127.0.0.1:6379";
	private String host;

	// timeout for jedis try to connect to redis server, not expire time! In milliseconds
	private int timeout ;

	private String password;

	private int port;
	
	private int database;

	private JedisPool jedisPool;

	private void init() {
		synchronized (this) {
			if (jedisPool == null) {
				//String[] hostAndPort = host.split(":");
				jedisPool = new JedisPool(getJedisPoolConfig(), host, port, timeout, password, database);
			}
		}
	}

	@Override
	protected Jedis getJedis() {
		if (jedisPool == null) {
			init();
		}
		return jedisPool.getResource();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
