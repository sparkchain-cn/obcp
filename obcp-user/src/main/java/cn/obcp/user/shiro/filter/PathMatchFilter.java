package cn.obcp.user.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathMatchFilter extends PathMatchingFilter {

	  private static final Logger log = LoggerFactory.getLogger(PathMatchFilter.class);
	
	@Override
	 protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

	        if (this.appliedPaths == null || this.appliedPaths.isEmpty()) {
	            if (log.isTraceEnabled()) {
	                log.trace("appliedPaths property is null or empty.  This Filter will passthrough immediately.");
	            }
	            return true;
	        }

	        for (String path : this.appliedPaths.keySet()) {
	            // If the path does match, then pass on to the subclass implementation for specific checks
	            //(first match 'wins'):
	            if (pathsMatch(path, request)) {
	                log.trace("Current requestURI matches pattern '{}'.  Determining filter chain execution...", path);
	                Object config = this.appliedPaths.get(path);
	                return isFilterChainContinued(request, response, path, config);
	            }
	        }

	        //no path matched, allow the request to go through:
	        return true;
	    }

	    private boolean isFilterChainContinued(ServletRequest request, ServletResponse response,
	                                           String path, Object pathConfig) throws Exception {

	        if (isEnabled(request, response, path, pathConfig)) { //isEnabled check added in 1.2
	            if (log.isTraceEnabled()) {
	                log.trace("Filter '{}' is enabled for the current request under path '{}' with config [{}].  " +
	                        "Delegating to subclass implementation for 'onPreHandle' check.",
	                        new Object[]{getName(), path, pathConfig});
	            }
	            //The filter is enabled for this specific request, so delegate to subclass implementations
	            //so they can decide if the request should continue through the chain or not:
	            return onPreHandle(request, response, pathConfig);
	        }

	        if (log.isTraceEnabled()) {
	            log.trace("Filter '{}' is disabled for the current request under path '{}' with config [{}].  " +
	                    "The next element in the FilterChain will be called immediately.",
	                    new Object[]{getName(), path, pathConfig});
	        }
	        //This filter is disabled for this specific request,
	        //return 'true' immediately to indicate that the filter will not process the request
	        //and let the request/response to continue through the filter chain:
	        return true;
	    }
	    
	    @Override
	    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
	        return true;
	    }
	
}
