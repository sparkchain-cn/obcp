package cn.obcp.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import cn.obcp.spring.EvnService;
import cn.obcp.spring.SpringInit;
import cn.obcp.spring.UuidCreator;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017/8/28 09:13
 */
@Configuration
@AutoConfigureOrder
public class BaseSpringAutoConfiguration {

	@Value("${com.sparkchain.debug:true}")
	private Boolean debug = true;

	@Value("${com.sparkchain.workerId:0}")
	private int workerId = 0;

	@Bean
	public EvnService evnService(DefaultListableBeanFactory beanFactory, Environment env) {
		return new EvnService(beanFactory, env);
	}

	@Bean
	public UuidCreator uuidCreator() {
		return new UuidCreator(debug, workerId);
	}

	@Bean
	public SpringInit springinit(DefaultListableBeanFactory beanFactory, Environment env) {
		return new SpringInit();
	}

}
