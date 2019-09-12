/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月15日
 */
package cn.obcp.user.shiro.realm;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Strings;
import cn.obcp.user.domain.TResources;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserRole;
import cn.obcp.user.service.ResourcesService;
import cn.obcp.user.service.UserExtendService;
import cn.obcp.user.service.UserRoleService;

public class UserRealm extends AuthorizingRealm{

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
    private UserExtendService userExtendService;//这是自己实现的用户信息操作类，实现用户信息，用户角色信息、用户权限信息查询功能
 
	Logger log = LoggerFactory.getLogger(UserRealm.class);
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		try {
			log.debug("授权");
			TUserExtend user = (TUserExtend) principals.getPrimaryPrincipal();
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			Set<String> roles = new  HashSet<>();
			Set<String> permissions = new HashSet<>();
			boolean isSysAdmin = userExtendService.isSysAdmin(user.getId());//只有系统超级管理员才能查看全部资源
			if(isSysAdmin) {
				List<TResources> resources = resourcesService.findAll();
				resources.forEach(resource ->{
					String permission = resource.getPath();
					if(!Strings.isNullOrEmpty(permission)) {
						permissions.add(permission);
					}
				});
			}else {
				// 查询角色信息
				List<TUserRole> userRoles = userRoleService.findByUser(user.getId());
				//TUserRole userRole = userRoleService.findByFieldName("uid", user.getId()); 
				
		    	if(userRoles != null && userRoles.size() > 0) { //用户已绑定角色
		    		for(TUserRole userRole : userRoles) {
		    			List<TResources> resources = resourcesService.findByRole(userRole.getRid());
		    			if(resources != null && resources.size() > 0) {
		    				resources.forEach(resource ->{
		    					String permission = resource.getPath();
		    					if(!Strings.isNullOrEmpty(permission)) {
		    						permissions.add(permission);
		    					}
		    				});
		    			}
		    		}
		    	}
			}
	    	info.setRoles(roles);
	    	info.setStringPermissions(permissions);
			return info;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
 
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)throws AuthenticationException{
		// 确保登录的用户信息已经插入AuthenticationToken中，这样才能通过shiro的认证流程
		String loginname= token.getPrincipal().toString();
		// 查询用户名对应的用户信息
		TUserExtend user = userExtendService.findByLogin(loginname);
		if (user != null&&user.getPassword()!=null) {
			//直接把用户信息对象和密码塞进shiro验证器，shiro会自动判断密码是否正确
			//,ByteSource.Util.bytes(user.getSalt())		
			String getpassword = new Md5Hash(user.getPassword() + user.getSalt()).toString();
//			log.info(String.format("用户%s密码密文：%s",loginname, getpassword ));
		    AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, getpassword ,getName());		    
		    return authenticationInfo;
		}
		return null;
	}
	

	/**
	 * 清除授权缓存
	 * TODO
	 * void
	 * lmf 创建于2018年11月22日
	 */
	public void clearAuthz(){		
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache(); 
		if (cache != null) { 
			for (Object key : cache.keys()) {
			   cache.remove(key); 
			}
		} 		
		this.getAuthenticationCache().clear();
		this.getAuthorizationCache().clear();
		    
	}

}
