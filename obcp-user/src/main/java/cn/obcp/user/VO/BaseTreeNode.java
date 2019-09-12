package cn.obcp.user.VO;

import java.util.List;

public abstract class BaseTreeNode<T,K> {
    private  String id;
    private  String name;
    private  String pId;
    private boolean checked;
    private boolean disabled;
    private  boolean drag;//拖拽

    private List<K> children;


    public BaseTreeNode() {
        // TODO Auto-generated constructor stub
    }

    public BaseTreeNode (String id, String name,String pid, boolean checked,boolean disabled, List<K> children) {
        if (children.size() == 0){
            children = null;
        }
        this.id = id;
        this.name = name;
        this.pId = pid;
        this.checked = checked;
        this.children = children;
        this.disabled = disabled;
        this.drag = true;//允许拖拽
    }

    public  abstract K addChild(List<T> list,K parent);


    public abstract   List<K>  bulid(List<T> list);



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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<K> getChildren() {
        return children;
    }

    public void setChildren(List<K> children) {
        this.children = children;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

}
