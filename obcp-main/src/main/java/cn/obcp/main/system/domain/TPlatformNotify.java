package cn.obcp.main.system.domain;

import java.util.Date;
import java.util.List;

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
 * 通知,站内消息
 * </pre>
 */
@Entity
@Table(name = "sys_platform_notify")
public class TPlatformNotify extends BaseEntity implements java.io.Serializable {

	public TPlatformNotify() {
	}

	public TPlatformNotify(Long id) {
		this.id = id;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "id", required = true, dataType = "Long")
	@Column(name = "id", length = 0, nullable = false)
	private Long id;

	@ApiModelProperty(value = "通知标题", required = true, dataType = "String")
	@Column(name = "title", length = 200)
	private String title;

	@ApiModelProperty(value = "通知内容", required = true, dataType = "String")
	@Column(name = "content", length = 65535, columnDefinition = "CLOB")
	private String content;

	@ApiModelProperty(value = "类型,0:全平台通知,1:指定用户通知,2:指定应用通知", required = true, dataType = "Integer")
	@Column(name = "msg_type", length = 0, nullable = false)
	private Integer msgType;

	@ApiModelProperty(value = "用户ID", required = true, dataType = "Long")
	@Column(name = "user_id", length = 0, nullable = false)
	private Long userId;

	@ApiModelProperty(value = "应用ID", required = true, dataType = "Long")
	@Column(name = "appid", length = 0, nullable = false)
	private Long appid;

	@ApiModelProperty(value = "消息标签,分类", required = true, dataType = "String")
	@Column(name = "label", length = 100)
	private String label;

	@ApiModelProperty(value = "推送类型,0:平台内部消息,1:短信通知", required = true, dataType = "Integer")
	@Column(name = "push_type", length = 0, nullable = false)
	private Integer pushType;

	@ApiModelProperty(value = "弹出显示", required = true, dataType = "Integer")
	@Column(name = "popup", length = 0, nullable = false)
	private Integer popup;

	@ApiModelProperty(value = "状态,1:正常,-1:删除", required = true, dataType = "Integer")
	@Column(name = "state", length = 0, nullable = false)
	private Integer state;

	@ApiModelProperty(value = "创建者", required = true, dataType = "Long")
	@Column(name = "creator", length = 0)
	private Long creator;

	@ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 0, nullable = false)
	private Date createtime;

	@ApiModelProperty(value = "更新时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime", length = 0, nullable = false)
	private Date updatetime;
	// ##remain#property#

	@Transient
	private List<String> applist;
	@Transient
	private List<String> userlist;
	
	public Long getId() {
		return this.id;
	}

	/**
	*   
	*/
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	/**
	*   
	*/
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	/**
	*   
	*/
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMsgType() {
		return this.msgType;
	}

	/**
	*   
	*/
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Long getUserId() {
		return this.userId;
	}

	/**
	*   
	*/
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAppid() {
		return this.appid;
	}

	/**
	*   
	*/
	public void setAppid(Long appid) {
		this.appid = appid;
	}

	public String getLabel() {
		return this.label;
	}

	/**
	*   
	*/
	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPushType() {
		return this.pushType;
	}

	/**
	*   
	*/
	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public Integer getPopup() {
		return this.popup;
	}

	/**
	*   
	*/
	public void setPopup(Integer popup) {
		this.popup = popup;
	}

	public Integer getState() {
		return this.state;
	}

	/**
	*   
	*/
	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCreator() {
		return this.creator;
	}

	/**
	*   
	*/
	public void setCreator(Long creator) {
		this.creator = creator;
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
	
	
	public List<String> getApplist() {
		return applist;
	}

	public void setApplist(List<String> applist) {
		this.applist = applist;
	}

	public List<String> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<String> userlist) {
		this.userlist = userlist;
	}

	public boolean equals(Object obj) {
		if (obj instanceof TPlatformNotify) {
			TPlatformNotify platformNotify = (TPlatformNotify) obj;
			if (id == null || platformNotify.id == null) {
				return false;
			}
			return (id.equals(platformNotify.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TPlatformNotify platformNotify = (TPlatformNotify) this;
		if (id == null)
			return super.hashCode();
		else

			return id.hashCode();

	}

}