package cn.obcp.main.system.controller;

import java.util.Date;
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
import cn.obcp.main.system.domain.TNotifyReadTime;
import cn.obcp.main.system.domain.TPlatformNotify;
import cn.obcp.main.system.service.NotifyReadTimeService;
import cn.obcp.main.system.service.PlatformNotifyService;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.service.UserExtendService;
import io.swagger.annotations.ApiOperation;

// ##remain#import#

/**
 * <pre>
  * 通知,站内消息的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/notify/")
public class PlatformNotifyController extends BaseController<TPlatformNotify, Long> {

	@Autowired
	private PlatformNotifyService platformNotifyService;
	@Autowired
	private NotifyReadTimeService notifyReadTimeService;
	@Autowired
	private UserExtendService userExtendService;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("scPlatformNotifyService") BaseService<TPlatformNotify, Long> entityService) {
		this.entityService = entityService;
	}

	@ApiOperation("获取平台通知列表")
	@RequestMapping(value = "getNotifyDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public LayUiRetData getNotifyDetail(@RequestParam(required=true) Long id) {
		return LayUiRetData.success(platformNotifyService.findById(id));
	}
	
	@ApiOperation("获取平台通知列表")
	@RequestMapping(value = "saveNotify", method = { RequestMethod.POST, RequestMethod.GET })
	public LayUiRetData saveNotify(TPlatformNotify params) {
		boolean result = false;
		params.setUpdatetime(new Date());
		if(params.getId() == null) {
			params.setId(uuidCreator.id());
			params.setCreator(userExtendService.getCurrentUser().getId());
			params.setPopup(0);
			params.setPushType(0);
			params.setState(1);
			params.setCreatetime(new Date());
			result = platformNotifyService.save(params);
		}else {
			TPlatformNotify notify = platformNotifyService.findById(params.getId());
			if(notify == null) {
				return LayUiRetData.error("数据不存在!");
			}
			result = platformNotifyService.update(params);
		}
		return result ? LayUiRetData.success("保存成功!"):LayUiRetData.error("保存失败!");
	}
	
	@ApiOperation("获取平台通知列表")
	@RequestMapping(value = "getPlatformNotifyList", method = { RequestMethod.POST, RequestMethod.GET })
	public LayUiRetData getPlatformNotifyList(@RequestParam Map<String, Object> params) {
		return platformNotifyService.getPlatformNotifyList(params);
	}

	@ApiOperation("获取用户通知列表")
	@RequestMapping(value = "getNotifyList", method = { RequestMethod.POST, RequestMethod.GET })
	public LayUiRetData getNotifyList(@RequestParam Map<String, Object> params) {
		TUserExtend user = userExtendService.getCurrentUser();
		Long userId = user.getId();
		params.put("userId", userId);
		Object pagenum = params.get("pagenum");
		boolean updateReadTime = pagenum == null || (pagenum != null && pagenum.equals("1"));
		if(updateReadTime) {
			//更新通知已读时间
			notifyReadTimeService.saveReadTime(user.getId());
		}
		return platformNotifyService.getUserNotifyList(params);
	}
	
	
	@ApiOperation("获取用户通知数量")
	@RequestMapping(value = "getNotifyNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public LayUiRetData getNotifyNumber(@RequestParam Map<String, Object> params) {
		TUserExtend user = userExtendService.getCurrentUser();
		Long userId = user.getId();
		params.put("userId", userId);
		TNotifyReadTime readTime= notifyReadTimeService.getReadTime(userId);
		if(readTime != null) {
			params.put("readTime", readTime.getReadTime());
		}
		return platformNotifyService.getUserNotifyNumber(params);
	}
}