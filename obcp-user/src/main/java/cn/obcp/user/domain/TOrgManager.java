package cn.obcp.user.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import cn.obcp.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "bc_org_manager")
public class TOrgManager extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 8656128222714547173L;

    public TOrgManager() {
    }

    @Id
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "ID", required = true, dataType = "Long")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "机构id", required = true, dataType = "Long")
    @Column(name = "org_id", length = 80)
    private String orgId;

    @ApiModelProperty(value = "用户id", required = true, dataType = "Long")
    @Column(name = "user_id", length = 80)
    private String userId;

    @ApiModelProperty(value = "状态", required = true, dataType = "Integer")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createtime;

    @ApiModelProperty(value = "修改时间", required = true, dataType = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updatetime;

    @Transient
    private String userName;

    @Transient
    private String orgName;

    @Transient
    private String userMobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TOrgManager that = (TOrgManager) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(orgId, that.orgId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createtime, that.createtime) &&
                Objects.equals(updatetime, that.updatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orgId, status, createtime, updatetime);
    }
}
