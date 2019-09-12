package cn.obcp.dict.domain;

import java.util.Date;

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

/**
 * <pre>
 * &#64;tablename:"sys_dict"
 * &#64;aliasName:"字典"
 * &#64;description:"字典"
 *
 * 该代码通过mgicode代码生成器生成
 *
 * </pre>
 */
// @LogicalDelete
@Entity
@Table(name = "sys_dict")
public class TDict extends BaseEntity implements java.io.Serializable {

    public TDict() {
    }

    public TDict(String code) {
        this.code = code;
    }

    @Id
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "字典编码", required = true, dataType = "String")
    @Column(name = "code", length = 255, nullable = false)
    private String code;

    @ApiModelProperty(value = "父级", required = true, dataType = "String")
    @Column(name = "parent", length = 255, nullable = false)
    private String parent;

    @ApiModelProperty(value = "字典名称", required = true, dataType = "String")
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @ApiModelProperty(value = "对应值", required = true, dataType = "String")
    @Column(name = "vals", length = 60)
    private String vals;

    @ApiModelProperty(value = "备注", required = true, dataType = "String")
    @Column(name = "remarks", length = 255)
    private String remarks;

    @ApiModelProperty(value = "排序号", required = true, dataType = "Integer")
    @Column(name = "sortnum")
    private Integer sortnum;

    @ApiModelProperty(value = "状态(-1删除0禁用1启用)", required = true, dataType = "Integer")
    @Column(name = "status", nullable = false)
    private Integer status;

    @ApiModelProperty(value = "创建人", required = true, dataType = "Integer")
    @Column(name = "creator")
    private Integer creator;

    @ApiModelProperty(value = "更新人", required = true, dataType = "Integer")
    @Column(name = "updater", length = 80)
    private Integer updater;

    @ApiModelProperty(value = "层级编码", required = true, dataType = "Integer")
    @Column(name = "levecode")
    private Integer levecode;

    @ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", nullable = false)
    private Date createTime;

    @ApiModelProperty(value = "修改时间", required = true, dataType = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", nullable = false)
    private Date updateTime;

    @Transient
    private String statusName;

    @Transient
    private String parentName;
    // ##remain#property#

    public String getCode() {
        return this.code;
    }

    /**
     * @aliasName:"字典编码"
     * @description:"字典编码"
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String getParent() {
        return this.parent;
    }

    /**
     * @aliasName:"父级"
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    /**
     * @aliasName:"字典名称"
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getVals() {
        return this.vals;
    }

    /**
     * @aliasName:"对应值"
     */
    public void setVals(String vals) {
        this.vals = vals;
    }

    public String getRemarks() {
        return this.remarks;
    }

    /**
     * @aliasName:"备注"
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSortnum() {
        return this.sortnum;
    }

    /**
     * @aliasName:"排序号"
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public Integer getStatus() {
        return this.status;
    }

    /**
     * @aliasName:"状态"
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreator() {
        return this.creator;
    }

    /**
     * @aliasName:"创建人"
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getUpdater() {
        return this.updater;
    }

    /**
     * @aliasName:"更新人"
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public Integer getLevecode() {
        return levecode;
    }

    /**
     * @aliasName:"层级编码"
     * @description:"层级编码"
     */
    public void setLevecode(Integer levecode) {
        this.levecode = levecode;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * @aliasName:"创建时间"
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * @aliasName:"修改时间"
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    // ##remain#method#

    public boolean equals(Object obj) {
        if (obj instanceof TDict) {
            TDict dict = (TDict) obj;
            if (code == null || dict.code == null) {
                return false;
            }
            return (code.equals(dict.code));
        }
        return super.equals(obj);
    }

    public int hashCode() {
        // TDict dict = (TDict) this;
        if (code == null)
            return super.hashCode();
        else

            return code.hashCode();

    }

}