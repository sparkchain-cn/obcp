package cn.obcp.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

public class EvnService {

    private DefaultListableBeanFactory beanFactory;
    private Environment env;


    public EvnService(DefaultListableBeanFactory beanFactory, Environment env) {
        this.beanFactory = beanFactory;
        this.env = env;
    }

    public static String nowserverName;

    public String convertServerName(String serverName) {
        if (serverName.startsWith("#")) {
            serverName = env.getProperty(serverName.substring(1));
            if (serverName == null || "".equalsIgnoreCase(serverName)) {
                serverName = env.getProperty("server.name");
                // TracingClientFactory.get().debug("根据" + annotation.service()
                // + "找不到对应的服务值" + ",采用" + serverName + "服务");
            }
        }
        return serverName;
    }

    public boolean isNowService(String serverName) {
        if (nowserverName == null) {
            nowserverName = env.getProperty("server.name");
        }

        if (nowserverName != null && nowserverName.equalsIgnoreCase(serverName)) {
            return true;
        } else {
            return false;
        }
    }


    public Object getClientAndMethodForLocal(String serverName, String path, String methodName) {

        Object client = null;
        Method method = null;
        if (isNowService(serverName) && beanFactory.containsBean(path)) {
            // 指定为当前的服务名，那么就直接调用当前应用实例的指定服务了
            client = beanFactory.getBean(path);
            // todo:缓存
            // 看看同名方法是否存在
            for (Method m : client.getClass().getDeclaredMethods()) {
                if (m.getName().equalsIgnoreCase(methodName)) {
                    method = m;
                    break;
                }
            }
        }

        if (method == null)
            return null;

        return new Object[]{client, method};

    }

}
