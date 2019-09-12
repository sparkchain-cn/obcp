/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月14日
 */
package cn.obcp.user.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.ObjectError;

import cn.obcp.base.RetData;
import cn.obcp.base.utils.StringUtils;

/**
 * @author lmf
 *
 */
public class VerifyException {


	private String errMsg = "";
	
	public VerifyException(List<ObjectError> errList) {
		// TODO Auto-generated constructor stub
		List<String> errMsgList = new ArrayList<String>(); 
		errList.forEach(s -> {
			errMsgList.add(s.getDefaultMessage()) ;
		});
		errMsg = StringUtils.join(errMsgList, ",");
	}
	
	
	public RetData getErrMsg() {
		return RetData.error(errMsg);
	}
}
