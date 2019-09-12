package cn.obcp.user.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.shiro.SecurityUtils;

import tk.mybatis.mapper.entity.Condition;

import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserRole;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.service.UserRoleService;
import cn.obcp.user.shiro.service.ShiroService;

// ##remain#import#

import cn.obcp.user.mapper.UserRoleMapper;
import cn.obcp.base.utils.PageUtils;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <pre>
  * 用户-角色的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsUserRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<TUserRole, Long> implements UserRoleService {

	protected final static Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

/*	@Autowired
	private ShiroService shiroService;
	*/
	@Resource
	private UserRoleMapper userRoleMapper;
	// ##remain#property#

	UserRoleServiceImpl() {
		super();
		this.objClz = TUserRole.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("userRoleMapper") BaseMapper<TUserRole> baseMapper) {
		this.baseMapper = baseMapper;

	}
	
	
	
	private boolean add(Long userId,Long roleId) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		//TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		TUserRole userRole = new TUserRole();
		userRole.setId(uuidCreator.id());
		userRole.setRid(roleId);
		userRole.setState(ResourceStateEnmu.NORMAL.getStatus());
		userRole.setUid(userId);
		userRole.setCreatetime(date);
		userRole.setUpdatetime(date);
		userRole.setCreator(userId);
		userRole.setSortnum(userRoleMapper.countByPage(PageUtils.getPageInfo(new HashMap<>())) + 1);
		return save(userRole);
	}
	
	/**
	 *  绑定用户角色
	 */
	@Override
	public boolean bindUser(Long userId, Long roleId) {
		try {	
			userRoleMapper.delteByUserAndRole(userId,roleId,ResourceStateEnmu.DELETED.getStatus());
			add(userId, roleId);	
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * 解除用户角色绑定
	 */
	public boolean unBindUser(Long userId, Long roleId) {
		return userRoleMapper.delteByUserAndRole(userId,roleId,ResourceStateEnmu.DELETED.getStatus());
	}
	
	/**
	 * todo:根据用户id，查询用户的全部角色
	 */
	@Override
	public List<TUserRole> findByUser(Long id){
		
		return userRoleMapper.findByUser(id);
	}
	
	/**
	 * 根据用户删除用户角色关系
	 */
	public boolean deleteByRoleId(Long id) {
		return userRoleMapper.deleteByRoleId(id,ResourceStateEnmu.DELETED.getStatus());
	}
	
	// ##remain#method#
}