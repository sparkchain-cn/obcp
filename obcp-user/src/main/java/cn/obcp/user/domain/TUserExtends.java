package cn.obcp.user.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import cn.obcp.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * &#64;schema:"user"
  &#64;tablename:"sys_user_extends"
  &#64;aliasName:"用户详细信息表"
  &#64;description:"用户详细信息表"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_user_extends")
public class TUserExtends extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547176L;

	public TUserExtends() {

	}

	public TUserExtends(Long uid, Date create) {
		this.uid = uid;
		this.createtime = create;
		this.updatetime = create;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "用户ID", required = true, dataType = "bigint")
	@Column(name = "uid", length = 80, nullable = false)
	private Long uid;

	@ApiModelProperty(value = "性别(DICT)", required = true, dataType = "String")
	@Column(name = "sex", length = 50)
	private String sex;

	@ApiModelProperty(value = "所在区划id", required = true, dataType = "String")
	@Column(name = "region", length = 80)
	private String region;

	@ApiModelProperty(value = "身份证", required = true, dataType = "String")
	@Column(name = "cardnum", length = 50)
	private String cardnum;

	@ApiModelProperty(value = "公司名称", required = true, dataType = "String")
	@Column(name = "companyName", length = 50)
	private String companyName;

	@ApiModelProperty(value = "办公手机", required = true, dataType = "String")
	@Column(name = "office_mobile", length = 50)
	private String officeMobile;

	@ApiModelProperty(value = "办公电话", required = true, dataType = "String")
	@Column(name = "office_tel", length = 50)
	private String officeTel;

	@ApiModelProperty(value = "工作邮箱", required = true, dataType = "String")
	@Column(name = "office_email", length = 50)
	private String officeEmail;

	@ApiModelProperty(value = "工作地址", required = true, dataType = "String")
	@Column(name = "office_addr", length = 500)
	private String officeAddr;

	@ApiModelProperty(value = "出生年月", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday")
	private Date birthday;

	@ApiModelProperty(value = "个人介绍", required = true, dataType = "String")
	@Column(name = "descriptions", length = 2000)
	private String descriptions;

	@ApiModelProperty(value = "注册时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registtime")
	private Date registtime;

	@ApiModelProperty(value = "QQ号", required = true, dataType = "String")
	@Column(name = "qq", length = 50)
	private String qq;

	@ApiModelProperty(value = "微信号", required = true, dataType = "String")
	@Column(name = "weixin", length = 250)
	private String weixin;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;

	@ApiModelProperty(value = "更新时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	private Date updatetime;

	@ApiModelProperty(value = "身份证正面照", required = true, dataType = "String")
	@Column(name = "cardnumFrontFile", length = 11, nullable = true)
	@NotBlank(message = "身份证照片必须同时上传正反面")
	private String cardnumFrontFile;//

	@ApiModelProperty(value = "身份证反面照", required = true, dataType = "String")
	@Column(name = "cardnumBackFile", length = 11, nullable = true)
	@NotBlank(message = "身份证照片必须同时上传正反面")
	private String cardnumBackFile;//

	@ApiModelProperty(value = "手持证件照", required = true, dataType = "String")
	@Column(name = "cardnumHandFile", length = 11, nullable = true)
	@NotBlank(message = "手持证件照")
	private String cardnumHandFile;
	// ##remain#property#

	public Long getUid() {
		return this.uid;
	}

	/**
	 * @aliasName:"用户ID"
	 * 
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getSex() {
		return this.sex;
	}

	/**
	 * @dict:"SEX"
	 * @combobox @aliasName:"性别(DICT)"
	 * @description:"性别(DICT)"
	 * 
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRegion() {
		return this.region;
	}

	/**
	 * @aliasName:"所在区划id"
	 * 
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	public String getCardnum() {
		return this.cardnum;
	}

	/**
	 * @aliasName:"身份证"
	 * 
	 */
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getOfficeMobile() {
		return this.officeMobile;
	}

	/**
	 * @aliasName:"办公手机"
	 * 
	 */
	public void setOfficeMobile(String officeMobile) {
		this.officeMobile = officeMobile;
	}

	public String getOfficeTel() {
		return this.officeTel;
	}

	/**
	 * @aliasName:"办公电话"
	 * 
	 */
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getOfficeEmail() {
		return this.officeEmail;
	}

	/**
	 * @aliasName:"工作邮箱"
	 * 
	 */
	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	public String getOfficeAddr() {
		return this.officeAddr;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @aliasName:"工作地址"
	 * 
	 */
	public void setOfficeAddr(String officeAddr) {
		this.officeAddr = officeAddr;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	/**
	 * @aliasName:"出生年月"
	 * 
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDescriptions() {
		return this.descriptions;
	}

	/**
	 * @aliasName:"个人介绍"
	 * 
	 */
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public Date getRegisttime() {
		return this.registtime;
	}

	/**
	 * @aliasName:"注册时间"
	 * 
	 */
	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}

	public String getQq() {
		return this.qq;
	}

	/**
	 * @aliasName:"QQ号"
	 * 
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return this.weixin;
	}

	/**
	 * @aliasName:"微信号"
	 * 
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
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
	 * @aliasName:"更新时间"
	 * 
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCardnumFrontFile() {
		return cardnumFrontFile;
	}

	public void setCardnumFrontFile(String cardnumFrontFile) {
		this.cardnumFrontFile = cardnumFrontFile;
	}

	public String getCardnumBackFile() {
		return cardnumBackFile;
	}

	public void setCardnumBackFile(String cardnumBackFile) {
		this.cardnumBackFile = cardnumBackFile;
	}

	public String getCardnumHandFile() {
		return cardnumHandFile;
	}

	public void setCardnumHandFile(String cardnumHandFile) {
		this.cardnumHandFile = cardnumHandFile;
	}

	// ##remain#method#
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TUserExtends) {
			TUserExtends userExtends = (TUserExtends) obj;
			if (uid == null || userExtends.uid == null) {
				return false;
			}
			return (uid.equals(userExtends.uid));
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TUserExtends userExtends = (TUserExtends) this;
		if (uid == null) {
			return super.hashCode();
		} else {
			return uid.hashCode();
		}
	}

}