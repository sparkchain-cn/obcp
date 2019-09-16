package cn.obcp.user.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.base.utils.MD5Utils;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.cache.ICache;
import cn.obcp.user.VO.CompanyAddVo;
import cn.obcp.user.VO.NavsNodeTree;
import cn.obcp.user.VO.RoleResourceVo;
import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TCompany;
import cn.obcp.user.domain.TResources;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserExtends;
import cn.obcp.user.domain.TUserToken;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.mapper.UserExtendMapper;
import cn.obcp.user.service.CompanyService;
import cn.obcp.user.service.ResourcesService;
import cn.obcp.user.service.RoleService;
import cn.obcp.user.service.UserExtendService;
import cn.obcp.user.service.UserExtendsService;
import cn.obcp.user.service.UserRoleService;
import cn.obcp.user.util.GeneratorRandom;

//import cn.obcp.user.BaseMapper;
// ##remain#import#

/**
 * <pre>
  * 用户表的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = false)
@Service("bsUserExtendService")
public class UserExtendServiceImpl extends BaseServiceImpl<TUserExtend, Long> implements UserExtendService {

	protected final static Logger logger = LoggerFactory.getLogger(UserExtendServiceImpl.class);

	@Autowired
	private ICache redissonUtils;
	
	@Resource
	private UserExtendMapper userExtendMapper;

	@Value("${com.sparkchain.gateway.server.name:spc-sparkchain-gateway}")
    private String gatewayServerName;
	@Autowired
	private CompanyService companyService;

	@Autowired
	private RoleService roleService;



	@Autowired
	private UserExtendsService userExtendsService;

	@Autowired
	private ResourcesService resourcesService;
	@Value("${com.sparkchain.general.roleId}")
	private String generalRole;
	// ##remain#property#
	@Autowired
	private UserRoleService userRoleService;

	  
	
	UserExtendServiceImpl() {
		super();
		this.objClz = TUserExtend.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("userExtendMapper") BaseMapper<TUserExtend> baseMapper) {
		this.baseMapper = baseMapper;

	}
	


	/**
	 * 用户新增
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	@Override
	public boolean save(TUserExtend userExtend)  throws NumberFormatException{
		try {
			Long id = uuidCreator.id();
			String sailt = GeneratorRandom.build();
			Date date = Calendar.getInstance().getTime();
			userExtend.setId(id);
			userExtend.setCreatetime(date);
			userExtend.setPassword(MD5Utils.MD5(userExtend.getPassword()));
			userExtend.setSalt(sailt); //创建随机盐
			userExtend.setState(ResourceStateEnmu.NORMAL.getStatus());//设置默认已通過审核
			userExtend.setOrganization(UserConstans.USER_TYPE_PERSONAL);//个人用户
			
			TUserExtends userExtends = userExtendsService.updateByUser(userExtend); // 根据提交的用户信息，修改用户子表信息
			userExtends.setRegisttime(date);
			boolean extendResult = userExtendsService.save(userExtends);
			if(!extendResult) {
				logger.error(userExtend.getId()+"创建子表失败！");
			}			
			Long roleId = Long.valueOf(generalRole);
			userRoleService.bindUser(id, roleId);
			return  super.save(userExtend);
		}catch (NumberFormatException e) {
			// TODO: handle exception
			logger.error("没有配置普通用户账号:com.sparkchain.general.roleId}");
			return false;
		}
	
	}
	
	
	/**
	 * 新加企业用户
	 */
	public boolean saveCompany(CompanyAddVo companyAddVo) {
		TUserExtend userExtend = JSONObject.parseObject(JSON.toJSONString(companyAddVo), TUserExtend.class);
		Long id = uuidCreator.id();

		String sailt = GeneratorRandom.build();
		Date date = Calendar.getInstance().getTime();
		userExtend.setId(id);
		userExtend.setCreatetime(date);
		userExtend.setPassword(MD5Utils.MD5(userExtend.getPassword()));
		userExtend.setSalt(sailt); //创建随机盐
		userExtend.setState(ResourceStateEnmu.NORMAL.getStatus());//设置默认待审核
		userExtend.setOrganization(UserConstans.USER_TYPE_COMPANY);
		TCompany company = companyService.updateByUser(userExtend); // 根据提交的用户信息，修改用户子表信息
		TUserExtends userextends = userExtendsService.updateByUser(userExtend); // 根据提交的用户信息，修改用户子表信息
		boolean companyResult = companyService.save(company);
		boolean extendResult = userExtendsService.save(userextends);
		try {
			Long roleId = Long.valueOf(generalRole);
			userRoleService.bindUser(id, roleId);
		}catch (NumberFormatException e) {
			// TODO: handle exception
			logger.error("没有配置普通用户账号:com.sparkchain.general.roleId}");
		}
		if(!extendResult) {
			logger.error(userExtend.getId()+"创建用户子表失败！");
			
		}
		if(!companyResult) {
			logger.error(userExtend.getId()+"创建公司子表失败！");
		}
		if(null != userExtend.getVip() && UserConstans.USER_TYPE_VIP == userExtend.getVip()) { //免费用户，设置默认免费时间
			String vipEndtime = redissonUtils.get(UserConstans.FREE_END_TIME);
			userExtend.setVipEndtime(vipEndtime);
		}
		return  super.save(userExtend);
	}
	
	/* (non-Javadoc)
	 * @see cn.obcp.base.service.BaseServiceImpl#update(java.lang.Object)
	 */
	@Override
	public boolean update(TUserExtend entity) {
		// TODO Auto-generated method stub
		TUserExtend userExtend = userExtendMapper.findUserById(entity.getId());
		if(null != entity.getPassword() && !userExtend.getPassword().equalsIgnoreCase(entity.getPassword())) { //修改时，密码被修改，对新密码加密
			entity.setPassword(MD5Utils.MD5(entity.getPassword()));
		}
		if(null != entity) {
			entity.setMobile(null);
		}
		return super.update(entity);
	}

	/**
	 * 登录查询用户
	 */
	@Override
	public TUserExtend findByLogin(String loginName) {
		return userExtendMapper.findByLogin(loginName);
	}
	

	/* (non-Javadoc)
	 * @see cn.obcp.base.service.BaseServiceImpl#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(TUserExtend entity) {
		// TODO Auto-generated method stub
		 Calendar calendar =Calendar.getInstance();
		 Date date = calendar.getTime();
		 
		TUserExtends userExtends = userExtendsService.findById(entity.getId());
		if(userExtends != null) {
			userExtends.setUpdatetime(date);
			userExtendsService.update(userExtends);
		}else {
			logger.error(entity.getId() +"不存在子表");
		}
		entity.setState(ResourceStateEnmu.DELETED.getStatus());
		return super.update(entity);
	}
	/* (non-Javadoc)
	 * @see cn.obcp.base.service.BaseServiceImpl#findById(java.lang.Object)
	 */
	//查询已注册成功的用户
	@Override
	public TUserExtend findById(Long id) {
		// TODO Auto-generated method stub
		return userExtendMapper.findById(id);
	}
	
	//查询全部用户
	public TUserExtend findUserById(Long id) {
		// TODO Auto-generated method stub
		return userExtendMapper.findUserById(id);
	}
	/*
	 * 是否存在手机号(存在=true，不存在=false)
	 */
	@Override
	public boolean checkMobileNum(String mobileMum) {
		TUserExtend t = new TUserExtend();
		t.setMobile(mobileMum);
		t.setState(ResourceStateEnmu.NORMAL.getStatus());
		TUserExtend user = userExtendMapper.findByLogin(mobileMum);
		return user != null;
	}
	
	/**
	 * 查询资源绑定页面数据
	 */
	@Override
	public Page<TUserExtend> findBindByPage(Page<TUserExtend> page){
		List<TUserExtend> list =  userExtendMapper.findBindByPage(page);
		list.forEach(u -> {
			u.setLAY_CHECKED(u.getHasRole() > 0);
		});
		int count = userExtendMapper.countByPage(page);
		page.setResult(list);
		page.setTotal(count);
		return page;
		
	}
	
	/**
	 * 查询用户
	 */
	public  Page<TUserExtend> findByPage(Page<TUserExtend> page) {
		List<TUserExtend> list = userExtendMapper.findByPage(page);
		int count = userExtendMapper.countByPage(page);
		page.setTotal(count);
		page.setResult(list);
		return page;
	}
	/**
	 * 查询企业账号
	 * @param page
	 * @return
	 */
	public  Page<TUserExtend> findCompanyByPage(Page<TUserExtend> page) {
		List<TUserExtend> list = userExtendMapper.findCompanyByPage(page);
		int count = userExtendMapper.countCompanyByPage(page);
		page.setTotal(count);
		page.setResult(list);
		return page;
	}
	
	/**
	 * 查询用户 查询条件：or 
	 */
	public Page<TUserExtend> searchByOr(Page<TUserExtend> page){
		List<TUserExtend> list = userExtendMapper.searchByOr(page);
		page.setResult(list);
		return page;
	}
	
	/**
	 *   todo:获取当前用户
	 */
	@Override
	public TUserExtend getCurrentUser() {
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		if(user != null) {
			return user;
		}else {
			return null;
		}
	}
	
	/**
	 * todo:获取当前用户资源
	 */
	@Override
	public List<NavsNodeTree>  getUserResource(){
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		if(user != null) {
			List<TResources> resources = new ArrayList<>();
			if(isAdmin(user.getId())) {
				resources = resourcesService.getAllMenuResource();//获取菜单资源
			}else {
				resources = resourcesService.getUserTreeList(user.getId());
			}
			return  NavsNodeTree.bulid(resources);
		}else {
			return null;
		}
	}

	/**
	 *  判断用户是否是系统超级管理员
	 * @param userId
	 * @return
	 */
	public Boolean isSysAdmin(Long userId){
		try {
			if (StringUtils.isNullOrEmpty(roleService.getSysAdminRole())) {
				return false;
			}
			List<String> adminRoles = Lists.newArrayList(roleService.getSysAdminRole().split(","));
			TUserExtend user = userExtendMapper.findAdminById(userId, adminRoles);
			return user != null;
		}catch (Exception e){
			return false;
		}
	}


	/**
	 * 判断用户是否拥有admin角色(平台管理员)
	 */
	public Boolean isAdmin(Long userId) {
		String sysAdmin = roleService.getSysAdminRole();
		if (StringUtils.isNullOrEmpty(sysAdmin)){
			return  false;
		}
		StringBuffer stringBuffer = new StringBuffer(sysAdmin);
		String adminRole = roleService.getAdminRole();
		if(StringUtils.isNotNullOrEmpty(adminRole)){
			stringBuffer.append(","+ adminRole);
		}
		List<String> adminRoles = Lists.newArrayList(new String(stringBuffer).split(","))
				.stream().filter(rid -> StringUtils.isNotNullOrEmpty(rid)).collect(Collectors.toList());
		TUserExtend user = userExtendMapper.findAdminById(userId,adminRoles);
		return user != null;
	}
	
	/**
	 *  退出登录
	 */
	public void logout() {
		  Subject subject = SecurityUtils.getSubject();
		  subject.logout();
	}
	
	/*
	 * 更新密码
	 */
	@Override
	@Transactional(readOnly = false)
	public int updatePassword(Long userid, String password) {

		String newPassword = MD5Utils.MD5(password);
	
		int count = userExtendMapper.updatePassword(userid,newPassword);
		return count;
	}

	/*
	 * 验证码
	 * */
	@Override
	public boolean checkValidateCode(String key, String validateCode) {
		String value = redissonUtils.get(key);
		if (StringUtils.isNullOrEmpty(value)) {
			return false;
		}
		if (!value.equals(validateCode)) {
			return false;
		}
		return true;
	}
	/**
	 * 未绑定role的用户
	 */
	public Page<TUserExtend> getNoBindByRole(Page<TUserExtend> page){
		List<TUserExtend> resources =  userExtendMapper.getNoBindByRole(page);
		int count = userExtendMapper.getNoBindByRoleCount(page);
		page.setResult(resources);
		page.setTotal(count);
		return page;
	}
	/**
	 * 已绑定role用户
	 */
	public Page<TUserExtend> getBindByRole(Page<TUserExtend> page){
		List<TUserExtend> resources =  userExtendMapper.getBindByRole(page);
		int count = userExtendMapper.getBindByRoleCount(page);
		page.setResult(resources);
		page.setTotal(count);
		return page;
	}
	/**
	 * 用户审核
	 */
	public RetData verifyUser(Long userId, Long verifyuser, int state, String explain) {
		TUserExtend checkuser = userExtendMapper.findUserById(userId);
		if(null != checkuser) {
			checkuser.setState(state);
			checkuser.setVerifyUser(String.valueOf(verifyuser));	
			if(ResourceStateEnmu.DELETED.getStatus() == state) { //拒绝
				checkuser.setExplain(explain);
			}else if(ResourceStateEnmu.NORMAL.getStatus() == state) { //通过
			
			}
			boolean res =super.update(checkuser);
			if(res)
				return RetData.succuess();
			else
				return RetData.error("审核失败");
		}
		return RetData.error("用户不存在");
		
	}
	
	/**
	 * 用户角色角色资源列表
	 */
	public Page<RoleResourceVo> getRoleResourceByUser(Page<RoleResourceVo> page){
		List<RoleResourceVo> list = userExtendMapper.getRoleResourceByUser(page);
		int count = userExtendMapper.countRoleResourceByUser(page);
		page.setResult(list);
		page.setTotal(count);
		return page;
	}


	/**
	 * 修改用戶PrincipalCollection
	 * @param user
	 * @return
	 */
	public boolean updateUserPrincipalCollection(TUserExtend user){
		try {
			String redisToken = createUserToken(user);
			user.setUserToken(redisToken);
			Subject subject = SecurityUtils.getSubject();
			PrincipalCollection originPrincipalCollection = subject.getPrincipals();
			if (null != originPrincipalCollection){
				String realmName = originPrincipalCollection.getRealmNames().iterator().next();
				PrincipalCollection principalCollection = new SimplePrincipalCollection(user, realmName);
				subject.runAs(principalCollection);
				//System.out.println("111111111111111++"+user.getIsAdmin());
				return true;
			}
			return false;
		}catch (Exception e){
			e.printStackTrace();
			return  false;
		}
	}

