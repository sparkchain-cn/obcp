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

import cn.obcp.user.common.UserConstans;
import cn.obcp.user.domain.TCompany;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserExtends;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.service.CompanyService;

// ##remain#import#

import cn.obcp.user.mapper.CompanyMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <pre>
  * 公司相关信息表的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bcCompanyService")
public class CompanyServiceImpl extends BaseServiceImpl<TCompany, Long> implements CompanyService {

	protected final static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Resource
	private CompanyMapper companyMapper;
	// ##remain#property#

	CompanyServiceImpl() {
		super();
		this.objClz = TCompany.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("companyMapper") BaseMapper<TCompany> baseMapper) {
		this.baseMapper = baseMapper;

	}
	
	public TCompany updateByUser(TUserExtend userExtend) {
	
		Date date = new Date();
		TCompany company = JSONObject.parseObject(JSON.toJSONString(userExtend), TCompany.class);
		company.setStatus(ResourceStateEnmu.VERIFY.getStatus());
		company.setCreatetime(date);
		company.setUpdatetime(date);
		company.setSupervisor(userExtend.getId());
		
		return company;
	}

	// ##remain#method#
}