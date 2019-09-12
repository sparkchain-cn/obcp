package cn.obcp.http.http;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author 彭仁夔
 * @email 546711211@qq.com
 * @time 2017年7月6日
 *
 */
public class OKClient {

	private static OkHttpClient okHttpClient = new OkHttpClient();

	public static String post(String url, Map<String, String> headersParams, Map<String, String> bodyParams) {

		Request.Builder builder = new Request.Builder().url(url);
		// if (headersParams != null && !headersParams.isEmpty()) {
		builder.headers(OkHttpUtils.setHeaders(headersParams));
		// }
		builder.post(OkHttpUtils.setRequestBody(bodyParams));

		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		try {
			return call.execute().body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String get(String url, Map<String, String> headersParams) {
		return get(url, (Map<String, String>) null, (Map<String, String>) null);
	}

	public static String get(String url, Map<String, String> params, Map<String, String> headersParams) {

		// todo:处理header
		Request request = new Request.Builder().headers(OkHttpUtils.setHeaders(headersParams))
				.url(url + OkHttpUtils.setGetParams(params)).get().build();
		Call call = okHttpClient.newCall(request);
		try {
			return call.execute().body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String postJson(String url, String json, Map<String, String> headersParams) {
		// todo:处理header
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(JSON, "json");

		OkHttpClient okHttpClient = new OkHttpClient();

		Request.Builder builder = new Request.Builder().url(url);
		builder.headers(OkHttpUtils.setHeaders(headersParams));
		builder.post(body);

		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		try {
			return call.execute().body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String post(String url, Map<String, String> bodyParams) {
		return post(url, bodyParams);
	}

	public static <T> T post(String url, Map<String, String> bodyParams, Class<T> clz) {
		String str = post(url, bodyParams);
		return new Gson().fromJson(str, clz);
	}

	public <T> T get(String url, Map<String, String> params, Class<T> cls) {
		String str = get(url, params);
		Gson gson = new Gson();
		return gson.fromJson(str, cls);
	}

	public <T> T get(String url, Class<T> cls) {
		String str = get(url, (Map<String, String>) null);
		Gson gson = new Gson();
		return gson.fromJson(str, cls);
	}
}
