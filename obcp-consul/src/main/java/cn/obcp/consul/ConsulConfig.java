package cn.obcp.consul;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017年7月10日
 */
@Configuration
public class ConsulConfig {

	@Bean(value = "consulIPFetch")
	@Primary
	public ConsulIPFetch ConsulIPFetch() {
		return new ConsulIPFetch();
	}

}