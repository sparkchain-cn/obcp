package cn.obcp.base.vo;

import java.util.HashMap;
import java.util.Map;

public class RequestParamsVo {

	private String ip;

	private String apiPath;

	private String uuid;

	private String contentType;

	private String appid;

	public Map<String, String[]> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String[]> paramsMap) {
		this.paramsMap = paramsMap;
	}

	private Map<String, String[]> paramsMap=new HashMap<>();
	// privae String

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
