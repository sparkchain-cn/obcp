package cn.obcp.dict.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.dict.common.Constant;
import cn.obcp.dict.domain.TDict;
import cn.obcp.dict.mapper.DictMapper;
import cn.obcp.dict.service.DictService;

/**
 * <pre>
  * 字典的业务实现
  * 该代码通过mgicode代码生成器生成
 * </pre>
 */
@Transactional(readOnly = false)
@Service("bsDictService")
public class DictServiceImpl extends BaseServiceImpl<TDict, String> implements DictService {

	protected final static Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

	@Resource
	private DictMapper dictMapper;
	// ##remain#property#

	DictServiceImpl() {
		super();
		this.objClz = TDict.class;
	}

	@Override
	@Autowired
	public void setBaseMapper(@Qualifier("dictMapper") BaseMapper<TDict> baseMapper) {
		this.baseMapper = baseMapper;

	}

	@Override
	public Integer getCount() {
		return dictMapper.countAll();
	}

	@Override
	public String saveDict(TDict tDict) {
		tDict.setUpdateTime(new Date());
		tDict.setCreateTime(new Date());
		tDict.setCreator(1);
		tDict.setStatus(Constant.DICT_STATUS_NORMAL);
		tDict.setSortnum(getCount() + 1);
		dictMapper.insert(tDict);
		return "字典新增成功！";
	}

	@Override
	public String updateDictStatus(String code) {
		TDict dict = dictMapper.findByCode(code);
		if (dict.getStatus().equals(Constant.DICT_STATUS_INNORMAL)){
			dict.setStatus(Constant.DICT_STATUS_NORMAL);
			this.update(dict);
			return "启用成功！";
		}
		if (dict.getStatus().equals(Constant.DICT_STATUS_NORMAL)){
			dict.setStatus(Constant.DICT_STATUS_INNORMAL);
			this.update(dict);
			return "禁用成功";
		}
		return "操作失败";
	}

	@Override
	public String deleteDict(String code) {
		TDict dict = dictMapper.findByCode(code);
		dict.setStatus(Constant.DICT_STATUS_DELETE);
		this.update(dict);
		return "删除成功！";
	}

	@Override
	public String getDictName(String code) {
		return dictMapper.getParentName(code);
	}

	@Override
	public TDict getParentById(String code) {
		return dictMapper.getParentById(code);
	}

	@Override
	public TDict getDictById(String code) {
		return dictMapper.getDictById(code);
	}

	@Override
	public List<TDict> getDictAll() {
		return dictMapper.getDictAll();
	}
}