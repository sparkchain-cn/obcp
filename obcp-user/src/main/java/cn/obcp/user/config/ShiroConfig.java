package cn.obcp.user.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.obcp.cache.ICache;
import cn.obcp.user.domain.TResources;
import cn.obcp.user.service.ResourcesService;
import cn.obcp.user.shiro.filter.LogoutFilter;
import cn.obcp.user.shiro.filter.PathMatchFilter;
import cn.obcp.user.shiro.filter.PrivateAccessTokenFilter;
import cn.obcp.user.shiro.filter.UrlPermissionFilter;
import cn.obcp.user.shiro.realm.RetryLimitCredentialsMatcher;
import cn.obcp.user.shiro.realm.TokenRealm;
import cn.obcp.user.shiro.realm.UserRealm;
import cn.obcp.user.shiro.redis.RedisCacheManager;
import cn.obcp.user.shiro.redis.RedisManager;
import cn.obcp.user.shiro.redis.RedisSessionDAO;
import cn.obcp.user.shiro.service.ShiroLifecycleBeanPostProcessorConfig;

@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
@Order(1)
public class ShiroConfig {
	
	private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);

	
	@Resource(name="redisUtils")
	private ICache redissonUtils;
	
	@Autowired(required=false)
	private ResourcesService resourcesService;

	@Value("${spring.redis.host}")
    private String redishost;
	@Value("${spring.redis.port}")
    private int redisport;
	@Value("${spring.redis.database}")
    private int redisdatabase;
	@Value("${spring.redis.timeout}")
	private int redisTimeout;
	@Value("${spring.redis.password}")
	private String password;
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		try {
			String loginUrl = redissonUtils.get("com.sparkchain.shiro.login.url");
			String successUrl = redissonUtils.get("com.sparkchain.shiro.success.url");
			String unauthorizedUrl = redissonUtils.get("com.sparkchain.shiro.unauthorized");
			String annoPath = redissonUtils.get("com.sparkchain.shiro.anno");
			String authPath = redissonUtils.get("com.sparkchain.shiro.anth");		

			shiroFilterFactoryBean.setSecurityManager(securityManager);		
			Map<String, Filter> filterMap = new HashMap<>();
			filterMap.put("privateToken", getPrivateTokenFilter());
		    filterMap.put("logoutFilter",logoutFilter());
		    filterMap.put("read",urlPerms());
		   // filterMap.put("userLogout", logoutUserFilter());
		   // filterMap.put("kickout", KitcoutFilter());
		    shiroFilterFactoryBean.setFilters(filterMap);
			shiroFilterFactoryBean.setLoginUrl(loginUrl);
			shiroFilterFactoryBean.setSuccessUrl(successUrl);
			shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
			
			Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
			
			
			//不拦截地址
	        if(!Strings.isNullOrEmpty(annoPath)) {
	        	String[] path = annoPath.split(",");
	        	List<String> pathList = Lists.newArrayList(Arrays.asList(path));
	        	log.debug("anon");
	        	pathList.forEach(p -> {
	        		log.debug(p);
	        		filterChainDefinitionMap.put(p, "anon");
	        	});
	        }
			//不在资源中，需要进行登陆验证
	        if(!Strings.isNullOrEmpty(authPath)) {
	        	String[] path = authPath.split(",");
	        	List<String> pathListauth = Lists.newArrayList(Arrays.asList(path));
	        	log.debug("authc");
	        	pathListauth.forEach(p -> {
	        		log.debug(p);
	        		filterChainDefinitionMap.put(p, "privateToken,authc,logoutFilter");
	        	});
	        }
	        
			//除去上面配置文件中存在的资源路径，全部需要登陆拦截
			List<TResources> list = resourcesService.findAll();		
			list.forEach(r -> {
				if(!Strings.isNullOrEmpty(r.getPath())) {
	//				String permission = "perms[" + r.getPath()+ "]";
//					String permission = "read";  //添加认证拦截
//					if(!Strings.isNullOrEmpty(r.getPermissioncode())) {
//						permission = r.getPermissioncode();
//					}
					filterChainDefinitionMap.put(r.getPath(),"privateToken,read,logoutFilter" );
				}
			});
			filterChainDefinitionMap.put("/actuator/health","anon");//健康檢查
			filterChainDefinitionMap.put("/logout", "logoutFilter");//退出登陆
			filterChainDefinitionMap.put("/proxy/**", "anon");//代理请求不验证
			filterChainDefinitionMap.put("/**", "privateToken,authc,logoutFilter");//非资源表数据，默认需要登陆拦截
			shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
			return shiroFilterFactoryBean;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("shiro 配置出错");
			return shiroFilterFactoryBean;
		}
	}
