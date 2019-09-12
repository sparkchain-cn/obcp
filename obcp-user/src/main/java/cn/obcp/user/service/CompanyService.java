package cn.obcp.user.service;

import cn.obcp.user.mapper.CompanyMapper;
import cn.obcp.user.domain.TCompany;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.domain.TUserExtends;
import cn.obcp.base.service.BaseService;

// ##remain#import#

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

/**
 * <pre>
 * 公司相关信息表的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface CompanyService extends BaseService<TCompany, Long> {

	TCompany updateByUser(@Valid TUserExtend userExtend);

	// ##remain#method#

	// 根据属性找到唯一的方法

}