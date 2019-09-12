package cn.obcp.user.service;

import cn.obcp.user.mapper.UserRoleMapper;
import cn.obcp.user.domain.TUserRole;
import cn.obcp.base.service.BaseService;

// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

/**
 * <pre>
 * 用户-角色的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface UserRoleService extends BaseService<TUserRole, Long> {

	/**
	 * TODO
	 * @param userId
	 * @param roleId
	 * @return
	 * boolean
	 * lmf 创建于2018年11月16日
	 */
	boolean bindUser(Long userId, Long roleId);

	/**
	 * TODO
	 * @param id
	 * @return
	 * List<TUserRole>
	 * lmf 创建于2018年11月19日
	 */
	List<TUserRole> findByUser(Long id);

	/**
	 * TODO
	 * @param id
	 * @return
	 * boolean
	 * lmf 创建于2018年11月24日
	 */
	boolean deleteByRoleId(Long id);

	/**
	 * TODO
	 * @param userId
	 * @param roleId
	 * @return
	 * boolean
	 * lmf 创建于2018年11月28日
	 */
	boolean unBindUser(Long userId, Long roleId);

	
	// ##remain#method#

	// 根据属性找到唯一的方法

}