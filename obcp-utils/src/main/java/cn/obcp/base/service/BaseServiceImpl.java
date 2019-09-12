package cn.obcp.base.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import cn.obcp.base.Page;
import cn.obcp.base.UuidCreator;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.vo.KeyValue;

public abstract class BaseServiceImpl<T, PK> implements BaseService<T, PK> {

	protected final static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	protected BaseMapper<T> baseMapper;
	protected Class objClz;

	public BaseMapper<T> getMapper() {
		return baseMapper;
	}

	@Autowired
	protected UuidCreator uuidCreator;

	public abstract void setBaseMapper(BaseMapper<T> baseMapper);

	@Override
	public boolean delete(T entity) {

		int result = baseMapper.delete(entity);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean save(T entity) {

		int result = baseMapper.insertSelective(entity);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean save(List<T> entities) {
		int result = baseMapper.insertList(entities);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteById(PK id) {
		int result = baseMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteByIds(String ids) {
		int result = baseMapper.deleteByIds(ids);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean update(T entity) {
		int result = baseMapper.updateByPrimaryKeySelective(entity);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public T findById(PK id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public T findByFieldName(String fieldName, Object value) {
		try {
			T entity = (T) objClz.newInstance();
			Field field = objClz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(entity, value);
			return baseMapper.selectOne(entity);
		} catch (ReflectiveOperationException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List findByIds(String ids) {
		return baseMapper.selectByIds(ids);
	}

	@Override
	public List<T> findAll() {
		return baseMapper.selectAll();
	}

	@Override
	public Page<T> findByPage(int page, int size, Map params) {
		PageHelper.startPage(page, size);
		List<T> list = findAll();
		Page<T> pageInfo = new Page<T>(list);
		return pageInfo;
	}

	/**
	 * 分页查询
	 */
	public Page<T> findByPage(int page, int size, T entity) {
		PageHelper.startPage(page, size);
		List<T> list = findAll();
		Page<T> pageInfo = new Page<T>(list);
		return pageInfo;
	}

	public Page<T> findByPage(Page<T> page) {
		List<T> list = baseMapper.findByPage(page);
		int total = baseMapper.countByPage(page);
		page.setTotal(total);
		page.setResult(list);
		return page;
	}

	public List<T> findByList(Map<String, Object> params, List<KeyValue> orders) {
		Page<T> pages = new Page<>();
		pages.setNeedPage(false);
		pages.setMeta(params == null ? new HashMap<>() : params);
		pages.setOrders(orders);

		List<T> list = baseMapper.findByPage(pages);
		return list;

	}

	// @Select("#{sql}")
	public List<Map<String, Object>> findBySql(@Param("sql") String sql) {
		return baseMapper.findBySql(sql);
	}

	// @Delete("#{sql}")
	public void delBySql(@Param("sql") String sql) {

		baseMapper.delBySql(sql);
	}

	// @Update("#{sql}")
	public void updateBySql(@Param("sql") String sql) {
		baseMapper.updateBySql(sql);
	}

}
