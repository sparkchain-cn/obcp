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
 * &#64;tablename:"sys_department"
  &#64;aliasName:"sys_department"
  &#64;description:"sys_department"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "bc_department")
public class TDepartment extends BaseEntity implements java.io.Serializable {

	public TDepartment() {
	}

	public TDepartment(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "id", required = true, dataType = "Long")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "pid", required = true, dataType = "Long")
	@Column(name = "pid", length = 80, nullable = false)
	private Long pid;

	@ApiModelProperty(value = "机构/组织id", required = true, dataType = "TOrg")
	@Column(name = "org_id", length = 80)
	private String orgId;

	@ApiModelProperty(value = "名称", required = true, dataType = "String")
	@Column(name = "name", length = 100)
	private String name;

	@ApiModelProperty(value = "编码", required = true, dataType = "String")
	@Column(name = "code", length = 80)
	private String code;

	@ApiModelProperty(value = "负责人", required = true, dataType = "String")
	@Column(name = "leader", length = 50)
	private String leader;

	@ApiModelProperty(value = "负责人号码", required = true, dataType = "String")
	@Column(name = "leader_number", length = 50)
	private String leaderNumber;

	@ApiModelProperty(value = "部门类型", required = true, dataType = "Integer")
	@Column(name = "type")
	private Integer type;

	@ApiModelProperty(value = "状态", required = true, dataType = "Integer")
	@Column(name = "status")
	private Integer status;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;

	@ApiModelProperty(value = "排序号", required = true, dataType = "Integer")
	@Column(name = "sortnum")
	private Integer sortnum;
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

	public Long getPid() {
		return pid;
	}
	/**
	 * @aliasName:"pid"
	 *
	 */
	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getOrgId() {
		return this.orgId;
	}

	/**
	 * @aliasName:"机构/组织id"
	 * 
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public String getCode() {
		return this.code;
	}

	/**
	 * @aliasName:"编码"
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getLeader() {
		return this.leader;
	}

	/**
	 * @aliasName:"负责人"
	 * 
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getLeaderNumber() {
		return this.leaderNumber;
	}

	/**
	 * @aliasName:"负责人号码"
	 * 
	 */
	public void setLeaderNumber(String leaderNumber) {
		this.leaderNumber = leaderNumber;
	}

	public Integer getType() {
		return this.type;
	}

	/**
	 * @aliasName:"部门类型"
	 * @description:"部门类型"
	 * 
	 */
	public void setType(Integer type) {
		this.type = type;
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
	// ##remain#method#

	public boolean equals(Object obj) {
		if (obj instanceof TDepartment) {
			TDepartment department = (TDepartment) obj;
			if (id == null || department.id == null) {
				return false;
			}
			return (id.equals(department.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TDepartment department = (TDepartment) this;
		if (id == null)
			return super.hashCode();
		else

			return id.hashCode();

	}

}