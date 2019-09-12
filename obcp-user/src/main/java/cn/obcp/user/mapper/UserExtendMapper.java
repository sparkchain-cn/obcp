package cn.obcp.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#
import cn.obcp.user.VO.RoleResourceVo;
import cn.obcp.user.domain.TUserExtend;

/**
 * <pre>
 * 用户表的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "userExtendMapper")
public interface UserExtendMapper extends BaseMapper<TUserExtend> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and u.id like concat('%',#{page.meta.id},'%')</if>"
			+ " <if test=\"page.meta.mobile != null \"> and u.mobile like concat('%',#{page.meta.mobile},'%')</if>"
			+ " <if test=\"page.meta.loginemail != null \"> and u.loginemail like concat('%',#{page.meta.loginemail},'%')</if>"			
			+ " <if test=\"page.meta.name != null \"> and u.name like concat('%',#{page.meta.name},'%')</if>"
			+ " <if test=\"page.meta.pingyinname != null \"> and u.pingyinname  like concat('%',#{page.meta.pingyinname},'%')</if>"
			+ " <if test=\"page.meta.nickname != null \"> and u.nickname  like concat('%',#{page.meta.nickname},'%')</if>"			
			+ " <if test=\"page.meta.organization != null \"> and u.organization = #{page.meta.organization}</if>"
			+"  <if test=\"page.meta.state != null \" >and u.state = #{page.meta.state}</if>"
			+"  <if test=\"page.meta.vip != null \" >and u.vip = #{page.meta.vip}</if>"
			+ "</where>";

	@Select("<script>select u.*,urn.`name` as roleName,urn.sex from sys_user u " + 
			"left join (SELECT u.id,u.sex,GROUP_CONCAT(r.name) as name from (" + 
			"SELECT u1.id,us.* from sys_user u1 LEFT JOIN sys_user_extends us on u1.id = us.uid) u,\r\n" + 
			"sys_role r,sys_user_role ur WHERE u.id=ur.uid and r.id = ur.rid and ur.state = 1 and r.state =1 GROUP BY u.id) urn" + 
			" on u.id = urn.id  " + whereStr
			+ "<choose><when test=\"page.orderStr !=null \"> order by #{page.orderStr} </when><otherwise>order by createtime desc</otherwise></choose>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	Page<TUserExtend> findByPage(@Param("page") Page<TUserExtend> page);

	@Select("<script>select  count(*) from sys_user u " + whereStr + "</script>")
	int countByPage(@Param("page") Page<TUserExtend> page);
	
	@Select("<script>select u.id,u.nickname,u.mobile,u.name from sys_user u </script>")
	List<TUserExtend> finUserAll();

	/**
	 * 查询企业账号
	 * @param page
	 * @return
	 */
	@Select("<script> select * from sys_user u left join bc_company cny on u.id = cny.supervisor  "
			+ whereStr
			+ "<choose><when test=\"page.orderStr !=null \"> order by #{page.orderStr} </when><otherwise>order by u.createtime desc</otherwise></choose>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TUserExtend> findCompanyByPage(@Param("page") cn.obcp.base.Page<TUserExtend> page);
	
	/**
	 * 查询企业账号
	 * @param page
	 * @return
	 */
	@Select("<script> select count(1) from sys_user u left join bc_company cny on u.id = cny.supervisor "
			+ whereStr
		    + "</script>")
	int countCompanyByPage(@Param("page") cn.obcp.base.Page<TUserExtend> page);
	
	/**
	 * TODO
	 * @param loginName
	 * @return
	 * TUserExtend
	 * lmf 创建于2018年11月15日
	 */
	@Select("select * from sys_user u where loginemail = #{loginName} or mobile = #{loginName} and state = 1")
	TUserExtend findByLogin(String loginName);

	/**
	 * TODO
	 * @param id
	 * @return
	 * TUserExtend
	 * lmf 创建于2018年11月16日
	 */
	@Select("select u.*,e.* from sys_user u left join sys_user_extends e on u.id = e.uid where id = #{id} and u.state = 1 ")
	TUserExtend findById(@Param("id") Long id);


	/**
	 * TODO
	 * @param page
	 * @return
	 * cn.obcp.base.Page<TUserExtend>
	 * lmf 创建于2018年11月17日
	 */
	@Select("<script>\r\n" + 
			"SELECT u.*,e.id as roleId,(e.state > 0) as hasRole from sys_user u LEFT JOIN \r\n" + 
			"(SELECT ur.id,ur.state,u1.id as uid FROM sys_user u1 LEFT JOIN sys_user_role ur on u1.id = ur.uid where ur.rid = #{page.meta.rid} and ur.state = 1 ) e on u.id = e.uid where u.state = 1 " 
			+ " <if test=\"page.meta.nickname != null \"> and u.nickname like concat('%',#{page.meta.nickname},'%')</if>"			
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TUserExtend> findBindByPage(@Param("page") cn.obcp.base.Page<TUserExtend> page);

	/**
	 *  查询用户，查询条件 -》 OR
	 * @param page
	 * @return
	 */
	@Select("<script>SELECT u.id,u.nickname,u.mobile,e.id as roleId,(e.state > 0) as hasRole from sys_user u LEFT JOIN \r\n" +
			"(SELECT ur.id,ur.state,u1.id as uid FROM sys_user u1 LEFT JOIN sys_user_role ur on u1.id = ur.uid where ur.rid = #{page.meta.rid} " +
			"and ur.state = 1 ) e on u.id = e.uid where u.state = 1 "
			+ " <if test=\"page.meta.nickname != null \"> and u.nickname like concat('%',#{page.meta.nickname},'%')</if>"	
			+ " <if test=\"page.meta.mobile != null \"> or u.mobile like concat('%',#{page.meta.mobile},'%')</if>"	
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TUserExtend> searchByOr(@Param("page") cn.obcp.base.Page<TUserExtend> page);
	
	/**
	 * TODO
	 * @param userId
	 * @param adminRoleId
	 * @return
	 * TUserExtend
	 * lmf 创建于2018年11月22日
	 */
	@Select("<script>SELECT u.id from  sys_user u LEFT JOIN sys_user_role ur on u.id = ur.uid where u.id=#{userId}" +
			" and u.state = 1  and ur.state = 1 and ur.rid in (" +
			"<foreach collection=\"adminRoleIds\" item=\"adminRoleId\" index=\"index\" separator=\",\">#{adminRoleId}</foreach>" +
			" )</script>")
	TUserExtend findAdminById(@Param("userId") Long userId, @Param("adminRoleIds") List<String> adminRoleId);

	/**
	 * TODO
	 * @param para
	 * @return
	 * int
	 * lmf 创建于2018年11月26日
	 */
	@Update("update sys_user set password = #{password} where id = #{userid}")
	int updatePassword(@Param("userid") Long userid, @Param("password") String password);

	/**
	 * TODO
	 * @param page
	 * @return
	 * cn.obcp.base.Page<TUserExtend>
	 * lmf 创建于2018年11月27日
	 */
	@Select("<script>\r\n" + 
			"SELECT u.*,e.id as roleId,(e.state > 0) as hasRole from sys_user u LEFT JOIN \r\n" + 
			"(SELECT ur.id,ur.state,u1.id as uid FROM sys_user u1 LEFT JOIN sys_user_role ur on u1.id = ur.uid where" +
			" ur.rid = #{page.meta.rid} and ur.state = 1 ) e on u.id = e.uid where u.state = 1 and e.state is null"
			+ "<if test=\"page.meta.mobile != null \"> and u.mobile like concat('%',#{page.meta.mobile},'%')</if>"
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TUserExtend> getNoBindByRole(@Param("page") cn.obcp.base.Page<TUserExtend> page);
	
	
	@Select("<script>\r\n" + 
			"SELECT u.*,e.id as roleId,(e.state > 0) as hasRole,e.updateTime from sys_user u LEFT JOIN \r\n" + 
			"(SELECT ur.id,ur.updateTime,ur.state,u1.id as uid FROM sys_user u1 LEFT JOIN sys_user_role ur on u1.id = ur.uid where ur.rid = #{page.meta.rid} and ur.state = 1 ) e on u.id = e.uid where u.state = 1 and e.state = 1" 			
			+ " <if test=\"page.meta.mobile != null \"> and u.mobile like concat('%',#{page.meta.mobile},'%')</if>"			
			+ " order by e.updateTime desc"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TUserExtend> getBindByRole(@Param("page") cn.obcp.base.Page<TUserExtend> page);

	@Select("<script>select count(*) from (SELECT u.*,e.id as roleId,(e.state > 0) as hasRole from sys_user u LEFT JOIN " + 
			"(SELECT ur.id,ur.state,u1.id as uid FROM sys_user u1 LEFT JOIN sys_user_role ur on u1.id = ur.uid where ur.rid = #{page.meta.rid} and ur.state = 1 ) e on u.id = e.uid where u.state = 1 and e.state = 1" 			
			+ " <if test=\"page.meta.mobile != null \"> and u.mobile like concat('%',#{page.meta.mobile},'%')</if>"	
			+ ") tb1 </script>")
	int getBindByRoleCount(@Param("page") cn.obcp.base.Page<TUserExtend> page);

	@Select("<script>select count(*) from (SELECT u.*,e.id as roleId,(e.state > 0) as hasRole from sys_user u LEFT JOIN \r\n" + 
			"(SELECT ur.id,ur.state,u1.id as uid FROM sys_user u1 LEFT JOIN sys_user_role ur on u1.id = ur.uid where ur.rid = #{page.meta.rid} and ur.state = 1 ) e on u.id = e.uid where u.state = 1 and e.state is null" 
			+ "<if test=\"page.meta.mobile != null \"> and u.mobile like concat('%',#{page.meta.mobile},'%')</if>"			
			+ ") tb1</script>")
	int getNoBindByRoleCount(@Param("page") cn.obcp.base.Page<TUserExtend> page);

	@Select("select * from sys_user u LEFT JOIN sys_user_extends us on u.id = us.uid LEFT JOIN bc_company cny on u.id = cny.supervisor where u.id = #{id}")
	TUserExtend findUserById(@Param("id") Long userId);

	
	/**
	 * 查询用户角色资源信息
	 * @param page
	 * @return
	 */
	@Select("<script>SELECT GROUP_CONCAT(res.`name` ) resourceName,sur.rid,r.`name` roleName FROM sys_user_role sur\r\n" + 
			" LEFT JOIN sys_role_resources srr on srr.rid = sur.rid " + 
			" LEFT JOIN sys_resources res on res.id = srr.res_id" + 
			" left join sys_role r on r.id = sur.rid  \r\n" + 
			"where  1 = 1 "+
			"<if test=\"page.meta.userId != null\" > and sur.uid = #{page.meta.userId} </if> and sur.state = 1 AND srr.state = 1 and r.state = 1  group by sur.rid"+
			"<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if></script>")
	List<RoleResourceVo> getRoleResourceByUser(@Param("page") cn.obcp.base.Page<RoleResourceVo> page);
	
	@Select("<script>SELECT count(1) from (SELECT sur.rid FROM sys_user_role sur\r\n" + 
			" LEFT JOIN sys_role_resources srr on srr.rid = sur.rid " + 
			" LEFT JOIN sys_resources res on res.id = srr.res_id" + 
			" left join sys_role r on r.id = sur.rid  " + 
			" where  1 = 1 "
			+ "<if test=\"page.meta.userId != null\" >and sur.uid = #{page.meta.userId} </if> and sur.state = 1 AND srr.state = 1 and r.state = 1  group by sur.rid) t</script>")
	int countRoleResourceByUser(@Param("page") cn.obcp.base.Page<RoleResourceVo> page);


	@Select("<script>SELECT u.* FROM sys_user u WHERE NOT EXISTS (SELECT u.* FROM bc_org_manager udet WHERE u.id = udet.user_id)"
			+ "<if test=\"page.meta.mobile != null \"> and u.mobile like concat('%',#{page.meta.mobile},'%')</if>"
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>"
			+"</script>")
    List<TUserExtend> findUserWithoutOrg(@Param("page")cn.obcp.base.Page<TUserExtend> page);

	@Select("<script>SELECT count(1) FROM sys_user u WHERE NOT EXISTS (SELECT u.* FROM bc_org_manager udet WHERE u.id = udet.user_id)"
			+ "<if test=\"page.meta.mobile != null \"> and u.mobile like concat('%',#{page.meta.mobile},'%')</if>"
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>"
			+"</script>")
	Integer countUserWiCountthoutOrg(@Param("page")cn.obcp.base.Page<TUserExtend> page);
}