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
 * &#64;tablename:"sys_user_token"
  &#64;aliasName:"用户token"
  &#64;description:"用户token"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_user_token")
public class TUserToken extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547178L;

	public TUserToken() {
	}

	public TUserToken(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)

	@ApiModelProperty(value = "ID", required = true, dataType = "bigint")
	@Column(name = "id", length = 80, nullable = false)
	private Long id;

	@ApiModelProperty(value = "用户ID", required = true, dataType = "bigint")
	@Column(name = "uid", length = 80)
	private Long uid;

	@ApiModelProperty(value = "token", required = true, dataType = "String")
	@Column(name = "token", length = 255)
	private String token;

	@ApiModelProperty(value = "IP", required = true, dataType = "String")
	@Column(name = "ip", length = 100)
	private String ip;

	@ApiModelProperty(value = "到期時間", required = true, dataType = "TUserExtend")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_time")
	private Date expiretime;

	@ApiModelProperty(value = "token作用域,-1:全部，0:v0,2:v2", required = true, dataType = "int")
	@Column(name = "scope")
	private int scope;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "TUserExtend")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	private Date createtime;
	// ##remain#property#

	private String scopeVal;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getScopeVal() {
		return scopeVal;
	}

	public void setScopeVal(String scopeVal) {
		this.scopeVal = scopeVal;
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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getToken() {
		return this.token;
	}

	/**
	 * @aliasName:"token"
	 * 
	 */
	public void setToken(String token) {
		this.token = token;
	}

	public String getIp() {
		return this.ip;
	}

	/**
	 * @aliasName:"IP"
	 * 
	 */
	public void setIp(String ip) {
		this.ip = ip;
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

	public Date getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	// ##remain#method#
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TUserToken) {
			TUserToken userToken = (TUserToken) obj;
			if (id == null || userToken.id == null) {
				return false;
			}
			return (id.equals(userToken.id));
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TUserToken userToken = (TUserToken) this;
		if (id == null) {
			return super.hashCode();
		} else {
			return id.hashCode();
		}

	}

}