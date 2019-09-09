package cn.obcp.consul;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017年7月6日
 */
//@Component
public class ConsulIPFetch {

	protected final static Logger logger = LoggerFactory.getLogger(ConsulIPFetch.class);

	Random random = new Random();

	@Autowired
	private DiscoveryClient discoveryClient;

//	public boolean exist(String serviceName) {
//
//		List<ServiceInstance> list = discoveryClient..getInstances(serviceName);
//		if (list == null || list.size() < 1) {
//			return false;
//		}
//	}

	public String buildUrl(String servicename) {

		List<ServiceInstance> list = discoveryClient.getInstances(servicename);
		if (list == null || list.size() < 1) {
			logger.error("服务名：" + servicename + "没有找到服务实例");
			return null;
		}

		if (list.size() == 1) {
			return list.get(0).getUri().toString();
		}

		// 以负载均衡的方式获取服务实例
		
		int rand = random.nextInt(list.size());
		String url = list.get(rand).getUri().toString();

		
		return url;

	}
	
	public List<String> buildAllUrl(String servicename) {

		List<ServiceInstance> list = discoveryClient.getInstances(servicename);
		List<String> urls = new ArrayList<>();
		if (list == null || list.size() < 1) {
			logger.error("服务名：" + servicename + "没有找到服务实例");
		}else {
			list.forEach(item-> {urls.add(item.getUri().toString());});
		}
		return urls;
	}
	
	/**
	 * 获取分布式服务实例
	 * @param servicename
	 * @return
	 */
	public List<ServiceInstance> buildAllService(String servicename) {

		List<ServiceInstance> list = discoveryClient.getInstances(servicename);
		if (list == null || list.isEmpty()) {
			logger.error("服务名：" + servicename + "没有找到服务实例");
		}
		return list;
	}

}

//
//class RoundRobin
//{
//    private static Integer pos = 0;
//    
//    public static String getServer(List<ServiceInstance> list)
//    {
//        // 重建一个Map，避免服务器的上下线导致的并发问题
//        Map<String, Integer> serverMap = 
//                new HashMap<String, Integer>();
//        serverMap.putAll(IpMap.serverWeightMap);
//        
//        // 取得Ip地址List
//        Set<String> keySet = serverMap.keySet();
//        ArrayList<String> keyList = new ArrayList<String>();
//        keyList.addAll(keySet);
//        
//        String server = null;
//        synchronized (pos)
//        {
//            if (pos > keySet.size())
//                pos = 0;
//            server = keyList.get(pos);
//            pos ++;
//        }
//        
//        return server;
//    }
//}


//public class Random
//{
//    public static String getServer()
//    {
//        // 重建一个Map，避免服务器的上下线导致的并发问题
//        Map<String, Integer> serverMap = 
//                new HashMap<String, Integer>();
//        serverMap.putAll(IpMap.serverWeightMap);
//        
//        // 取得Ip地址List
//        Set<String> keySet = serverMap.keySet();
//        ArrayList<String> keyList = new ArrayList<String>();
//        keyList.addAll(keySet);
//        
//        java.util.Random random = new java.util.Random();
//        int randomPos = random.nextInt(keyList.size());
//        
//        return keyList.get(randomPos);
//    }
//}
//@ResponseBody
//@RequestMapping("")
//public String serviceUrl(@RequestParam String servername) {
//	List<ServiceInstance> list = discoveryClient.getInstances(servername);
//	if (list != null && list.size() > 0) {
//		for (ServiceInstance si : list) {
//			System.out.println(si.getUri()+","+si.getHost()+","+si.getPort());
//		}
//
//		/// return list.get(0).getUri();
//		// return RetData.succuess(appChainService.getEnabledAppChainList(appId));
//	}
//	return null;
//}
