package cn.obcp.user.shiro.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.obcp.base.utils.StringUtils;
import cn.obcp.cache.ICache;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.shiro.realm.UserRealm;

public class LogoutFilter extends AdviceFilter {
    Logger logger = LoggerFactory.getLogger(LogoutFilter.class);

    public static final String DEFAULT_REDIRECT_URL = "/";

    private String redirectUrl = DEFAULT_REDIRECT_URL;

    private boolean postOnlyLogout = false;

    private String logoutUrl = "/logout";

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {


        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requrl = httpServletRequest.getRequestURL().toString();
        Subject subject = getSubject(request, response);

        if (requrl.lastIndexOf(logoutUrl) == -1){
            return  true;
        }
        // Check if POST only logout is enabled
        else if (isPostOnlyLogout()) {
            // check if the current request's method is a POST, if not redirect
            if (!WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("POST")) {
                return onLogoutRequestNotAPost(request, response);
            }
        }

        clearCache(request,response,subject);
        String redirectUrl = getRedirectUrl(request, response, subject);
        issueRedirect(request, response, redirectUrl);
        return false;
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        Subject subject =   getSubject(request, response);
        TUserExtend userExtend = (TUserExtend) subject.getPrincipal();
        if (userExtend != null && StringUtils.isNullOrEmpty(userExtend.getMobile()) ){
            subject.logout();
        }
    }

    private  Subject   clearCache(ServletRequest request, ServletResponse response,Subject subject) throws IOException {
        //清空認證緩存
        RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm)rsm.getRealms().iterator().next();
        realm.clearAuthz();

        try {
            if (null != subject)
                subject.logout();
        } catch (SessionException ise) {
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        return subject;
    }

    private void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws IOException {
        WebUtils.issueRedirect(request, response, redirectUrl);
    }

    private String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        return redirectUrl;
    }

    private boolean onLogoutRequestNotAPost(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        httpServletResponse.setHeader("Allow", "POST");
        return false;
    }

    private boolean isPostOnlyLogout() {
        return postOnlyLogout;
    }

    private Subject getSubject(ServletRequest request, ServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        return  subject;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setPostOnlyLogout(boolean postOnlyLogout) {
        this.postOnlyLogout = postOnlyLogout;
    }
}
