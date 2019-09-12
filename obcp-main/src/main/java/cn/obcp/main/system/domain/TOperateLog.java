package cn.obcp.main.system.domain;

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
 * &#64;tablename:"sys_operate_log"
  &#64;aliasName:"操作记录"
  &#64;description:"操作记录"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_operate_log")
public class TOperateLog extends BaseEntity implements java.io.Serializable {

	public TOperateLog() {
	}

	public TOperateLog(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "id", required = true, dataType = "Long")
	@Column(name = "id")
	private Long id;

	@ApiModelProperty(value = "模块", required = true, dataType = "Integer")
	@Column(name = "module")
	private Integer module;

	@ApiModelProperty(value = "关联属性值", required = true, dataType = "String")
	@Column(name = "refval", length = 500)
	private String refval;

	@ApiModelProperty(value = "操作类型", required = true, dataType = "Integer")
	@Column(name = "type")
	private Integer type;

	@ApiModelProperty(value = "日志级别", required = true, dataType = "Integer")
	@Column(name = "lever")
	private Integer lever;
	
	@ApiModelProperty(value = "值", required = true, dataType = "String")
	@Column(name = "val", length = 500)
	private String val;

	@ApiModelProperty(value = "原值", required = true, dataType = "String")
	@Column(name = "preval", length = 500)
	private String preval;

	@ApiModelProperty(value = "说明", required = true, dataType = "String")
	@Column(name = "remark", length = 500)
	private String remark;

	@ApiModelProperty(value = "创建人", required = true, dataType = "String")
	@Column(name = "creator", length = 80)
	private String creator;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;
	// ##remain#property#

	@Transient
	private String userName;
	
	@Transient
	private String userMobile;
	
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

	public Integer getModule() {
		return this.module;
	}

	/**
	 * @aliasName:"模块"
	 * @description:"模块"
	 * 
	 */
	public void setModule(Integer module) {
		this.module = module;
	}

	public String getRefval() {
		return this.refval;
	}

	/**
	 * @aliasName:"关联属性值"
	 * @description:"关联属性值"
	 * 
	 */
	public void setRefval(String refval) {
		this.refval = refval;
	}

	public Integer getType() {
		return this.type;
	}

	/**
	 * @aliasName:"操作类型"
	 * @description:"操作类型"
	 * 
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public String getVal() {
		return this.val;
	}

	/**
	 * @aliasName:"值"
	 * 
	 */
	public void setVal(String val) {
		this.val = val;
	}

	public String getPreval() {
		return this.preval;
	}

	/**
	 * @aliasName:"原值"
	 * @description:"原值"
	 * 
	 */
	public void setPreval(String preval) {
		this.preval = preval;
	}

	public String getRemark() {
		return this.remark;
	}

	/**
	 * @aliasName:"说明"
	 * 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	/**
	 * @aliasName:"创建人"
	 * 
	 */
	public void setCreator(String creator) {
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
	
	
	// ##remain#method#

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}
	
	public String getUserName() {
		return userName;
	}
	// 创建人昵称
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public boolean equals(Object obj) {
		if (obj instanceof TOperateLog) {
			TOperateLog operateLog = (TOperateLog) obj;
			if (id == null || operateLog.id == null) {
				return false;
			}
			return (id.equals(operateLog.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TOperateLog operateLog = (TOperateLog) this;
		if (id == null)
			return super.hashCode();
		else

			return id.hashCode();

	}

}