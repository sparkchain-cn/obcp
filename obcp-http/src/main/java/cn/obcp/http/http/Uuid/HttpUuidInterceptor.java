package cn.obcp.http.http.Uuid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

import cn.obcp.base.utils.StringUtils;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017年7月9日
 */
@Component
public class HttpUuidInterceptor extends HandlerInterceptorAdapter {

	// Spring Boot的核心启动类继承WebMvcConfigurerAdapter

	// 增加拦截器
	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// registry.addInterceptor(new RequestLog());
	// }
	// 这个RequestLog就是我们定义的拦截器

	//
	// 我们自己实现的拦截器需要继承HandlerInterceptorAdapter，并重写如下三个方法:
	//
	// 在preHandle中，可以进行编码、安全控制等处理；
	// 在postHandle中，有机会修改ModelAndView；
	// 还存在一个也比较重要的方法在afterCompletion中，下面介绍一些这三个方法的执行流程:
	//
	// 发起请求,进入拦截器链，运行所有拦截器的preHandle方法.
	// 当preHandle方法返回false时，（后面的拦截器就不再执行了）从当前拦截器往回执行所有拦截器的afterCompletion方法（不是postHandle方法哦），再退出拦截器链。
	//
	// 当preHandle方法全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。然后进入拦截器链，运行所有拦截器的postHandle方法,完后从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
	// 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法
	//
	//
	// preHandle方法：
	// 返回true，映射处理器执行链将继续执行；
	// 当返回false时，DispatcherServlet处理器认为拦截器已经处理完了请求（这个请求已经被拦截了），而不继续执行执行链中的其它拦截器和处理器。

	protected final static Log logger = LogFactory.getLog(HttpUuidInterceptor.class);

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 取得请求的路径，如http://localhost:8888/mgicode/ucDict-manager.do? 会取得其中间的mgicode
	 *
	 * @param req
	 * @param servletPath
	 * @return
	 */
	public static String getServiceName(HttpServletRequest req, String servletPath) {
		if (StringUtils.isNullOrEmpty(servletPath)) {
			servletPath = req.getServletPath();
		}
		String path = servletPath;
		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}

		int secondPos = path.indexOf('/');
		if (secondPos == -1) {
			// 说明请求当前路由中的地址，不是服务名
			return null;
		} else {
			path = path.substring(0, secondPos);
		}

		return path;
	}

	/**
	 * 取得请求的路径，如http://localhost:8888/mgicode/ucDict-manager.do? 会取最后一个/后面的内容
	 *
	 * @param req
	 * @param servletPath
	 * @return
	 */
	public static String getActionName(HttpServletRequest req, String servletPath) {
		if (StringUtils.isNullOrEmpty(servletPath)) {
			servletPath = req.getServletPath();
		}
		String path = "";
		int secondPos = servletPath.lastIndexOf('/');
		path = servletPath.substring(secondPos);

		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("\n path:" + path);
		}
		return path;
	}

	public static Map getMapFromJson(String postStr, boolean needTrim) {
		if (postStr == null) {
			return new HashMap<String, Object>();
		}
		if (postStr.startsWith("[")) {
			postStr = "{defvalue:" + postStr + "}";
		}
		Map map = new Gson().fromJson(postStr, Map.class);// JacksonHelper.getMapFromJson(postStr);

		if (needTrim) {
			Map retMap = new HashMap();
			for (Object key : map.keySet()) {

				Object v = map.get(key);

				if (v instanceof String && (v != null)) {
					v = ((String) v).trim();
				}
				retMap.put(key, v);

			}
			return retMap;
		}

		return map;
	}

	/**
	 * @param req
	 * @param requestName
	 * @return
	 */
	public static StringBuffer getPostData(HttpServletRequest req, String requestName) {
		StringBuffer s = getData(req, "utf-8");
		if (s.length() != 0)
			return s;
		if (requestName == null)
			return s;
		String xml = req.getParameter(requestName);
		if (xml == null)
			return s;
		else
			return new StringBuffer(xml);
	}

	/**
	 * 以指定encode的方式把httpRequest中的全部数据 取到StringBuffer中。
	 *
	 * @param request
	 * @param encode
	 * @return
	 */
	public static StringBuffer getData(HttpServletRequest request, String encode) {
		StringBuffer sb = new StringBuffer();
		String s = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), encode));
			while ((s = br.readLine()) != null)
				sb.append(s).append("\n");
			br.close();
			return sb;
		} catch (IOException e) {
			throw new RuntimeException((new StringBuilder("getXml error")).append(e).toString());
		} catch (Exception e) {
			throw new RuntimeException((new StringBuilder("getXml error")).append(e).toString());
		}
	}

	public void retJson(HttpServletResponse response, String json) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(json);
//			System.out.println("返回的JSON字符串:" + json);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
