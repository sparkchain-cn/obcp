package cn.obcp.main.utils;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import cn.obcp.base.utils.StringUtils;
import cn.obcp.user.VO.NavsNodeTree;

public class TreeUtil {
	
	//接口返回的树节点数据转换为JSONArray格式
	public static JSONArray NavsToArray(List<NavsNodeTree> list) {
		JSONArray array = new JSONArray();
		//去除有接口访问权限，无页面访问权限一级菜单
		List<NavsNodeTree> nodeList = Lists.newArrayList();
		for(NavsNodeTree node:list) {
			if(null != node.getChildren() && node.getChildren().size() > 0) {
				nodeList.add(node);
			}
		}
		//递归算法
		recursive(nodeList,array);
		//System.out.println("array="+array);
		return array;
	}
	
	//递归算法
	private static void recursive(List<NavsNodeTree> list,JSONArray array) {
		if(list!=null&&list.size()>0) {
			//循环节点
		
			for(NavsNodeTree node : list) {
				//如果icon不存在，给个默认
				if(StringUtils.isNullOrEmpty(node.getIcon())) {
					node.setIcon("&#xe609;");
				}
				//开始
				JSONObject obj = new JSONObject();
				obj.put("title", node.getTitle());
				obj.put("icon", node.getIcon());
				obj.put("href", node.getHref());
				obj.put("spread", false);
				//节点增加
				array.add(obj);
				//System.out.println("node="+obj);
				//展开子节点,递归调用
				if(node.getChildren().size()>0) {
					JSONArray children = new JSONArray();
					obj.put("children", children);
					recursive(node.getChildren(),children);
				}
			}
		}
	}

}
