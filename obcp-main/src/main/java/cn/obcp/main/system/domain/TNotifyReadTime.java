package cn.obcp.main.system.domain;

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
@Table(name = "bc_notify_read_time")
public class TNotifyReadTime extends BaseEntity implements java.io.Serializable {

	public TNotifyReadTime() {
	}

	public TNotifyReadTime(Long userId) {
		this.userId = userId;
	}

	@Id
	@JSONField(serializeUsing = ToStringSerializer.class)
	@ApiModelProperty(value = "用户ID", required = true, dataType = "Long")
	@Column(name = "user_id", length = 0, nullable = false)
	private Long userId;

	@ApiModelProperty(value = "阅读时间", required = true, dataType = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "read_time", length = 0, nullable = false)
	private Date readTime;
	// ##remain#property#

	public Long getUserId() {
		return this.userId;
	}

	/**
	*   
	*/
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getReadTime() {
		return this.readTime;
	}

	/**
	*   
	*/
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	// ##remain#method#

	public boolean equals(Object obj) {
		if (obj instanceof TNotifyReadTime) {
			TNotifyReadTime notifyReadTime = (TNotifyReadTime) obj;
			if (userId == null || notifyReadTime.userId == null) {
				return false;
			}
			return (userId.equals(notifyReadTime.userId));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		// TNotifyReadTime notifyReadTime = (TNotifyReadTime) this;
		if (userId == null)
			return super.hashCode();
		else

			return userId.hashCode();

	}

}