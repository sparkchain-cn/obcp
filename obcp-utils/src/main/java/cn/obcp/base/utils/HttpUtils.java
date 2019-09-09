package cn.obcp.base.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

//import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017年7月6日
 *
 */
public class HttpUtils {

	private static OkHttpClient okHttpClient;
	
	static {
//		okHttpClient = new OkHttpClient()
//				.newBuilder()
//				.connectTimeout(25, TimeUnit.SECONDS)// 单位是秒
//				.readTimeout(240, TimeUnit.SECONDS)
//				.writeTimeout(240, TimeUnit.SECONDS)
//
//				.retryOnConnectionFailure(true)
//				.build();
//		okHttpClient.dispatcher().setMaxRequests(2000);
//		okHttpClient.dispatcher().setMaxRequestsPerHost(1000);
		
		okHttpClient = new OkHttpClient()
				.newBuilder()
				.connectTimeout(5, TimeUnit.SECONDS) //设置连接超时// 单位是秒
				.readTimeout(10, TimeUnit.SECONDS)//设置读超时
				.writeTimeout(10, TimeUnit.SECONDS)//设置写超时
				.retryOnConnectionFailure(false)//是否自动重连
				.build();
		// 最大并发请求数为64
		okHttpClient.dispatcher().setMaxRequests(100);
		//maxRequestsPerHost = 5: 每个主机最大请求数为5
		okHttpClient.dispatcher().setMaxRequestsPerHost(10);
	}

	// client = new OkHttpClient.Builder();
	public static String post(String url, Map<String, String> headersParams, Map<String, String> bodyParams)
			throws IOException {

		Request.Builder builder = new Request.Builder()
				.url(url);

		// if (headersParams != null && !headersParams.isEmpty()) {
		builder.headers(setHeaders(headersParams));

		// }
		builder.post(setRequestBody(bodyParams));

		Request request = builder.build();
		Call call = okHttpClient.newCall(request);

		// try {
		return call.execute().body().string();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// return null;
	}

	public static String get(String url, Map<String, String> headersParams) throws IOException {
		return get(url, (Map<String, String>) null, (Map<String, String>) null);
	}

	public static String get(String url, Map<String, String> params, Map<String, String> headersParams)
			throws IOException {

		// todo:处理header
		Request request = new Request.Builder().headers(setHeaders(headersParams))
				.url(url + setGetParams(params)).get().build();
		Call call = okHttpClient.newCall(request);
		// try {
		return call.execute().body().string();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// return null;
	}

	public static String postJson(String url, String json, Map<String, String> headersParams) throws IOException {
		// todo:处理header
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(JSON, json);

		OkHttpClient okHttpClient = new OkHttpClient();

		Request.Builder builder = new Request.Builder().url(url);
		builder.headers(setHeaders(headersParams));
		builder.post(body);

		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		// try {
		return call.execute().body().string();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// return null;
	}

	public static String post(String url, Map<String, String> bodyParams) throws IOException {
		return post(url, (Map<String, String>) null, bodyParams);
	}

	public static <T> T post(String url, Map<String, String> bodyParams, Class<T> clz) throws IOException {
		String str = post(url, bodyParams);
		return JSON.parseObject(str, clz);
	}

	public static <T> T get(String url, Map<String, String> params, Class<T> cls) throws IOException {
		String str = get(url, params, (Map<String, String>) null);
		// Gson gson = new Gson();
		return JSON.parseObject(str, cls);
	}

	public static <T> T get(String url, Class<T> cls) throws IOException {
		String str = get(url, (Map<String, String>) null);
		// Gson gson = new Gson();
		return JSON.parseObject(str, cls);
	}

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
	private static Headers setHeaders(Map<String, String> headersParams) {
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
	private static RequestBody setRequestBody(Map<String, String> BodyParams) {
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

	private static String setGetParams(Map<String, String> params) {
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
