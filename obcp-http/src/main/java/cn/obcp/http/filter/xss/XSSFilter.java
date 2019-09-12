package cn.obcp.http.filter.xss;


import com.alibaba.druid.util.DruidWebUtils;
import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * XSS拦截处理器,其中url例外判断依赖与druid
 *
 * @version 2017-12-06 chenchen create
 */
public class XSSFilter implements Filter {

    //所有例外的url
    private Set<String> excludesPattern;
    //上下文地址
    protected String contextPath;

    protected PatternMatcher pathMatcher = new ServletPathMatcher();




    @Override
    public void destroy() {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,

                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestURI = getRequestURI(httpRequest);

        /**
         * 判断是否例外
         */
        if (isExclusion(requestURI)) {
            chain.doFilter(request, response);
            return;
        }else{
            chain.doFilter(new XSSRequestWrapper(
                    httpRequest), response);
        }



    }
    @Override
    public void init(FilterConfig config) throws ServletException {
        String exclusions = config.getInitParameter("exclusions");
        if (exclusions != null && exclusions.trim().length() != 0) {
            excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
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
    public String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

}