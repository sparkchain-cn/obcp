/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月22日
 */
package cn.obcp.user.VO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.obcp.user.domain.TResources;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.enmu.ResourceType;

/**
 * @author lmf
 *
 */
public class NavsNodeTree {

    private String title; //标题
	
	private String value; //id
	
	private String icon; //图标
	
	private String href;//链接
	
	private boolean spread; //默认选中
	
	 
	private List<NavsNodeTree> children; //子节点

	
	public NavsNodeTree(String title,String icon,String value, String href,boolean spread,List<NavsNodeTree> children) {
		this.title = title;
		this.icon = icon;
		this.href = href;
		this.value = value;
		this.spread = spread;
		this.children = children;
	}
	
	public NavsNodeTree() {
		// TODO Auto-generated constructor stub
	}
	
	public static NavsNodeTree create(String title,String icon,String value, String href,boolean spread,List<NavsNodeTree> children) {
		NavsNodeTree node = new NavsNodeTree();
		node.title = title;
		node.icon = icon;
		node.value = value;
		node.href = href;
		node.spread = spread;
		node.children = children;
		return node;
	}
	
	private static NavsNodeTree addChild(List<TResources> list,NavsNodeTree parent) {
		Iterator<TResources> iterator = list.iterator();
		  while(iterator.hasNext()){	
			  TResources resources = iterator.next();
			  if( String.valueOf(resources.getParentid()).equals(parent.getValue())) {//父级id等资源父级id
				  List<NavsNodeTree> childrens = parent.getChildren();
				 // boolean state = resources.getState().equals(ResourceStateEnmu.DISABLED.getStatus());
				  List<NavsNodeTree> children = new ArrayList<>();
				  childrens.add(NavsNodeTree.create(resources.getAliasName(),resources.getIcon(),String.valueOf( resources.getId()),resources.getPath(), false,children));
				  parent.setChildren(childrens);
			  }		
		  }
		  if(parent.getChildren() != null && parent.getChildren().size() > 0) {
			  Iterator<NavsNodeTree> treeitr =  parent.getChildren().iterator();
			  while(treeitr.hasNext()) {
				  NavsNodeTree node = treeitr.next();
				  addChild(list, node);
			  }
				
		  }else {
			  return parent;
		  }
		return parent;
		  
	}
	
	
	public static  List<NavsNodeTree>  bulid(List<TResources> list) {
		
		List<NavsNodeTree> baseNode = new ArrayList<NavsNodeTree>(); //父节点
		list.forEach( r -> {
			if(r.getParentid() == null && r.getResTypeCode().equalsIgnoreCase(ResourceType.PAGEFOLDER.getType())) { //必须要文件夹佳能作为父级菜单
				//boolean state = r.getState().equals(ResourceStateEnmu.DISABLED.getStatus());
				List<NavsNodeTree> children = new ArrayList<>();
				baseNode.add(NavsNodeTree.create(r.getAliasName(), r.getIcon(), String.valueOf(r.getId()), r.getPath(),false, children));
			}
		});  //得到全部根节点
		
		baseNode.forEach(node ->{
			NavsNodeTree current = addChild(list, node);			
		});
		
		
		return baseNode;
	}
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getHref() {
		return href;
	}


	public void setHref(String href) {
		this.href = href;
	}


	public boolean isSpread() {
		return spread;
	}


	public void setSpread(boolean spread) {
		this.spread = spread;
	}


	public List<NavsNodeTree> getChildren() {
		return children;
	}


	public void setChildren(List<NavsNodeTree> children) {
		this.children = children;
	}
	
	
	
}
