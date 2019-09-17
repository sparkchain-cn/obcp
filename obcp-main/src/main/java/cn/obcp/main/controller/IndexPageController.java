package cn.obcp.main.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONArray;

import cn.obcp.cache.ICache;
import cn.obcp.main.constants.MainConstants;
import cn.obcp.main.utils.TreeUtil;
import cn.obcp.user.VO.NavsNodeTree;
import cn.obcp.user.VO.UserVo;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.service.UserExtendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;

@Controller
@RequestMapping("/")
@Api(value="index")
public class IndexPageController {
	@Autowired
	UserExtendService userExtendService;
	@Autowired
	ICache redissonUtils;


	@RequestMapping("index")
	public ModelAndView index(ModelMap modelMap) {
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		// 获取用户App
		try {
			modelMap.put("user", new UserVo().build(user));
			modelMap.put("userId", user.getId().toString());
		} catch (Exception e) {
			// 出错时，打印异常信息，默认当前用户为无应用
			e.printStackTrace();
		}
		return new ModelAndView("index");
	}

	// 欢迎页
	@RequestMapping("welcome")
	public ModelAndView welcome(ModelMap modelMap) {
		return new ModelAndView("admin/welcome");
	}

	
	// 取出用户左侧菜单栏列表
	@ApiOperation(value = "左侧菜单", notes="左侧菜单", httpMethod="POST")
	@RequestMapping("navs")
	@ResponseBody
	public JSONArray getNavs() {
		// 取出用户所持有的菜单列表
		List<NavsNodeTree> list = userExtendService.getUserResource();
		// 将其转化为JSON传向前台
		JSONArray array = TreeUtil.NavsToArray(list);
		return array;
	}

	// ————————————————————————消息通知,站内信——————————————————//
	@RequestMapping("platformnotify/list")
	public ModelAndView platformNotifyList(ModelMap modelMap) {
		return new ModelAndView("admin/page/platformNotifyList");
	}

	@RequestMapping("platformnotify/edit")
	public ModelAndView platformNotifyEdit(ModelMap modelMap, @RequestParam(required = false) Long mesid) {
		if (mesid != null) {
			modelMap.put("mesid", mesid.toString());
		}
		return new ModelAndView("admin/page/platformNotifyEdit");
	}

	@RequestMapping("clientnotify/list")
	public ModelAndView clientNotifyList(ModelMap modelMap) {
		return new ModelAndView("admin/page/clientNotifyList");
	}
	
}
