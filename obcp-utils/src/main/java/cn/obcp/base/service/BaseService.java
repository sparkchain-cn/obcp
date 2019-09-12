package cn.obcp.base.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.vo.KeyValue;

public interface BaseService<T, PK> {

	public BaseMapper<T> getMapper();

	// 插入数据
	boolean save(T dict);

	// 批量插入
	boolean save(List<T> domains);

	// 通过主键刪除
	boolean deleteById(PK id);

	// 批量刪除 eg：ids -> “1,2,3,4”
	boolean deleteByIds(String ids);

	// 更新数据
	boolean update(T dict);

	// 通过ID查找
	T findById(PK id);

	// 通过domain中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
	T findByFieldName(String fieldName, Object value);

	// 通过多个ID查找//eg：ids -> “1,2,3,4”
	List<T> findByIds(String ids);

	// 获取所有数据
	List<T> findAll();

	// 以分页的形式查找
	Page<T> findByPage(int page, int size, Map<String, ?> params);

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @param entity
	 * @return lmf 创建于 2018年4月23日 TODO:
	 */
	Page<T> findByPage(int page, int size, T entity);

	Page<T> findByPage(Page<T> page);

	List<T> findByList(Map<String, Object> params, List<KeyValue> orders);

	boolean delete(T entity);

	// @Select("#{sql}")
	List<Map<String, Object>> findBySql(@Param("sql") String sql);

	// @Delete("#{sql}")
	void delBySql(@Param("sql") String sql);

	// @Update("#{sql}")
	void updateBySql(@Param("sql") String sql);

}