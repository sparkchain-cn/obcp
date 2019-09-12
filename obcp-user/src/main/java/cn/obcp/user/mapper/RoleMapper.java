package cn.obcp.user.mapper;

import cn.obcp.user.domain.TRole;
import cn.obcp.user.domain.TUserRole;

import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 角色的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "roleMapper")
public interface RoleMapper extends BaseMapper<TRole> {

	
	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and role.id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.code != null \"> and role.code like concat('%',#{page.meta.code},'%')</if>"
			+ " <if test=\"page.meta.name != null \"> and role.name like concat('%',#{page.meta.name},'%')</if>"
			+"  <choose><when test=\"page.meta.stete != null and page.meta.state  > 0 \" >"
			+ "and role.state = #{page.meta.state}</when>"
			+ "<otherwise>and role.state > 0</otherwise>"
			+ "</choose>"
		//	+ " <if test=\"page.meta.state != null \"> and role.state > 0</if>"
			+ " </where>";

/*	@Select("<script>SELECT role.*,rn.`name` as `resourceName`  FROM sys_role role LEFT JOIN (SELECT rr1.rid,GROUP_CONCAT(rr1.name) as name from (\r\n" +
			"SELECT r.id,r.name,rr.rid from sys_resources r LEFT JOIN sys_role_resources rr on r.id = rr.res_id WHERE rr.state > 0 \r\n" + 
			") rr1 GROUP BY rr1.rid ) rn on role.id = rn.rid " + whereStr
			+ "order by role.createtime desc"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")*/
	@Select("<script>select  * from sys_role role " + whereStr + "</script>")
	Page<TRole> findByPage(@Param("page") Page<TRole> page);

	@Select("<script>select  count(*) from sys_role role " + whereStr + "</script>")
	int countByPage(@Param("page") Page<TRole> page);
	// ##remain#method#

	/**
	 * TODO
	 * @param id
	 * @return
	 * TRole
	 * lmf 创建于2018年11月16日  //默认过滤已删除
	 */
	@Select("select * from sys_role role where id = #{id} and state >0")
	TRole findById(Long id);

	
	/**
	 * 用户绑定角色页面接口
	 * TODO
	 * @param page
	 * @return
	 * List<TRole>
	 * lmf 创建于2018年11月23日
	 */
	@Select("SELECT role.*,uer.state as hasRole  from sys_role role LEFT JOIN (\r\n" + 
			"	SELECT ur.uid as uid ,ur.rid,ur.state FROM sys_user u LEFT JOIN (\r\n" + 
			"		SELECT userRole.state,userRole.uid,userRole.rid FROM sys_user_role userRole LEFT JOIN (\r\n" + 
			"				SELECT roleResource.res_id,roleResource.rid FROM sys_role_resources roleResource \r\n" + 
			"					LEFT JOIN sys_resources resource on roleResource.res_id = resource.id where roleResource.state = 1 and resource.state = 1\r\n" + 
			"		) roleresurce on userRole.rid = roleresurce.rid   where userRole.state = 1 AND userRole.uid = #{userId}" + 
			"	) ur on u.id = ur.uid WHERE ur.state = 1 and u.state > 0 \r\n" + 
			") uer on role.id = uer.rid "+whereStr+" GROUP BY role.id")
	cn.obcp.base.Page<TRole> bindUserPage(@Param("page") cn.obcp.base.Page<TRole> page, @Param("userId") Long userId);

}