package cn.obcp.main.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.main.system.domain.TOperateLog;

// ##remain#import#

import cn.obcp.main.system.mapper.OperateLogMapper;
import cn.obcp.main.system.service.OperateLogService;

/**
 * <pre>
  * 操作记录的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = false)
@Service("bsOperateLogService")
public class OperateLogServiceImpl extends BaseServiceImpl<TOperateLog, Long> implements OperateLogService {

	protected final static Logger logger = LoggerFactory.getLogger(OperateLogServiceImpl.class);

	
	
	@Resource
	private OperateLogMapper operateLogMapper;
	// ##remain#property#

	OperateLogServiceImpl() {
		super();
		this.objClz = TOperateLog.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("operateLogMapper") BaseMapper<TOperateLog> baseMapper) {
		this.baseMapper = baseMapper;

	}
	
	/**
	 * 添加日志
	 */
	public boolean addLog(TOperateLog operateLog) {
		Date now = new Date();
		operateLog.setId(uuidCreator.id());
		operateLog.setCreatetime(now);
		return  super.save(operateLog);
	}
	
	/**
	 * 创建日志
	 */
	public boolean addLog(String userId, int logLever, int logModule, String refval, int logType,
			String val, String preval,String remarks) {		
		TOperateLog operateLog = new TOperateLog();
		Date now = new Date();
		operateLog.setId(uuidCreator.id());
		operateLog.setCreatetime(now);
		operateLog.setCreator(userId);
		operateLog.setModule(logModule);
		operateLog.setType(logType);
		operateLog.setVal(val);
		operateLog.setRemark(remarks);
		operateLog.setPreval(preval);
		operateLog.setRefval(refval);
		return  super.save(operateLog);
	}
	
	/**
	 *  日志查询
	 */
	public LayUiRetData getList(Map<String, Object> params) {
		Page<TOperateLog> page = PageUtils.getPageInfo(params);
		List<TOperateLog> operList = operateLogMapper.findByPage(page);
		int count = operateLogMapper.countByPage(page);
		
		return LayUiRetData.success(operList, Long.valueOf(count));
	}

	// ##remain#method#
}