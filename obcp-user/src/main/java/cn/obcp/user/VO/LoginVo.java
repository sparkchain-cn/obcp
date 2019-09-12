/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月15日
 */
package cn.obcp.user.VO;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author lmf
 *
 */
public class LoginVo {

	private String loginName;
	
	private String password;

	@NotBlank(message="登录名不能为空")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@NotBlank(message="登录密码不能为空")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
