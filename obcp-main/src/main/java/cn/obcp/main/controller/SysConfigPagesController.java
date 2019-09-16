package cn.obcp.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

@Controller
@RequestMapping("/")
public class SysConfigPagesController {

	/**
     *  系统配置列表页面tokenList
     * @param modelmap
     * @return
     */
    @RequestMapping("sysConfigList")
    public ModelAndView sysChainList(ModelMap modelmap,@RequestParam String bizCode,@RequestParam String typeCode){
        modelmap.put("bizCode",bizCode);
        modelmap.put("typeCode",typeCode);
        return new ModelAndView("admin/sysConfigList");
    }

    /**
     *  系统配置添加页面
     * @param modelmap
     * @return
     */
    @RequestMapping("sysConfigAdd")
    public ModelAndView sysChainAdd(ModelMap modelmap, @RequestParam String bizCode,@RequestParam String typeCode,
                                    @RequestParam(required=false) String configKey){
        modelmap.put("bizCode",bizCode);
        modelmap.put("typeCode",typeCode);
        if(!Strings.isNullOrEmpty(configKey)) {
            modelmap.put("configKey",configKey);
        }
        return new ModelAndView("admin/sysConfigAdd");
    }
    
    

}