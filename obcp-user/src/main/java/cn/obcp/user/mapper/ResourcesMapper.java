package cn.obcp.user.mapper;

import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.user.domain.TResources;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

//import cn.obcp.user.BaseMapper;
// ##remain#import#

/**
 * <pre>
 * 资源的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "resourcesMapper")
public interface ResourcesMapper extends BaseMapper<TResources> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and r.id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.name != null \"> and r.name like concat('%',#{page.meta.name},'%')</if>"
			+ " <if test=\"page.meta.aliasName != null \"> and r.alias_name = #{page.meta.aliasName}</if>"
			+ " <if test=\"page.meta.res_type_code != null \"> and r.res_type_code = #{page.meta.res_type_code}</if>"
			+ " <if test=\"page.meta.permissioncode != null \"> and r.permissioncode = #{page.meta.permissioncode}</if>"	
			+ " <if test=\"page.meta.code != null \"> and r.code like concat('%',#{page.meta.code},'%')</if>"
			+ " <if test=\"page.meta.resourceGroup != null \"> and r.resourceGroup = #{page.meta.code} </if>"
			+"  <choose><when test=\"page.meta.stete != null and page.meta.state  > 0 \" >"
			+ "and r.state = #{page.meta.state}</when>"
			+ "<otherwise>and r.state = 1</otherwise>"
			+ "</choose>"
			+ " <if test=\"page.meta.levelcode != null \"> and r.levelcode = #{page.meta.levelcode}</if>"
			+ " <if test=\"page.meta.parentid != null \"> and r.parentid = #{page.meta.parentid}</if>"
			+ " <if test=\"page.meta.resAddrType != null \"> and r.res_addr_type = #{page.meta.resAddrType}</if>"
			+ " </where>";

	@Select("<script>SELECT r.*,p.name as parentName FROM sys_resources r LEFT JOIN (SELECT id,name FROM sys_resources  WHERE state = 1) p on r.parentid = p.id" + whereStr
			+ " order by r.res_type_code,r.sortnum,r.createtime desc"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if> " + "</script>")
	List<TResources> findByPage(@Param("page") Page<TResources> page);

	
	/**
	 * TODO
	 * @param page
	 * @return  根据rid,uid查询资源权限，必填参数：rid,uid
	 * List<TResources>
	 * lmf 创建于2018年11月18日
	 */
