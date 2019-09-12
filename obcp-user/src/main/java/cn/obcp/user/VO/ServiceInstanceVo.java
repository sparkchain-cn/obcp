package cn.obcp.user.VO;

import org.springframework.cloud.client.ServiceInstance;

/**
 * 服务实例
 * @author cx
 */
public class ServiceInstanceVo {
	//服务ID
	private String serviceid;
	//服务名称
	private String serviceName;
	//服务部署IP
	private String host;
	//访问部署端口
	private int port;
	//服务请求地址
	private String uri;
	//请求数
	private String requestCount;
	//业务错误数量
	private String bizExceptionCount;
	//连接错误数量
	private String ioExceptionCount;
	//连接池名称
	private String chainNodesName;

	/**
	 * 获取服务实例
	 * @param serviceName
	 * @param instance
	 */
	public ServiceInstanceVo(String serviceName,ServiceInstance instance) {
		this.serviceName = serviceName;
		this.serviceid = instance.getServiceId();
		this.host = instance.getHost();
		this.port = instance.getPort();
		this.uri = instance.getUri().toString();
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(String requestCount) {
		this.requestCount = requestCount;
	}

	public String getBizExceptionCount() {
		return bizExceptionCount;
	}

	public void setBizExceptionCount(String bizExceptionCount) {
		this.bizExceptionCount = bizExceptionCount;
	}

	public String getIoExceptionCount() {
		return ioExceptionCount;
	}

	public void setIoExceptionCount(String ioExceptionCount) {
		this.ioExceptionCount = ioExceptionCount;
	}

	public String getChainNodesName() {
		return chainNodesName;
	}

	public void setChainNodesName(String chainNodesName) {
		this.chainNodesName = chainNodesName;
	}

}
