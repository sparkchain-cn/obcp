/**
 * TODO
 * 
 * 
 * lmf 创建于2018年12月3日
 */
package cn.obcp.user.shiro.realm;

import cn.obcp.base.utils.MD5Utils;
import cn.obcp.user.shiro.redis.RedisCacheManager;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lmf
 *
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

	private Cache<String, AtomicInteger> passwordRetryCache;
	
	public RetryLimitCredentialsMatcher(RedisCacheManager redisCacheManager) {
		// TODO Auto-generated constructor stub
		redisCacheManager.setExpire(3000);
		passwordRetryCache = redisCacheManager.getCache("passwordRetryCache");
	}
	
	@Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String)token.getPrincipal(); //当前用户
		if(null !=  passwordRetryCache.get(username)) {
			AtomicInteger retryCount = passwordRetryCache.get(username);
			if(retryCount.get() > 5) {
				
				throw new LockedAccountException();
			}else {
				retryCount = new AtomicInteger(retryCount.addAndGet(1));
				passwordRetryCache.put(username,retryCount);
			}
			//System.out.println(passwordRetryCache.get(username));
		}else {
			AtomicInteger retryCount = new AtomicInteger(0);
	        passwordRetryCache.put(username, retryCount);
		}
		setHashAlgorithmName("MD5");
        Object tokenHashedCredentials = hashProvidedCredentials(token, info);
        Object accountCredentials = getCredentials(info);
    
        boolean result =  MD5Utils.MD5(String.valueOf(accountCredentials)).equalsIgnoreCase(String.valueOf(tokenHashedCredentials));
       //  boolean result = doCredentialsMatch(token, info);
        if(result) {
        	passwordRetryCache.remove(username);
        }
        return result;
    }
}
