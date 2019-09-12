package cn.obcp.user.util;

import com.alibaba.fastjson.JSON;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.springframework.stereotype.Service;

import java.util.Map;

public class CodeUtils {
	
	private static String yunpianAppKey = "67d3b84d189107099d10ab480e0114b4";

	
	public static String generateValidateCode(Integer length) {
        if (length == null || length <= 0) {
            length = 4;
        }
        int result = (int)((Math.random()*9+1)* Math.pow(10, length-1));
        return String.valueOf(result);
    }
	
	public static String getValidateCodeMessage(String code, int effectiveTime, Integer effectiveUnit) {
		String message = "【火花社区】您好，您的验证码是" + code + "。有效期为" + effectiveTime;
		if (2 == effectiveUnit) {
			message += "小时";
		} else if (3 == effectiveUnit) {
			message += "天";
		} else {
			message += "分钟";
		}
		message += "，请尽快验证 ";
		return message;
	}
	
	public static String getValidateCodeMessage(String code, int effectiveTime) {
		return getValidateCodeMessage(code, effectiveTime, 1);
	}
	
	public static void sendCode(String phone, String text) {
		YunpianClient clnt = new YunpianClient(yunpianAppKey).init();

		//发送短信API
		Map<String, String> param = clnt.newParam(2);
		param.put(YunpianClient.MOBILE, phone);
		param.put(YunpianClient.TEXT, text);
		try{
			Result<SmsSingleSend> r = clnt.sms().single_send(param);
			System.out.println("--------------短信返回码："+JSON.toJSON(r));
		}catch (Exception e){
			e.printStackTrace();
		}
		//获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()

		//账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*

		//释放clnt
		clnt.close();
	}
	
	
}