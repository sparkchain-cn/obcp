package cn.obcp.base.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import cn.obcp.base.Page;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Component
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T>, ConditionMapper<T> {

	List<T> findByPage(@Param("page") Page<T> page);

	int countByPage(@Param("page") Page<T> page);

	@Select("${sql}")
	List<Map<String, Object>> findBySql(@Param("sql") String sql);

	@Delete("${sql}")
	void delBySql(@Param("sql") String sql);

	@Update("${sql}")
	void updateBySql(@Param("sql") String sql);

}
