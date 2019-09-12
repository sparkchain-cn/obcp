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

import cn.obcp.user.domain.TRoleResources;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserRole;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.service.RoleResourcesService;
import cn.obcp.user.service.RoleService;
import cn.obcp.user.shiro.service.ShiroService;

// ##remain#import#

import cn.obcp.user.mapper.RoleResourcesMapper;
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
  * 角色-资源的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsRoleResourcesService")
public class RoleResourcesServiceImpl extends BaseServiceImpl<TRoleResources, Long> implements RoleResourcesService {

	protected final static Logger logger = LoggerFactory.getLogger(RoleResourcesServiceImpl.class);

	@Resource
	private RoleResourcesMapper roleResourcesMapper;
	// ##remain#property#

	RoleResourcesServiceImpl() {
		super();
		this.objClz = TRoleResources.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("roleResourcesMapper") BaseMapper<TRoleResources> baseMapper) {
		this.baseMapper = baseMapper;

	}

	
	/**
	 *  资源绑定角色
	 */
	@Transactional(readOnly=false)
	public boolean bindRole(Long resId,Long roleId) {
		return this.add(resId, roleId);
	}

	private boolean add(Long resId,Long roleId) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
		TRoleResources roleResource = new TRoleResources();
		roleResource.setId(uuidCreator.id());
		roleResource.setRid(roleId);
		roleResource.setState(ResourceStateEnmu.NORMAL.getStatus());
		roleResource.setResId(resId);		
		roleResource.setCreatetime(date);
		roleResource.setUpdatetime(date);
		roleResource.setCreator(user.getId());
		roleResource.setSortnum(roleResourcesMapper.countByPage(PageUtils.getPageInfo(new HashMap<>())) + 1);
		return save(roleResource);
	}
	
	
	/**
	 * 绑定资源
	 */
	@Override
	@Transactional(readOnly=false)
	public boolean bindResource(List<String> resourcesList, Long roleId) {
		try {
			//TUserExtend user = (TUserExtend) SecurityUtils.getSubject().getPrincipal();
			roleResourcesMapper.updateToDelete(ResourceStateEnmu.DELETED.getStatus(),roleId); //将已关联当前角色的关系全部设置为已删除
			//List<TRoleResources> userRoles = roleResourcesMapper.findByRole(roleId);			
			for (int j = 0; j <resourcesList.size(); j++) {
				Long resId = Long.valueOf(resourcesList.get(j));
				add(resId, roleId);
			}
			/*ShiroService.updatePermission();	*/	
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据角色删除关联
	 */
	public boolean deleteByRoleId(Long id) {
		return roleResourcesMapper.deleteByRoleId(id,ResourceStateEnmu.DELETED.getStatus());
	}
	// ##remain#method#
}