/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月19日
 */
package cn.obcp.user.shiro.service;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;

/**
 * @author lmf
 *
 */
public class ShiroLifecycleBeanPostProcessorConfig {


	@Bean
	LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
