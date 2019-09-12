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
 * &#64;schema:"res"
  &#64;treeself  @tablename:"sys_resources"
  &#64;aliasName:"资源"
  &#64;description:"资源"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_resources")
public class TResources extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547173L;
	
	public TResources() {
	}

	public TResources(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "ID", required = true, dataType = "Long")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ApiModelProperty(value = "资源编码", required = true, dataType = "String")
	@Column(name = "code", length = 100)
	@NotBlank(message="编码不能为空")
	private String code;

	@ApiModelProperty(value = "资源名称", required = true, dataType = "String")
	@Column(name = "name", length = 500)
	@NotBlank(message="名称不能为空")
	private String name;

	@ApiModelProperty(value = "资源别名", required = true, dataType = "String")
	@Column(name = "alias_name", length = 500)
	private String aliasName;

	// 页面文件夹：1  页面：2  数据接口：3 
	@ApiModelProperty(value = "资源类型（DICT)", required = true, dataType = "String")
	@Column(name = "res_type_code", length = 50)
	/*@NotBlank(message="资源类型不能为空")*/
	private String resTypeCode;

	@ApiModelProperty(value = "资源权限编码", required = true, dataType = "String")
	@Column(name = "permissioncode", length = 500)
	private String permissioncode;

	@ApiModelProperty(value = "所在业务系统ID", required = true, dataType = "String")
	@Column(name = "systemid", length = 50)
	private String systemid;

	//  -1：已删除 ;1:正常  2:禁用  
	@ApiModelProperty(value = "状态", required = true, dataType = "smallint")
	@Column(name = "state")
	private Integer state;

	@ApiModelProperty(value = "图标路径", required = true, dataType = "String")
	@Column(name = "icon", length = 500)
	private String icon;

	@ApiModelProperty(value = "资源路径", required = true, dataType = "String")
	@Column(name = "path", length = 500)
	private String path;

	@ApiModelProperty(value = "描述", required = true, dataType = "String")
	@Column(name = "description", length = 1500)
	private String description;

	@ApiModelProperty(value = "资源层级编码", required = true, dataType = "String")
	@Column(name = "levelcode", length = 500)
	private String levelcode;

	@ApiModelProperty(value = "父资源ID", required = true, dataType = "bigint")
	@Column(name = "parentid", length = 80)
	private Long parentid;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;

	@ApiModelProperty(value = "修改时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	private Date updatetime;

	@ApiModelProperty(value = "修改人", required = true, dataType = "bigint")
	@Column(name = "updater")
	private Long updater;

	@ApiModelProperty(value = "创建人", required = true, dataType = "bigint")
	@Column(name = "creator")
	private Long creator;

	@ApiModelProperty(value = "排序号", required = true, dataType = "Integer")
	@Column(name = "sortnum")
	private Integer sortnum;

	// 1：页面端 2： 服务端 
	@ApiModelProperty(value = "资源所在分类（DICT)", required = true, dataType = "String")
	@Column(name = "res_addr_type", length = 500)
	private String resAddrType;
	
	//1:控制台资源   2:应用管理端资源
	@ApiModelProperty(value = "资源类型", required = true, dataType = "String")
	@Column(name="resourceGroup")
	private Integer resourceGroup;
	// ##remain#property#
	// 父级资源名称
	@Transient
	private String parentName;
	//类型名称
	@Transient 
	private String typeName;
	
	@Transient
	private Integer hasRole;
	

	private boolean checked;//是否绑定
	
	
	
	
	public Integer getResourceGroup() {
		return resourceGroup;
	}

	public void setResourceGroup(Integer resourceGroup) {
		this.resourceGroup = resourceGroup;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

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

	public String getName() {
		return this.name;
	}

	/**
	 * @name @aliasName:"资源名称"
	 * @description:"资源名称"
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getAliasName() {
		return this.aliasName;
	}

	/**
	 * @aliasName:"资源别名"
	 * 
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getResTypeCode() {
		return this.resTypeCode;
	}

	/**
	 * @dict:"RESTYPE"
	 * @combobox @aliasName:"资源类型（DICT)"
	 * @description:"01:页面，02：页面组件，03：数据
	 * 
	 * 									"
	 * 
	 */
	public void setResTypeCode(String resTypeCode) {
		this.resTypeCode = resTypeCode;
	}

	public String getPermissioncode() {
		return this.permissioncode;
	}

	/**
	 * @dict:"RESADDRTYPE"
	 * @combobox @aliasName:"资源权限编码"
	 * @description:"资源权限代码 : v1:user:list,多个使用英文逗号隔开
	 * 
	 *                      "
	 * 
	 */
	public void setPermissioncode(String permissioncode) {
		this.permissioncode = permissioncode;
	}

	public String getSystemid() {
		return this.systemid;
	}

	/**
	 * @combobox @aliasName:"所在业务系统ID"
	 * @description:" dd "
	 * 
	 */
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	

	public Integer getState() {
		return state;
	}
	/**
	 * @aliasName:"状态"
	 * 
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	public String getIcon() {
		return this.icon;
	}

	/**
	 * @aliasName:"图标路径"
	 * 
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return this.path;
	}

	/**
	 * @aliasName:"资源路径"
	 * 
	 */
	public void setPath(String path) {
		this.path = path;
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

	public String getLevelcode() {
		return this.levelcode;
	}

	/**
	 * @aliasName:"资源层级编码"
	 * @description:"资源层级编码"
	 * 
	 */
	public void setLevelcode(String levelcode) {
		this.levelcode = levelcode;
	}

	public Long getParentid() {
		return this.parentid;
	}

	/**
	 * @aliasName:"父资源ID"
	 * 
	 */
	public void setParentid(Long parentid) {
		this.parentid = parentid;
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

	public String getResAddrType() {
		return this.resAddrType;
	}

	/**
	 * @dict:"RESADDRTYPE"
	 * @combobox @aliasName:"资源所在分类（DICT)"
	 * @description:"01:管理后台，02：前台
	 * 
	 * 							"
	 * 
	 */
	public void setResAddrType(String resAddrType) {
		this.resAddrType = resAddrType;
	}
	
	
	// ##remain#method#

	public Integer getHasRole() {
		return hasRole;
	}

	public void setHasRole(Integer hasRole) {
		this.hasRole = hasRole;
	}

	
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TResources) {
			TResources resources = (TResources) obj;
			if (id == null || resources.id == null) {
				return false;
			}
			return (id.equals(resources.id));
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TResources resources = (TResources) this;
		if (id == null) {
			return super.hashCode();
		}
		else {
			return id.hashCode();
		}
	}
}