package cn.obcp.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.user.Exception.VerifyException;
import cn.obcp.user.VO.SelectNode;
import cn.obcp.user.VO.ZTreeNode;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TResources;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.dto.ResourceDto;
import cn.obcp.user.service.ResourcesService;
import cn.obcp.user.service.RoleService;
import cn.obcp.user.service.UserExtendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

// ##remain#import#

/**
 * pageRegisterList
 * 
 * <pre>
  * 资源的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@Api("Resource")
@RequestMapping("/v1/resources/")
public class ResourcesController extends BaseController<TResources, Long> {

	private Logger log = LoggerFactory.getLogger(ResourcesController.class);

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserExtendService userExtendService;

	@Resource
	private cn.obcp.user.shiro.service.ShiroService shiroService;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsResourcesService") BaseService<TResources, Long> entityService) {
		this.entityService = entityService;
	}

	/**
	 * 添加资源下拉树形
	 * 
	 * @return List<SelectNode> lmf 创建于2018年11月26日
	 */
	@ApiOperation(value = "添加资源下拉树形", notes = "添加资源下拉树形")
	@PostMapping("selectTree")
	public List<SelectNode> getSelectNodeTree() {
		new ArrayList<>();
		List<SelectNode> trees = new ArrayList<>();
		try {
			List<TResources> list = resourcesService.getAllResource(new TResources());
			trees = SelectNode.bulid(list);
			return trees;
		} catch (Exception e) {
			e.printStackTrace();
			return trees;

		}
	}

	/**
	 * 资源列表页面左侧树形
	 * 
	 * @return String lmf 创建于2018年11月26日
	 */
	@PostMapping("treeList")
	@ApiOperation(value = "资源列表页面左侧树形", notes = "资源列表页面左侧树形")
	public String getUserTreeList() {
		// 暂时不支持根据条件 搜索 节点

		TUserExtend userExtend = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		List<ZTreeNode> trees = Lists.newArrayList();
		try {
			List<TResources> resourcesList;
			if (userExtendService.isAdmin(userExtend.getId())) {
				resourcesList = resourcesService.getAllMenuResource();
			} else {
				resourcesList = resourcesService.findResourceByUserId(userExtend.getId(),
						UserConstans.RESOURCE_CONSOULE_GROUP);
			}
			trees = new ZTreeNode().bulid(resourcesList);
			return JSON.toJSONString(trees);
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(trees);

		}
	}

	/**
	 * 资源列表（根据父级ID获取）
	 * 
	 * @return LayUiRetData lmf 创建于2018年11月14日
	 */
	@ApiOperation(value = "资源列表", notes = "资源列表")
	@RequestMapping(value = "resourcesList", method = RequestMethod.POST)
	public LayUiRetData resourcesList(ResourceDto resourceDto) {
		//
		Map params = resourceDto.getParamBuDto(resourceDto);
		try {
			List<TResources> resourcesList = new ArrayList<>();
			Page<TResources> page = PageUtils.getPageInfo(params);
			long count = 0L;
			if (null != page.getMeta().get("parentid")) {

				Page<TResources> pageInfo = resourcesService.findByPage(page);
				if (null != pageInfo.getResult()) {
					resourcesList = pageInfo.getResult();
					count = pageInfo.getTotal();
				}
			} else {
				List<TResources> resources = resourcesService.findRoot(page);
				if (null != resources && resources.size() > 0) {
					resourcesList = resources;
				}
				count = resourcesService.getRootCount();
			}
			return LayUiRetData.success(resourcesList, count);
		} catch (Exception e) {
			e.printStackTrace();

			return LayUiRetData.error("获取失败！");
		}
	}

	/**
	 * 绑定角色页面数据接口
	 * 
	 * @param
	 * @return String lmf 创建于2018年11月18日
	 */
	@RequestMapping(value = "bindResourceList", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "绑定角色页面资源数据接口", notes = "绑定角色页面资源数据接口")
	public List<ZTreeNode> bindResourceList(@RequestParam("rid") String rid) {
		Long uid = userExtendService.getCurrentUser().getId();
		List<ZTreeNode> trees = new ArrayList<>();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("rid", rid);
			param.put("uid", uid);
			Page<TResources> page = PageUtils.getPageInfo(param);
			page.setNeedPage(false);

			if (userExtendService.isAdmin(uid)) {// 管理员查看全部权限
				if (roleService.getSysAdminRole().equalsIgnoreCase(rid)) {
					List<TResources> resources = resourcesService.findAllRes();
					page.setResult(resources);
				} else
					page = resourcesService.findByAdmin(page);
			} else {
				page = resourcesService.findUserResBindByPage(page);
			}
			trees = new ZTreeNode().bulid(page.getResult());
			System.out.println(JSON.toJSONString(trees));
			return (trees);
		} catch (Exception e) {

			e.printStackTrace();
			return trees;
		}
	}

	/**
	 * 保存资源
	 * 
	 * @param dict
	 * @param bindingResult
	 * @return RetData lmf 创建于2018年11月14日
	 */
	@ApiOperation(value = "新增资源", notes = "新增资源")
	@RequestMapping(value = "saveResource", method = RequestMethod.POST)
	public RetData save(@Valid TResources res, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
			} else {
				if (resourcesService.exitCode(res.getCode())) {
					return RetData.error("编码已存在！");
				} else {
					log.info("新增资源");
					RetData result = resourcesService.saveRes(res);
					if (result.isSuccess()) {
						shiroService.reloadFilterChains();
					}
					return result;
				}
			}
		} catch (Exception e) {

			log.error("新增资源出错");
			return RetData.error("发生错误！");
		}
	}

	/**
	 * 修改资源
	 * 
	 * @param dict
	 * @param bindingResult
	 * @return RetData lmf 创建于2018年11月14日
	 */
	@ApiOperation("编辑资源")
	@RequestMapping(value = "updateResource", method = RequestMethod.POST)
	public RetData update(@Valid TResources res, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
			} else {
				log.info("修改资源");
				boolean result = resourcesService.update(res);
				if (result) {
					shiroService.reloadFilterChains();
					return RetData.succuess();
				} else {
					return RetData.error("修改失败！");
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
			return RetData.error("发生错误！");
		}
	}

	@ApiOperation("删除资源")
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public RetData remove(@ApiParam @RequestParam("id") Long id) {
		try {
			log.info("开始删除资源");
			RetData r = resourcesService.remove(id);
			if (r.isSuccess()) { // 刷新缓存
				shiroService.reloadFilterChains();
			}
			return r;
		} catch (Exception e) {

			return RetData.error("发生错误！");
		}
	}

	@ApiOperation("查询资源")
	@RequestMapping(value = "getInfo", method = RequestMethod.POST)
	public RetData getInfo(@ApiParam(name = "id", value = "id") TResources resource) {
		TResources resources = new TResources();
		try {
			if (resource.getId() != null) {

				resources = resourcesService.findResourceById(resource.getId());
			} else if (resource.getParentid() != null) {
				resources = resourcesService.findParentResourceById(resource.getParentid());
			} else {
				return RetData.error("参数错误！");
			}
			return RetData.succuess(resources);
		} catch (Exception e) {
			logger.error("获取资源详情出错");
			return RetData.error("发生错误！");
		}

	}

	/**
	 * 获取
	 * 
	 * @param id
	 * @return LayUiRetData lmf 创建于2018年11月27日
	 */
	@ApiOperation("根据父级ID查询子级资源")
	@RequestMapping(value = "getByPid", method = RequestMethod.POST)
	public LayUiRetData getByPid(@ApiParam @RequestParam("parentid") String parentid) {
		List<TResources> resources = new ArrayList<>();
		try {
			Long id = Long.valueOf(parentid);

			TResources resource = resourcesService.findById(id);
			if (null != resource) {
				resources.add(resource);
			}
			return LayUiRetData.success(resources);
		} catch (NumberFormatException e) {
			logger.error("获取资源详情出错");
			return LayUiRetData.success(resources);
		}
	}

	/**
	 * 批量更新资源父级
	 * 
	 * @param dropNodeIds 选择移动的节点
	 * @param parentId    移动的目标节点
	 * @param moveType    移动类型
	 * @return
	 */
	@ApiOperation("批量更新资源父级")
	@RequestMapping("updateNodeParent")
	public RetData updateNodeParent(@RequestParam(value = "dropNodeIds[]") String[] dropNodeIds,
			@RequestParam(required = false) Long parentId, @RequestParam String moveType) {
		// 移除不是Long类型的数据
		List<String> ids = Lists.newArrayList(dropNodeIds).stream().filter(id -> isLong(id))
				.collect(Collectors.toList());

		return resourcesService.updateNodeParent(ids, parentId, moveType);
	}

	private boolean isLong(String id) {
		try {
			Long.valueOf(id);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	// ##remain#property#

}