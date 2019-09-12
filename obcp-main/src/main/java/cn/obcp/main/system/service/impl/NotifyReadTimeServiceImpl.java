package cn.obcp.main.system.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.main.system.domain.TNotifyReadTime;

// ##remain#import#

import cn.obcp.main.system.mapper.NotifyReadTimeMapper;
import cn.obcp.main.system.service.NotifyReadTimeService;

/**
 * <pre>
  * 消息阅读记录的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("scNotifyReadTimeService")
public class NotifyReadTimeServiceImpl extends BaseServiceImpl<TNotifyReadTime, Long> implements NotifyReadTimeService {

	protected final static Logger logger = LoggerFactory.getLogger(NotifyReadTimeServiceImpl.class);

	@Resource
	private NotifyReadTimeMapper notifyReadTimeMapper;
	// ##remain#property#

	NotifyReadTimeServiceImpl() {
		super();
		this.objClz = TNotifyReadTime.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("notifyReadTimeMapper") BaseMapper<TNotifyReadTime> baseMapper) {
		this.baseMapper = baseMapper;

	}

	@Transactional(readOnly = false)
	@Override
	public LayUiRetData saveReadTime(Long userId) {
		boolean save = false;
		TNotifyReadTime readTime = getReadTime(userId);
		if(readTime == null) {
			readTime = new TNotifyReadTime();
			readTime.setUserId(userId);
			readTime.setReadTime(new Date());
			save = save(readTime);
		}else {
			readTime.setReadTime(new Date());
			save = update(readTime);
		}
		return save ? LayUiRetData.success("保存成功!"):LayUiRetData.error("保存失败!");
	}

	@Override
	public TNotifyReadTime getReadTime(Long userId) {
		return notifyReadTimeMapper.findReadTime(userId);
	}
}