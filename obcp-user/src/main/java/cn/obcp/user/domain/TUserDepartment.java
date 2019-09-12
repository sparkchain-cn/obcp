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
 * &#64;tablename:"sys_user_department"
  &#64;aliasName:"sys_user_department"
  &#64;description:"sys_user_department"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_user_department")
public class TUserDepartment extends BaseEntity implements java.io.Serializable {

	public TUserDepartment() {
	}

	public TUserDepartment(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "id", required = true, dataType = "Long")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@ApiModelProperty(value = "用户id", required = true, dataType = "TUserExtend")
	@Column(name = "user_id", length = 80)
	private String userId;

	@ApiModelProperty(value = "部门id", required = true, dataType = "TDepartment")
	@Column(name = "dep_id", length = 80)
	private String depId;

	@ApiModelProperty(value = "状态", required = true, dataType = "Integer")
	@Column(name = "status")
	private Integer status;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;
	// ##remain#property#

	public Long getId() {
		return this.id;
	}

	/**
	 * @aliasName:"id"
	 * 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	/**
	 * @aliasName:"用户id"
	 * 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepId() {
		return this.depId;
	}

	/**
	 * @aliasName:"部门id"
	 * 
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}

	public Integer getStatus() {
		return this.status;
	}

	/**
	 * @aliasName:"状态"
	 * @description:"状态"
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	// ##remain#method#

	public boolean equals(Object obj) {
		if (obj instanceof TUserDepartment) {
			TUserDepartment userDepartment = (TUserDepartment) obj;
			if (id == null || userDepartment.id == null) {
				return false;
			}
			return (id.equals(userDepartment.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TUserDepartment userDepartment = (TUserDepartment) this;
		if (id == null)
			return super.hashCode();
		else

			return id.hashCode();

	}

}