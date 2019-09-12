/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月22日
 */
package cn.obcp.user.shiro.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.obcp.cache.ICache;



/**
 * @author lmf
 *
 */
public class AjaxFilter extends FormAuthenticationFilter  {

	@Autowired
	private ICache redissonUtils;
	
	String loginUrl  = redissonUtils.get("com.sparkchain.shiro.login.url");
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		boolean isAJax = isAjaxRequest((HttpServletRequest)request);
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if(isAJax) { //ajax請求
			Map<String,Object> redirectToLogin = new HashMap<>(); 
			//String loginUrl  = redissonUtils.get("com.sparkchain.shiro.login.url");
			redirectToLogin.put("toLogin", true); 
			redirectToLogin.put("loginUrl", loginUrl); 
			redirectToLogin.put("status", 403); 
			String json = JSON.toJSONString(redirectToLogin);
			httpServletResponse.getWriter().write(json);			
		}else {
			redirectToLogin(request,response);
		}
		return false;
	}
	
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
       // String loginUrl  = redissonUtils.get("com.sparkchain.shiro.login.url");
        WebUtils.issueRedirect(request, response, loginUrl);

	}
	
	public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}
