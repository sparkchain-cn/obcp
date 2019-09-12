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
import org.springframework.beans.factory.annotation.Value;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.shiro.SecurityUtils;

import tk.mybatis.mapper.entity.Condition;

import cn.obcp.user.domain.TResources;
import cn.obcp.user.domain.TRole;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserRole;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.service.ResourcesService;
import cn.obcp.user.service.RoleResourcesService;
import cn.obcp.user.service.RoleService;
import cn.obcp.user.service.UserRoleService;

// ##remain#import#

import cn.obcp.user.mapper.RoleMapper;
import cn.obcp.user.mapper.RoleResourcesMapper;
import cn.obcp.user.mapper.UserRoleMapper;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
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
  * 角色的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsRoleService")
public class RoleServiceImpl extends BaseServiceImpl<TRole, Long> implements RoleService {

	protected final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);


	@Autowired
	private RoleResourcesMapper roleResourcesMapper;
	@Autowired
	private RoleResourcesService roleResourcesService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private UserRoleService userRoleService;
	
	@Resource
	private RoleMapper roleMapper;
	// ##remain#property#

	RoleServiceImpl() {
		super();
		this.objClz = TRole.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("roleMapper") BaseMapper<TRole> baseMapper) {
		this.baseMapper = baseMapper;

	}

	@Value("${com.sparkchain.admin.roleId}")
	private String adminRole;

	@Value("${com.sparkchain.sysAdmin.roleId}")
	private	String sysAdminRoleId;

	/**
	 *  获取系统级管理员角色
	 * @return
	 */
	public String getSysAdminRole(){
		return sysAdminRoleId;
	}

	/**
	 *  获取平台级管理员角色
	 * @return
	 */
	public String getAdminRole(){
		return adminRole;
	}
	/**
	 * 重置角色资源
	 * TODO
	 * @param
	 * @return
	 * boolean
	 * lmf 创建于2018年11月24日
	 */
	@Transactional(readOnly=false)
	public boolean retSetAdmin() {
		try {
			Long adminId= Long.valueOf(adminRole);
			
			roleResourcesMapper.updateToDelete(ResourceStateEnmu.DELETED.getStatus(),adminId);  //将全部关联资源删除
			
			List<TResources> resources = resourcesService.findAll();
			if(resources != null && resources.size() > 0) {
				resources.forEach(r -> {
					roleResourcesService.bindRole(r.getId(), adminId);
				});
			}						
			return true;
			
		}catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	

	
	/* (non-Javadoc)
	 * @see cn.obcp.base.service.BaseServiceImpl#save(java.util.List)
	 */
	@Override
	@Transactional(readOnly=false)
	public boolean save(TRole entities) {
		// TODO Auto-generated method stub
		try {
			TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();			 
			 Calendar calendar =Calendar.getInstance();
			 Date date = calendar.getTime();
			 Long id = uuidCreator.id();
			 entities.setId(id);
			 entities.setState(ResourceStateEnmu.NORMAL.getStatus());
			 entities.setCreatetime(date);
			 entities.setUpdatetime(date);
			 entities.setCreator(user.getId());
			 entities.setSortnum((roleMapper.countByPage(PageUtils.getPageInfo(new HashMap<>()))+1)); //排序号当前数量加一
			 return super.save(entities);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see cn.obcp.base.service.BaseServiceImpl#update(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly=false)
	public boolean update(TRole entity) {
		// TODO Auto-generated method stub
		try {
			TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
			 Calendar calendar =Calendar.getInstance();
			 Date date = calendar.getTime();
			entity.setUpdatetime(date);
			entity.setUpdater(user.getId());
			return super.update(entity);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.obcp.base.service.BaseServiceImpl#delete(java.lang.Object)
	 */
	/**
	 * 重写delete，改为已删除状态
	 */
	@Override
	@Transactional(readOnly=false)
	public boolean delete(TRole entity) {
		// TODO Auto-generated method stub
		try {
		    Calendar calendar =Calendar.getInstance();
		    Date date = calendar.getTime();
		    entity.setCode("");//重置
			entity.setState(ResourceStateEnmu.DELETED.getStatus());
			entity.setUpdatetime(date);
	//		entity.setUpdater(updater);
			return super.update(entity);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	// ##remain#method#
	
	/* (non-Javadoc)
	 * @see cn.obcp.base.service.BaseServiceImpl#findById(java.lang.Object)
	 */
	@Override
	public TRole findById(Long id) {
		// TODO Auto-generated method stub
		return roleMapper.findById(id);
	}
	
	public Page<TRole> bindUserPage(Page<TRole> page,Long userId){
		return   roleMapper.bindUserPage(page,userId);
	}
	

	/**
	 * 验证code是否已存在
	 */
	public boolean exitCode(String code) {
		TRole role = new TRole();
		role.setCode(code);
		int  count = roleMapper.selectCount(role);
		return count > 0;
	}
	
	public RetData  remove(TRole role) {
		
			if(adminRole.equalsIgnoreCase(String.valueOf(role.getId()))) {
				logger.error("删除角色"+role.getName()+"失败,角色为admin");
				return RetData.error("admin角色不能删除!");
			}
			
			int usercount = userRoleMapper.countByRole(role.getId());
			int rescount = roleResourcesMapper.countByRole(role.getId());
			if(usercount > 0) {
				logger.error("删除角色"+role.getName()+"失败,角色已绑定用户");
				return RetData.error("删除失败,角色已关联用户!");
			}else if(rescount > 0) {
				logger.error("删除角色"+role.getName()+"失败,角色已绑定资源");
				return RetData.error("删除失败，角色已关联资源!");
			}else {
				boolean result = super.delete(role);
				if(result) {
					boolean deleteById = roleResourcesService.deleteByRoleId(role.getId());
			/*		if(!deleteById) {
						logger.error("删除角色"+id+"时，删除角色资源关联失败或该角色未关联资源！");
					}*/
					boolean delById = userRoleService.deleteByRoleId(role.getId());
			/*		if(!deleteById) {
						logger.error("删除角色"+id+"时，删除角色用户关联失败或该角色未关联资源！");
					}*/
					logger.info("删除角色"+role.getName()+"成功");
					return RetData.succuess("删除成功!");
				}else {
					logger.error("删除角色"+role.getName()+"出错");
					return RetData.error("删除失败!");
				}	
			}
	}

}