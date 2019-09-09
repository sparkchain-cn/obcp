package cn.obcp.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

import com.sparkchain.framework.exception.BaseException;
import com.sparkchain.framework.utils.BeanUtils;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017/9/6 10:43
 */
public class SpringUtils {
	protected final static Logger logger = LoggerFactory.getLogger(SpringInit.class);

	public static Object getTargetBean(Object bean) {
		Object target = bean;
		while (AopUtils.isAopProxy(target)) {
			try {
				target = ((Advised) target).getTargetSource().getTarget();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return target;
	}

	public static Object invokeBySpring(String servername, String methodname, Class[] types,
			List<?> objlist) {

		Object service = SpringInit.getApplicationContext().getBean(servername);
		Object obj = null;
		try {
			obj = BeanUtils.invokePrivateMethod(service, methodname, types,
					objlist.toArray());
		} catch (NoSuchMethodException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
			e.printStackTrace();
		}
		return obj;
	}

	public static Object invokeBySpringArr(String servername,
			String methodname, Class[] types, Object[] arr) {

		Object service = SpringInit.getApplicationContext().getBean(servername);
		Object obj = null;
		try {
			obj = BeanUtils.invokePrivateMethod(service, methodname, types, arr);
		} catch (NoSuchMethodException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
			e.printStackTrace();
		}
		return obj;
	}

	public static Object invokeBySpringParams(String servername,
			String methodname, Class[] types, Object... params) {

		Object service = SpringInit.getApplicationContext().getBean(servername);
		if (service == null) {
			throw new BaseException("1001",
					"can not find spring service:" + servername + ", check the spring scan or bean name.");
		}
		Object obj = null;
		try {
			obj = BeanUtils.invokePrivateMethod(service, methodname, types, params);
		} catch (NoSuchMethodException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
			e.printStackTrace();
		}
		return obj;
	}

}
