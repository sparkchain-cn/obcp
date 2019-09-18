package cn.obcp.user.service.impl;

import java.util.*;

import cn.obcp.base.RetData;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import cn.obcp.user.domain.TUserToken;
import cn.obcp.user.service.UserTokenService;

// ##remain#import#

import cn.obcp.user.mapper.UserTokenMapper;

//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <pre>
  * 用户token的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsUserTokenService")
public class UserTokenServiceImpl extends BaseServiceImpl<TUserToken, Long> implements UserTokenService {

	protected final static Logger logger = LoggerFactory.getLogger(UserTokenServiceImpl.class);

	@Resource
	private UserTokenMapper userTokenMapper;
	// ##remain#property#

	UserTokenServiceImpl() {
		super();
		this.objClz = TUserToken.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("userTokenMapper") BaseMapper<TUserToken> baseMapper) {
		this.baseMapper = baseMapper;

	}

	public RetData checkToken(TUserToken userToken) {
		if (userToken != null) {
			if (userToken.getExpiretime().getTime() > Calendar.getInstance().getTime().getTime()) {
				return RetData.succuess();
			} else {
				return RetData.error("token was over time");
			}
		}
		return RetData.error("invalid token");
	}

	public TUserToken findByToken(String token) {
		return userTokenMapper.findByToken(token);
	}

	public TUserToken findByUserScope(Long id, int scope) {
		return userTokenMapper.findByUserScope(id, scope);
	}

	// ##remain#method#
}