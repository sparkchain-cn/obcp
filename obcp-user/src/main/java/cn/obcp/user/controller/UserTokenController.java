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
import io.swagger.annotations.ApiOperation;

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

	@ApiOperation("创建用户临时访问令牌")
	@ResponseBody
	@RequestMapping("createUserToken")
	public RetData createUserToken(@RequestParam String expireTime, @RequestParam int scope) {
		try {
			String tokenStr = null;
			Date expireTimeDate = DateUtils.parseDateDayFormat(expireTime);
			TUserExtend userExtend = userExtendService.getCurrentUser();
			TUserToken userToken = userTokenService.findByUserScope(userExtend.getId(), scope);
			if (userToken != null) {
				return RetData.error("已存在相同接口级别token");
			} else {
				tokenStr = UUID.randomUUID().toString();

				userToken = new TUserToken();
				userToken.setId(uuidCreator.id());
				userToken.setToken(tokenStr);
				userToken.setUid(userExtend.getId());
				userToken.setExpiretime(expireTimeDate);
				userToken.setScope(scope);
				entityService.save(userToken);
				redisUtils.hashSet(UserConstans.USER_ACESSTOKEN_KEY + "::" + userToken.getUid(), userToken.getToken(),
						JSON.toJSONString(userToken));
				redisUtils.hashSet(UserConstans.USER_ACESSTOKEN_KEY + "::TOKEN", userToken.getToken(),
						userToken.getUid()+"");
				return RetData.succuess(tokenStr);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return RetData.error("服务端异常！");
		}
	}

	@ApiOperation("删除用户临时访问令牌")
	@RequestMapping("delete")
	public RetData delete(@RequestParam String token) {
		try {
			TUserToken userToken = userTokenService.findByToken(token);
			TUserExtend userExtend = userExtendService.getCurrentUser();
			if (userExtend == null) {
				return RetData.error("token不存在");
			}
			if (!userExtend.getId().equals(userToken.getUid())) {
				return RetData.error("只能删除自己的token");
			}
			userTokenService.delete(userToken);
			redisUtils.hashDel(UserConstans.USER_ACESSTOKEN_KEY + "::" + userToken.getUid(), userToken.getToken());
			redisUtils.hashDel(UserConstans.USER_ACESSTOKEN_KEY + "::TOKEN", userToken.getToken());
			return RetData.succuess();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return RetData.error("服务端异常！");
		}
	}

}