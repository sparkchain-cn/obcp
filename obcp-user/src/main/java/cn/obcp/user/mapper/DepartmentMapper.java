package cn.obcp.user.mapper;

import cn.obcp.user.domain.TDepartment;
import org.apache.ibatis.annotations.Select;

//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * sys_department的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "departmentMapper")
public interface DepartmentMapper extends BaseMapper<TDepartment> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.org != null \"> and org_id = #{page.meta.org}</if>"
			+ " <if test=\"page.meta.name != null \"> and name = #{page.meta.name}</if>"
			+ " <if test=\"page.meta.code != null \"> and code = #{page.meta.code}</if>"
			+ " <if test=\"page.meta.leader != null \"> and leader = #{page.meta.leader}</if>"
			+ " <if test=\"page.meta.leaderNumber != null \"> and leader_number = #{page.meta.leaderNumber}</if>"
			+ " <if test=\"page.meta.type != null \"> and type = #{page.meta.type}</if>"
			+ " <if test=\"page.meta.status != null \"> and status = #{page.meta.status}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.sortnum != null \"> and sortnum = #{page.meta.sortnum}</if>" + " </where>";

	@Select("<script>select * from sys_department" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TDepartment> findByPage(@Param("page") Page<TDepartment> page);

	@Select("<script>select  count(*) from sys_department" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TDepartment> page);
	// ##remain#method#
}