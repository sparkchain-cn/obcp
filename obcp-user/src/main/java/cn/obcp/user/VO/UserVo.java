/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月29日
 */
package cn.obcp.user.VO;

import java.io.Serializable;

import cn.obcp.user.domain.TUserExtend;

/**
 * @author lmf
 *
 */
public class UserVo implements Serializable{
	private static final long serialVersionUID = -656465041293371312L;
	
	private Long id;
	private String nickname; //昵称
	private String icon; //頭像
	private boolean isAdmin;//是否是普通用戶,false：普通用户，true:管理员

	public UserVo build(TUserExtend u) {
		
		UserVo user = new UserVo();
		if(null != u) {
			user.setId(u.getId());
			user.setNickname(u.getNickname());
			user.setIcon(u.getIcon());
			user.setAdmin("true".equalsIgnoreCase(u.getIsAdmin()));
		}
		return user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

}
