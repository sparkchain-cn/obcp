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
 * &#64;schema:"user"
  &#64;tablename:"sys_login_record"
  &#64;aliasName:"用户登录记录"
  &#64;description:"用户登录记录"
    
 * 该代码通过mgicode代码生成器生成
 * 
 * </pre>
 * 
 */
// @LogicalDelete
@Entity
@Table(name = "sys_login_record")
public class TLoginRecord extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8656128222714547172L;
	
	public TLoginRecord() {
	}

	public TLoginRecord(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "ID", required = true, dataType = "bigint")
	@Column(name = "id", nullable = false)
	private Long id;

	@ApiModelProperty(value = "用户id", required = true, dataType = "bigint")
	@Column(name = "user")
	private Long user;

	@ApiModelProperty(value = "内容", required = true, dataType = "String")
	@Column(name = "content", length = 500)
	private String content;

	@ApiModelProperty(value = "登录IP", required = true, dataType = "String")
	@Column(name = "ip", length = 80)
	private String ip;

	@ApiModelProperty(value = "登录时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logintime")
	private Date logintime;
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

	public Long getUser() {
		return this.user;
	}

	/**
	 * @aliasName:"用户id"
	 * 
	 */
	public void setUser(Long user) {
		this.user = user;
	}

	public String getContent() {
		return this.content;
	}

	/**
	 * @aliasName:"内容"
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getIp() {
		return this.ip;
	}

	/**
	 * @aliasName:"登录IP"
	 * 
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLogintime() {
		return this.logintime;
	}

	/**
	 * @aliasName:"登录时间"
	 * 
	 */
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
	// ##remain#method#

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TLoginRecord) {
			TLoginRecord loginRecord = (TLoginRecord) obj;
			if (id == null || loginRecord.id == null) {
				return false;
			}
			return (id.equals(loginRecord.id));
		}
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TLoginRecord loginRecord = (TLoginRecord) this;
		if (id == null) {
			return super.hashCode();
		}
		else {
			return id.hashCode();
		}
	}
}