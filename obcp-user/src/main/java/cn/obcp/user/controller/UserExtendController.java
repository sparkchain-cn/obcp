package cn.obcp.user.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.base.utils.MD5Utils;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.base.utils.RSACoder;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.cache.ICache;
import cn.obcp.user.Exception.VerifyException;
import cn.obcp.user.VO.CompanyAddVo;
import cn.obcp.user.VO.LoginVo;
import cn.obcp.user.VO.RoleResourceVo;
import cn.obcp.user.VO.UserVo;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserExtends;
import cn.obcp.user.dto.UserListparamDto;
import cn.obcp.user.service.UserExtendService;
import cn.obcp.user.service.UserExtendsService;
import cn.obcp.user.util.CheckStrength;
import cn.obcp.user.util.CodeUtils;
import cn.obcp.user.util.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;
import sun.misc.BASE64Decoder;

// ##remain#import#

/**
 * <pre>
  * 用户表的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 * @param <TCompanyAddVo>
 * @param <TCompanyAddVo>
 */
@RestController
@RequestMapping("/v1/userextend/")
@Api(value="USER")
public class UserExtendController<TCompanyAddVo> extends BaseController<TUserExtend, Long> {

	@Autowired
	private ICache redissonUtils;
	
	
    @Value("${spring.application.name:spc-chain-baas-main}")
    private String applicationName;

	
	@Autowired
	private UserExtendService userExtendService;
	@Resource
  	private cn.obcp.user.shiro.service.ShiroService shiroService;
	@Autowired
	private UserExtendsService userExtendsService;
	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsUserExtendService") BaseService<TUserExtend, Long> entityService) {
		this.entityService = entityService;

	}
	
	/**
	 * 用户列表(管理员)
	 * TODO
	 * @param params(page,limit)
	 * @return
	 * LayUiRetData
	 * lmf 创建于2018年11月27日
	 */
	@ApiOperation(value="查询用户列表",notes="查询用户列表")
	@RequestMapping(value="userList",method={RequestMethod.POST})
	public LayUiRetData userList(UserListparamDto userListparamDto) {
		 try {
		 	if (!userExtendService.currIsAdmin()){
		 		return LayUiRetData.error("无权限");
			}
			Page<TUserExtend> page = PageUtils.getPageInfo(UserListparamDto.getParamBuDto(userListparamDto));
	        Page<TUserExtend> pageInfo = userExtendService.findByPage(page);
	        return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		  }catch (Exception e) {
			  e.printStackTrace();
			// TODO: handle exception
			  return LayUiRetData.error("获取失败！");
		}	
	}

	@ApiOperation(value="查询未加入Org用户列表",notes="查询未加入Org用户列表")
	@RequestMapping(value="orgUserList",method={RequestMethod.POST,RequestMethod.GET},produces = "application/json")
	@ResponseBody
	public LayUiRetData orgUserList( @RequestBody(required = false) UserListparamDto userListparamDto) {
		try {
			Page<TUserExtend> page = PageUtils.getPageInfo(UserListparamDto.getParamBuDto(userListparamDto));
			Page<TUserExtend> pageInfo = userExtendService.findUserWithoutOrg(page);
			return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return LayUiRetData.error("获取失败！");
		}
	}

	/**
	 * 用户列表(普通用户)
	 * TODO
	 * @param params(page,limit)
	 * @return
	 * LayUiRetData
	 * lmf 创建于2018年11月27日
	 */
	@ApiOperation(value="查询用户列表",notes="查询用户列表")
	@RequestMapping(value="userPlatList")
	public LayUiRetData userPlatList(UserListparamDto userListparamDto) {
		try {
			if (StringUtils.isNullOrEmpty(userExtendService.getCurrentUser().getAppid())){
				return LayUiRetData.error("请选择应用!");
			}
			Page<TUserExtend> page =PageUtils.getPageInfo(UserListparamDto.getParamBuDto(userListparamDto));
			Page<TUserExtend> pageInfo = userExtendService.findByPage(page);
			return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return LayUiRetData.error("获取失败！");
		}
	}

	//用户角色资源信息列表
	@ApiOperation(value="查询用户角色资源信息列表",notes="查询用户角色资源信息列表")
	@RequestMapping(value="getRoleResourceByUser",method={RequestMethod.POST,RequestMethod.GET})
	public LayUiRetData getRoleResourceByUser(@RequestParam Map<String, Object> params) {
		try {
			if(params.containsKey("userId")) {
				params.put("pagenum", null != params.get("page") ? String.valueOf(params.get("page")) : "1");
				params.put("pagesize", null != params.get("limit") ? String.valueOf(params.get("limit")) :"10");
				Page<RoleResourceVo> page =PageUtils.getPageInfo(params);
		        Page<RoleResourceVo> pageInfo = userExtendService.getRoleResourceByUser(page);       
		        return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
			}
			return LayUiRetData.error("参数错误，没有指定用户！");
			
		  }catch (Exception e) {
			  e.printStackTrace();
			// TODO: handle exception
			  return LayUiRetData.error("获取失败！");
		}
	}
	@ApiIgnore
	@ApiOperation(value="查询企业用户列表",notes="查询企业用户列表")
	@RequestMapping(value="companyList",method=RequestMethod.POST)
	public LayUiRetData companyList(@RequestParam Map<String,Object> params) {
		try {
			params.put("organization",UserConstans.USER_TYPE_COMPANY);
			
			params.put("pagenum", null != params.get("page") ? String.valueOf(params.get("page")) : "1");
			params.put("pagesize", null != params.get("limit") ? String.valueOf(params.get("limit")) :"10");
			Page<TUserExtend> page =PageUtils.getPageInfo(params);
	        Page<TUserExtend> pageInfo = userExtendService.findCompanyByPage(page);       
	        return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		}catch (Exception e) {
			// TODO: handle exception
			 return LayUiRetData.error("获取失败！");
		}
	} 
	
	/**
	 * 用户审核
	 * @param userId
	 * @param verifyuser
	 * @param State
	 * @param explain
	 * @return
	 */
	@ApiIgnore
	@ApiOperation(value="用户审核",notes="用户审核")
	@RequestMapping(value="verify",method=RequestMethod.POST)
	public RetData verify(@RequestParam Long userId,@RequestParam Long verifyuser,@RequestParam int state,
			@RequestParam(required=false) String explain) {
		try {
			RetData res =  userExtendService.verifyUser(userId,verifyuser,state,explain);
			if(res.isSuccess()) {
				//审核通过,同步用户APP
				//notifySyncFreeVisitApp();
			}
			return res;
		}catch (Exception e) {
			// TODO: handle exception
			return RetData.error("服务端异常！");
		}
	}


	/**
	 *  用户多条件并列查询
	 * @param params
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("userDropList")
	public LayUiRetData userDropList(@RequestParam Map<String,Object> params) {
		 try {
			params.put("pagenum", null != params.get("page") ? String.valueOf(params.get("page")) : "1");
			params.put("pagesize", null != params.get("limit") ? String.valueOf(params.get("limit")) :"10");
			Page<TUserExtend> page =PageUtils.getPageInfo(params);
	        Page<TUserExtend> pageInfo = userExtendService.searchByOr(page);       
	        return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		  }catch (Exception e) {
			  e.printStackTrace();
			// TODO: handle exception
			  return LayUiRetData.error("获取失败！");
		}	
	}
	
	/**
	 * 已绑定rid用户
	 * TODO
	 * @param params(roleId,page,limit)
	 * @return
	 * LayUiRetData
	 * lmf 创建于2018年11月27日
	 */
	@ApiOperation(value="已绑定roleId用户",notes="已绑定roleId用户")
	@RequestMapping(value="userBindList",method=RequestMethod.POST)
	public LayUiRetData userBindList(@ApiParam(name="params(rid,page,limit)",value="角色id，分页参数")
	@RequestParam(required = false)  Map<String,Object> params) {
		 try {
			params.put("pagenum", params.get("page").toString());
			params.put("pagesize", params.get("limit").toString());
			Page<TUserExtend> page =PageUtils.getPageInfo(params);
	        Page<TUserExtend> pageInfo = userExtendService.getBindByRole(page);       
	        return LayUiRetData.success(pageInfo.getResult(), pageInfo.getTotal());
		  }catch (Exception e) {
			  e.printStackTrace();
			// TODO: handle exception
			  return LayUiRetData.error("获取失败！");
		}	
	}
	
	/**
	 * 未绑定roleId用户列表
	 * TODO
	 * @param params (roleId,page,limit)
	 * @return
	 * LayUiRetData
	 * lmf 创建于2018年11月27日
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value="查询未绑定roleId用户列表",notes="查询未绑定roleId用户列表")
	@RequestMapping(value="bindUserList",method=RequestMethod.POST)
	public LayUiRetData bindUserList(@ApiParam @RequestParam(required = false) Map<String,Object> params) {//
		 try {	
//			if (!userExtendService.currIsAdmin()){
//			}
			params.put("pagenum", params.get("page"));
			params.put("pagesize", params.get("limit"));
			 Page<TUserExtend> page =PageUtils.getPageInfo(params);
			 page = userExtendService.getNoBindByRole(page);
	       /* TypeUtils.compatibleWithFieldName = true;*/
	        return  LayUiRetData.success(page.getResult(), page.getTotal());
		  }catch (Exception e) {
			  e.printStackTrace();
			  return LayUiRetData.error("获取失败！");
		}	
	}
	
	
	/**
	 * 用户注册
	 * TODO
	 * @param userExtend
	 * @param bindingResult
	 * @return
	 * RetData
	 * lmf 创建于2018年11月15日
	 */
	@ApiOperation(value="用戶注册",notes="用户注册")
	@RequestMapping(value="register",method=RequestMethod.POST)
	public RetData Register(@RequestParam String mobile,@RequestParam String password,@RequestParam String confirmPassword,@RequestParam String nickname,@RequestParam String validateCode) {
		try {
	    		
	    		boolean result = userExtendService.checkMobileNum(mobile);
	    		if(result) {
	    			return RetData.error("手机号码已注册");
	    		}else {
	    			
	    			String key = "register_phone_" + mobile;
	    			boolean code =  userExtendService.checkValidateCode(key, validateCode);
	    			if (code != true) {
	    				return RetData.error("验证码错误！");
	    			}
	    			
	    			 int passwordScore = CheckStrength.score(password);
	    			 if(passwordScore < 0) {
	    				 return RetData.error("注册失败,密码不符合要求！");
	    			 }else {
	    				 	/*String inviteCodeExpire =redissonUtils.get("INVATE_CODE_EXPIRE");//邀请码过期时间
		 	    			Map inviteCodeMap = redissonUtils.getMap(UserConstans.INVITE_CODE_KEY);
		 	    			if(inviteCodeMap.containsKey(invateCode)) {
		 	    				String timestamp = String.valueOf(inviteCodeMap.get(invateCode));//时间戳字符串
		 	    				if(new Date().getTime() - Long.valueOf(inviteCodeExpire) > Long.valueOf(timestamp)) {
		 	    					return RetData.error("邀请码已过期");
		 	    				}
		 	    			}else {
		 	    				return RetData.error("邀请码无效");
		 	    			}*/
	    				 TUserExtend user = new TUserExtend();
	    				 user.setMobile(mobile);
	    				 user.setPassword(password);
	    				 user.setNickname(nickname);
	    				 
	    				   result = userExtendService.save(user);
	    					if(result) {
	    						logger.info("用户注册成功");
	    						redissonUtils.delete(key); //注册成功清除缓存
	    					/*	inviteCodeMap.remove(invateCode);
	    						redissonUtils.addAllByMap(UserConstans.INVITE_CODE_KEY, inviteCodeMap);*/
	    	 	    			return RetData.succuess();
	    	 	    		}else {
	    	 	    			logger.error("用户注册失败");
	    	 	    			return RetData.error("注册失败！");
	    	 	    		}
	    			 }
	    		}	    		
		}catch (NumberFormatException e) {
			return RetData.error("发生错误！");
		}
		
		catch (Exception e) {
			// TODO: handle exception
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("用户注册发生错误");
			return RetData.error("发生错误！");
		}
	}
	
	// 注册发送手机验证码
	@RequestMapping(value="register/code",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="注册发送验证码",notes="注册发送验证码")
	public RetData sendRegisterCode(@ApiParam(name="mobileNum",value="手机号")
		@RequestParam String mobileNum) {
		// 验证手机号重复
		if (userExtendService.checkMobileNum(mobileNum)) {
			return RetData.error("该手机号码已注册！");//已删除||已禁用的账号可以重新注册
		}		
		//new RedisUtils().set
		String key = "register_phone_" + mobileNum;
		String code = CodeUtils.generateValidateCode(6);
		//Long time =  30 * 60L;
        redissonUtils.set(key, code, 30 * 60L);
		CodeUtils.sendCode(mobileNum, CodeUtils.getValidateCodeMessage(code, 30));
		logger.info("用户注册发送验证码");
		return RetData.succuess();
	}
	
	/**
	 *  获取登录当前用户信息
	 * TODO
	 * @return
	 * RetData
	 * lmf 创建于2018年11月29日
	 */
	@ApiOperation(value="获取当前登录用户信息",notes="获取当前登录用户信息")
	@RequestMapping(value="getLoginUser",method= {RequestMethod.POST,RequestMethod.GET})
	public RetData getLoginUser() {
		try {
			TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
			UserVo userVo = new UserVo().build(user);
			boolean isAdmin = userExtendService.isAdmin(user.getId());
			userVo.setAdmin(isAdmin);
			return RetData.succuess(userVo);
		}catch (Exception e){
			return  RetData.error("用户未登录！");
		}
	}



    /**
     * 用户登录
     * TODO
     * @return
     * RetData
     * lmf 创建于2018年11月15日
     */
	@SuppressWarnings("restriction")
	@ApiOperation(value="用户登录",notes="用户登录")
	@PostMapping(value="login")
	@ApiImplicitParams({
		@ApiImplicitParam(name="登录信息",value="登录名：loginName,密码：password",paramType="query")
	})
	public RetData login(HttpServletRequest request,HttpServletResponse response,@Valid LoginVo loginVo,BindingResult bindingResult) {
		 String msg= "";
	     try {
	    	 if(bindingResult.hasErrors()) {
		    	return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
		    }else {
		    	logger.info(new BASE64Decoder().decodeBuffer(loginVo.getPassword()).toString());
		    	//String key = redissonUtils.get(UserConstans.RES_PRIVATE_KEY);
		    	String pubKey = redissonUtils.get(UserConstans.RES_PUBLIC_KEY);
		    	String pwddeStr = loginVo.getPassword();//new String(RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(loginVo.getPassword()),key));
		    	String loginName = loginVo.getLoginName();///new String(RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(loginVo.getLoginName()),key));
		    	Subject subject = SecurityUtils.getSubject();
		    	String password = new Md5Hash(URLEncoder.encode(pwddeStr,"utf-8")).toString();
		        TUserExtend user = userExtendService.findByLogin(loginName);
	    		if(user != null) {
	    		    String getpassword = new Md5Hash(password + user.getSalt()).toString();
	    		    UsernamePasswordToken token = new UsernamePasswordToken(loginName,getpassword);
	    		    //生成用户验证cookie
                    byte[] strBytes =RSACoder.encryptByPublicKey(String.valueOf(user.getId()).getBytes(), pubKey);
                    String cookiestr =RSACoder.encryptBASE64(strBytes).replace("\n", "")
                            .replace("\r", "").replace(" ", "");
                    HttpUtil.setCookie(response, UserConstans.USER_TOKEN,cookiestr, null);
                    subject.login(token);
                    UserVo userVo = new UserVo().build(user);
                    return RetData.succuess(userVo);
	    		}else {
	    			  logger.info(String.format("登录失败:未查询到登录名为%s的用户", loginName));
	    			  return RetData.error("手机号或密码不正确！");
	    		   }
		    	}	
	       }
	      catch (LockedAccountException e) {
	    	  logger.info(String.format("登录失败:登录账号已被锁定"));
	    	 msg = "账号已被锁定,请五分钟后再重新尝试!";
	    	  return RetData.error(msg);
		 }
	     catch (IncorrectCredentialsException e) {
    	 logger.info(String.format("登录失败:密码不正确, 错误信息:%s",e.getMessage()));
	    	  msg = "手机号或密码不正确!";
	    	  return RetData.error(msg);
		}
	     catch (UnknownAccountException e) {
	    	 logger.info(String.format("登录失败:手机号不正确"));
	    	  msg = "手机号或密码不正确!";
	    	  return RetData.error(msg);
	    }
		catch(Exception e) {
			 logger.info(String.format("登录失败:%s",e.getMessage()));
			msg = "发生错误！";
			logger.error("用户登录出错");
			 return RetData.error(msg);
		}
	}

	// ##remain#property#
	
	/**
	 * 查询用户详细信息
	 * TODO
	 * @param id
	 * @return
	 * RetData
	 * lmf 创建于2018年11月16日
	 */
	@ApiOperation(value="获取用户详细信息",notes="获取用户详细信息")
	@RequestMapping(value="getInfo",method=RequestMethod.POST)
	public RetData getInfo(@ApiParam(name="id",value="id") @RequestParam("id") Long id) {
		TUserExtend userExtend = userExtendService.findUserById(id);
		if(userExtend != null) {
			return RetData.succuess(userExtend);
		}else {
			return RetData.error("用户不存在！");
		}
	}
	
	/**
	 * 获取当前登录用户信息
	 * TODO
	 * @return
	 * RetData
	 * lmf 创建于2018年12月4日
	 */
	@ApiOperation(value="获取当前登录用户信息",notes="获取当前登录用户信息")
	@RequestMapping(value="userInfo",method=RequestMethod.POST)
	public RetData userInfo() {
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		if(null != user) {
			user = userExtendService.findById(user.getId());
			return RetData.succuess(user);
		}else {
			return RetData.error("用户未登录！");
		}
	}
	
	/**
	 * TODO
	 *  后台添加用户
	 * lmf 创建于2018年11月16日
	 */
	@ApiOperation(value="用户添加",notes="用户添加")
	@RequestMapping(value="userAdd",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public RetData  userAdd(@Valid TUserExtend userExtend,BindingResult bindingResult) {
		// TODO Auto-generated method stub
		if(bindingResult.hasErrors()) {
			return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
		}else {
			boolean result = userExtendService.checkMobileNum(userExtend.getMobile());
			if(result) {
				return RetData.error("号码已存在！");
			}else {
//				TUserExtend pUser = userExtendService.getCurrentUser();
				result = userExtendService.save(userExtend);
				if(result) {
					logger.info("用户添加成功");
					//notifySyncFreeVisitApp();
					return RetData.succuess();
				}else {
					logger.error("用户添加失败");
					return RetData.error("添加失败！");
				}
			}					
		}
	}
	
	/**
	 * TODO
	 * void 后台添加企业账户
	 * lmf 创建于2018年11月16日
	 */
	@ApiOperation(value="添加企业用户",notes="添加企业用户")
	@RequestMapping(value="companyAdd",method=RequestMethod.POST)
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public RetData  companyAdd(@Valid CompanyAddVo companyAddVo,BindingResult bindingResult) {
		// TODO Auto-generated method stub
		if(bindingResult.hasErrors()) {
			return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
		}else {
			boolean result = userExtendService.checkMobileNum(companyAddVo.getMobile());
			if(result) {
				return RetData.error("号码已存在！");
			}else {			
				result = userExtendService.saveCompany(companyAddVo); 
				if(result) {
					logger.info("用户添加成功");
					//notifySyncFreeVisitApp();
	    			return RetData.succuess();
	    		}else {
	    			logger.info("用户添加成功");
	    			return RetData.error("添加失败！");
	    		} 	 
			}					
		}
	}

	
	/**
	 *  个人用户编辑
	 * @param userExtend
	 * @param bindingResult
	 * @return
	 */
	@ApiOperation(value="个人用户编辑",notes="个人用户编辑")
	@RequestMapping(value="userUpdate",method=RequestMethod.POST)
	public RetData  userUpdate(TUserExtend userExtend ,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new VerifyException(bindingResult.getAllErrors()).getErrMsg();
		}else {
			TUserExtends userExtends = userExtendsService.findById(userExtend.getId());
			if(userExtends == null) {
				logger.error("用户修改失败,用户子表未创建");
				return RetData.error("用户子表不存在，请联系管理员！");
			}else {
				userExtendService.update(userExtend);
				userExtends = userExtendsService.updateByUser(userExtend); // 根据提交的用户信息，修改用户子表信息
				boolean result = userExtendsService.update(userExtends);
				if(!result) {
					logger.error("用户修改失败");
					return RetData.error("修改失败！");
				}
			}
			if(null != userExtend.getVip() && UserConstans.USER_TYPE_VIP == userExtend.getVip()) { //免费用户，设置默认免费时间
				String vipEndtime = redissonUtils.get(UserConstans.FREE_END_TIME);
				userExtend.setVipEndtime(vipEndtime);
			}
			boolean result = userExtendService.update(userExtend); 
			if(result) {
				//notifySyncFreeVisitApp();
    			return RetData.succuess();
    		}else {
    			logger.error("用户修改失败");
    			return RetData.error("修改失败！");
    		}
		}
	}
	
	
	
	/**
	 * 
	 * TODO
	 * @param request
	 * @param mobileNum
	 * @param password
	 * @param validateCode
	 * @return
	 * RetData
	 * lmf 创建于2018年11月26日
	 */
	@SuppressWarnings("restriction")
	@ApiOperation(value="忘记密码",notes="忘记密码")
	@RequestMapping(value="forgetPassword",method=RequestMethod.POST)
	@ResponseBody
	@Transactional(readOnly = false)
	public RetData rePassword(HttpServletRequest request, @RequestParam String mobileNum, @RequestParam String password,
			@RequestParam String validateCode) {
		// 验证码
		try {
			String key = redissonUtils.get(UserConstans.RES_PRIVATE_KEY);
	    	
			String passwordDec = new String(RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(password),key));
			String mobileNumDec = new String(RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(mobileNum),key));
			TUserExtend user = userExtendService.findByLogin(mobileNumDec);
			if(null == user) {
				return RetData.error("号码未注册！");
			}else {
				String redisKey = "forgetPwd_phone_" + mobileNumDec;
				boolean code =  userExtendService.checkValidateCode(redisKey, validateCode);
				if (code != true) {
					return RetData.error("验证码错误！");
				}
				// 用户修改密码
				int count = userExtendService.updatePassword(user.getId(), passwordDec);
				if (count > 0) {
					logger.info("用户忘记密码修改成功");
					shiroService.updatePermission();
					return RetData.succuess();
				}
				logger.error("用户忘记密码修改失败");
				return RetData.error("修改失敗");
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetData.error("服务端异常！");
		}
	}

      // 忘记密码发送手机验证码
      @RequestMapping(value="forgetPassword/code",method=RequestMethod.POST)
      @ApiOperation(value="忘记密码发送手机验证码",notes="忘记密码发送手机验证码")
      @ResponseBody
      public RetData sendforgetPwdCode(@RequestParam String mobileNum) {
    	  TUserExtend user = userExtendService.findByLogin(mobileNum);
  		   if(null == user) {
  			  return RetData.error("号码未注册！");
  		    }else {
	            String key = "forgetPwd_phone_" + mobileNum;
	            String code = CodeUtils.generateValidateCode(6);
	            redissonUtils.set(key, code, 30 * 60L);
	            CodeUtils.sendCode(mobileNum, CodeUtils.getValidateCodeMessage(code, 30));
	            logger.info("用户忘记密码发送验证码成功");
	            return RetData.succuess();
	  		}
      }


  
      /**
	 * 
	 * TODO
	 * @param request
	 * @param password
	 * @return
	 * RetData
	 * lmf 创建于2018年11月26日
	 */
	@SuppressWarnings("restriction")
	@RequestMapping(value="updatePassword",method=RequestMethod.POST 	)
	@ResponseBody
	@ApiOperation(value="修改密码",notes="修改密码")
	@Transactional(readOnly = false)
	public RetData updatePassword(HttpServletRequest request, @RequestParam String oldpassword, @RequestParam String password) {
		try {
			TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
			//取出库里的密码
			String key = redissonUtils.get(UserConstans.RES_PRIVATE_KEY);
	    	String oldpasswordDec = new String(RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(oldpassword),key));
			String oldPwdDatabase = userExtendService.findById(user.getId()).getPassword();
			if (!oldPwdDatabase.equals(MD5Utils.MD5(oldpasswordDec))) {
				return RetData.error("原密码不正确！");
			}else {
				
		    	String passwordDec = new String(RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(password),key));
				// 用户修改密码
				int count = userExtendService.updatePassword(user.getId(), passwordDec);
				if (count > 0) {
					logger.info("用户密码修改成功");
					shiroService.updatePermission();
					return RetData.succuess();
				}
				logger.info("用户密码修改失败");
				return RetData.error("修改失敗");
			}
		}catch (Exception e) {
			// TODO: handle exception
			return RetData.error("服务端异常！");
		}
	}

	@ApiOperation("获取所有用户简历信息")
	@RequestMapping("getAllUser")
	public RetData selectApp(@RequestParam Map<String, Object> params){
		return RetData.succuess(userExtendService.finUserAll());
	}
	

}