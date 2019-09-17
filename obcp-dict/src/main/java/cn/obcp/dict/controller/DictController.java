package cn.obcp.dict.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.dict.common.Constant;
import cn.obcp.dict.domain.TDict;
import cn.obcp.dict.model.ZTreeNode;
import cn.obcp.dict.service.DictService;
import cn.obcp.dict.vo.SelectNode;


// ##remain#import#

/**
 * <pre>
 * 字典的Controller
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/dict/")
public class DictController extends BaseController<TDict, String> {

    @Autowired
    private DictService dictService;

    @Autowired
    @Override
    public void setEntityService(@Qualifier("bsDictService") BaseService<TDict, String> entityService) {
        this.entityService = entityService;

    }

    // ##remain#property#
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public LayUiRetData getDictList(@RequestParam Map<String, Object> params) {
        if (params.containsKey("page")){
            params.put("pagenum",params.get("page").toString());
            params.remove("page");
        }
        if (params.containsKey("limit")){
            params.put("pagesize",params.get("limit").toString());
            params.remove("limit");
        }
        Page pageInfo = PageUtils.getPageInfo(params);
        Page<TDict> page = dictService.findByPage(pageInfo);
        List<TDict> list = page.getResult();
        if (list != null && list.size() > 0) {
            list.forEach(tDict -> {
                if (tDict.getStatus().equals(Constant.DICT_STATUS_INNORMAL)){
                    tDict.setStatusName("禁用");
                }
                if (tDict.getStatus().equals(Constant.DICT_STATUS_NORMAL)){
                    tDict.setStatusName("启用");
                }
                tDict.setParentName(dictService.getDictName(tDict.getParent()));
            });
        }
        return LayUiRetData.success(page.getResult(), page.getTotal());
    }

    @RequestMapping(value = "treeList", method = {RequestMethod.GET,RequestMethod.POST})
    public String treeList() {
        List<TDict> list = dictService.getDictAll();
        List<ZTreeNode> trees = new ZTreeNode().bulid(list);
        return JSON.toJSONString(trees);
    }

    @RequestMapping(value = "saveDict", method = RequestMethod.POST)
    public LayUiRetData saveDict(@Valid TDict tDict) {
    	if(tDict.getCode() == null) {
    		return LayUiRetData.error("字典编码不能为空");
    	}else if(tDict.getName() == null) {
    		return LayUiRetData.error("字典名称不能为空");
    	}
    	if(StringUtils.isNullOrEmpty(tDict.getParent())) {
    		tDict.setParent(null);
    	}
    	TDict et = dictService.getDictById(tDict.getCode());
    	if(et != null) {
    		 return LayUiRetData.error("字典编码已存在"); 
    	}
        String result = dictService.saveDict(tDict);
        return LayUiRetData.success(result);
    }

    @RequestMapping(value = "updateDict", method = RequestMethod.POST)
    public LayUiRetData updatDict(@Valid TDict tDict) {
    	if(tDict.getCode() == null) {
    		return LayUiRetData.error("字典编码不能为空");
    	}else if(tDict.getName() == null) {
    		return LayUiRetData.error("字典名称不能为空");
    	}
    	if(StringUtils.isNullOrEmpty(tDict.getParent())) {
    		tDict.setParent(null);
    	}
    	dictService.update(tDict);
        return LayUiRetData.success("");
    }
    
    @RequestMapping(value = "deleteDict", method = RequestMethod.POST)
    public LayUiRetData deleteDict(@RequestParam(required = true) String code) {
        String result = dictService.deleteDict(code);
        return LayUiRetData.success(result);
    }

    @RequestMapping("getInfo")
    public LayUiRetData getInfo(TDict dict) {
        TDict tDict = new TDict();
        try {
            if(StringUtils.isNotNullOrEmpty(dict.getCode())) {
                tDict = dictService.getDictById(dict.getCode());
            }else if(StringUtils.isNotNullOrEmpty(dict.getParent())) {
                tDict = dictService.getParentById(dict.getParent());
            }
            else {
                return LayUiRetData.error("参数错误！");
            }
            return LayUiRetData.success(tDict);
        }catch (Exception e) {
            return LayUiRetData.error("发生错误！");
        }

    }
    
	@PostMapping("selectTree")
	public List<SelectNode> getSelectNodeTree() {
		new ArrayList<>();
		List<SelectNode> trees = new ArrayList<>();
		try {
			List<TDict> list = dictService.findAll();
			trees = SelectNode.bulid(list);
			return trees;
		} catch (Exception e) {
			e.printStackTrace();
			return trees;
		}
	}
}