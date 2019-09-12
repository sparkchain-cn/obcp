package cn.obcp.user.service;

import cn.obcp.base.service.BaseService;
import cn.obcp.user.domain.TOrg;

/**
 * <pre>
 * sys_org的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface OrgService extends BaseService<TOrg, Long> {
    TOrg findBySupervisor(Long userId);

    // ##remain#method#

	// 根据属性找到唯一的方法

}
