package cn.obcp.http.http.Uuid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time  2017年7月7日 
 *
 */
@Configuration
public class HttpUuidConfig extends WebMvcConfigurerAdapter {

	@Value("${http.uuid.enable:false}")
	boolean enable = false;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (enable) {
			// 多个拦截器组成一个拦截器链
			// addPathPatterns 用于添加拦截规则
			// excludePathPatterns 用户排除拦截
			registry.addInterceptor(new HttpUuidInterceptor()).addPathPatterns("/**");

			super.addInterceptors(registry);
		}
	}

}