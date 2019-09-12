package cn.obcp.main.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.obcp.base.utils.RSACoder;
import cn.obcp.base.utils.StringUtils;
import cn.obcp.cache.ICache;
import cn.obcp.main.constants.MainConstants;
import cn.obcp.user.common.UserConstans;
import sun.misc.BASE64Decoder;

public class ParamFilter  extends OncePerRequestFilter {

    String rsakey = null ;
    @Autowired
    private ICache redissonUtils;
    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //如果请求是属于需要进行参数解密的地址，对参数进行解密
        if (StringUtils.isNotNullOrEmpty(MainConstants.PARAM_RSAENCODE_URL)
                && MainConstants.PARAM_RSAENCODE_URL.equalsIgnoreCase(req.getRequestURI())) {
            Map<String, String[]> parameterMap = getRequestParams(request);
            parameterMap.remove(MainConstants.PARAM_PARAMS_KEY);//清除加密参数
            ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(req, parameterMap);
            req = requestWrapper;
        }

        chain.doFilter(req,response);

    }

    public Map<String,String[]>  getRequestParams(ServletRequest request) {
        Map<String,String[]> params =  request.getParameterMap();
        Map<String,String[]> paramMap = new HashMap<>();
        if (null != params && params.size() > 0){
            for (String param:params.keySet()){
               if (!param.equalsIgnoreCase(MainConstants.PARAM_TOKEN_KEY)){
                   paramMap.putAll(putParam(params.get(param),param));
               }
            }
        }
        return paramMap;
    }

    private Map<String,String[]> putParam(String[] param,String key) {
        Map<String,String[]> paramMap = new HashMap<>();
        if (null == rsakey && null != redissonUtils){
            rsakey = redissonUtils.get(UserConstans.RES_PRIVATE_KEY);
        }
        if (null != param){

            for (int index =0;index < param.length;index ++){
                //System.out.println(param[index]);
                String paramJsonStr = "";
                try {
                    if(StringUtils.isNotNullOrEmpty(param[index])) {
                        paramJsonStr = new String(RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(param[index]), rsakey));
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                }
                if (StringUtils.isNotNullOrEmpty(paramJsonStr)){
                    try {
                        paramMap = JSON.parseObject(paramJsonStr,Map.class);
                    }catch (Exception e){
                        paramMap.put(key,new String[]{paramJsonStr});
                    }
                }
                //System.out.println(paramJsonStr);
            }
        }
        return paramMap;
    }

}
