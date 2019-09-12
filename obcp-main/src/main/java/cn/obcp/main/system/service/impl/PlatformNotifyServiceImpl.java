package cn.obcp.main.system.service.impl;

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
import cn.obcp.main.system.domain.TPlatformNotify;
import cn.obcp.main.system.mapper.NotifyReadTimeMapper;
import cn.obcp.main.system.mapper.PlatformNotifyMapper;
import cn.obcp.main.system.service.NotifyReadTimeService;
import cn.obcp.main.system.service.PlatformNotifyService;

/**
 * <pre>
  * 通知,站内消息的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("scPlatformNotifyService")
public class PlatformNotifyServiceImpl extends BaseServiceImpl<TPlatformNotify, Long> implements PlatformNotifyService {

	protected final static Logger logger = LoggerFactory.getLogger(PlatformNotifyServiceImpl.class);

	@Autowired
	private NotifyReadTimeService notifyReadTimeService;
	@Resource
	private PlatformNotifyMapper platformNotifyMapper;
	@Resource
	private NotifyReadTimeMapper notifyReadTimeMapper;

	PlatformNotifyServiceImpl() {
		super();
		this.objClz = TPlatformNotify.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("platformNotifyMapper") BaseMapper<TPlatformNotify> baseMapper) {
		this.baseMapper = baseMapper;

	}

	@Override
	public LayUiRetData getPlatformNotifyList(Map<String, Object> params) {
		Page<TPlatformNotify> page = PageUtils.getPageInfo(params);
		List<TPlatformNotify> operList = platformNotifyMapper.findByPage(page);
		int count = platformNotifyMapper.countByPage(page);

		return LayUiRetData.success(operList, Long.valueOf(count));
	}

	@Override
	public LayUiRetData getUserNotifyList(Map<String, Object> params) {
		Page<TPlatformNotify> page = PageUtils.getPageInfo(params);
		List<TPlatformNotify> operList = platformNotifyMapper.findUserNotify(page);
		int count = platformNotifyMapper.findUserNotifyCount(page);
		return LayUiRetData.success(operList, Long.valueOf(count));
	}

	@Override
	public LayUiRetData getUserNotifyNumber(Map<String, Object> params) {
		Page<TPlatformNotify> page = PageUtils.getPageInfo(params);
		int count = platformNotifyMapper.findUserNotifyCount(page);
		return LayUiRetData.success(null, Long.valueOf(count));
	}

}