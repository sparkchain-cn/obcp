package cn.obcp.main.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.main.system.domain.TNotifyReadTime;

/**
 * <pre>
 * 消息阅读记录的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "notifyReadTimeMapper")
public interface NotifyReadTimeMapper extends BaseMapper<TNotifyReadTime> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.userId != null \"> and user_id = #{page.meta.userId}</if>"
			+ " </where>";

	@Select("<script>select * from bc_notify_read_time " + whereStr + "</script>")
	List<TNotifyReadTime> findByPage(@Param("page") Page<TNotifyReadTime> page);

	@Select("<script>select  count(*) from bc_notify_read_time " + whereStr + "</script>")
	int countByPage(@Param("page") Page<TNotifyReadTime> page);
	// ##remain#method#
	
	@Select("select * from bc_notify_read_time where user_id = #{userId} ")
	TNotifyReadTime findReadTime(@Param("userId") Long userId);
}