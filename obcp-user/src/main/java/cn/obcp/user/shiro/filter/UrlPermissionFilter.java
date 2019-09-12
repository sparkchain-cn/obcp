/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月19日
 */
package cn.obcp.user.shiro.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.cache.ICache;

/**
 * @author lmf
 *
 */
public class UrlPermissionFilter extends PermissionsAuthorizationFilter {

	@Autowired
	private ICache redissonUtils;

	String annoPath = null;

	Logger logger = LoggerFactory.getLogger(UrlPermissionFilter.class);

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		if (null == annoPath) {
			annoPath = redissonUtils.get("com.sparkchain.shiro.anno");
		}
		if (StringUtils.isNullOrEmpty(annoPath)) {
			annoPath = "";
		}
		String uri = req.getRequestURI();
		logger.debug(uri);
		List<String> pathList = Lists.newArrayList(Arrays.asList(annoPath.split(",")));
		if (pathList.contains(uri)) {
			return true;
		}
		return super.isAccessAllowed(request, response, buildPermissionsFromRequest(request));
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String requestedWith = httpServletRequest.getHeader("X-Requested-With");
		if (StringUtils.isNotNullOrEmpty(requestedWith) && "XMLHttpRequest".equals(requestedWith)) {// 如果是ajax返回指定格式数据

			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json");
			httpServletResponse.getWriter().write(JSONObject.toJSONString(LayUiRetData.error("403", "访问拒绝！")));
		} else {// 如果是普通请求进行重定向
			httpServletResponse.sendRedirect("/403");
		}
		return false;
	}

	protected String[] buildPermissionsFromRequest(ServletRequest request) {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String apiVersion = redissonUtils.get("com.sparkchain.api.version");
		String uri = apiVersion + servletRequest.getRequestURI();
		String tmpUri = uri.substring(1, uri.length());
		String reUri = tmpUri.substring(tmpUri.indexOf("/"), tmpUri.length());
		return new String[] { reUri };// 返回请求URI
	}
}