/*	@Select("<script>SELECT u.*,e.id as roleId,(e.state > 0) as hasRole from sys_resources u LEFT JOIN \n" +
			"(SELECT ur.id,ur.rid,ur.state,u1.id as resId FROM sys_resources u1 LEFT JOIN sys_role_resources ur \n" +
			" on u1.id = ur.res_id \n" +
			"where  ur.state >0) e on u.id = e.resId" +
			" where 1=1 and u.state =1 and e.rid = #{page.meta.rid} " +
			 " <if test=\"page.meta.name != null \"> and u.name = like concat('%',#{page.meta.name},'%')</if>"+
			" order by u.sortnum desc,createtime desc"+
			 "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")*/
	@Select("<script>SELECT u.*,e.id as roleId,(e.state > 0) as hasRole from sys_resources u LEFT JOIN " +
			"(SELECT ur.id,ur.state,u1.id as resId FROM sys_resources u1 LEFT JOIN sys_role_resources ur on u1.id = ur.res_id where"
			+ " ur.rid = #{page.meta.rid} and ur.state >0) e on u.id = e.resId where 1=1 and u.state =1  "
			+ " <if test=\"page.meta.name != null \"> and u.name = like concat('%',#{page.meta.name},'%')</if>"
			+ ""
			+ " order by u.res_type_code,u.sortnum ,u.createtime DESC"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TResources> findBindByPage(@Param("page") Page<TResources> page);


	@Select("<script>SELECT res.*,t.hasRole FROM sys_resources res RIGHT JOIN (SELECT sr.id,rs.hasRole  FROM (SELECT rr.res_id as id FROM sys_role_resources rr INNER JOIN \n" +
			"(SELECT ur.uid,r.id from sys_role r INNER JOIN sys_user_role ur on r.id = ur.rid WHERE ur.state > 0\n" +
			" and ur.uid = #{page.meta.uid} ) ur on rr.rid = ur.id GROUP BY rr.res_id ORDER BY res_id) sr \n" +
			"left JOIN ( SELECT res.id,(srr.state > 0) as hasRole FROM sys_resources res LEFT JOIN  sys_role_resources srr on srr.res_id = res.id\n" +
			"  WHERE srr.rid = #{page.meta.rid} AND srr.state = 1 ORDER BY res.id) rs\n" +
			" on sr.id = rs.id ) t on t.id = res.id order by res.res_type_code,res.sortnum,res.createtime DESC" +
			"<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size}</if></script>")
	List<TResources> findUserResBindByPage(@Param("page") Page<TResources> page);

	@Select("<script>select  count(*) from sys_resources r " + whereStr + "</script>")
	int countByPage(@Param("page") Page<TResources> page);
	// ##remain#method#

	
	/**
	 * TODO
	 * @param parentid
	 * @return
	 * String
	 * lmf 创建于2018年11月13日
	 */
	@Select("SELECT levelcode FROM sys_resources WHERE (levelcode) LIKE ('#{parentCode%}') AND parentid is NOT NULL ORDER BY r.res_type_code,r.sortnum ,createtime DESC LIMIT 0,1")
	String getPreLeverCode(String parentCode);

	/**
	 * TODO
	 * @param id
	 * @return
	 * TResources
	 * lmf 创建于2018年11月15日
	 */
	@Select("SELECT r.*,t.name as parentName from sys_resources r LEFT JOIN sys_resources t on r.parentid = t.id where r.id=#{id} and r.state=1 ")
	TResources findResourceById(@Param("id") Long id);

	/**
	 * TODO
	 * @param id
	 * @return
	 * TResources
	 * lmf 创建于2018年11月15日
	 */
	@Select("select name as parentName,id as parentid FROM sys_resources where id = #{id} and state = 1 and res_type_code between 1 and 2")
	TResources findParentResourceById(Long id);
	
	/**
	 * 
	 * TODO
	 * @param roleId
	 * @return
	 * List<TResources>
	 * lmf 创建于2018年11月15日
	 */
	@Select("SELECT r.* FROM sys_resources r INNER JOIN sys_role_resources rs on r.id=rs.res_id where rs.rid = #{roleId} and rs.state > 0   order by r.res_type_code,r.sortnum")
	List<TResources> findByRole(@Param("roleId") Long roleId);


	/**
	 * TODO
	 * @param userId
	 * @return
	 * List<TResources>
	 * lmf 创建于2018年11月21日
	 */
	@Select("<script>SELECT r.* FROM sys_resources r INNER JOIN (" + 
			"SELECT rr.res_id FROM sys_role_resources rr INNER JOIN " + 
			"(SELECT ur.uid,r.id from sys_role r INNER JOIN sys_user_role ur on r.id = ur.rid WHERE ur.state > 0"
			+ " and ur.uid = #{userId} ) ur on rr.rid = ur.id and rr.state > 0) rs" + 
			" on r.id=rs.res_id where  r.res_type_code between 1 and 2 "
			+ "<if test=\"isAppGroup != null \" > and r.resourceGroup = #{isAppGroup} </if>"
			+ " group by r.id order by r.res_type_code,r.sortnum ,r.createtime DESC</script>")
	List<TResources> findResourceByUserId(@Param("userId") Long userId, @Param("isAppGroup") Integer isAppGroupVal);


	/**
	 * TODO
	 * @return
	 * long
	 * lmf 创建于2018年11月27日
	 */
	@Select("SELECT COUNT(id) as count FROM sys_resources t WHERE t.parentid is null")
	int getRootCount();


	/**
	 * TODO
	 * @param page 
	 * @return
	 * List<TResources>
	 * lmf 创建于2018年11月27日
	 */
	@Select("<script>SELECT r.* FROM sys_resources r "+whereStr+" and r.parentid is null" 
			+ " order by  r.res_type_code,r.sortnum ,r.createtime desc"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if> " + "</script>")
	List<TResources> findRoot(@Param("page") Page<TResources> page);


	/**
	 * 查询菜单
	 * @return
	 */
	@Select("select r.* from sys_resources r where r.state =1 and r.resourceGroup = 1 and r.res_type_code between 1 and 2 "
//			+ "<if test=\"resource.resourceGroup != null \"> and r.resourceGroup = #{resource.resourceGroup}</if>"
//			+ "<if test=\"resource.code != null \"> and r.code like concat('%',#{resource.code},'%')</if>"
//			+ "<if test=\"resource.name != null \"> and r.name like concat('%',#{resource.name},'%')</if>"
//			+ "<when><if test=\"resource.parentid != null \"> and r.parentid = #{resource.parentid}</if>"
//			+ "<otherwise> and r.parentid is null </otherwise></when>"
			+"order by r.res_type_code,r.sortnum ,r.createtime DESC")
	List<TResources> getAllMenuResource();


	@Select("<script>select r.* from sys_resources r where  r.state =1 and r.res_type_code between 1 and 2"
			+ "<if test=\"resource.resourceGroup != null \"> and r.resourceGroup = #{resource.resourceGroup}</if>"
			+ " order by r.res_type_code,r.sortnum,r.createtime DESC</script>")
	List<TResources> getAllResource(@Param("resource") TResources res);


	@Insert("<script>insert ignore into sys_resources (id,code,path,alias_name,name,parentid,res_type_code,state,description,resourceGroup,createtime,updatetime)"
			+ " values "
			+ "<foreach collection=\"resList\" item=\"res\" index=\"index\"  separator=\",\">"
			+" (#{res.id},#{res.code},#{res.path},#{res.aliasName},#{res.aliasName},#{res.parentid},"
			+ "#{res.resTypeCode},#{res.state},#{res.description},#{res.resourceGroup},#{res.createtime},#{res.updatetime})"
			+"</foreach></script>")
	boolean addList(@Param("resList") List<TResources> resList);


	/**
	 * 根据服务吗删除资源
	 * @param services
	 * @param parentid
	 * @return
	 */
	@Delete("<script>delete from sys_resources where (1 = 0 "
			+ "<foreach collection=\"services\" item=\"s\" index=\"index\" >"
			+ " or code LIKE concat('',#{s},'%') "
			+ "</foreach> ) and parentid = ${parentid} </script>")
	int deleteBySeviceName(@Param("services") Set<String> services,@Param("parentid") Long parentid);

	@Select("select * from sys_resources where state = 1")
	List<TResources> findAllRes();

	@Update("<script>update sys_resources set parentid = #{parentId} , sortnum= #{sortnum} where id in ( " +
			"<foreach collection=\"ids\" item=\"id\" index=\"index\" separator=\",\">"+
			"#{id}"+
			"</foreach>)</script>")
	int updateNodeParent(@Param("ids") List<String> ids, @Param("parentId") Long parentId,@Param("sortnum") Integer sortnum);

	@Update("<script>update sys_resources " +
			" set parentid = null,sortnum= #{sortnum} where id in ( " +
			"<foreach collection=\"ids\" item=\"id\" index=\"index\" separator=\",\">"+
			"#{id}"+
			"</foreach>)</script>")
	int updateNodeParentNull(@Param("ids") List<String> ids,@Param("sortnum") Integer sortnum);

	@Select("<script>select * from sys_resources where code in ( " +
			"<foreach collection=\"resList\" item=\"code\" index=\"index\" separator=\",\">"+
			"#{code}"+
			"</foreach>)</script>")
	List<TResources> findByResourcesCodeList(@Param("resList") List<String> resList);

	@Select("<script>SELECT MAX(sortnum) FROM sys_resources</script>")
	Integer getMaxSortNum(Page<Object> objectPage);

	@Update("<script>UPDATE sys_resources SET sortnum  = (sortnum+1) WHERE 1 = 1 and id &lt;&gt; #{targetId}"
            + "<choose><when test=\"parentid != null  and parentid != 0 \"> and parentid = #{parentid}</when>"
            +"<otherwise>and parentid is null</otherwise></choose>"
            + "and sortnum >= #{sortnum} </script>")
	int setSortToNext(@Param("parentid") Long parentid,@Param("targetId") Long targetId ,@Param("sortnum") Integer sortnum);

	@Update("<script>update sys_resources set sortnum= #{sortnum} where id in ( " +
			"<foreach collection=\"ids\" item=\"id\" index=\"index\" separator=\",\">"+
			"#{id}"+
			"</foreach>)</script>")
	int updateNodeSortNum(@Param("ids") List<String> ids,@Param("sortnum") Integer sortnum);

	@Update("<script>UPDATE sys_resources SET sortnum  = (sortnum + 2) WHERE 1 = 1 "
			+ "<choose><when test=\"parentid != null and parentid != 0 \"> and parentid = #{parentid}</when>"
			+"<otherwise>and parentid is null</otherwise></choose>"
			+ "and sortnum >= #{sortnum}</script>")
	int setSortToPrev(@Param("parentid") Long parentid,@Param("sortnum") Integer sortnum);

	/**
	 *  根据id
	 * @param ids
	 * @return
	 */
	@Select("<script>select * from  sys_resources where id in ( " +
			"<foreach collection=\"ids\" item=\"id\" index=\"index\" separator=\",\">"+
			"#{id}"+
			"</foreach>)</script>")
	List<TResources> findByIds(@Param("ids") List<String> ids);
}