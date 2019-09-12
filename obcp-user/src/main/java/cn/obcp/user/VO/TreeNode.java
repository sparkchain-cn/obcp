/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月14日
 */
package cn.obcp.user.VO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.obcp.user.domain.TResources;
import cn.obcp.user.enmu.ResourceStateEnmu;

/**
 * @author lmf
 *
 */
public class TreeNode {
	

	private String title; //标题
	
	private String value; //值
	
	private boolean checked; //默认选中
	
	private boolean disabled; //默认禁止
	 
	private List<TreeNode> data; //子节点

	public TreeNode(String title,String value, boolean checked,boolean disabled,List<TreeNode> data) {
		this.title = title;
		this.value = value;
		this.checked = checked;
		this.disabled = disabled;
		this.data = data;
	}
	
	/**
	 * 
	 */
	public TreeNode() {
		// TODO Auto-generated constructor stub
	}

	/**
	 *  创建
	 * TODO
	 * @param title
	 * @param value
	 * @param checked
	 * @param disabled
	 * @param data
	 * @return
	 * TreeNode
	 * lmf 创建于2018年11月14日
	 */
	public static TreeNode create(String title,String value, boolean checked,boolean disabled,List<TreeNode> data) {
		TreeNode node = new TreeNode();
		node.title = title;
		node.value = value;
		node.checked = checked;
		node.disabled = disabled;
		node.data = data;
		return node;
	}
	
	
	/**
	 *  构建树形
	 * TODO
	 * @param list
	 * @param parent
	 * @return
	 * TreeNode
	 * lmf 创建于2018年11月15日
	 */
	private static TreeNode addChild(List<TResources> list,TreeNode parent) {
		Iterator<TResources> iterator = list.iterator();
		  while(iterator.hasNext()){	
			  TResources resources = iterator.next();
			  if( String.valueOf(resources.getParentid()).equals(parent.getValue())) {//父级id等资源父级id
				  List<TreeNode> childrens = parent.getData();
				  boolean state = resources.getState().equals(ResourceStateEnmu.DISABLED.getStatus());
				  List<TreeNode> children = new ArrayList<>();
				  childrens.add(TreeNode.create(resources.getName(),String.valueOf( resources.getId()), resources.isChecked(), state,children  ));
				  parent.setData(childrens);
			  }		
		  }
		  if(parent.getData() != null && parent.getData().size() > 0) {
			  Iterator<TreeNode> treeitr =  parent.getData().iterator();
			  while(treeitr.hasNext()) {
				  TreeNode node = treeitr.next();
				  addChild(list, node);
			  }
				
		  }else {
			  return parent;
		  }
		return parent;
		  
	}
	
	public static  List<TreeNode>  bulid(List<TResources> list) {
		
		List<TreeNode> baseNode = new ArrayList<TreeNode>(); //父节点
		list.forEach( r -> {
			if(r.getParentid() == null) {
				boolean state = r.getState().equals(ResourceStateEnmu.DISABLED.getStatus());
				List<TreeNode> children = new ArrayList<>();
				baseNode.add(TreeNode.create(r.getName(), String.valueOf(r.getId()), r.isChecked(),state ,children ));
			}
		});  //得到全部根节点
		
		baseNode.forEach(node ->{
			TreeNode current = addChild(list, node);			
		});
		
		
		return baseNode;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<TreeNode> getData() {
		return data;
	}

	public void setData(List<TreeNode> data) {
		this.data = data;
	}

	
	
}
