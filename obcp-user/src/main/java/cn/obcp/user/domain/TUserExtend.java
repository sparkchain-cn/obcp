package cn.obcp.user.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.obcp.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * &#64;tablename:"sys_user_extend"
  &#64;aliasName:"用户表"
  &#64;description:"用户表"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_user")
public class TUserExtend extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547171L;

	public TUserExtend() {
	}

	public TUserExtend(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)

	@ApiModelProperty(value = "ID", required = true, dataType = "bigint")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@NotBlank(message = "手机号码不能为空")
	@ApiModelProperty(value = "登录号码", required = true, dataType = "String")
	@Column(name = "mobile", length = 50, nullable = false)
	private String mobile;

	@ApiModelProperty(value = "登录邮箱", required = true, dataType = "String")
	@Column(name = "loginemail", length = 50, unique = true)
	private String loginemail;

	@NotBlank(message = "登录密码不能为空")
	@ApiModelProperty(value = "登录密码", required = true, dataType = "String")
	@Column(name = "password", length = 60)
	private String password;

	@NotBlank(message = "姓名不能为空")
	@ApiModelProperty(value = "姓名", required = true, dataType = "String")
	@Column(name = "Name", length = 60)
	private String name;

	@ApiModelProperty(value = "姓名拼音", required = true, dataType = "String")
	@Column(name = "pingyinname", length = 150)
	private String pingyinname;

	@NotBlank(message = "用户昵称不能为空")
	@ApiModelProperty(value = "昵称", required = true, dataType = "String")
	@Column(name = "nickname", length = 60)
	private String nickname;

	@ApiModelProperty(value = "头像(图片地址)", required = true, dataType = "String")
	@Column(name = "icon", length = 150)
	private String icon;

	@ApiModelProperty(value = "加密盐", required = true, dataType = "String")
	@Column(name = "salt", length = 60)
	private String salt;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", nullable = false)
	private Date createtime;

	@ApiModelProperty(value = "用户状态(DICT)", required = true, dataType = "smallint")
	@Column(name = "state", length = 250, nullable = false)
	private Integer state;

	@ApiModelProperty(value = "用户异常说明", required = true, dataType = "String")
	@Column(name = "explain", length = 2000, nullable = true)
	private String explain;
	// ##remain#property#
	@ApiModelProperty(value = "组织类型（0:个人  1：企业）", required = true, dataType = "int")
	@Column(name = "organization", length = 4, nullable = true)
	private Integer organization;

	@ApiModelProperty(value = "审核时间", required = true, dataType = "Date")
	@Column(name = "verifytime", length = 4, nullable = true)
	private String verifyTime;

	@ApiModelProperty(value = "审核人", required = true, dataType = "bigint")
	@Column(name = "verifyuser", length = 80, nullable = true)
	private String verifyUser;

	@ApiModelProperty(value = "特权用户（0：普通用户，1：免费用户）", required = true, dataType = "int")
	@Column(name = "vip", length = 11, nullable = true)
	private Integer vip;

	@ApiModelProperty(value = "特权结束时间", required = true, dataType = "Date")
	@Column(name = "vipEndtime", length = 11, nullable = true)
	private String vipEndtime;

	@ApiModelProperty(value = "用户等级", required = true, dataType = "int")
	@Column(name = "rank", length = 11, nullable = true)
	private String rank;

	@Transient
	private String sex;
	@Transient
	private String region;
	@Transient
	private String cardnum;
	@Transient
	private String officeMobile;
	@Transient
	private String officeTel;
	@Transient
	private String officeEmail;
	@Transient
	private String officeAddr;
	@Transient
	private Date birthday;
	@Transient
	private String descriptions;
	@Transient
	private Date registtime;
	@Transient
	private String qq;
	@Transient
	private String weixin;
	@Transient

	private Long rid;
	@Transient
	private int hasRole;
	@Transient
	private String roleName;
	@Transient
	@JsonProperty(value = "LAY_CHECKED")
	private boolean LAY_CHECKED;// 是否绑定
	@Transient
	private String companyno;
	@Transient
	private String companyname;
	@Transient
	private String companyaddr;
	@Transient
	private String companytel;
	@Transient
	private String taxid;
	@Transient
	private String authfile;
	@Transient
	private String businesslicensefile;
	@Transient
	private String corporationname;
	@Transient
	private String corporationcardnum;
	@Transient
	private String isAdmin;
	// 用户ＡＰＰ
	@Transient
	private Object applist;

	@Transient
	private String appid;
	@Transient
	private String appName;

	// 用戶token
	private String userToken;

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Object getApplist() {
		return applist;
	}

	public void setApplist(Object applist) {
		this.applist = applist;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	@JsonIgnore
	public boolean isLAY_CHECKED() {
		return LAY_CHECKED;
	}

	public void setLAY_CHECKED(boolean lAY_CHECKED) {
		LAY_CHECKED = lAY_CHECKED;
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

	public String getMobile() {
		return this.mobile;
	}

	/**
	 * @aliasName:"登录号码"
	 * 
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginemail() {
		return this.loginemail;
	}

	/**
	 * @unique @aliasName:"登录邮箱"
	 * @description:"登录邮箱"
	 * 
	 */
	public void setLoginemail(String loginemail) {
		this.loginemail = loginemail;
	}

	public String getPassword() {
		return this.password;
	}

	/**
	 * @aliasName:"登录密码"
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @name @aliasName:"姓名"
	 * @description:"姓名"
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getPingyinname() {
		return this.pingyinname;
	}

	/**
	 * @aliasName:"姓名拼音"
	 * 
	 */
	public void setPingyinname(String pingyinname) {
		this.pingyinname = pingyinname;
	}

	public String getNickname() {
		return this.nickname;
	}

	/**
	 * @aliasName:"昵称"
	 * 
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIcon() {
		return this.icon;
	}

	/**
	 * @aliasName:"头像(图片地址)"
	 * 
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSalt() {
		return this.salt;
	}

	/**
	 * @aliasName:"加密盐"
	 * 
	 */
	public void setSalt(String salt) {
		this.salt = salt;
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

	public Integer getState() {
		return this.state;
	}

	/**
	 * @dict:"USERSTATE"
	 * @combobox @aliasName:"用户状态(DICT)"
	 * @description:"用户状态(DICT)"
	 * 
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	public String getExplain() {
		return this.explain;
	}

	/**
	 * @aliasName:"用户异常说明"
	 * 
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	// @Transient

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getOfficeMobile() {
		return officeMobile;
	}

	public void setOfficeMobile(String officeMobile) {
		this.officeMobile = officeMobile;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getOfficeEmail() {
		return officeEmail;
	}

	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	public String getOfficeAddr() {
		return officeAddr;
	}

	public void setOfficeAddr(String officeAddr) {
		this.officeAddr = officeAddr;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public Date getRegisttime() {
		return registtime;
	}

	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	// ##remain#method#

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getHasRole() {
		return hasRole;
	}

	public void setHasRole(int hasRole) {
		this.hasRole = hasRole;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(String verifyUser) {
		this.verifyUser = verifyUser;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Integer getOrganization() {
		return organization;
	}

	public void setOrganization(Integer organization) {
		this.organization = organization;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public String getVipEndtime() {
		return vipEndtime;
	}

	public void setVipEndtime(String vipEndtime) {
		this.vipEndtime = vipEndtime;
	}

	public String getCompanyno() {
		return companyno;
	}

	public void setCompanyno(String companyno) {
		this.companyno = companyno;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyaddr() {
		return companyaddr;
	}

	public void setCompanyaddr(String companyaddr) {
		this.companyaddr = companyaddr;
	}

	public String getCompanytel() {
		return companytel;
	}

	public void setCompanytel(String companytel) {
		this.companytel = companytel;
	}

	public String getTaxid() {
		return taxid;
	}

	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}

	public String getAuthfile() {
		return authfile;
	}

	public void setAuthfile(String authfile) {
		this.authfile = authfile;
	}

	public String getBusinesslicensefile() {
		return businesslicensefile;
	}

	public void setBusinesslicensefile(String businesslicensefile) {
		this.businesslicensefile = businesslicensefile;
	}

	public String getCorporationname() {
		return corporationname;
	}

	public void setCorporationname(String corporationname) {
		this.corporationname = corporationname;
	}

	public String getCorporationcardnum() {
		return corporationcardnum;
	}

	public void setCorporationcardnum(String corporationcardnum) {
		this.corporationcardnum = corporationcardnum;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TUserExtend) {
			TUserExtend userExtend = (TUserExtend) obj;
			if (id == null || userExtend.id == null) {
				return false;
			}
			return (id.equals(userExtend.id));
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TUserExtend userExtend = (TUserExtend) this;
		if (id == null) {
			return super.hashCode();
		} else {
			return id.hashCode();
		}
	}
}