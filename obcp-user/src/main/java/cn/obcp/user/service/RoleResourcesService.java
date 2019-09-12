package cn.obcp.user.service;

import cn.obcp.user.mapper.RoleResourcesMapper;
import cn.obcp.user.domain.TRoleResources;
import cn.obcp.base.service.BaseService;

// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

/**
 * <pre>
 * 角色-资源的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface RoleResourcesService extends BaseService<TRoleResources, Long> {

	/**
	 * TODO
	 * @param resourcesList
	 * @param roleId
	 * @return
	 * boolean
	 * lmf 创建于2018年11月18日
	 */
	boolean bindResource(List<String> resourcesList, Long roleId);

	/**
	 * TODO  资源绑定角色
	 * @param value
	 * @param adminid
	 * @return
	 * boolean
	 * lmf 创建于2018年11月24日
	 */
	boolean bindRole(Long resId, Long adminid);

	/**
	 * TODO 根据角色删除关联关系
	 * @param id
	 * @return
	 * boolean
	 * lmf 创建于2018年11月24日
	 */
	boolean deleteByRoleId(Long id);

	// ##remain#method#

	// 根据属性找到唯一的方法

}