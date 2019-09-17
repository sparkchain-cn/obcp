package cn.obcp.user.shiro.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.obcp.cache.redis.RedissonUtils;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TResources;
import cn.obcp.user.service.ResourcesService;
import cn.obcp.user.shiro.realm.UserRealm;


@Service("shiroService")
public class ShiroService   {

	private Logger log = LoggerFactory.getLogger(ShiroService.class);
	
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	@Autowired(required=false)
	private ResourcesService resourcesService;
	
	@Autowired
	private RedissonUtils redissonUtils;
	
	/**
	 * 初始化权限
	 * @return
	 */
	public static  Map<String, String> loadPermission(){
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		//
		return filterChainDefinitionMap;
	}
	
	/**
	 * 清除权限
	 */
	public  static void updatePermission() {
	
		RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		UserRealm realm = (UserRealm)rsm.getRealms().iterator().next();
		realm.clearAuthz();
	}
	
	/**
	 *  加载权限
	 * @return
	 * Map<String,String>
	 * lmf 创建于2018年11月21日
	 */
	public Map<String, String> loadFilterChainDefinitions() {
		
		String annoPath = redissonUtils.get("com.sparkchain.shiro.anno");
		String authPath = redissonUtils.get("com.sparkchain.shiro.anth");
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
	    
		if(!Strings.isNullOrEmpty(annoPath)) {
        	String[] path = annoPath.split(",");
        	List<String> pathList = Lists.newArrayList(Arrays.asList(path));
        	pathList.forEach(p -> {
        		filterChainDefinitionMap.put(p, "anon");
        	});
        }
        
        if(!Strings.isNullOrEmpty(authPath)) { //不在资源中，需要拦截
        	String[] path = authPath.split(",");
        	List<String> pathListauth = Lists.newArrayList(Arrays.asList(path));
        	pathListauth.forEach(p -> {
        		filterChainDefinitionMap.put(p, "authc");
        	});
        }
		
		
		List<TResources> list = resourcesService.findAll();
		
		list.forEach(r -> {
			if(!Strings.isNullOrEmpty(r.getPath())) {
				String permission = "read";  //添加认证拦截
				if(!Strings.isNullOrEmpty(r.getPermissioncode())) {
					permission = r.getPermissioncode();
				}
				filterChainDefinitionMap.put(r.getPath(),permission);
			}
		});
		filterChainDefinitionMap.put("/v1/logout", "logout");
		filterChainDefinitionMap.put(UserConstans.SHIRO_DEF_FILTER_URL_PREFIX, "read");//全部接口访问拦截处理
		filterChainDefinitionMap.put("/**", "authc");
		return filterChainDefinitionMap;
	}
	
	/**
	 *  更新 
	 * @param permissionFactory
	 * void
	 * lmf 创建于2018年11月21日
	 */
    public void reloadFilterChains() {  
        synchronized (shiroFilterFactoryBean) {   //强制同步，控制线程安全  
            AbstractShiroFilter shiroFilter = null;  
            try {  
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();  

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter  
                        .getFilterChainResolver();  
                // 过滤管理器  
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();  
                // 清除权限配置  
                manager.getFilterChains().clear();  
                shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();  
                // 重新设置权限  
                Map<String, String> filterChainDefinitionMap = loadFilterChainDefinitions();
                shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);;//传入配置中的filterchains  

                Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();  
                //重新生成过滤链  
                log.debug("重新生成过滤链 ");
                if (null != chains && !chains.isEmpty()) {  
                    chains.forEach((url, definitionChains) -> {  
                    	if(Strings.isNullOrEmpty(definitionChains)){
                    		definitionChains = "anon";
                    	}
                        manager.createChain(url, definitionChains.trim().replace(" ", ""));  
                    });  
                }  
                updatePermission();
            } catch (Exception e) { 
            	log.error("重新生成过滤链出错");
                e.printStackTrace();  
            }  
        }  
    }  
}
