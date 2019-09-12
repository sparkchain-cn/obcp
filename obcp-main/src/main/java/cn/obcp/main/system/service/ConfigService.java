package cn.obcp.main.system.service;

import java.util.Map;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.RetData;
import cn.obcp.base.service.BaseService;
import cn.obcp.main.system.domain.TConfig;

/**
 * <pre>
 * 系统配置的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface ConfigService extends BaseService<TConfig, Long> {

	LayUiRetData getList(Map<String, Object> params);

	RetData saveOrUpdate(TConfig config);

	/**
	 * 初始化缓存配置
	 */
	void initCache();

	// ##remain#method#

	// 根据属性找到唯一的方法

}