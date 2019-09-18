package cn.obcp.user.service;

import cn.obcp.base.RetData;
import cn.obcp.user.mapper.UserTokenMapper;
import cn.obcp.user.domain.TUserToken;
import cn.obcp.base.service.BaseService;

// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

/**
 * <pre>
 * 用户token的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface UserTokenService extends BaseService<TUserToken, Long> {

    RetData checkToken(TUserToken userToken);


    TUserToken findByToken(String token);
    
    TUserToken findByUserScope(Long id, int scope);



    // ##remain#method#

	// 根据属性找到唯一的方法

}