package cn.obcp.user.mapper;

import cn.obcp.user.domain.TUserToken;
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
 * 用户token的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "userTokenMapper")
public interface UserTokenMapper extends BaseMapper<TUserToken> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.uid != null \"> and uid = #{page.meta.uid}</if>"
			+ " <if test=\"page.meta.token != null \"> and token = #{page.meta.token}</if>"
			+ " <if test=\"page.meta.ip != null \"> and ip = #{page.meta.ip}</if>"
			+ " <if test=\"page.meta. != null \"> and createtime = #{page.meta.}</if>" + " </where>";

	@Select("<script>select * from sys_user_token" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{skip},#{pageSize} </if>" + "</script>")
	Page<TUserToken> findByPage(@Param("page") Page<TUserToken> page);

	@Select("<script>select  count(*) from sys_user_token" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TUserToken> page);

	@Select("select * from sys_user_token where token = #{token}")
    TUserToken findByToken(@Param("token") String token);

	@Select("select * from sys_user_token where uid = #{uid} and scope = #{scope}")
    TUserToken findByUserScope(@Param("uid") Long uid,@Param("scope") int scope);

    // ##remain#method#
}