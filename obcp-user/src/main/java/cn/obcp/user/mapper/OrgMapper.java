package cn.obcp.user.mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import cn.obcp.user.domain.TOrg;
import org.apache.ibatis.annotations.Select;

//import cn.obcp.user.BaseMapper;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * sys_org的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "orgMapper")
public interface OrgMapper extends BaseMapper<TOrg> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and org.id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.name != null \"> and org.name like concat(#{page.meta.name},'%')</if>"
			+ " <if test=\"page.meta.code != null \"> and org.code like concat(#{page.meta.code},'%')</if>"
			+ " <if test=\"page.meta.tel != null \"> and org.tel = #{page.meta.tel}</if>"
			+ " <if test=\"page.meta.supervisor != null \"> org.and supervisor = #{page.meta.supervisor}</if>"
		    + " </where>";

	@Select("<script>select org.*,u.nickName as userName from bc_organization org left join sys_user u on u.id = org.supervisor " + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.meta.skip},#{page.size} </if>" + "</script>")
	Page<TOrg> findByPage(@Param("page") Page<TOrg> page);

	@Select("<script>select  count(*) from bc_organization org " + whereStr + "</script>")
	int countByPage(@Param("page") Page<TOrg> page);

	@Select("<script>select * from bc_organization where supervisor = #{supervisor}</script>")
    TOrg findBySupervisor(@Param("supervisor") Long supervisor);
    // ##remain#method#
}
