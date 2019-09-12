package cn.obcp.user.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.obcp.cache.ICache;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserToken;
import cn.obcp.user.service.UserTokenService;

public class TokenRealm  extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(TokenRealm.class);

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private ICache redisUtils;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("token authorization");
        TUserExtend user = (TUserExtend) principals.getPrimaryPrincipal();
        if (user != null && redisUtils != null){//redisUtils.hashSet(, userToken.getToken(), JSON.toJSONString(userToken));
            String userTokenStr = redisUtils.hashGet(UserConstans.USER_ACESSTOKEN_KEY,user.getUserToken().toString(),String.class);
            System.out.println(userTokenStr);
        }
        return null;
    }

    /**
     *  使用usertoken进行认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        try{
            logger.debug(token.getPrincipal().toString());
            String loginName = token.getPrincipal().toString();//使用userid作为登录名
          //  Long userId = Long.valueOf(loginName);
            //查询user-token是否存在
            TUserToken userToken = userTokenService.findByToken(loginName);
            if (userToken != null){
                TUserExtend userExtend = new TUserExtend(userToken.getUid());
                userExtend.setUserToken(userToken.getToken());
//                String getpassword = new Md5Hash(userToken.getUid().toString()).toString();
                AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userExtend,userToken.getUid().toString() , getName());

                return authenticationInfo;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
