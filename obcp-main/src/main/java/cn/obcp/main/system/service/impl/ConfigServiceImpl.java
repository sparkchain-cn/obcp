package cn.obcp.main.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.base.utils.PageUtils;
import cn.obcp.cache.redis.RedissonUtils;
import cn.obcp.main.system.domain.TConfig;
import cn.obcp.main.system.mapper.ConfigMapper;
import cn.obcp.main.system.service.ConfigService;

//import cn.obcp.main.system.BaseMapper;
// ##remain#import#

/**
 * <pre>
  * 系统配置的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = false)
@Service("bsConfigService")
@Order(0)
public class ConfigServiceImpl extends BaseServiceImpl<TConfig, Long> implements ConfigService {

	protected final static Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);



	@Autowired
	private RedissonUtils redissonUtils;
	@Resource
	private ConfigMapper configMapper;
	// ##remain#property#

	
	ConfigServiceImpl() {
		super();
		this.objClz = TConfig.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("configMapper") BaseMapper<TConfig> baseMapper) {
		this.baseMapper = baseMapper;

	}
	
	public LayUiRetData getList(Map<String, Object> params) {
		Page<TConfig> page = PageUtils.getPageInfo(params);
		List<TConfig> operList = configMapper.findByPageList(page);
		int count = configMapper.countByPage(page);
		
		return LayUiRetData.success(operList, Long.valueOf(count));
	}
	
	/**
	 *  添加或修改
	 */
	public RetData saveOrUpdate(TConfig config) {
		Date now = new Date();
		if(null != config.getId()) {
			config.setUpdatetime(now);
			boolean res = super.update(config);
			if(res) {
				redissonUtils.setByString(config.getCode(), config.getVal());
				return RetData.succuess("修改成功！");
			}
			else
				return RetData.succuess("修改失败！");
		}else {
			TConfig conf = configMapper.findByCode(config.getCode());
			if(null != conf) {
				return RetData.error("编码已存在,请使用其他编码！");
			}else {
				config.setId(uuidCreator.id());
				config.setCreatetime(now);
				config.setUpdatetime(now);
				int res = configMapper.insert(config);
				if(res > 0) {
					//redissonUtils.addByMapsStringCodec(SystemConstans.CONFIG_REFIS_KEY, config.getCode(), config.getVal());
					redissonUtils.setByString(config.getCode(), config.getVal());
					return RetData.succuess("添加成功！");
				}
				else
					return RetData.succuess("添加失败！");
				
			}
		}
		
	}

	/**
	 * 初始化配置缓存
	 */
	@PostConstruct
	public void initCache() {
		List<TConfig> configList = findAll();
		for(TConfig conf : configList) {
			redissonUtils.setByString(conf.getCode(), conf.getVal());
		}
		logger.debug("系统配置初始化完成");
	}
	
	// ##remain#method#
}