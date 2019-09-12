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

import cn.obcp.user.domain.TOrg;
import cn.obcp.user.service.OrgService;

// ##remain#import#

import cn.obcp.user.mapper.OrgMapper;

//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <pre>
  * sys_org的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("scOrgService")
public class OrgServiceImpl extends BaseServiceImpl<TOrg, Long> implements OrgService {

	protected final static Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);

	@Resource
	private OrgMapper orgMapper;
	// ##remain#property#

	OrgServiceImpl() {
		super();
		this.objClz = TOrg.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("orgMapper") BaseMapper<TOrg> baseMapper) {
		this.baseMapper = baseMapper;

	}


	public TOrg findBySupervisor(Long userId){
		return orgMapper.findBySupervisor(userId);
	}

	// ##remain#method#
}