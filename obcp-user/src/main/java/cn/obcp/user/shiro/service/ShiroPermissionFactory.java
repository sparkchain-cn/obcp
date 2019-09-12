package cn.obcp.user.shiro.service;///**
// * TODO
// * 
// * 
// * lmf 创建于2018年11月21日
// */
//package cn.obcp.user.shiro.service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.shiro.config.Ini;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.util.CollectionUtils;
//import org.apache.shiro.web.config.IniFilterChainResolverFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.common.annotations.Beta;
//import com.google.common.base.Strings;
//import cn.obcp.base.utils.StringUtils;
//import cn.obcp.user.domain.TResources;
//import cn.obcp.user.service.ResourcesService;
//
///**
// * @author lmf
// *
// */
//
//public class ShiroPermissionFactory extends ShiroFilterFactoryBean {
//	
//	public static String filterChainDefinitions = "";
//	
//	@Autowired
//	private ResourcesService resourceService;
//	
//	/**
//	 * @param securityManager
//	 */
//
//	@Override
//    public void setFilterChainDefinitions(String definitions) {
//        filterChainDefinitions = definitions;// 记录配置的静态过滤链
//        //ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        Map<String, String> otherChains = new HashMap<String, String>();
//        @SuppressWarnings({ "unchecked", "rawtypes" })
//		List<TResources> list = (List) resourceService.findAll();
//        for (TResources resource : list) {
//            if (!Strings.isNullOrEmpty(resource.getPath()) && !Strings.isNullOrEmpty(resource.getPermissioncode())) {
//                otherChains.put(resource.getPath(), resource.getPermissioncode());
//            }
//        }
//        otherChains.put("/**", "authc");
//       Ini ini = new Ini();
//       ini.load(filterChainDefinitions);
//       Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);  
//       if (CollectionUtils.isEmpty(section)) {  
//           section = ini.getSection(Ini.DEFAULT_SECTION_NAME);  
//       }  
//       // 加上数据库中过滤链  
//       section.putAll(otherChains);  
//       setFilterChainDefinitionMap(section);
//    }
//
//}
