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
 * 
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_user_workorder")
public class TUserWorkorder extends BaseEntity implements java.io.Serializable {

	public TUserWorkorder() {
	}

	public TUserWorkorder(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "id", required = true, dataType = "Long")
	@Column(name = "id", length = 0, nullable = false)
	private Long id;

	@ApiModelProperty(value = "登录号码", required = true, dataType = "String")
	@Column(name = "mobile", length = 50, nullable = false)
	private String mobile;

	@ApiModelProperty(value = "登录邮箱", required = true, dataType = "String")
	@Column(name = "loginemail", length = 50)
	private String loginemail;

	@ApiModelProperty(value = "name", required = true, dataType = "String")
	@Column(name = "name", length = 60)
	private String name;

	@ApiModelProperty(value = "organization", required = true, dataType = "Integer")
	@Column(name = "organization", length = 0)
	private Integer organization;

	@ApiModelProperty(value = "公司编码", required = true, dataType = "String")
	@Column(name = "companyno", length = 100)
	private String companyno;

	@ApiModelProperty(value = "公司名称", required = true, dataType = "String")
	@Column(name = "companyname", length = 200)
	private String companyname;

	@ApiModelProperty(value = "公司地址", required = true, dataType = "String")
	@Column(name = "companyaddr", length = 500)
	private String companyaddr;

	@ApiModelProperty(value = "公司联系电话", required = true, dataType = "String")
	@Column(name = "companytel", length = 50)
	private String companytel;

	@ApiModelProperty(value = "税号编码", required = true, dataType = "String")
	@Column(name = "taxid", length = 100)
	private String taxid;

	@ApiModelProperty(value = "授权文件地址", required = true, dataType = "String")
	@Column(name = "authfile", length = 300)
	private String authfile;

	@ApiModelProperty(value = "营业执照文件地址", required = true, dataType = "String")
	@Column(name = "businesslicensefile", length = 300)
	private String businesslicensefile;

	@ApiModelProperty(value = "法人姓名", required = true, dataType = "String")
	@Column(name = "corporationname", length = 50)
	private String corporationname;

	@ApiModelProperty(value = "法人身份证", required = true, dataType = "String")
	@Column(name = "corporationcardnum", length = 50)
	private String corporationcardnum;

	@ApiModelProperty(value = "身份证正面文件", required = true, dataType = "String")
	@Column(name = "cardnumfrontfile", length = 300)
	private String cardnumfrontfile;

	@ApiModelProperty(value = "身份证手持文件", required = true, dataType = "String")
	@Column(name = "cardnumhandfile", length = 300)
	private String cardnumhandfile;

	@ApiModelProperty(value = "身份证反面文件", required = true, dataType = "String")
	@Column(name = "cardnumbackfile", length = 300)
	private String cardnumbackfile;

	@ApiModelProperty(value = "驳回消息", required = true, dataType = "String")
	@Column(name = "reviewmessage", length = 500)
	private String reviewmessage;

	@ApiModelProperty(value = "status", required = true, dataType = "Integer")
	@Column(name = "status", length = 0, nullable = false)
	private Integer status;

	@ApiModelProperty(value = "verifyuser", required = true, dataType = "Long")
	@Column(name = "verifyuser", length = 0)
	private Long verifyuser;

	@ApiModelProperty(value = "审核时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "verifytime", length = 0)
	private Date verifytime;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 0, nullable = false)
	private Date createtime;

	@ApiModelProperty(value = "更新时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime", length = 0, nullable = false)
	private Date updatetime;
	// ##remain#property#

	public Long getId() {
		return this.id;
	}

	/**
	*   
	*/
	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return this.mobile;
	}

	/**
	*   
	*/
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginemail() {
		return this.loginemail;
	}

	/**
	*   
	*/
	public void setLoginemail(String loginemail) {
		this.loginemail = loginemail;
	}

	public String getName() {
		return this.name;
	}

	/**
	*   
	*/
	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrganization() {
		return this.organization;
	}

	/**
	*   
	*/
	public void setOrganization(Integer organization) {
		this.organization = organization;
	}

	public String getCompanyno() {
		return this.companyno;
	}

	/**
	*   
	*/
	public void setCompanyno(String companyno) {
		this.companyno = companyno;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	/**
	*   
	*/
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyaddr() {
		return this.companyaddr;
	}

	/**
	*   
	*/
	public void setCompanyaddr(String companyaddr) {
		this.companyaddr = companyaddr;
	}

	public String getCompanytel() {
		return this.companytel;
	}

	/**
	*   
	*/
	public void setCompanytel(String companytel) {
		this.companytel = companytel;
	}

	public String getTaxid() {
		return this.taxid;
	}

	/**
	*   
	*/
	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}

	public String getAuthfile() {
		return this.authfile;
	}

	/**
	*   
	*/
	public void setAuthfile(String authfile) {
		this.authfile = authfile;
	}

	public String getBusinesslicensefile() {
		return this.businesslicensefile;
	}

	/**
	*   
	*/
	public void setBusinesslicensefile(String businesslicensefile) {
		this.businesslicensefile = businesslicensefile;
	}

	public String getCorporationname() {
		return this.corporationname;
	}

	/**
	*   
	*/
	public void setCorporationname(String corporationname) {
		this.corporationname = corporationname;
	}

	public String getCorporationcardnum() {
		return this.corporationcardnum;
	}

	/**
	*   
	*/
	public void setCorporationcardnum(String corporationcardnum) {
		this.corporationcardnum = corporationcardnum;
	}

	public String getCardnumfrontfile() {
		return this.cardnumfrontfile;
	}

	/**
	*   
	*/
	public void setCardnumfrontfile(String cardnumfrontfile) {
		this.cardnumfrontfile = cardnumfrontfile;
	}

	public String getCardnumhandfile() {
		return this.cardnumhandfile;
	}

	/**
	*   
	*/
	public void setCardnumhandfile(String cardnumhandfile) {
		this.cardnumhandfile = cardnumhandfile;
	}

	public String getCardnumbackfile() {
		return this.cardnumbackfile;
	}

	/**
	*   
	*/
	public void setCardnumbackfile(String cardnumbackfile) {
		this.cardnumbackfile = cardnumbackfile;
	}

	public String getReviewmessage() {
		return this.reviewmessage;
	}

	/**
	*   
	*/
	public void setReviewmessage(String reviewmessage) {
		this.reviewmessage = reviewmessage;
	}

	public Integer getStatus() {
		return this.status;
	}

	/**
	*   
	*/
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getVerifyuser() {
		return this.verifyuser;
	}

	/**
	*   
	*/
	public void setVerifyuser(Long verifyuser) {
		this.verifyuser = verifyuser;
	}

	public Date getVerifytime() {
		return this.verifytime;
	}

	/**
	*   
	*/
	public void setVerifytime(Date verifytime) {
		this.verifytime = verifytime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	/**
	*   
	*/
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	*   
	*/
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	// ##remain#method#

	public boolean equals(Object obj) {
		if (obj instanceof TUserWorkorder) {
			TUserWorkorder userWorkorder = (TUserWorkorder) obj;
			if (id == null || userWorkorder.id == null) {
				return false;
			}
			return (id.equals(userWorkorder.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TUserWorkorder userWorkorder = (TUserWorkorder) this;
		if (id == null)
			return super.hashCode();
		else

			return id.hashCode();

	}

}