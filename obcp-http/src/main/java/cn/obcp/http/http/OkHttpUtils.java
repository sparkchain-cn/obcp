package cn.obcp.http.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017年7月6日
 *
 */
public class OkHttpUtils {

	private static void setCommonHeader(Map<String, String> headersParams, String name, String value) {
		if (!headersParams.containsKey(name)) {
			headersParams.put(name, value);
		}
	}

	/**
	 * 设置请求头
	 * 
	 * @param headersParams
	 * @return
	 */
	public static Headers setHeaders(Map<String, String> headersParams) {
		Headers headers = null;
		okhttp3.Headers.Builder headersbuilder = new okhttp3.Headers.Builder();

		if (headersParams == null) {
			headersParams = new HashMap<>();
		}

		if (headersParams != null) {
			Iterator<String> iterator = headersParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				headersbuilder.add(key, headersParams.get(key));

			}
		}
		headers = headersbuilder.build();

		return headers;
	}

	/**
	 * post请求参数
	 * 
	 * @param BodyParams
	 * @return
	 */
	public static RequestBody setRequestBody(Map<String, String> BodyParams) {
		RequestBody body = null;
		okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
		if (BodyParams != null) {
			Iterator<String> iterator = BodyParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				formEncodingBuilder.add(key, BodyParams.get(key));
			}
		}
		body = formEncodingBuilder.build();
		return body;

	}

	public static String setGetParams(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		if (params != null && params.size() > 0) {
			Set<Map.Entry<String, String>> entrySet = params.entrySet();
			sb.append("?");
			for (Map.Entry<String, String> entry : entrySet) {
				sb.append(entry.getKey());
				sb.append("=");
				try {
					sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

}
