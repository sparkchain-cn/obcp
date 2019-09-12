package cn.obcp.dict.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;

import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.dict.domain.TRegion;

/**
 * <pre>
 * 行政区划的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "regionMapper")
public interface RegionMapper extends BaseMapper<TRegion> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.parentid != null \"> and parentid = #{page.meta.parentid}</if>"
			+ " <if test=\"page.meta.code != null \"> and code = #{page.meta.code}</if>"
			+ " <if test=\"page.meta.name != null \"> and name = #{page.meta.name}</if>"
			+ " <if test=\"page.meta.shortname != null \"> and shortname = #{page.meta.shortname}</if>"
			+ " <if test=\"page.meta.allname != null \"> and allname = #{page.meta.allname}</if>"
			+ " <if test=\"page.meta.level != null \"> and level = #{page.meta.level}</if>"
			+ " <if test=\"page.meta.sortnum != null \"> and sortnum = #{page.meta.sortnum}</if>"
			+ " <if test=\"page.meta.updater != null \"> and updater = #{page.meta.updater}</if>"
			+ " <if test=\"page.meta.creator != null \"> and creator = #{page.meta.creator}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " <if test=\"page.meta.updatetime != null \"> and updatetime = #{page.meta.updatetime}</if>"
			+ " </where>";

	@Select("<script>select * from sys_region" + whereStr
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TRegion> findByPage(@Param("page") Page<TRegion> page);

	@Select("<script>select  count(*) from sys_region" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TRegion> page);

	@Select("<script>select  count(*) from sys_region" + "</script>")
	int countAll();
	// ##remain#method#
	@Select("<script>select name as parentName,id as parentid from sys_region where id = #{id}</script>")
	TRegion getParentById(Long id);

	@Select("SELECT r.*,t.name as parentName from sys_region r LEFT JOIN sys_region t on r.parentid = t.id where r.id=#{id}")
	TRegion getRegionById(@Param("id")Long id);
}