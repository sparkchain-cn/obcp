package cn.obcp.user.mapper;

import cn.obcp.user.domain.TUserRole;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.pagehelper.Page;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#

import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 用户-角色的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "userRoleMapper")
public interface UserRoleMapper extends BaseMapper<TUserRole> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.r != null \"> and rid = #{page.meta.r}</if>"
			+ " <if test=\"page.meta.u != null \"> and uid = #{page.meta.u}</if>"
			+ " <if test=\"page.meta.status != null \"> and status = #{page.meta.status}</if>"
			+ " <if test=\"page.meta.sortnum != null \"> and sortnum = #{page.meta.sortnum}</if>"
			+ " <if test=\"page.meta.updater != null \"> and updater = #{page.meta.updater}</if>"
			+ " <if test=\"page.meta.creator != null \"> and creator = #{page.meta.creator}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.updatetime != null \"> and updatetime = #{page.meta.updatetime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_user_role" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TUserRole> findByPage(@Param("page") Page<TUserRole> page);

	@Select("<script>select  count(*) from sys_user_role" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TUserRole> page);
	// ##remain#method#

	/**
	 * TODO
	 * @param roleId
	 * @return
	 * List<TUserRole>
	 * lmf 创建于2018年11月16日
	 */
	
	@Select("select * from sys_user_role where rid = #{roleId} and state > 0")
	List<TUserRole> findByRole(@Param("roleId") Long roleId);

	/**
	 * TODO
	 * @param roleId
	 * @return
	 * boolean
	 * lmf 创建于2018年11月17日
	 */
	@Select("update sys_user_role set state= #{state} where rid=#{roleId}")
	void updateToDelete(@Param("state") int state, @Param("roleId") Long roleId);

	/**
	 * TODO
	 * @param id
	 * @return
	 * List<TUserRole>
	 * lmf 创建于2018年11月19日
	 */
	@Select("select id,rid,uid from sys_user_role where uid = #{uid} and state > 0")
	List<TUserRole> findByUser(@Param("uid") Long id);

	/**
	 * TODO
	 * @param id
	 * @param status
	 * @return
	 * boolean
	 * lmf 创建于2018年11月24日
	 */
	
	@Update("UPDATE sys_user_role SET state = #{status} WHERE uid = #{uid}")
	boolean deleteByRoleId(@Param("uid") Long uid, @Param("status") int status);

	/**
	 * TODO
	 * @param id
	 * @return
	 * int
	 * lmf 创建于2018年11月27日
	 */
	@Select("SELECT COUNT(id) FROM sys_user_role WHERE rid = #{rid} AND state = 1")
	int countByRole(@Param("rid") Long id);

	/**
	 * TODO
	 * @param userId
	 * @param roleId
	 * @param status
	 * @return
	 * boolean
	 * lmf 创建于2018年11月28日
	 */
	@Update("UPDATE sys_user_role SET state = #{status} WHERE uid = #{uid} and rid=#{rid}")
	boolean delteByUserAndRole(@Param("uid") Long userId, @Param("rid") Long roleId, @Param("status") int status);
}