/*	
	@Bean("userLogoutFilter")
	public LogoutUserFilter logoutUserFilter() {
		return new LogoutUserFilter();
	}
	*/
	/**
	 * redis配置
	 * TODO
	 * @return
	 * RedisManager
	 * lmf 创建于2018年11月29日
	 */
	public RedisManager redisManager() {
         RedisManager redisManager = new RedisManager();
         redisManager.setTimeout(redisTimeout);
         redisManager.setHost(redishost);
         redisManager.setPort(redisport);
         redisManager.setDatabase(redisdatabase);
         if(!Strings.isNullOrEmpty(password)) {
        	 redisManager.setPassword(password);
         }
         return redisManager;
    }

	/**
     * cacheManager 缓存 redis实现
     * 
     * @return
     */
    public RedisCacheManager cacheManager() {
    	String sessionTimeout = redissonUtils.get("com.sparkchain.shiro.timeout")!= null ?
  			  redissonUtils.get("com.sparkchain.shiro.timeout"):"5000";
    	int expire = Integer.valueOf(sessionTimeout);
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(expire);
        return redisCacheManager;
    }
    
    /**
	 * redisDao层
	 * TODO
	 * @return
	 * RedisSessionDAO
	 * lmf 创建于2018年11月29日
	 */
	public RedisSessionDAO redisSessionDAO() {
	  String sessionTimeout = redissonUtils.get("com.sparkchain.shiro.timeout") != null ?
			  redissonUtils.get("com.sparkchain.shiro.timeout"):"5000";
	  int expire = Integer.valueOf(sessionTimeout);
      RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
      redisSessionDAO.setExpire(expire);//毫秒
      redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * shiro session的管理
     */
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
    /**
     * 设置cookie
     * TODO
     * @return
     * SimpleCookie
     * lmf 创建于2018年11月29日
     */
    public SimpleCookie remembermeCookie() {
    	SimpleCookie cookie = new SimpleCookie("baasRememberMe");
    	cookie.setMaxAge(7*24*60*60);
    	return cookie;
    }
    /**
         *  记住登录用户
     * TODO
     * @return
     * RememberMeManager
     * lmf 创建于2018年11月29日
     */
    public RememberMeManager rememberMeManager() {
    	CookieRememberMeManager  rememberMeManager = new CookieRememberMeManager();
    	rememberMeManager.setCookie(remembermeCookie());
    	return rememberMeManager;
    }
	
    @Bean("urlFilter")
    public UrlPermissionFilter urlPerms() {
        return new UrlPermissionFilter();
    }

    @Bean("logoutFilter")
    public LogoutFilter logoutFilter(){
    	return new LogoutFilter();
	}

	@Bean
    public PrivateAccessTokenFilter getPrivateTokenFilter(){
        return new PrivateAccessTokenFilter();
    }

    @Bean
    public PathMatchFilter pathMatchFilter() {
        return new PathMatchFilter();
    }
    
    /**
     * 设置最大出错次数
     * TODO
     * @return
     * RetryLimitCredentialsMatcher
     * lmf 创建于2018年12月3日
     */
    @Bean
    public RetryLimitCredentialsMatcher getRetryLimitCredentialsMatcher() {
    	return new RetryLimitCredentialsMatcher(cacheManager());
    }
    
	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		Collection<Realm> collection = Lists.newArrayList();
		collection.add(realm());
		collection.add(tokenRealm());
		securityManager.setRealms(collection);
		securityManager.setCacheManager(cacheManager());		
		securityManager.setSessionManager(sessionManager());
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}
	/**
	 * 身份认证realm
	 * @return
	 */
	@Bean
	public UserRealm realm() {
		UserRealm realm = new UserRealm();
		realm.setCachingEnabled(true); //开启缓存
		realm.setAuthenticationCachingEnabled(true); // 认证认证信息缓存
		realm.setCredentialsMatcher(getRetryLimitCredentialsMatcher()); //身份认证匹配
		realm.setAuthenticationCacheName("authenticationCache");
		realm.setAuthorizationCacheName("authorizationCache");
		return realm;
	}

	@Bean
	public TokenRealm tokenRealm(){
		TokenRealm realm = new TokenRealm();
//		realm.setCachingEnabled(true); //开启缓存
//		realm.setAuthenticationCachingEnabled(true); // 认证认证信息缓存
//		realm.setAuthenticationCacheName("tokenAuthenticationCache");
//		realm.setAuthorizationCacheName("tokenAuthorizationCache");
		return realm;
	}

	/**
	 * 开启shiro注解(如@ReqiresRoles,@RequiresPermission),需借助SpringAOP扫描使用shiro注解类，并在必要是进行安全逻辑验证
	 * 配置DefaultAdvisorAutoProxyCreator（可选）,AuthorizationAttributeSourceAdvisor
	 * @return
	 */
	@Bean
	@DependsOn
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
		
	}
	
}
