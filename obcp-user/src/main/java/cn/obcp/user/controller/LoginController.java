package cn.obcp.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.obcp.base.RetData;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.cache.ICache;
import cn.obcp.user.domain.TUserExtend;
import springfox.documentation.annotations.ApiIgnore;

// ##remain#import#

/**
 * <pre>
  * 用户登录记录的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Controller
@ApiIgnore
@RequestMapping("/v1/")
public class LoginController {

	@Autowired
	private ICache redisUtils;

	@RequestMapping("logout")
	public RetData logout() {
		try {
			Subject subject = SecurityUtils.getSubject();
			TUserExtend userExtend = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
			if (null != userExtend && StringUtils.isNotNullOrEmpty(userExtend.getUserToken())) {
				redisUtils.delete(userExtend.getUserToken());// 删除token缓存
			}
			return RetData.succuess();
		} catch (SessionException e) {
			return RetData.succuess();
		} catch (Exception e) {
			return RetData.succuess();
		}
	}

}