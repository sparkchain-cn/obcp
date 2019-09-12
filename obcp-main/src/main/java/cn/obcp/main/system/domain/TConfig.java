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
 * &#64;tablename:"sys_config"
  &#64;aliasName:"系统配置"
  &#64;description:"系统配置"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_config")
public class TConfig extends BaseEntity implements java.io.Serializable {

	public TConfig() {
	}

	public TConfig(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "id", required = true, dataType = "bigint")
	@Column(name = "id", length = 80)
	private Long id;

	@ApiModelProperty(value = "名称", required = true, dataType = "String")
	@Column(name = "name", length = 200)
	private String name;

	@ApiModelProperty(value = "编码", required = true, dataType = "String")
	@Column(name = "code", length = 500)
	private String code;

	@ApiModelProperty(value = "值", required = true, dataType = "String")
	@Column(name = "val", columnDefinition = "CLOB")
	private String val;

	@ApiModelProperty(value = "分组", required = true, dataType = "String")
	@Column(name = "groups", length = 200)
	private String groups;

	@ApiModelProperty(value = "说明", required = true, dataType = "String")
	@Column(name = "remark", length = 2000)
	private String remark;

	@ApiModelProperty(value = "创建人", required = true, dataType = "String")
	@Column(name = "creator", length = 80)
	private String creator;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;

	@ApiModelProperty(value = "修改人", required = true, dataType = "String")
	@Column(name = "updater", length = 80)
	private String updater;

	@ApiModelProperty(value = "修改时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	private Date updatetime;
	// ##remain#property#

	@Transient
	private String userName;


	public Long getId() {
		return id;
	}
	/**
	 *  
	 * @aliasName:"ID"
	 */
	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}
	/**
	 * @aliasName:"名称"
	 * @description:"名称"
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
	 * @description:"编码"
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getVal() {
		return this.val;
	}

	/**
	 * @aliasName:"值"
	 * @description:"值"
	 * 
	 */
	public void setVal(String val) {
		this.val = val;
	}

	public String getGroups() {
		return groups;
	}
	/**
	 * @aliasName:"分组"
	 * 
	 */
	public void setGroups(String groups) {
		this.groups = groups;
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
	 * @description:"创建人"
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
	 * @description:"创建时间"
	 * 
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUpdater() {
		return this.updater;
	}

	/**
	 * @aliasName:"修改人"
	 * @description:"修改人"
	 * 
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	 * @aliasName:"修改时间"
	 * @description:"修改时间"
	 * 
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
	
	// ##remain#method#

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean equals(Object obj) {
		if (obj instanceof TConfig) {
			TConfig config = (TConfig) obj;
			if (id == null || config.id == null) {
				return false;
			}
			return (id.equals(config.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TConfig config = (TConfig) this;
		if (id == null)
			return super.hashCode();
		else

			return id.hashCode();

	}

}