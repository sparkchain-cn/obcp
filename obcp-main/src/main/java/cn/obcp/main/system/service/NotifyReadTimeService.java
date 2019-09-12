package cn.obcp.main.system.service;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.service.BaseService;
import cn.obcp.main.system.domain.TNotifyReadTime;

/**
 * <pre>
 * 消息阅读记录的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface NotifyReadTimeService extends BaseService<TNotifyReadTime, Long> {
	
	/**
	 * 保存通知读取时间
	 * @param entity
	 * @return
	 */
	LayUiRetData saveReadTime(Long userId);
	
	/**
	 * 获取用户通知读取时间
	 * @param userid
	 * @return
	 */
	TNotifyReadTime getReadTime(Long userId);
}