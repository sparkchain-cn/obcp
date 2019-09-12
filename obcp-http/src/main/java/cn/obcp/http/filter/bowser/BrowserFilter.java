package cn.obcp.http.filter.bowser;

import com.alibaba.druid.util.DruidWebUtils;
import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.BrowserType;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * XSS拦截处理器,其中url例外判断依赖与druid
 *
 * @version 2017-12-06 chenchen create
 */
public class BrowserFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(BrowserFilter.class);
	// 所有例外的url
	private Set<String> excludesPattern;
	// 上下文地址
	protected String contextPath;

	protected PatternMatcher pathMatcher = new ServletPathMatcher();
	// 所有可以访问的浏览器版本
	private HashSet<String> allowsPattern;

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
		 * 判断是否例外
		 */
		if (isExclusion(requestURI)) {
			chain.doFilter(request, response);
			return;
		} else {
			// 获取浏览器信息
			Browser browser = UserAgent.parseUserAgentString(httpRequest.getHeader("User-Agent")).getBrowser();
			if (browser == null) {
				chain.doFilter(request, response);
				return;
			}
			// 获取浏览器版本号
			Version version = browser.getVersion(httpRequest.getHeader("User-Agent"));
			/**
			 * 由于应用本身还存在接口访问，对于接口访问不需要判断浏览器版本
			 */
			if (browser.getBrowserType().equals(BrowserType.WEB_BROWSER)) {
				log.debug(browser.name());
				log.debug("{}|{}|{}", browser.name(), browser.getName(), version.getVersion());

				if (!isAllowBrowser(browser.name())) {
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/global/browserError");
				}
			}
			chain.doFilter(request, response);
			return;
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String exclusions = config.getInitParameter("exclusions");
		if (exclusions != null && exclusions.trim().length() != 0) {
			excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
		}
		this.contextPath = DruidWebUtils.getContextPath(config.getServletContext());
		String allows = config.getInitParameter("allows");
		if (allows != null && allows.trim().length() != 0) {
			allowsPattern = new HashSet<String>(Arrays.asList(allows.split("\\s*,\\s*")));
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

	/**
	 * 判断当前传入的浏览器版本是否允许访问
	 * 
	 * @param browser
	 * @return
	 */
	public boolean isAllowBrowser(String browser) {
		if (allowsPattern == null || browser == null) {
			return false;
		}

		for (String allowBrowser : allowsPattern) {
			if (allowBrowser.equalsIgnoreCase(browser)) {
				return true;
			}
		}

		return false;
	}

	public String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

}