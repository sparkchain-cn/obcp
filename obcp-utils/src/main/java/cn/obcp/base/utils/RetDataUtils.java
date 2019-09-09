package cn.obcp.base.utils;

import java.util.Map;

import cn.obcp.base.LayUiRetData;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;

public class RetDataUtils {

	public static String getCode(Map<String, ?> map) {
		if (map == null)
			return null;
		return (String) map.get("code");
	}

	public static boolean isSucc(Map<String, ?> map) {
		if (map == null)
			return false;
		return (Boolean) map.get("success");
	}

	public static Map<String, ?> getData(Map<String, ?> map) {
		if (map == null)
			return null;
		return (Map<String, ?>) map.get("data");
	}

	public static Object getDataValue(Map<String, ?> map, String datakey) {
		if (map == null || map.get("data") == null) {
			return null;
		}
		Map<String, ?> data = (Map<String, ?>) map.get("data");
		return data.get(datakey);
	}

	/**
	 * retData 转换成通用类型的 layui 列表数据
	 *
	 * @param retData
	 * @return
	 */
	public static LayUiRetData retDataToListLayUiRetData(RetData retData) {
		if (!retData.isSuccess()) {
			return LayUiRetData.error(retData.getCode(), retData.getMessage());
		}
		if (retData.getData() instanceof Page) {
			Page p = (Page) retData.getData();
			return LayUiRetData.success(p.getResult(), p.getTotal());
		} else {
			Object data = retData.getData();
			return LayUiRetData.success(data, null == data ? 0l : 1l);
		}
	}
}
