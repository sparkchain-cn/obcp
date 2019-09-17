package cn.obcp.dict.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.obcp.base.utils.StringUtils;
import cn.obcp.dict.domain.TDict;

public class SelectNode {

	private String id;

	private String name;

	private Boolean open;

	private List<SelectNode> children;

	public static SelectNode create(String id, String name, boolean open, List<SelectNode> children) {
		SelectNode node = new SelectNode();
		node.id = id;
		node.name = name;
		node.open = open;
		node.children = children;
		return node;
	}

	private static SelectNode addChild(List<TDict> list, SelectNode parent) {
		Iterator<TDict> iterator = list.iterator();
		while (iterator.hasNext()) {
			TDict resources = iterator.next();
			if (parent.getId().equals(resources.getParent())) {// 父级id等资源父级id
				List<SelectNode> childrens = parent.getChildren();
				List<SelectNode> children = new ArrayList<>();
				childrens.add(SelectNode.create(resources.getCode(), resources.getName(), true, children));
				parent.setChildren(childrens);
			}
		}
		if (parent.getChildren() != null && parent.getChildren().size() > 0) {
			Iterator<SelectNode> treeitr = parent.getChildren().iterator();
			while (treeitr.hasNext()) {
				SelectNode node = treeitr.next();
				addChild(list, node);
			}

		} else {
			return parent;
		}
		return parent;

	}

	public static List<SelectNode> bulid(List<TDict> list) {

		List<SelectNode> baseNode = new ArrayList<SelectNode>(); // 父节点
		list.forEach(r -> {
			if (StringUtils.isNullOrEmpty(r.getParent())) {
				List<SelectNode> children = new ArrayList<>();
				baseNode.add(SelectNode.create(r.getCode(), r.getName(), true, children));
			}
		}); // 得到全部根节点

		baseNode.forEach(node -> {
			addChild(list, node);
		});

		return baseNode;
	}

	// get set
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<SelectNode> getChildren() {
		return children;
	}

	public void setChildren(List<SelectNode> children) {
		this.children = children;
	}

}
