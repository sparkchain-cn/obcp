package cn.obcp.dict.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.dict.domain.TRegion;
import cn.obcp.dict.mapper.RegionMapper;
import cn.obcp.dict.service.RegionService;

/**
 * <pre>
  * 行政区划的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = true)
@Service("bsRegionService")
public class RegionServiceImpl extends BaseServiceImpl<TRegion, Long> implements RegionService {

	protected final static Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

	@Resource
	private RegionMapper regionMapper;
	// ##remain#property#

	RegionServiceImpl() {
		super();
		this.objClz = TRegion.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("regionMapper") BaseMapper<TRegion> baseMapper) {
		this.baseMapper = baseMapper;

	}

	@Override
	public int countAll() {
		return regionMapper.countAll();
	}

	@Override
	public TRegion getParentById(Long id) {
		return regionMapper.getParentById(id);
	}

	@Override
	public TRegion getRegionById(Long id) {
		return regionMapper.getRegionById(id);
	}

	// ##remain#method#
}