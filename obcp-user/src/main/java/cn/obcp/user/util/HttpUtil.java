package cn.obcp.user.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.obcp.base.utils.StringUtils;
import cn.obcp.user.shiro.filter.MAPIHttpServletRequestWrapper;

public class HttpUtil {


	 
    /**
     * 根据名字获取cookie
     * @param request
     * @param name  cookie名字
     * @return Cookie
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

	/**
	 * 添加cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param time
	 * @return HttpServletResponse
	 */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, Integer time) {
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 单位：秒
        if(null == time) {
        	time = 24*60*60;
        }
        cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        response.setCharacterEncoding("utf-8");
        return response;
    }
    
    /**
     *  删除cookie
     * @param request
     * @param response
     * @param deleteKey
     * @throws NullPointerException
     */
    private void delectCookieByName(HttpServletRequest request, HttpServletResponse response, String deleteKey) throws NullPointerException {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        for (String key : cookieMap.keySet()) {
            if (key == deleteKey && key.equals(deleteKey)) {
                Cookie cookie = cookieMap.get(key);
                // 默认值是-1，即：关闭浏览器时就清除cookie;
                // 当设置为0的时候：创建完cookie，使用后马上就删除;
                // 因为时间到了，又因为，cookie没有清除方法，所以设置为 0，就相当于清除方法;
                // 当设置时间大于0，当时间到达后就会自动删除
                cookie.setMaxAge(0);//设置cookie有效时间为0
                cookie.setPath("/");//不设置存储路径
                response.addCookie(cookie);
            }
        }
    }
    
    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return Map<String, Cookie>
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    public static String getReqParam(String paramName,HttpServletRequest req) throws IOException {
        String privateToken = null;
     //
        //从参数读取token值
        if (isBodyReq(req)){
            HttpServletRequest requestWrapper = new MAPIHttpServletRequestWrapper(req);
            String paramString = ParamsUtil.getBodyString(requestWrapper);
            req = requestWrapper;
            JSONObject jsonObject = JSONObject.parseObject(paramString);
            if (jsonObject != null) {
                privateToken = jsonObject.get(paramName) != null ? jsonObject.get(paramName).toString() : null;
            }
        }else {
            privateToken = req.getParameter(paramName);
        }
        return privateToken;
    }

    public static boolean isBodyReq(HttpServletRequest req) {
        String contentType = req.getContentType();
        if (StringUtils.isNotNullOrEmpty(contentType) && contentType.indexOf("application/json") > -1) { //采用流传输参
            return true;
        }
        return false;
    }
}
