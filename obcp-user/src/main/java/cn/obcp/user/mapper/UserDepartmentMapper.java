package cn.obcp.user.mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import cn.obcp.user.domain.TUserDepartment;
import org.apache.ibatis.annotations.Select;

//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * sys_user_department的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "userDepartmentMapper")
public interface UserDepartmentMapper extends BaseMapper<TUserDepartment> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.user != null \"> and user_id = #{page.meta.user}</if>"
			+ " <if test=\"page.meta.dep != null \"> and dep_id = #{page.meta.dep}</if>"
			+ " <if test=\"page.meta.status != null \"> and status = #{page.meta.status}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_user_department" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TUserDepartment> findByPage(@Param("page") Page<TUserDepartment> page);

	@Select("<script>select  count(*) from sys_user_department" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TUserDepartment> page);
	// ##remain#method#
}