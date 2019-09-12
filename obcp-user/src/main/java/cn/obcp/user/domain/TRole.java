package cn.obcp.user.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import cn.obcp.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * &#64;schema:"role"
  &#64;tablename:"sys_role"
  &#64;aliasName:"角色"
  &#64;description:"角色"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_role")
public class TRole extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547174L;
	
	public TRole() {
	}

	public TRole(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "ID", required = true, dataType = "bigint")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@ApiModelProperty(value = "资源编码", required = true, dataType = "String")
	@Column(name = "code", length = 100)
	@NotBlank(message="编码不能为空")
	private String code;
	
	@NotBlank(message="角色名称不能为空")
	@ApiModelProperty(value = "名称", required = true, dataType = "String")
	@Column(name = "name", length = 500, nullable = false)
	private String name;

	@ApiModelProperty(value = "状态", required = true, dataType = "smallint")
	@Column(name = "state", nullable = false)
	private Integer state;

	@ApiModelProperty(value = "描述", required = true, dataType = "String")
	@Column(name = "description", length = 500)
	private String description;

	@ApiModelProperty(value = "创建人", required = true, dataType = "bigint")
	@Column(name = "creator", length = 80)
	private Long creator;

	@ApiModelProperty(value = "修改人", required = true, dataType = "bigint")
	@Column(name = "updater", length = 80)
	private Long updater;

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
	@Transient
	private String resourceName;

	@Transient
	private int hasRole;
	
	@Transient
	private boolean checked;//是否绑定
	
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

	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @aliasName:"名称"
	 * 
	 */
	public void setName(String name) {
		this.name = name;
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

	public String getDescription() {
		return this.description;
	}

	/**
	 * @aliasName:"描述"
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
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

	
	public int getHasRole() {
		return hasRole;
	}

	public void setHasRole(int hasRole) {
		this.hasRole = hasRole;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @aliasName:"修改时间"
	 * 
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	// ##remain#method#

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TRole) {
			TRole role = (TRole) obj;
			if (id == null || role.id == null) {
				return false;
			}
			return (id.equals(role.id));
		}
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TRole role = (TRole) this;
		if (id == null) {
			return super.hashCode();
		}
		else {
			return id.hashCode();
		}
	}
}