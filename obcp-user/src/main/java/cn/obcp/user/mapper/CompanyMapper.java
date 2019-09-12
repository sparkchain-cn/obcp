package cn.obcp.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.user.domain.TCompany;

/**
 * <pre>
 * 公司相关信息表的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "companyMapper")
public interface CompanyMapper extends BaseMapper<TCompany> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta. != null \"> and supervisor = #{page.meta.}</if>"
			+ " <if test=\"page.meta.companyno != null \"> and companyno = #{page.meta.companyno}</if>"
			+ " <if test=\"page.meta.companyname != null \"> and companyname = #{page.meta.companyname}</if>"
			+ " <if test=\"page.meta.companyaddr != null \"> and companyaddr = #{page.meta.companyaddr}</if>"
			+ " <if test=\"page.meta.companytel != null \"> and companytel = #{page.meta.companytel}</if>"
			+ " <if test=\"page.meta.taxid != null \"> and taxid = #{page.meta.taxid}</if>"
			+ " <if test=\"page.meta.authfile != null \"> and authfile = #{page.meta.authfile}</if>"
			+ " <if test=\"page.meta.businesslicensefile != null \"> and businesslicensefile = #{page.meta.businesslicensefile}</if>"
			+ " <if test=\"page.meta.corporationname != null \"> and corporationname = #{page.meta.corporationname}</if>"
			+ " <if test=\"page.meta.corporationcardnum != null \"> and corporationcardnum = #{page.meta.corporationcardnum}</if>"
			+ " <if test=\"page.meta.status != null \"> and status = #{page.meta.status}</if>"
			+ " <if test=\"page.meta.reviewmessage != null \"> and reviewmessage = #{page.meta.reviewmessage}</if>"
			+ " <if test=\"page.meta.verifytime != null \"> and verifytime = #{page.meta.verifytime}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.updatetime != null \"> and updatetime = #{page.meta.updatetime}</if>"
			+ " </where>";

	@Select("<script>select * from bc_company" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TCompany> findByPage(@Param("page") Page<TCompany> page);

	@Select("<script>select  count(*) from bc_company" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TCompany> page);
	// ##remain#method#
}