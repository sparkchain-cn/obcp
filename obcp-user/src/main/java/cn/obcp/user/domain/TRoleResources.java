package cn.obcp.user.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import cn.obcp.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * &#64;schema:"role"
  &#64;tablename:"sys_role_resources"
  &#64;aliasName:"角色-资源"
  &#64;description:"角色-资源"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_role_resources")
public class TRoleResources extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547175L;
	public TRoleResources() {
	}

	public TRoleResources(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	
	@ApiModelProperty(value = "ID", required = true, dataType = "bigint")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	
	@ApiModelProperty(value = "角色Id", required = true, dataType = "bigint")
	@Column(name = "rid", length = 80)
	private Long rid;

	@ApiModelProperty(value = "状态", required = true, dataType = "smallint")
	@Column(name = "state")
	private Integer state;
	
	
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "资源Id", required = true, dataType = "bigint")
	@Column(name = "res_id", length = 80)
	private Long resId;

	@ApiModelProperty(value = "修改人", required = true, dataType = "bigint")
	@Column(name = "updater", length = 80)
	private Long updater;

	@ApiModelProperty(value = "创建人", required = true, dataType = "bigint")
	@Column(name = "creator", length = 80)
	private Long creator;

	@ApiModelProperty(value = "排序号", required = true, dataType = "Integer")
	@Column(name = "sortnum")
	private Integer sortnum;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;

	@ApiModelProperty(value = "修改时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	private Date updatetime;
	// ##remain#property#

	public Long getId() {
		return this.id;
	}

	/**
	 * @aliasName:"ID"
	 * 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}



	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Integer getSortnum() {
		return this.sortnum;
	}

	/**
	 * @aliasName:"排序号"
	 * 
	 */
	public void setSortnum(Integer sortnum) {
		this.sortnum = sortnum;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * @aliasName:"创建时间"
	 * 
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	 * @aliasName:"修改时间"
	 * 
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	// ##remain#method#
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TRoleResources) {
			TRoleResources roleResources = (TRoleResources) obj;
			if (id == null || roleResources.id == null) {
				return false;
			}
			return (id.equals(roleResources.id));
		}
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TRoleResources roleResources = (TRoleResources) this;
		if (id == null) {
			return super.hashCode();
		}
		else {
			return id.hashCode();
		}
	}
}