//	@Autowired
//    private  RedisTokenGenerator redisTokenGenerator;
	/**
	 *  创建token
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String createUserToken(TUserExtend user) throws Exception{
		/*if (StringUtils.isNotNullOrEmpty(user.getUserToken())){
			//修改user token信息，更新redis
			 redisTokenGenerator.updateUserToken(user.getUserToken(),user.getId(),user.getAppid());
			 return user.getUserToken();
		}
		return redisTokenGenerator.generate(user.getId().toString(),user.getPassword(),user.getAppid());//创建用户token，当前只有userid*/
		return "";
	}

	/**
	 * 判断当前用户是否是管理员
	 * @return
	 */
	public boolean currIsAdmin(){
		TUserExtend userExtend = getCurrentUser();
		return  isAdmin(userExtend.getId());
	}

	public RetData runWith(TUserToken userToken){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.getPrincipal() == null){
				UsernamePasswordToken token = new UsernamePasswordToken( userToken.getToken(),String.valueOf(userToken.getUid()));
				subject.login(token);
			}else {
				PrincipalCollection originPrincipalCollection = subject.getPrincipals();
				String realmName = originPrincipalCollection.getRealmNames().iterator().next();
				PrincipalCollection principalCollection = new SimplePrincipalCollection(new TUserExtend(userToken.getUid()), realmName);
				subject.runAs(principalCollection);
			}
			return RetData.succuess();
		}catch (Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}


	public Page<TUserExtend> findUserWithoutOrg(Page<TUserExtend> page){
		List<TUserExtend> userExtends = userExtendMapper.findUserWithoutOrg(page);
		int total = userExtendMapper.countUserWiCountthoutOrg(page);
		page.setResult(userExtends);
		page.setTotal(total);
		return page;
	}
	// ##remain#method#

	@Override
	public List<TUserExtend> finUserAll() {
		return userExtendMapper.finUserAll();
	}
}
