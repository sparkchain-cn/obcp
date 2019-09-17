package cn.obcp.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;

import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.dict.domain.TDict;

/**
 * <pre>
 * 字典的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "dictMapper")
public interface DictMapper extends BaseMapper<TDict> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.code != null and page.meta.code != ''\"> and code = #{page.meta.code}</if>"
			+ " <choose>"
			+ " <when test=\"page.meta.parent != null and page.meta.parent != ''\"> "
			+ " and parent = #{page.meta.parent} "
			+ " </when> "
			+ " <otherwise>"
			+ " and parent is null "
			+ " </otherwise>"
			+ " </choose>"
			+ " <if test=\"page.meta.name != null and page.meta.name != '' \"> and name like concat('%',#{page.meta.name},'%')</if>"
			+ " <if test=\"page.meta.vals != null and page.meta.vals != '' \"> and vals = #{page.meta.vals}</if>"
			+ " <if test=\"page.meta.remarks != null and page.meta.remarks != '' \"> and remarks = #{page.meta.remarks}</if>"
			+ " <if test=\"page.meta.sortnum != null and page.meta.sortnum != '' \"> and sortnum = #{page.meta.sortnum}</if>"
			+ " <if test=\"page.meta.status != null and page.meta.status != '' \"> and status = #{page.meta.status}</if>"
			+ " <if test=\"page.meta.creator != null and page.meta.creator != '' \"> and creator = #{page.meta.creator}</if>"
			+ " <if test=\"page.meta.updater != null and page.meta.updater != '' \"> and updater = #{page.meta.updater}</if>"
			+ " <if test=\"page.meta.levecode != null and page.meta.levecode != '' \"> and levecode = #{page.meta.levecode}</if>"
			+ " <if test=\"page.meta.createTime != null and page.meta.createTime != '' \"> and createTime = #{page.meta.createTime}</if>"
			+ " <if test=\"page.meta.updateTime != null and page.meta.updateTime != '' \"> and updateTime = #{page.meta.updateTime}</if>"
			+ " </where>";

	
	@Insert("insert INTO sys_dict  value(#{dict.code},#{dict.parent},#{dict.name},#{dict.vals},#{dict.remarks},#{dict.sortnum}"
			+ ",#{dict.status},#{dict.creator},#{dict.updater},#{dict.levecode},#{dict.createTime},#{dict.updateTime}) ")
	int save(@Param("dict") TDict dict);
	
	@Select("<script>select * from sys_dict " + whereStr + "and status != -1"
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TDict> findByPage(@Param("page") Page<TDict> page);

	@Select("<script>select  count(*) from sys_dict" + whereStr + "</script>")
	int countByPage(@Param("page") Page<TDict> page);

	@Select("<script>select  count(*) from sys_dict" + "</script>")
	int countAll();
	
	@Select("<script>select * from sys_dict where status > -1 </script>")
	List<TDict> getDictAll();

	@Select("<script>select * from sys_dict" + " where code = #{code}" + "</script>")
	TDict findByCode(@Param("code")String code);

	@Select("<script>select name from sys_dict" + " where code = #{code}" + "</script>")
	String getParentName(@Param("code")String code);

	@Select("<script>select name as parentName,code as parent from sys_dict where 1=1 and code = #{code}</script>")
    TDict getParentById(String code);
	// ##remain#method#

	@Select("SELECT r.*,t.name as parentName from sys_dict r LEFT JOIN sys_dict t on r.parent = t.code and t.status > -1 where 1 = 1  and r.code=#{code}")
	TDict getDictById(@Param("code")String code);

}