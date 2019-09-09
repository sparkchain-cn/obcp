package cn.obcp.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sparkchain.framework.utils.BeanUtils;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017年7月14日
 * @wetsite:www.mgicode.com
 * @license:GPL
 */
//@Component
public class SpringInit implements ApplicationContextAware {

    protected final static Logger logger = LoggerFactory
            .getLogger(SpringInit.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if (SpringInit.applicationContext == null) {
            SpringInit.applicationContext = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        if (getApplicationContext() == null) {
            // TracingClient.error("SpringContext上下文还没有初始化，就调用了其上下文进行跟踪处理");
            if (logger.isErrorEnabled()) {
                logger.error(
                        "springboot没有取其applicationContext，没有初始化SpringInit的上下文！");
            }
            return null;
        }
        if (getApplicationContext().containsBean(name)) {

            return getApplicationContext().getBean(name);
        } else {
            return null;
        }
    }

    public static String getConfig(String name) {
        if (applicationContext != null) {
            return getApplicationContext().getEnvironment().getProperty(name);
        }
        logger.warn("cann't found spring applicationContext, maybe spring being starting. ");
        return null;
    }

    public static String getServiceName(){
        return getConfig("spring.application.name");
    }



    public static Object invoke(String servername, String methodname,
                                Class[] clses, Object... objects) {

        Object service = SpringInit.getApplicationContext().getBean(servername);
        Object obj = null;
        try {
            obj = BeanUtils.invokePrivateMethod(service, methodname, clses, objects);
        } catch (NoSuchMethodException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
            e.printStackTrace();
        }
        return obj;

    }


    // public static Object invokeBySpring(String servername, String methodname,
    // List<?> objlist) {
    //
    // Object service = SpringInit.getApplicationContext().getBean(servername);
    // Object obj = null;
    // try {
    // obj = BeanUtils.invokePrivateMethod(service, methodname,
    // objlist.toArray());
    // } catch (NoSuchMethodException e) {
    // if (logger.isErrorEnabled()) {
    // logger.error(e.getMessage());
    // }
    // e.printStackTrace();
    // }
    // return obj;
    // }
    //
    // public static Object invokeBySpringArr(String servername,
    // String methodname, Object[] arr) {
    //
    // Object service = SpringInit.getApplicationContext().getBean(servername);
    // Object obj = null;
    // try {
    // obj = BeanUtils.invokePrivateMethod(service, methodname, arr);
    // } catch (NoSuchMethodException e) {
    // if (logger.isErrorEnabled()) {
    // logger.error(e.getMessage());
    // }
    // e.printStackTrace();
    // }
    // return obj;
    // }
    //
    // public static Object invokeBySpringParams(String servername,
    // String methodname, Object... params) {
    //
    // Object service = SpringInit.getApplicationContext().getBean(servername);
    // Object obj = null;
    // try {
    // obj = BeanUtils.invokePrivateMethod(service, methodname, params);
    // } catch (NoSuchMethodException e) {
    // if (logger.isErrorEnabled()) {
    // logger.error(e.getMessage());
    // }
    // e.printStackTrace();
    // }
    // return obj;
    // }

}
