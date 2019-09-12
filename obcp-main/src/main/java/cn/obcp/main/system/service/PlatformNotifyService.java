package cn.obcp.main.system.service;

import java.util.Map;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.service.BaseService;
import cn.obcp.main.system.domain.TPlatformNotify;

/**
 * <pre>
 * 通知,站内消息的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */
public interface PlatformNotifyService extends BaseService<TPlatformNotify, Long> {

	/**
	 * 获取平台通知列表
	 * @param params
	 * @return
	 */
	public LayUiRetData getPlatformNotifyList(Map<String, Object> params);
	
	/**
	 * 获取用户通知列表
	 * @param params
	 * @return
	 */
	public LayUiRetData getUserNotifyList(Map<String, Object> params);
	
	/**
	 * 获取用户通知数量
	 * @param params
	 * @return
	 */
	public LayUiRetData getUserNotifyNumber(Map<String, Object> params);

}