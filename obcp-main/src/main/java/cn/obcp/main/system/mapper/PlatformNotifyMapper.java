package cn.obcp.main.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.main.system.domain.TPlatformNotify;

/**
 * <pre>
 * 通知,站内消息的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "platformNotifyMapper")
public interface PlatformNotifyMapper extends BaseMapper<TPlatformNotify> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.title != null and page.meta.title != '' \"> and title = #{page.meta.title}</if>"
			+ " <if test=\"page.meta.content != null \"> and content = #{page.meta.content}</if>"
			+ " <if test=\"page.meta.msgType != null \"> and msg_type = #{page.meta.msgType}</if>"
			+ " <if test=\"page.meta.userId != null and page.meta.userId != ''\"> and user_id = #{page.meta.userId}</if>"
			+ " <if test=\"page.meta.appid != null \"> and appid = #{page.meta.appid}</if>"
			+ " <if test=\"page.meta.label != null \"> and label = #{page.meta.label}</if>"
			+ " <if test=\"page.meta.pushType != null \"> and push_type = #{page.meta.pushType}</if>"
			+ " <if test=\"page.meta.state != null \"> and state = #{page.meta.state}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_platform_notify" + whereStr
			+ " order by createtime desc "
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TPlatformNotify> findByPage(@Param("page") Page<TPlatformNotify> page);

	@Select("<script>select count(*) from sys_platform_notify" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TPlatformNotify> page);
	// ##remain#method#
	
	
	@Select("<script>select * from sys_platform_notify n " 
			+ " where n.`state` = 1 and  (n.user_id = #{page.meta.userId} or n.msg_type = 0)  " 
			+ " order by createtime desc "
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" 
			+ "</script>")
	List<TPlatformNotify> findUserNotify(@Param("page") Page<TPlatformNotify> page);
	
	@Select("<script>select count(1) from sys_platform_notify n "
			+ " where n.`state` = 1 and (n.user_id = #{page.meta.userId} or n.msg_type = 0) " 
			+ " <if test=\"page.meta.readTime != null \"> and createtime > #{page.meta.readTime} </if>"
			+ "</script>")
	int findUserNotifyCount(@Param("page") Page<TPlatformNotify> page);
}