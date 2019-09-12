package cn.obcp.main.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.main.system.domain.TOperateLog;
import cn.obcp.main.system.service.OperateLogService;
import io.swagger.annotations.ApiOperation;

// ##remain#import#

/**
 * <pre>
  * 操作记录的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/operatelog/")
public class OperateLogController extends BaseController<TOperateLog, Long> {

	@Autowired
	private OperateLogService operateLogService;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsOperateLogService") BaseService<TOperateLog, Long> entityService) {
		this.entityService = entityService;

	}
	
	@ApiOperation("操作日志列表")
	@RequestMapping(value="getList",method={RequestMethod.POST,RequestMethod.GET})
	public LayUiRetData getList(@RequestParam Map<String, Object> params) {
		return operateLogService.getList(params);
	}

	// ##remain#property#

}