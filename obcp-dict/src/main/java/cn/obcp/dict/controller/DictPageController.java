package cn.obcp.dict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// ##remain#import#

/**
 * <pre>
 * 字典的Controller
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Controller
@RequestMapping("/dict/")
public class DictPageController {

	@RequestMapping("dictIndex")
	public ModelAndView dictIndex(ModelMap modelMap) {
		return new ModelAndView("admin/page/dict/dictIndex");
	}

	@RequestMapping("regionIndex")
	public ModelAndView regionIndex(ModelMap modelMap) {
		return new ModelAndView("admin/page/dict/regionIndex");
	}

}