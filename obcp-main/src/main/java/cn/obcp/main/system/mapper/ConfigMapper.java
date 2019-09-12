package cn.obcp.main.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
// ##remain#import#
import cn.obcp.main.system.domain.TConfig;

/**
 * <pre>
 * 系统配置的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "configMapper")
public interface ConfigMapper extends BaseMapper<TConfig> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.name != null \"> and name like concat('%', #{page.meta.name},'%')</if>"
			+ " <if test=\"page.meta.code != null \"> and code like concat('%', #{page.meta.code},'%')</if>"
			+ " <if test=\"page.meta.val != null \"> and val = #{page.meta.val}</if>"
			+ " <if test=\"page.meta.group != null \"> and group = #{page.meta.group}</if>"
			+ " <if test=\"page.meta.remark != null \"> and remark = #{page.meta.remark}</if>"
			+ " <if test=\"page.meta.creator != null \"> and creator = #{page.meta.creator}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.updater != null \"> and updater = #{page.meta.updater}</if>"
			+ " <if test=\"page.meta.updatetime != null \"> and updatetime = #{page.meta.updatetime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_config" + whereStr
			+ "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TConfig> findByPage(@Param("page") Page<TConfig> page);

	@Select("<script>select sc.*,ue.nickname as userName from sys_config sc left join sys_user ue on sc.creator = ue.id where 1 = 1 " 
			+ " <if test=\"page.meta.keyVal != null \"> and ( sc.name like concat('%', #{page.meta.keyVal},'%') or sc.code like concat('%', #{page.meta.keyVal},'%') )</if>"
			+ " <if test=\"page.meta.groups != null \"> and sc.groups like concat('%', #{page.meta.groups},'%')</if>"
			+ " order by sc.updatetime desc"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TConfig> findByPageList(@Param("page") Page<TConfig> page);

	
	@Select("<script>select COUNT(*) from (select sc.*,ue.nickname as userName from sys_config sc left join sys_user ue on sc.creator = ue.id where 1 = 1 " 
			+ " <if test=\"page.meta.keyVal != null \">and ( sc.name like concat('%', #{page.meta.keyVal},'%') or sc.code like concat('%', #{page.meta.keyVal},'%') )</if>"
			+ " <if test=\"page.meta.groups != null \">and sc.groups like concat('%', #{page.meta.groups},'%')</if>"			
			+ ") t"
			+ "</script>")
	int countByPage(@Param("page") Page<TConfig> page);
	// ##remain#method#

	@Select("select * from sys_config where code = #{code} ")
	TConfig findByCode(@Param("code") String code);
}