package cn.obcp.user.VO;

import org.hibernate.validator.constraints.NotBlank;
/**
 *  注册企业账号vo
 * @author lmf
 *
 */
public class CompanyAddVo {

	private Long id;
	@NotBlank(message="密码不能为空")
	private String password;
	private Integer vip;
	@NotBlank(message="联系人不能为空")
	private String nickname;
	@NotBlank(message="联系人电话不能为空")
	private String mobile;
	@NotBlank(message="联系人身份证号码不能为空")
	private String cardnum;
	@NotBlank(message="联系人身份证正面照不能为空")
	private String cardnumFrontFile;
	@NotBlank(message="联系人身份证反面照不能为空")
	private String cardnumBackFile;
	
	private String companyno;
	@NotBlank(message="公司名称不能为空")
	private String companyname;
	
	private String companyaddr;
	
	private String companytel;
	@NotBlank(message="公司税号不能为空")
	private String taxid;
	
	private String authfile;
	@NotBlank(message="加盖公章运营执照不能为空")
	private String businesslicensefile;
	@NotBlank(message="法人姓名不能为空")
	private String corporationname;
	@NotBlank(message="法人身份证号不能为空")
	private String corporationcardnum;
	

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
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
	
	
	
}
