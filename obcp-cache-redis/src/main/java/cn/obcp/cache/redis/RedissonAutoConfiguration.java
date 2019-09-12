package cn.obcp.cache.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {
	@Autowired
	private RedissonProperties redssionProperties;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Value("${spring.redis.host:}")
	String redisIP = "";

	@Value("${spring.redis.port:}")
	String redisPort = "";

	/**
	 * 单机模式自动装配
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(name = "redisson.address")
	RedissonClient redissonSingle() {
		Config config = new Config();
		SingleServerConfig serverConfig = config.useSingleServer().setAddress(redssionProperties.getAddress())
				.setTimeout(redssionProperties.getTimeout())
				.setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
				.setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize())
				.setDatabase(redssionProperties.getDatabase());

		// if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
		if (!StringUtils.isEmpty(redssionProperties.getPassword())) {
			serverConfig.setPassword(redssionProperties.getPassword());
		}

		return Redisson.create(config);
	}

	@Bean(name = "redissonUtils")
	@ConditionalOnProperty(name = "redisson.address")
	RedissonUtils redissonUtils() {
		return new RedissonUtils();
	}

	@Primary
	@Bean(name = "redisUtils")
	RedisUtils RedisUtils() {
		if (StringUtils.isEmpty(redisIP) || StringUtils.isEmpty(redisPort)) {
			System.out.println("no config redisIP or redisPort, cannot new RedisUtils!");
			return null;
		} else {
			return new RedisUtils(redisTemplate);
		}
	}
}
