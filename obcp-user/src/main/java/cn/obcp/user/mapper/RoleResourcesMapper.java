package cn.obcp.user.mapper;

import cn.obcp.user.domain.TRoleResources;
import cn.obcp.user.domain.TUserRole;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
 * 角色-资源的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "roleResourcesMapper")
public interface RoleResourcesMapper extends BaseMapper<TRoleResources> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.r != null \"> and rid = #{page.meta.r}</if>"
			+ " <if test=\"page.meta.res != null \"> and res_id = #{page.meta.res}</if>"
			+ " <if test=\"page.meta.updater != null \"> and updater = #{page.meta.updater}</if>"
			+ " <if test=\"page.meta.creator != null \"> and creator = #{page.meta.creator}</if>"
			+ " <if test=\"page.meta.sortnum != null \"> and sortnum = #{page.meta.sortnum}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.updatetime != null \"> and updatetime = #{page.meta.updatetime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_role_resources" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TRoleResources> findByPage(@Param("page") Page<TRoleResources> page);

	@Select("<script>select  count(*) from sys_role_resources" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TRoleResources> page);
	// ##remain#method#

	/**
	 * TODO
	 * @param status
	 * @param roleId
	 * void
	 * lmf 创建于2018年11月18日
	 */
	@Select("update sys_role_resources set state= #{state} where rid=#{roleId}")
	void updateToDelete(@Param("state") int state, @Param("roleId") Long roleId);

	/**
	 * TODO
	 * @param roleId
	 * @return
	 * List<TUserRole>
	 * lmf 创建于2018年11月18日
	 */
	@Select("select * from sys_role_resources where rid = #{roleId} and state = 1")
	List<TRoleResources> findByRole(@Param("roleId") Long roleId);

	/**
	 * 判断资源是否已关联角色
	 * TODO
	 * @param resId
	 * @return
	 * List<TRoleResources>
	 * lmf 创建于2018年11月27日
	 */
	@Select("select count(id) > 0  as count from sys_role_resources where res_id = #{resId} and state = 1")
	boolean hadRoleRes(@Param("resId") Long resId);
	
	/**
	 * TODO
	 * @param rid
	 * @param status
	 * @return
	 * boolean
	 * lmf 创建于2018年11月24日
	 */
	@Update("UPDATE sys_role_resources SET state = #{status} WHERE rid = #{id}")
	boolean deleteByRoleId(@Param("id") Long rid, @Param("status") int status);

	/**
	 * TODO
	 * @param id
	 * @return
	 * int
	 * lmf 创建于2018年11月27日
	 */
	@Select("SELECT COUNT(id) FROM sys_role_resources WHERE rid = #{rid} AND state = 1")
	int countByRole(@Param("rid") Long id);
	
	
}