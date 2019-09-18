package cn.obcp.user.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import cn.obcp.base.Constants;
import cn.obcp.cache.ICache;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserToken;
import cn.obcp.user.service.UserExtendService;

@Controller
@RequestMapping("/")
public class UserPagesController {
	@Autowired
	UserExtendService userExtendService;
	@Autowired
	ICache redissonUtils;

	@RequestMapping("login")
	public ModelAndView login(ModelMap modelMap) {
		Object user = SecurityUtils.getSubject().getPrincipal(); // 判断当前是否已登录，已登录直接返回主页面
		if (null == user) {
			modelMap.put("key", redissonUtils.get(UserConstans.RES_PUBLIC_KEY));
			return new ModelAndView("admin/login");
		} else {
			return new ModelAndView(new RedirectView("index"));
		}
	}

	@RequestMapping("logout")
	public ModelAndView logout(ModelMap modelMap) {
		return new ModelAndView("admin/login");
	}

	// 修改密码
	@RequestMapping("updatePwd")
	public ModelAndView updatePwd(ModelMap modelMap) {
		modelMap.put("key", redissonUtils.get(UserConstans.RES_PUBLIC_KEY));
		return new ModelAndView("admin/updatePwd");
	}

	// 忘记密码
	@RequestMapping("forgetPwd")
	public ModelAndView forgetPwd(ModelMap modelMap) {
		modelMap.put("key", redissonUtils.get(UserConstans.RES_PUBLIC_KEY));
		return new ModelAndView("admin/forgetPwd");
	}

	// 注册
	@RequestMapping("signup")
	public ModelAndView signup(ModelMap modelMap) {
		return new ModelAndView("admin/signup");
	}

	@RequestMapping("resourceIndex")
	public ModelAndView resourceIndex(ModelMap modelMap) {
		return new ModelAndView("admin/page/user/resourceIndex");
	}

	/**
	 * 角色首页
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("roleIndex")
	public ModelAndView roleIndex(ModelMap modelMap) {
		return new ModelAndView("admin/page/user/roleIndex");
	}

	@RequestMapping("role/userIndex")
	public ModelAndView roleUserIndex(ModelMap modelMap) {
		return new ModelAndView("admin/page/user/roleUserIndex");
	}

	@RequestMapping("user/userRoleResource")
	public ModelAndView userRoleResource(ModelMap modelMap, @RequestParam String userId) {
		modelMap.put("userId", userId);
		return new ModelAndView("admin/page/user/userRoleResource");
	}

	@RequestMapping("userIndex")
	public ModelAndView userIndex(ModelMap modelMap) {
		return new ModelAndView("admin/page/user/userIndex");
	}

	// 添加个人用户
	@RequestMapping("user/userAdd")
	public ModelAndView userAdd(ModelMap modelMap, @RequestParam(required = false) Long userId) {
		String uploadPath = redissonUtils.get(Constants.FILESERVER_UPLOAD_PATH_KEY);
		String downPath = redissonUtils.get(Constants.FILESERVER_DOWN_PATH_KEY);
		if (null != userId) {
			modelMap.put("userId", userId.toString());
		}
		modelMap.put("uploadPath", uploadPath);
		modelMap.put("downPath", downPath);
		return new ModelAndView("/admin/page/user/userAdd");
	}

	@RequestMapping("userInfo")
	public ModelAndView userInfo(ModelMap modelMap) {
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		if (null != user) {
			user = userExtendService.findUserById(user.getId());
			// 未设置用户类型。默认为个人用户
			String uploadPath = redissonUtils.get(Constants.FILESERVER_UPLOAD_PATH_KEY);
			String downPath = redissonUtils.get(Constants.FILESERVER_DOWN_PATH_KEY);

			modelMap.put("userId", user.getId().toString());
			modelMap.put("uploadPath", uploadPath);
			modelMap.put("downPath", downPath);
			return new ModelAndView("admin/page/user/viewUser");
		}
		modelMap.put("key", redissonUtils.get(UserConstans.RES_PUBLIC_KEY));
		return new ModelAndView("admin/login");
	}

	@RequestMapping("user/userInfo")
	public ModelAndView userInfo(ModelMap modelMap, @RequestParam(required = false) Long userId) {
		String uploadPath = redissonUtils.get(Constants.FILESERVER_UPLOAD_PATH_KEY);
		String downPath = redissonUtils.get(Constants.FILESERVER_DOWN_PATH_KEY);
		if (null != userId) {
			modelMap.put("userId", userId.toString());
		}
		modelMap.put("uploadPath", uploadPath);
		modelMap.put("downPath", downPath);
		return new ModelAndView("/admin/page/user/userInfo");
	}

	@RequestMapping("createPrivateToken")
    public ModelAndView createPrivateToken(ModelMap modelMap){
        TUserExtend u = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
        modelMap.put("nickName",u.getNickname());
        List<TUserToken> list = Lists.newArrayList();
        Map<?,?> map = redissonUtils.hashGet(UserConstans.USER_ACESSTOKEN_KEY+"::"+u.getId(), Map.class);
        if (map != null) {
            Collection<?> linkedHashMap = map.values();
            linkedHashMap.stream().forEach(token ->{
            	try{
                  	list.add(JSON.parseObject(String.valueOf(token),TUserToken.class));
              	}catch (Exception e){
              		e.printStackTrace();
              	};
            });

           modelMap.put("userTokens",list.stream().map((t) -> getScope(t)).collect(Collectors.toList()));
        }
        return new ModelAndView("admin/page/user/createPrivateToken");
    }
	
	private TUserToken getScope(TUserToken userToken) {
		if (userToken.getScope() == -1){
           userToken.setScopeVal("用户全部权限");
        }
        return userToken;
	}
}