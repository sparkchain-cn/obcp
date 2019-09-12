package cn.obcp.dict.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.dict.domain.TRegion;
import cn.obcp.dict.service.RegionService;


// ##remain#import#

/**
 * <pre>
  * 行政区划的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/region/")
public class RegionController extends BaseController<TRegion, Long> {

	@Autowired
	private RegionService regionService;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsRegionService") BaseService<TRegion, Long> entityService) {
		this.entityService = entityService;

	}

	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public LayUiRetData getRegion(@RequestBody Map<String,Object> params){
		if (params.containsKey("page")){
			params.put("pagenum",params.get("page"));
			params.remove("page");
		}
		if (params.containsKey("limit")){
			params.put("pagesize",params.get("limit"));
			params.remove("limit");
		}
		Page pageInfo = PageUtils.getPageInfo(params);
		Page<TRegion> page = regionService.findByPage(pageInfo);
		return LayUiRetData.success(page);
	}

	@RequestMapping(value = "/saveRegion",method = RequestMethod.POST)
	public LayUiRetData saveRegion(TRegion tRegion){
		tRegion.setUpdateTime(new Date());
		if (tRegion.getId() == null){
			tRegion.setId(uuidCreator.id());
			tRegion.setSortnum(regionService.countAll()+1);
			tRegion.setCreateTime(new Date());
			return LayUiRetData.success(regionService.save(tRegion));
		}else {
			return LayUiRetData.success(regionService.update(tRegion));
		}
	}

	@RequestMapping(value = "/deleteRegion",method = RequestMethod.POST)
	public LayUiRetData deleteRegion(@RequestParam Long id){
		regionService.deleteById(id);
		return  LayUiRetData.success("删除成功！");
	}

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public String getAll() {
//		List<TRegion> list = regionService.findAll();
//		List<TreeNode> nodes = TreeNode.bulidRegion(list);
//		return JSON.toJSONString(nodes);
		return null;
	}

	@RequestMapping("getInfo")
	public LayUiRetData getInfo(TRegion region) {
		TRegion tRegion = new TRegion();
		try {
			if(region.getId() != null) {

				tRegion = regionService.getRegionById(region.getId());
			}else if(region.getParentid() != null) {
				tRegion = regionService.getParentById(region.getParentid());
			}
			else {
				return LayUiRetData.error("参数错误！");
			}
			return LayUiRetData.success(tRegion);
		}catch (Exception e) {
			return LayUiRetData.error("发生错误！");
		}

	}
	// ##remain#property#

}