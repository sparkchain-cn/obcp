package cn.obcp.user.mapper;

import cn.obcp.base.Page;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.user.domain.TOrgManager;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "orgManagerMapper")
public interface OrgManagerMapper extends BaseMapper<TOrgManager> {

    public static String whereStr = "<where> 1= 1 "
            + " <if test=\"page.meta.id != null \"> and org.id = #{page.meta.id}</if>"
            + " <if test=\"page.meta.name != null \"> and org.name like concat(#{page.meta.name},'%')</if>"
            + " <if test=\"page.meta.code != null \"> and org.code like concat(#{page.meta.code},'%')</if>"
            + " <if test=\"page.meta.tel != null \"> and org.tel = #{page.meta.tel}</if>"
            + " <if test=\"page.meta.supervisor != null \"> org.and supervisor = #{page.meta.supervisor}</if>"
            + " </where>";

    @Select("<script>select * from bc_organization org left join sys_user u on u.id = org.supervisor " + whereStr
            + "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
            + "<if test=\"page.needPage ==true \"> limit #{page.meta.skip},#{page.size} </if>" + "</script>")
    com.github.pagehelper.Page<TOrgManager> findByPage(@Param("page") Page<TOrgManager> page);

    @Select("<script>SELECT bom.*,u.nickname as userName,u.mobile as userMobile, org.`name` as orgName FROM  bc_org_manager bom LEFT JOIN sys_user u on bom.user_id = u.id LEFT JOIN bc_organization org on bom.org_id = org.id "
            + " <if test=\"page.meta.orgId != null \"> and org.org_id = #{page.meta.orgId}</if>"
            + " <if test=\"page.meta.userName != null \"> and u.nick_name like concat(#{page.meta.userName},'%')</if>"
            + " <if test=\"page.meta.orgName != null \"> and org.name like concat(#{page.meta.orgName},'%')</if>"
            + "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
            + "<if test=\"page.needPage ==true \"> limit #{page.meta.skip},#{page.size} </if>"
            +"</script>")
    List<TOrgManager> findByOrgManagerPage(@Param("page") Page<TOrgManager> page);

    @Select("<script>SELECT count(1) FROM  bc_org_manager bom LEFT JOIN sys_user u on bom.user_id = u.id LEFT JOIN bc_organization org on bom.org_id = org.id "
            + " <if test=\"page.meta.orgId != null \"> and org.org_id = #{page.meta.orgId}</if>"
            + " <if test=\"page.meta.userName != null \"> and u.nick_name like concat(#{page.meta.userName},'%')</if>"
            + " <if test=\"page.meta.orgName != null \"> and org.name like concat(#{page.meta.orgName},'%')</if>"
            + "<if test=\"page.orderStr !=null \"> order by #{page.orderStr} </if>"
            + "<if test=\"page.needPage ==true \"> limit #{page.meta.skip},#{page.size} </if>"
            +"</script>")
    Integer findCountByOrgManagerPage(@Param("page") Page<TOrgManager> page);

    @Select("<script>select  count(*) from bc_organization org " + whereStr + "</script>")
    int countByPage(@Param("page") Page<TOrgManager> page);

    @Insert("<script>insert into bc_org_manager values " +
                "<foreach collection='orgManagers' item='item' index='index' separator=','>"+
                "(#{item.id},#{item.orgId},#{item.userId},#{item.status},#{item.createtime},#{item.updatetime})"+
                "</foreach>" +
            "</script>")
    boolean addManager(@Param("orgManagers") List<TOrgManager> orgManagers,@Param("orgId") Long orgId);
}
