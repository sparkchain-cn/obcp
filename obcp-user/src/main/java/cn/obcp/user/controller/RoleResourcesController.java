package cn.obcp.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.user.domain.TRole;
import cn.obcp.user.domain.TRoleResources;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.service.RoleResourcesService;
import cn.obcp.user.service.RoleService;
import cn.obcp.user.service.UserExtendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// ##remain#import#

/**
 * <pre>
  * 角色-资源的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@Api("RoleResource")
@RequestMapping("/v1/roleresources/")
public class RoleResourcesController extends BaseController<TRoleResources, Long> {

	@Autowired
	private RoleResourcesService roleResourcesService;
	@Value("${com.sparkchain.admin.roleId}")
	private String adminRole;
	@Resource
	private cn.obcp.user.shiro.service.ShiroService shiroService;

	@Autowired
	private UserExtendService userExtendService;

	@Autowired
	private RoleService roleService;
	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsRoleResourcesService") BaseService<TRoleResources, Long> entityService) {
		this.entityService = entityService;

	}

	@ApiOperation("角色绑定资源")
	@RequestMapping(value="bindResource",method=RequestMethod.POST)
	public RetData bindResource(@RequestParam("resorces") String resources,@RequestParam("roleId") Long roleId) {
		
		TRole role = roleService.findById(roleId);
		if(role == null) {
			return RetData.error("角色不存在");
		}else if(role.getState() == ResourceStateEnmu.DISABLED.getStatus()) {
			return RetData.error("角色已禁用");
		}
		else if(adminRole.equalsIgnoreCase(String.valueOf(roleId)) && !userExtendService.currIsAdmin() ) {
			return RetData.error("admin权限不能修改");
		}
		List<String> resourcesList = new ArrayList<>();
		
		if(!StringUtils.isNullOrEmpty(resources)) {
			resourcesList = Arrays.asList(resources.split(","));
		}
		boolean result = roleResourcesService.bindResource(resourcesList,roleId);
		if(result) {
			logger.info("绑定资源角色成功");
			shiroService.updatePermission();
			logger.info("清除用户授权缓存成功");
			return RetData.succuess();
		}else {
			logger.error("绑定资源角色失败");
			return RetData.error("绑定失败！");
		}
		
	}
	// ##remain#property#

}