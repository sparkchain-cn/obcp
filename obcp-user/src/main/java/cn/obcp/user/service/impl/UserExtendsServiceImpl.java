package cn.obcp.user.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserExtends;

// ##remain#import#

import cn.obcp.user.mapper.UserExtendsMapper;
import cn.obcp.user.service.UserExtendsService;

/**
 * <pre>
  * 用户详细信息表的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsUserExtendsService")
public class UserExtendsServiceImpl extends BaseServiceImpl<TUserExtends, Long> implements UserExtendsService {

	protected final static Logger logger = LoggerFactory.getLogger(UserExtendsServiceImpl.class);

	@Resource
	private UserExtendsMapper userExtendsMapper;
	// ##remain#property#

	UserExtendsServiceImpl() {
		super();
		this.objClz = TUserExtends.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("userExtendsMapper") BaseMapper<TUserExtends> baseMapper) {
		this.baseMapper = baseMapper;

	}
	
	/**
	 * 根据userExtend 获取userExtends对应表实体
	 */
	@Override
	public TUserExtends updateByUser(TUserExtend userExtend) {
		TUserExtends entity =JSON.parseObject(JSON.toJSONString(userExtend),TUserExtends.class);
		entity.setUid(userExtend.getId());
		return entity;
	}

	// ##remain#method#
}