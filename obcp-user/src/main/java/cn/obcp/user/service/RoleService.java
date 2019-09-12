package cn.obcp.user.service;

import cn.obcp.user.mapper.RoleMapper;
import cn.obcp.user.domain.TRole;
import cn.obcp.user.domain.TUserRole;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.service.BaseService;

// ##remain#import#

import java.util.List;
import java.util.SplittableRandom;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

/**
 * <pre>
 * 角色的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface RoleService extends BaseService<TRole, Long> {

	// ##remain#method#
	 Page<TRole> bindUserPage(Page<TRole> page, Long userId);
	// 根据属性找到唯一的方法

	/**
	 * TODO
	 * @return
	 * boolean
	 * lmf 创建于2018年11月24日
	 */
	boolean retSetAdmin();

	/**
	 * TODO
	 * @param code
	 * @return
	 * boolean
	 * lmf 创建于2018年11月26日
	 */
	boolean exitCode(String code);

	/**
	 * TODO
	 * @param role
	 * @return
	 * RetData
	 * lmf 创建于2018年11月27日
	 */
	RetData remove(TRole role);


    String getSysAdminRole();

	String getAdminRole();
}