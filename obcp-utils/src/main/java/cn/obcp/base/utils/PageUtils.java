package cn.obcp.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.obcp.base.Page;
import cn.obcp.base.vo.KeyValue;

public class PageUtils {

	public static Page getPageInfo(Map<String, ?> params) {

		// int pageNum = 1, size = 10;
		// if (params.containsKey("pagenum")) {
		// pageNum = Integer.parseInt((String) params.get("pagenum"));
		// params.remove("pagenum");
		// }
		//
		// if (params.containsKey("pagesize")) {
		// size = Integer.parseInt((String) params.get("pagesize"));
		// params.remove("pagesize");
		// }
		//
		// Page page = new Page(pageNum, size);
		// page.setMeta(params);
		//
		// return page;

		return getPageInfo(params, null);

	}

	public static Page getPageInfo(Map<String, ?> params, List<KeyValue> orders) {
		if (params == null) {
			params = new HashMap<>();
		}

		int pageNum = 1, size = 10;
		if (params.containsKey("pagenum")) {
			pageNum = Integer.parseInt((String) params.get("pagenum"));
			params.remove("pagenum");
		}

		if (params.containsKey("pagesize")) {
			size = Integer.parseInt((String) params.get("pagesize"));
			params.remove("pagesize");
		}

		Page page = new Page(pageNum, size);
		page.setMeta(params);
		page.setOrders(orders);

		return page;

	}

}
