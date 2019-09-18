package cn.obcp.user.shiro.filter;

import java.io.IOException;
import java.util.Calendar;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.obcp.base.RetData;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.cache.ICache;
import cn.obcp.cache.redis.RedisUtils;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TUserToken;
import cn.obcp.user.service.UserExtendService;
import cn.obcp.user.util.HttpUtil;

public class PrivateAccessTokenFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(PrivateAccessTokenFilter.class);

    @Autowired
    private ICache redisUtils;
    @Autowired
    private UserExtendService userExtendService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String provateTokenParam = "privateToken";//參數名
        String privateToken = HttpUtil.getReqParam(provateTokenParam,req);
        //开始验证
        if (StringUtils.isNotNullOrEmpty(privateToken)) {
            if (redisUtils != null) {
                //从缓存读取private-token
            	String uid = redisUtils.hashGet(UserConstans.USER_ACESSTOKEN_KEY + "::TOKEN", privateToken, String.class);
                String token = redisUtils.hashGet(UserConstans.USER_ACESSTOKEN_KEY + "::"+uid, privateToken, String.class);
                if (StringUtils.isNotNullOrEmpty(token)) {
                    TUserToken userToken = JSON.parseObject(token, TUserToken.class);
                    RetData checkRes = checkToken(userToken);
                    if (checkRes.isSuccess()){
                        if (userToken.getScope() == -1 ||  req.getRequestURI().startsWith("/v"+userToken.getScope())) {
                            	userExtendService.runWith(userToken);
                            logger.debug(String.format("private_token验证通过"));
                        }
                    }
                }
            }
        }
//        System.out.println(((HttpServletRequest) request).getRequestURI());
        chain.doFilter(request,response);
    }


    private RetData checkToken(TUserToken userToken) {
        if (userToken != null) {
            //验证时间
            if (userToken.getExpiretime().getTime() > Calendar.getInstance().getTime().getTime()){
                return RetData.succuess();
            }else {
                return RetData.error("private_token was expired");
            }
        }else {
            return RetData.error("private-token should not  be null");
        }
    }





    @Override
    public void destroy() {

    }
}
