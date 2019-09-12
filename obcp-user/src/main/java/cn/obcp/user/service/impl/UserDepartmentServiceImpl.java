package cn.obcp.user.service.impl;

import java.util.ArrayList;
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
import tk.mybatis.mapper.entity.Condition;

import cn.obcp.user.domain.TUserDepartment;
import cn.obcp.user.service.UserDepartmentService;

// ##remain#import#

import cn.obcp.user.mapper.UserDepartmentMapper;

//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <pre>
  * sys_user_department的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("scUserDepartmentService")
public class UserDepartmentServiceImpl extends BaseServiceImpl<TUserDepartment, Long> implements UserDepartmentService {

	protected final static Logger logger = LoggerFactory.getLogger(UserDepartmentServiceImpl.class);

	@Resource
	private UserDepartmentMapper userDepartmentMapper;
	// ##remain#property#

	UserDepartmentServiceImpl() {
		super();
		this.objClz = TUserDepartment.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("userDepartmentMapper") BaseMapper<TUserDepartment> baseMapper) {
		this.baseMapper = baseMapper;

	}

	// ##remain#method#
}