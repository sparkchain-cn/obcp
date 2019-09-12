package cn.obcp.user.mapper;

import cn.obcp.user.domain.TUserWorkorder;
import org.apache.ibatis.annotations.Select;

import cn.obcp.base.Page;
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
 * sys_user_workorder的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "userWorkorderMapper")
public interface UserWorkorderMapper extends BaseMapper<TUserWorkorder> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.mobile != null \"> and mobile = #{page.meta.mobile}</if>"
			+ " <if test=\"page.meta.loginemail != null \"> and loginemail = #{page.meta.loginemail}</if>"
			+ " <if test=\"page.meta.name != null \"> and name = #{page.meta.name}</if>"
			+ " <if test=\"page.meta.organization != null \"> and organization = #{page.meta.organization}</if>"
			+ " <if test=\"page.meta.companyno != null \"> and companyno = #{page.meta.companyno}</if>"
			+ " <if test=\"page.meta.companyname != null \"> and companyname = #{page.meta.companyname}</if>"
			+ " <if test=\"page.meta.companyaddr != null \"> and companyaddr = #{page.meta.companyaddr}</if>"
			+ " <if test=\"page.meta.companytel != null \"> and companytel = #{page.meta.companytel}</if>"
			+ " <if test=\"page.meta.taxid != null \"> and taxid = #{page.meta.taxid}</if>"
			+ " <if test=\"page.meta.authfile != null \"> and authfile = #{page.meta.authfile}</if>"
			+ " <if test=\"page.meta.businesslicensefile != null \"> and businesslicensefile = #{page.meta.businesslicensefile}</if>"
			+ " <if test=\"page.meta.corporationname != null \"> and corporationname = #{page.meta.corporationname}</if>"
			+ " <if test=\"page.meta.corporationcardnum != null \"> and corporationcardnum = #{page.meta.corporationcardnum}</if>"
			+ " <if test=\"page.meta.cardnumfrontfile != null \"> and cardnumfrontfile = #{page.meta.cardnumfrontfile}</if>"
			+ " <if test=\"page.meta.cardnumhandfile != null \"> and cardnumhandfile = #{page.meta.cardnumhandfile}</if>"
			+ " <if test=\"page.meta.cardnumbackfile != null \"> and cardnumbackfile = #{page.meta.cardnumbackfile}</if>"
			+ " <if test=\"page.meta.reviewmessage != null \"> and reviewmessage = #{page.meta.reviewmessage}</if>"
			+ " <if test=\"page.meta.status != null \"> and status = #{page.meta.status}</if>"
			+ " <if test=\"page.meta.verifyuser != null \"> and verifyuser = #{page.meta.verifyuser}</if>"
			+ " <if test=\"page.meta.verifytime != null \"> and verifytime = #{page.meta.verifytime}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.updatetime != null \"> and updatetime = #{page.meta.updatetime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_user_workorder" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	List<TUserWorkorder> findByPage(@Param("page") Page<TUserWorkorder> page);

	@Select("<script>select  count(*) from sys_user_workorder" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TUserWorkorder> page);
	// ##remain#method#
}