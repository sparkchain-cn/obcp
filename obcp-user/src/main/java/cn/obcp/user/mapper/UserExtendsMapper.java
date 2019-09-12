package cn.obcp.user.mapper;

import cn.obcp.user.domain.TUserExtends;
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
 * 用户详细信息表的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "userExtendsMapper")
public interface UserExtendsMapper extends BaseMapper<TUserExtends> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.u != null \"> and uid = #{page.meta.u}</if>"
			+ " <if test=\"page.meta.sex != null \"> and sex = #{page.meta.sex}</if>"
			+ " <if test=\"page.meta.region != null \"> and region = #{page.meta.region}</if>"
			+ " <if test=\"page.meta.cardnum != null \"> and cardnum = #{page.meta.cardnum}</if>"
			+ " <if test=\"page.meta.officeMobile != null \"> and office_mobile = #{page.meta.officeMobile}</if>"
			+ " <if test=\"page.meta.officeTel != null \"> and office_tel = #{page.meta.officeTel}</if>"
			+ " <if test=\"page.meta.officeEmail != null \"> and office_email = #{page.meta.officeEmail}</if>"
			+ " <if test=\"page.meta.officeAddr != null \"> and office_addr = #{page.meta.officeAddr}</if>"
			+ " <if test=\"page.meta.birthday != null \"> and birthday = #{page.meta.birthday}</if>"
			+ " <if test=\"page.meta.descriptions != null \"> and descriptions = #{page.meta.descriptions}</if>"
			+ " <if test=\"page.meta.registtime != null \"> and registtime = #{page.meta.registtime}</if>"
			+ " <if test=\"page.meta.qq != null \"> and qq = #{page.meta.qq}</if>"
			+ " <if test=\"page.meta.weixin != null \"> and weixin = #{page.meta.weixin}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.updatetime != null \"> and updatetime = #{page.meta.updatetime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_user_extends" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TUserExtends> findByPage(@Param("page") Page<TUserExtends> page);

	@Select("<script>select  count(*) from sys_user_extends" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TUserExtends> page);
	// ##remain#method#
}