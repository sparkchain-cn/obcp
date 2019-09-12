package cn.obcp.user.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.DateUtils;
import cn.obcp.cache.ICache;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserToken;
import cn.obcp.user.service.UserExtendService;
import cn.obcp.user.service.UserTokenService;

// ##remain#import#

/**
 * <pre>
  * 用户token的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/usertoken/")
public class UserTokenController extends BaseController<TUserToken, Long> {

	@Autowired
	private UserTokenService userTokenService;

	@Autowired
	private UserExtendService userExtendService;

	@Autowired
	private ICache redisUtils;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsUserTokenService") BaseService<TUserToken, Long> entityService) {
		this.entityService = entityService;

	}

	@ResponseBody
	@RequestMapping("createUserToken")
	public RetData createUserToken(@RequestParam  String expireTimeStr,@RequestParam int scope){
		try {
			String tokenStr = UUID.randomUUID().toString();
			Date expireTime = DateUtils.parseDateDayFormat(expireTimeStr);
			TUserExtend userExtend = userExtendService.getCurrentUser();
			TUserToken userToken = new TUserToken();
			userToken.setId(uuidCreator.id());
			userToken.setToken(tokenStr);
			userToken.setUid(userExtend.getId());
			userToken.setExpiretime(expireTime);
			userToken.setScope(scope);
			entityService.save(userToken);
			redisUtils.hashSet(UserConstans.USER_ACESSTOKEN_KEY+"::"+userToken.getUid(), userToken.getToken(), JSON.toJSONString(userToken));
			return RetData.succuess(tokenStr);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return RetData.error("服务端异常！");
		}
	}
	// ##remain#property#

}