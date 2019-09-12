package cn.obcp.user.Exception;

public class UnAuthorization extends Exception {

	String message = "无数据";
	int code = 403;

	
	 public  UnAuthorization() {
		  this.setCode(code);
		  this.setMessage(message);
	 }

	public  UnAuthorization(String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	 
	 
}
