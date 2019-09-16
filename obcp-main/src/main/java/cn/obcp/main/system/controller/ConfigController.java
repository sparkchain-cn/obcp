package cn.obcp.main.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.RetData;
import cn.obcp.base.controller.BaseController;
import cn.obcp.base.service.BaseService;
import cn.obcp.cache.redis.RedissonUtils;
import cn.obcp.main.system.domain.TConfig;
import cn.obcp.main.system.service.ConfigService;
import io.swagger.annotations.ApiOperation;

// ##remain#import#

/**
 * <pre>
  * 系统配置的Controller
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@RestController
@RequestMapping("/v1/config/")
public class ConfigController extends BaseController<TConfig, Long> {

	@Autowired
	private RedissonUtils redissonUtils;
	
	@Autowired
	private ConfigService configService;

	@Autowired
	@Override
	public void setEntityService(@Qualifier("bsConfigService") BaseService<TConfig, Long> entityService) {
		this.entityService = entityService;
	}

	@ApiOperation("系统配置列表")
	@RequestMapping(value="getList",method= {RequestMethod.POST,RequestMethod.GET})
	public LayUiRetData getList(@RequestParam Map<String, Object> params) {
		
		return configService.getList(params);
	}
	
	@ApiOperation("添加系统配置")
	@RequestMapping(value="save",method=RequestMethod.POST)
	public RetData save(TConfig config) {
		try {
			return configService.saveOrUpdate(config);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RetData.error("保存失败！");
		}
	}
	
	@ApiOperation("删除系统配置")
	@RequestMapping(value="remove",method=RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public RetData remove(Long id) {
		try {
			TConfig conf = configService.findById(id);
			boolean res = configService.deleteById(id);
			if(res) {
				redissonUtils.del(conf.getCode());
				return RetData.succuess();
			}else {
				return RetData.error("删除失败！");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RetData.error("删除失败！");
		}
	}
	
	@ApiOperation("系统配置详情")
	@RequestMapping(value="getInfo",method= {RequestMethod.POST,RequestMethod.GET})
	public RetData getInfo(Long id) {
		try {
			TConfig conf = configService.findById(id);
			if(null != conf) {
				return RetData.succuess(conf);
			}else {
				return RetData.error("配置不存在！");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RetData.error("获取失败！");
		}
	}
	
	// ##remain#property#

}