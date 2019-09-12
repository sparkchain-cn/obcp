package cn.obcp.main.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import cn.obcp.user.domain.TUserExtend;

@Controller
@RequestMapping("/")
public class PagesController {

	@RequestMapping("403")
	public ModelAndView unthen(ModelMap modelMap) {
		return new ModelAndView("admin/403");
	}

	@RequestMapping("404")
	public ModelAndView notfound(ModelMap modelMap) {
		return new ModelAndView("admin/404");
	}

	@RequestMapping("500")
	public ModelAndView serverError(ModelMap modelMap) {
		return new ModelAndView("admin/500");
	}

	@RequestMapping("common/configIndex")
	public ModelAndView configIndex(ModelMap modelMap) {
        TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
        modelMap.put("userId", String.valueOf(user.getId()));
		return new ModelAndView("admin/common/configIndex");
	}

	@RequestMapping("common/configAdd")
	public ModelAndView configAdd(ModelMap modelMap, @RequestParam(name = "id", required = false) String id) {
        TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
        modelMap.put("userId", String.valueOf(user.getId()));
		if (!Strings.isNullOrEmpty(id)) {
			modelMap.put("id", id);
		}
		return new ModelAndView("admin/common/addConfig");
	}

	// 隐私条款
	@RequestMapping("privacyService")
	public ModelAndView privacyService(ModelMap modelMap) {
//        String privacyService = redissonUtils.getByString("privacyService");
//        modelMap.put("privacyService", privacyService);
		return new ModelAndView("admin/privacyService");
	}
}