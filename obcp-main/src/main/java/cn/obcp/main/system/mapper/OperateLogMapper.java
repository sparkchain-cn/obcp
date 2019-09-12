package cn.obcp.main.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.main.system.domain.TOperateLog;

/**
 * <pre>
 * 操作记录的mapper接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

@Mapper
@Component(value = "operateLogMapper")
public interface OperateLogMapper extends BaseMapper<TOperateLog> {

	public static String whereStr = "<where> 1= 1 "
			+ " <if test=\"page.meta.id != null \"> and id = #{page.meta.id}</if>"
			+ " <if test=\"page.meta.module != null \"> and module = #{page.meta.module}</if>"
			+ " <if test=\"page.meta.refval != null \"> and refval = #{page.meta.refval} </if>"
			+ " <if test=\"page.meta.type != null \"> and type = #{page.meta.type}</if>"
			+ " <if test=\"page.meta.val != null \"> and val = #{page.meta.val}</if>"
			+ " <if test=\"page.meta.preval != null \"> and preval = #{page.meta.preval}</if>"
			+ " <if test=\"page.meta.remark != null and page.meta.remark != '' \"> and remark like concat('%',#{page.meta.remark},'%')</if>"
			+ " <if test=\"page.meta.creator != null \"> and creator = #{page.meta.creator}</if>"
			+ " <if test=\"page.meta.createtime != null \"> and createtime = #{page.meta.createtime}</if>"
			+ " </where>";

	@Select("<script>SELECT sol.*,u.nickname as userName,u.mobile as userMobile FROM sys_operate_log sol LEFT JOIN sys_user u on sol.creator = u.id where 1 = 1 " 
			+ " <if test=\"page.meta.module != null and page.meta.module != '' \"> and sol.module = #{page.meta.module}</if>"
			+ " <if test=\"page.meta.refval != null \"> and sol.refval = #{page.meta.refval} </if>"
			+ " <if test=\"page.meta.remark != null and page.meta.remark != '' \"> and sol.remark like concat('%',#{page.meta.remark},'%')</if>"
			+ " <if test=\"page.meta.start and page.meta.start != '' != null \"> and DATE_FORMAT(createtime, '%Y-%m-%d') &gt;= #{page.meta.start} </if>"
			+ " <if test=\"page.meta.end != null and page.meta.end != '' \"> and DATE_FORMAT(createtime, '%Y-%m-%d') &lt;= #{page.meta.end} </if>"
			+ " order by sol.createtime desc "
			+ "<if test=\"page.needPage ==true \"> limit #{page.startRow},#{page.size} </if>" + "</script>")
	List<TOperateLog> findByPage(@Param("page") Page<TOperateLog> page);

	@Select("<script>select count(*) from (SELECT sol.id FROM sys_operate_log sol LEFT JOIN sys_user u on sol.creator = u.id where 1 = 1 " 
			+ " <if test=\"page.meta.module != null and page.meta.module != '' \"> and sol.module = #{page.meta.module}</if>"
			+ " <if test=\"page.meta.refval != null \"> and sol.refval = #{page.meta.refval} </if>"
			+ " <if test=\"page.meta.remark != null and page.meta.remark != '' \"> and sol.remark like concat('%',#{page.meta.remark},'%')</if>"
			+ " <if test=\"page.meta.start and page.meta.start != '' != null \"> and DATE_FORMAT(createtime, '%Y-%m-%d') &gt;= #{page.meta.start} </if>"
			+ " <if test=\"page.meta.end != null and page.meta.end != '' \"> and DATE_FORMAT(createtime, '%Y-%m-%d') &lt;= #{page.meta.end} </if>"
			+ ") t </script>")
	int countByPage(@Param("page") Page<TOperateLog> page);
	// ##remain#method#
}