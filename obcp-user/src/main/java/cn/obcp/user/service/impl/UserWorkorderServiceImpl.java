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

import cn.obcp.user.domain.TUserWorkorder;
import cn.obcp.user.service.UserWorkorderService;

// ##remain#import#

import cn.obcp.user.mapper.UserWorkorderMapper;

//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <pre>
  * sys_user_workorder的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsUserWorkorderService")
public class UserWorkorderServiceImpl extends BaseServiceImpl<TUserWorkorder, Long> implements UserWorkorderService {

	protected final static Logger logger = LoggerFactory.getLogger(UserWorkorderServiceImpl.class);

	@Resource
	private UserWorkorderMapper userWorkorderMapper;
	// ##remain#property#

	UserWorkorderServiceImpl() {
		super();
		this.objClz = TUserWorkorder.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("userWorkorderMapper") BaseMapper<TUserWorkorder> baseMapper) {
		this.baseMapper = baseMapper;

	}

	// ##remain#method#
}