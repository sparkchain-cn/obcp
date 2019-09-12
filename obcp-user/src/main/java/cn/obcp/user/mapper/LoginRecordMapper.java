package cn.obcp.user.mapper;

import cn.obcp.user.domain.TLoginRecord;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 用户登录记录的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "loginRecordMapper")
public interface LoginRecordMapper extends BaseMapper<TLoginRecord> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta. != null \"> and user = #{page.meta.}</if>"
			+ " <if test=\"page.meta.content != null \"> and content = #{page.meta.content}</if>"
			+ " <if test=\"page.meta.ip != null \"> and ip = #{page.meta.ip}</if>"
			+ " <if test=\"page.meta.logintime != null \"> and logintime = #{page.meta.logintime}</if>" + " </where>";

	@Select("<script>select * from sys_login_record" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TLoginRecord> findByPage(@Param("page") Page<TLoginRecord> page);

	@Select("<script>select  count(*) from sys_login_record" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TLoginRecord> page);
	// ##remain#method#
}