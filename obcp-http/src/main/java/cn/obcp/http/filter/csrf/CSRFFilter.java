package cn.obcp.http.filter.csrf;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.DruidWebUtils;
import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;

import cn.obcp.base.utils.StringUtils;

/**
 * XSS拦截处理器,其中url例外判断依赖与druid
 *
 * @version 2017-12-06 chenchen create
 */
public class CSRFFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(CSRFFilter.class);
	// 所有例外的url
	private Set<String> excludesPattern;
	// 所有允许访问的url
	private Set<String> accessPattern;
	// 上下文地址
	protected String contextPath;

	protected PatternMatcher pathMatcher = new ServletPathMatcher();

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,

			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String requestURI = getRequestURI(httpRequest);
		/**
		 * 判断是否例外,或者get请求
		 */
		if (isExclusion(requestURI) || httpRequest.getMethod().equalsIgnoreCase("get")) {
			chain.doFilter(request, response);
			return;
		} else {
			String referrer = httpRequest.getHeader("referer");
			log.debug("referrer:{}", referrer);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(request.getScheme()).append("://").append(request.getServerName());
			log.debug("basePath:{}", stringBuffer);
			/**
			 * 判断当前请求是否允许访问
			 */
			if (isAccess(referrer)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
			}

		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String exclusions = config.getInitParameter("exclusions");
		if (StringUtils.isNotNullOrEmpty(exclusions)) {
			excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
		}
		String accesses = config.getInitParameter("accesses");
		if (StringUtils.isNotNullOrEmpty(accesses)) {
			accessPattern = new HashSet<String>(Arrays.asList(accesses.split("\\s*,\\s*")));
		}
		this.contextPath = DruidWebUtils.getContextPath(config.getServletContext());
	}

	public boolean isExclusion(String requestURI) {
		if (excludesPattern == null || requestURI == null) {
			return false;
		}

		if (contextPath != null && requestURI.startsWith(contextPath)) {
			requestURI = requestURI.substring(contextPath.length());
			if (!requestURI.startsWith("/")) {
				requestURI = "/" + requestURI;
			}
		}

		for (String pattern : excludesPattern) {
			if (pathMatcher.matches(pattern, requestURI)) {
				return true;
			}
		}

		return false;
	}

	public boolean isAccess(String referrer) {
		if (accessPattern == null || referrer == null) {
			return false;
		}
		for (String pattern : accessPattern) {
			if (referrer.toLowerCase().startsWith(pattern)) {
				return true;
			}
		}
		return false;
	}

	public String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

}