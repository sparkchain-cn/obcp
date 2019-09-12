package cn.obcp.user.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.user.Exception.VerifyException;
import cn.obcp.user.domain.TRole;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.dto.RoleListParamDto;
import cn.obcp.user.service.RoleService;
import cn.obcp.user.service.UserExtendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// ##remain#import#

/**
 * <pre>
  * 角色的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@Api("Role")
@RequestMapping("/v1/role/")
public class RoleController extends BaseController<TRole, Long> {


	@Autowired
	private UserExtendService userExtendService;
	
	@Autowired
	private RoleService roleService;
	
	@Value("${com.sparkchain.admin.roleId}")
	private String adminRole;
	
	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsRoleService") BaseService<TRole, Long> entityService) {
		this.entityService = entityService;

	}
	/**
	 *  todo：管理员查看角色列表接口
	 * @param roleListParamDto
	 * @return
	 */
	  @RequestMapping(value="roleList",method=RequestMethod.POST)
	  public LayUiRetData resourcesList(RoleListParamDto roleListParamDto) {
		  //
		  try {
		  	  if (!userExtendService.currIsAdmin()){
		  	  	 return LayUiRetData.error("权限不足");
		  	  }
		  	  //获取参数
			  Map<String,Object> params = RoleListParamDto.getParamBuDto(roleListParamDto);

			  Page<TRole> page =PageUtils.getPageInfo(params);
			  Page<TRole> pageInfo = roleService.findByPage(page);
			  return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		  }catch (Exception e) {
			  e.printStackTrace();
			
			  return LayUiRetData.error("获取失败！");
		}		
	  }

	/**
	 *  非管理员用户访问角色列表
 	 * @param
	 * @return
	 */
	@RequestMapping(value="roleListUser",method={RequestMethod.POST,RequestMethod.GET})
	public LayUiRetData roleListUser(RoleListParamDto roleListParamDto) {
		//
		try {
			TUserExtend userExtend = userExtendService.getCurrentUser();
			if (StringUtils.isNullOrEmpty(userExtend.getAppid())){
				return LayUiRetData.error("缺少应用");
			}
			Map<String,Object> params = RoleListParamDto.getParamBuDto(roleListParamDto);
			Page<TRole> page =PageUtils.getPageInfo(params);
			Page<TRole> pageInfo = roleService.findByPage(page);
			return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		}catch (Exception e) {
			e.printStackTrace();
			
			return LayUiRetData.error("获取失败！");
		}
	}

	  /**
	   *  新增角色
	   * TODO
	   * @param role
	   * @param bindingResult
	   * @return
	   * RetData
	   * lmf 创建于2018年11月20日
	   */
	  @Transactional
	  @ApiOperation("新增角色")
	  @PostMapping("saveRole")
	  public RetData saveResource( @Valid TRole role,BindingResult bindingResult) {
	  	try {
			if (bindingResult.hasErrors()) {
				return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
			} else {
				if (roleService.exitCode(role.getCode())) {
					return RetData.error("编码已存在！");
				} else {
//					TUserExtend userExtend = userExtendService.getCurrentUser();
					boolean result = roleService.save(role);
					if (result) {
						logger.info("新增角色" + role.getName() + "成功");
						return RetData.succuess();
					} else {
						logger.error("新增角色" + role.getName() + "出错");
						return RetData.error("添加失败！");
					}
				}
			}
		}catch (Exception e){
	  		return RetData.error("");
		}
	 }
	
	  /**
	   * 
	   * TODO 修改角色
	   * @param role
	   * @param bindingResult
	   * @return
	   * RetData
	   * lmf 创建于2018年11月20日
	   */
	  @Transactional
	  @ApiOperation("编辑角色")
	  @RequestMapping(value="updateRole",method=RequestMethod.POST)
	  public RetData updateResource(@Valid TRole role,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
		}else {		
			TRole r = roleService.findByFieldName("code", role.getCode());
			if(null != r  && !role.getId().equals(r.getId())) {
				return RetData.error("编码已存在！");				
			}else {
				logger.info("修改角色"+role.getName());
				boolean result = roleService.update(role); 
				if(result) {
					logger.info("修改角色"+role.getName()+"成功");
	    			return RetData.succuess();
	    		}else {
	    			logger.error("修改角色"+role.getName()+"出错");
	    			return RetData.error("添加失败！");
	    		}  
			}
		}
	 }
	  
	  
	  /**
	 * TODO
	 * RetData
	 * lmf 创建于2018年11月15日
	 */
	  @ApiOperation("角色详情")
	  @RequestMapping(value="getInfo",method=RequestMethod.POST)
	  public RetData getInfo(@RequestParam("id") Long id) {
		// TODO Auto-generated method stub
		try {
			TRole role  = entityService.findById(id);	
			if(role != null) {
				return RetData.succuess(role);
			}else {
				return RetData.error("角色不存在！");
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			
			logger.error("获取角色出错");
			return RetData.error("发生错误！");
		}
	}

    @RequestMapping(value="remove",method=RequestMethod.POST)
	@Transactional
	@ApiOperation("角色删除")
    public RetData remove(@RequestParam("id") Long id) {
    	try {
    		TRole role = roleService.findById(id);
    		if(null != role) {
    			logger.info("开始删除角色"+role.getName());
    			if (!userExtendService.currIsAdmin()){
					return RetData.error("非管理员用户");
				}
				return roleService.remove(role);

    		}else {
    			logger.error("删除角色"+role.getName()+"失败,角色不存在");
    			return RetData.error("角色不存在");
    		}
    	}catch (Exception e) {
			
    		logger.error("删除角色出错");
    		return RetData.error("发生错误！");
		}
	}
	
	/**
	 * 用户绑定角色页面
	 * TODO
	 * @param params
	 * @return
	 * LayUiRetData
	 * lmf 创建于2018年11月22日
	 */
	@RequestMapping(value="userRoleList",method=RequestMethod.POST)
	@ApiOperation("用户绑定角色页面")
	public String userRoleList(@RequestParam Map<String,Object> params) {
		 try {
			 if(params.containsKey("page")) {
				  params.put("pagenum", params.get("page").toString());
			  }
			  if(params.containsKey("limit")) {
				  params.put("pagesize", params.get("limit").toString());
			  }
				Page<TRole> page =PageUtils.getPageInfo(params);
				Long userId;
				Object userParam = params.get("userId");
				if( userParam == null) {
					 return JSON.toJSONString(LayUiRetData.error("请选择用户！！"));
				}else {
					userId = Long.valueOf(String.valueOf(userParam));
			        Page<TRole> pageInfo = roleService.bindUserPage(page,userId);       
			        return JSON.toJSONString(LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal()));
				}
			  }
		 		catch (NumberFormatException e) {
					
		 			return JSON.toJSONString(LayUiRetData.error("用户无效！"));
				}
		 		catch (Exception e) {
				  e.printStackTrace();
				
				  return JSON.toJSONString(LayUiRetData.error("获取失败！"));
			}	
	}
	// ##remain#property#

}