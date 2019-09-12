package cn.obcp.main.system.service;

import java.util.Map;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.service.BaseService;
import cn.obcp.main.system.domain.TOperateLog;

/**
 * <pre>
 * 操作记录的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface OperateLogService extends BaseService<TOperateLog, Long> {

	// ##remain#method#

	// 根据属性找到唯一的方法
	
	public boolean addLog(TOperateLog operateLog) ;

	/**
	 *  添加日志
	 * @param userId 创建人
	 * @param logLeverInfo 日志级别
	 * @param logModuleAuth 日志模块
	 * @param refval  引用对象值
	 * @param logType 日志类型
	 * @param value 值
	 * @param preval 原值
	 * @param remarkStr 
	 * @return
	 */
	public boolean addLog(String userId, int logLever, int logModule, String refval, int logType,
                          String Val, String preval, String remarkStr);

	/**
	 * 日志查询
	 * @param params
	 * @return
	 */
	public LayUiRetData getList(Map<String, Object> params);
}