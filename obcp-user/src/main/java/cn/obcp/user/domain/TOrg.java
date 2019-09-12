package cn.obcp.user.domain;

import java.util.Date;
import java.util.Objects;

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
 * &#64;tablename:"sys_org"
  &#64;aliasName:"sys_org"
  &#64;description:"sys_org"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "bc_organization")
public class TOrg extends BaseEntity implements java.io.Serializable {

	public TOrg() {
	}

	public TOrg(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "id", required = true, dataType = "Long")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@NotBlank(message = "名称不能为空")
	@ApiModelProperty(value = "名称", required = true, dataType = "String")
	@Column(name = "name", length = 100)
	private String name;

	@NotBlank(message = "编码不能为空")
	@ApiModelProperty(value = "编码", required = true, dataType = "String")
	@Column(name = "code", length = 80)
	private String code;

	@NotBlank(message = "机构负责人不能为空")
	@ApiModelProperty(value = "负责人", required = true, dataType = "Long")
	@Column(name = "supervisor", length = 80)
	private String supervisor;

	@NotBlank(message = "机构联系电话不能为空")
	@ApiModelProperty(value = "号码", required = true, dataType = "String")
	@Column(name = "tel", length = 50)
	private String tel;

	@ApiModelProperty(value = "logo", required = true, dataType = "String")
	@Column(name = "logo", length = 50)
	private String logo;

	@ApiModelProperty(value = "地址", required = true, dataType = "String")
	@Column(name = "addr", length = 100)
	private String addr;

	@ApiModelProperty(value = "营业执照", required = true, dataType = "String")
	@Column(name = "license", length = 100)
	private String license;

	@ApiModelProperty(value = "所属行业", required = true, dataType = "String")
	@Column(name = "industry", length = 100)
	private String industry;

	@ApiModelProperty(value = "状态", required = true, dataType = "String")
	@Column(name = "status", length = 100)
	private int status;


	@ApiModelProperty(value = "简介", required = true, dataType = "String")
	@Column(name = "intro")
	private String intro;

	@ApiModelProperty(value = "creator", required = true, dataType = "Long")
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


//	@ApiModelProperty(value = "排序号", required = true, dataType = "Integer")
//	@Column(name = "sort_num")
//	private Integer sortNum;
	// ##remain#property#

	@Transient
	private String userName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TOrg tOrg = (TOrg) o;
		return status == tOrg.status &&
				Objects.equals(id, tOrg.id) &&
				Objects.equals(name, tOrg.name) &&
				Objects.equals(code, tOrg.code) &&
				Objects.equals(tel, tOrg.tel) &&
				Objects.equals(logo, tOrg.logo) &&
				Objects.equals(addr, tOrg.addr) &&
				Objects.equals(license, tOrg.license) &&
				Objects.equals(industry, tOrg.industry) &&
				Objects.equals(intro, tOrg.intro) &&
				Objects.equals(creator, tOrg.creator) &&
				Objects.equals(createtime, tOrg.createtime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, code, tel, logo, addr, license, industry, status, intro, creator, createtime);
	}
}