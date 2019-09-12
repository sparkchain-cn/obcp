package cn.obcp.user.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.user.domain.TRole;
import cn.obcp.user.domain.TUserRole;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.service.RoleService;
import cn.obcp.user.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// ##remain#import#

/**
 * <pre>
  * 用户-角色的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@Api("UserRole")
@RequestMapping("/v1/userrole/")
public class UserRoleController extends BaseController<TUserRole, Long> {

	@Autowired
	private UserRoleService userRoleService;
	@Resource
	private cn.obcp.user.shiro.service.ShiroService shiroService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsUserRoleService") BaseService<TUserRole, Long> entityService) {
		this.entityService = entityService;

	}

	/**
	 * bindUser
	 * TODO
	 * @param users
	 * @param roleId
	 * @return
	 * RetData
	 * lmf 创建于2018年11月16日
	 */
	@ApiOperation("角色绑定用户")
	@RequestMapping(value="bindUser",method=RequestMethod.POST)
	public String bindUser(@RequestParam("userId") Long userId ,@RequestParam("roleId") Long roleId) {
		//List<String> usersList = Lists.newArrayList(Arrays.asList(users));
		TRole role = roleService.findById(roleId);
		if(role == null) {
			logger.error("角色绑定用户失败，角色不存在");
			return JSON.toJSONString(RetData.error("角色不存在"));
		}else if(role.getState() == ResourceStateEnmu.DISABLED.getStatus()) {
			logger.error("角色绑定用户失败，角色已被禁用");
			return JSON.toJSONString(RetData.error("角色已禁用"));
		}	
		else {
			boolean result = userRoleService.bindUser(userId,roleId);
			if(result) {
				logger.info("角色绑定用户成功");
				shiroService.updatePermission();
				return JSON.toJSONString(RetData.succuess());
			}else {
				logger.error("角色绑定用户失败");
				return JSON.toJSONString(RetData.error("绑定失败！"));
			}
		}		
	}
	@ApiOperation("角色取消绑定用户")
	@RequestMapping(value="unBindUser",method=RequestMethod.POST)
	public String unBindUser(@RequestParam("userId") Long userId ,@RequestParam("roleId") Long roleId) {
		//List<String> usersList = Lists.newArrayList(Arrays.asList(users));
		TRole role = roleService.findById(roleId);
		if(role == null) {
			logger.error("角色解除绑定用户失败，角色不存在");
			return JSON.toJSONString(RetData.error("角色不存在"));
		}else if(role.getState() == ResourceStateEnmu.DISABLED.getStatus()) {
			logger.error("角色解除绑定用户失败，角色已被禁用");
			return JSON.toJSONString(RetData.error("角色已禁用"));
		}	
		else {
			boolean result = userRoleService.unBindUser(userId,roleId);
			if(result) {
				logger.info("角色解除绑定用户成功");
				shiroService.updatePermission();
				return JSON.toJSONString(RetData.succuess());
			}else {
				logger.error("角色解除绑定用户失败");
				return JSON.toJSONString(RetData.error("绑定失败！"));
			}
		}		
	}
	
	// ##remain#property#

}