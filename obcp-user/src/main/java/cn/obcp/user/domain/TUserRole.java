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
 * &#64;schema:"user"
  &#64;tablename:"sys_user_role"
  &#64;aliasName:"用户-角色"
  &#64;description:"用户-角色"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_user_role")
public class TUserRole extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547177L;

	public TUserRole() {
	}

	public TUserRole(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)

	@ApiModelProperty(value = "ID", required = true, dataType = "bigint")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@ApiModelProperty(value = "角色id", required = true, dataType = "TRole")
	@Column(name = "rid", length = 80)
	private Long rid;

	@ApiModelProperty(value = "用户id", required = true, dataType = "TUserExtend")
	@Column(name = "uid", length = 80)
	private Long uid;

	@ApiModelProperty(value = "状态", required = true, dataType = "smallint")
	@Column(name = "state")
	private Integer state;

	@ApiModelProperty(value = "排序号", required = true, dataType = "Integer")
	@Column(name = "sortnum")
	private Integer sortnum;

	@ApiModelProperty(value = "修改人", required = true, dataType = "bigint")
	@Column(name = "updater", length = 80)
	private Long updater;

	@ApiModelProperty(value = "创建人", required = true, dataType = "bigint")
	@Column(name = "creator", length = 80)
	private Long creator;

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

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getState() {
		return state;
	}

	/**
	 * @aliasName 状态
	 */
	public void setState(Integer state) {
		this.state = state;
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
		if (obj instanceof TUserRole) {
			TUserRole userRole = (TUserRole) obj;
			if (id == null || userRole.id == null) {
				return false;
			}
			return (id.equals(userRole.id));
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TUserRole userRole = (TUserRole) this;
		if (id == null) {
			return super.hashCode();
		} else {
			return id.hashCode();
		}
	}

}