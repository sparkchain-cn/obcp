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
 * &#64;schema:"region"
  &#64;tablename:"sys_region"
  &#64;aliasName:"行政区划"
  &#64;description:"行政区划"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_region")
public class TRegion extends BaseEntity implements java.io.Serializable {

	public TRegion() {
	}

	public TRegion(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "ID", required = true, dataType = "Long")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@ApiModelProperty(value = "上级区划", required = true, dataType = "Long")
	@Column(name = "parentid", length = 80, nullable = false)
	private Long parentid;

	@ApiModelProperty(value = "编码", required = true, dataType = "String")
	@Column(name = "code", length = 200)
	private String code;

	@ApiModelProperty(value = "名称", required = true, dataType = "String")
	@Column(name = "name", length = 500)
	private String name;

	@ApiModelProperty(value = "简称", required = true, dataType = "String")
	@Column(name = "shortname", length = 200)
	private String shortname;

	@ApiModelProperty(value = "全称", required = true, dataType = "String")
	@Column(name = "allname", length = 200)
	private String allname;

	@ApiModelProperty(value = "等级", required = true, dataType = "Integer")
	@Column(name = "level")
	private Integer level;

	@ApiModelProperty(value = "排序号", required = true, dataType = "Integer")
	@Column(name = "sortnum")
	private Integer sortnum;

	@ApiModelProperty(value = "更新人", required = true, dataType = "Integer")
	@Column(name = "updater")
	private Integer updater;

	@ApiModelProperty(value = "创建人", required = true, dataType = "Integer")
	@Column(name = "creator")
	private Integer creator;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false)
	private Date createTime;

	@ApiModelProperty(value = "修改时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime", nullable = false)
	private Date updateTime;

	@Transient
	private String parentName;
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

	public Long getParentid() {
		return this.parentid;
	}

	/**
	 * @aliasName:"上级区划"
	 * 
	 */
	public void setParentid(Long parentid) {
		this.parentid = parentid;
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

	public String getShortname() {
		return this.shortname;
	}

	/**
	 * @aliasName:"简称"
	 * @description:"简称"
	 * 
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getAllname() {
		return this.allname;
	}

	/**
	 * @aliasName:"全称"
	 * 
	 */
	public void setAllname(String allname) {
		this.allname = allname;
	}

	public Integer getLevel() {
		return this.level;
	}

	/**
	 * @aliasName:"等级"
	 * 
	 */
	public void setLevel(Integer level) {
		this.level = level;
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

	public Integer getUpdater() {
		return this.updater;
	}

	/**
	 * @aliasName:"更新人"
	 * 
	 */
	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public Integer getCreator() {
		return this.creator;
	}

	/**
	 * @aliasName:"创建人"
	 * @description:"创建人"
	 * 
	 */
	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @aliasName:"创建时间"
	 * 
	 */


	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * @aliasName:"修改时间"
	 * 
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	// ##remain#method#

	public boolean equals(Object obj) {
		if (obj instanceof TRegion) {
			TRegion region = (TRegion) obj;
			if (id == null || region.id == null) {
				return false;
			}
			return (id.equals(region.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TRegion region = (TRegion) this;
		if (id == null)
			return super.hashCode();
		else

			return id.hashCode();

	}

}