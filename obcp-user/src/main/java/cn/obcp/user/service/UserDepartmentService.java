package cn.obcp.user.service;

import cn.obcp.user.mapper.UserDepartmentMapper;
import cn.obcp.user.domain.TUserDepartment;
import cn.obcp.base.service.BaseService;

// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

/**
 * <pre>
 * sys_user_department的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface UserDepartmentService extends BaseService<TUserDepartment, Long> {

	// ##remain#method#

	// 根据属性找到唯一的方法

}